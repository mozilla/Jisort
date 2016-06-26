package com.mozilla.hackathon.kiboko.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mozilla.hackathon.kiboko.models.Tutorial;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.provider.DsoContractHelper;
import com.mozilla.hackathon.kiboko.provider.DsoDatabase;
import com.mozilla.hackathon.kiboko.utilities.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGW;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Created by Audrey on 25/06/2016.
 */
public class TutorialsHandler  extends JSONHandler {
    private static final String TAG = makeLogTag(TutorialsHandler.class);
    private HashMap<String, Tutorial> mTutorials = new HashMap<String, Tutorial>();


    public TutorialsHandler(Context context) {
        super(context);

    }

    @Override
    public void process(JsonElement element) {
        for (Tutorial tutorial : new Gson().fromJson(element, Tutorial[].class)) {
            mTutorials.put(tutorial.id, tutorial);
        }
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        Uri uri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Tutorials.CONTENT_URI);

        // build a map of tutorial to tutorial import hashcode so we know what to update,
        // what to insert, and what to delete
        HashMap<String, String> tutorialHashCodes = loadtutorialHashCodes();
        boolean incrementalUpdate = (tutorialHashCodes != null) && (tutorialHashCodes.size() > 0);

        // set of tutorials that we want to keep after the sync
        HashSet<String> tutorialsToKeep = new HashSet<String>();

        if (incrementalUpdate) {
            LOGD(TAG, "Doing incremental update for tutorials.");
        } else {
            LOGD(TAG, "Doing full (non-incremental) update for tutorials.");
            list.add(ContentProviderOperation.newDelete(uri).build());
        }

        int updatedtutorials = 0;
        for (Tutorial tutorial : mTutorials.values()) {
            // Set the tutorial grouping order in the object, so it can be used in hash calculation
            tutorial.groupingOrder = computeTypeOrder(tutorial);

            // compute the incoming tutorial's hashcode to figure out if we need to update
            String hashCode = tutorial.getImportHashCode();
            tutorialsToKeep.add(tutorial.id);

            // add tutorial, if necessary
            if (!incrementalUpdate || !tutorialHashCodes.containsKey(tutorial.id) ||
                    !tutorialHashCodes.get(tutorial.id).equals(hashCode)) {
                ++updatedtutorials;
                boolean isNew = !incrementalUpdate || !tutorialHashCodes.containsKey(tutorial.id);
                buildtutorial(isNew, tutorial, list);

                // add relationships to speakers and track
//                buildtutorialSpeakerMapping(tutorial, list);
//                buildTagsMapping(tutorial, list);
            }
        }

        int deletedtutorials = 0;
        if (incrementalUpdate) {
            for (String tutorialId : tutorialHashCodes.keySet()) {
                if (!tutorialsToKeep.contains(tutorialId)) {
                    buildDeleteOperation(tutorialId, list);
                    ++deletedtutorials;
                }
            }
        }

        LOGD(TAG, "tutorials: " + (incrementalUpdate ? "INCREMENTAL" : "FULL") + " update. " +
                updatedtutorials + " to update, " + deletedtutorials + " to delete. New total: " +
                mTutorials.size());
    }

    private void buildDeleteOperation(String tutorialId, List<ContentProviderOperation> list) {
        Uri tutorialUri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Tutorials.buildTutorialUri(tutorialId));
        list.add(ContentProviderOperation.newDelete(tutorialUri).build());
    }

    private HashMap<String, String> loadtutorialHashCodes() {
        Uri uri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Tutorials.CONTENT_URI);
        LOGD(TAG, "Loading tutorial hashcodes for tutorial import optimization.");
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, tutorialHashcodeQuery.PROJECTION,
                    null, null, null);
            if (cursor == null || cursor.getCount() < 1) {
                LOGW(TAG, "Warning: failed to load tutorial hashcodes. Not optimizing tutorial import.");
                return null;
            }
            HashMap<String, String> hashcodeMap = new HashMap<String, String>();
            if (cursor.moveToFirst()) {
                do {
                    String tutorialId = cursor.getString(tutorialHashcodeQuery.Tutorial_ID);
                    String hashcode = cursor.getString(tutorialHashcodeQuery.Tutorial_IMPORT_HASHCODE);
                    hashcodeMap.put(tutorialId, hashcode == null ? "" : hashcode);
                } while (cursor.moveToNext());
            }
            LOGD(TAG, "tutorial hashcodes loaded for " + hashcodeMap.size() + " tutorials.");
            return hashcodeMap;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    StringBuilder mStringBuilder = new StringBuilder();

    private void buildtutorial(boolean isInsert,
                              Tutorial tutorial, ArrayList<ContentProviderOperation> list) {
        ContentProviderOperation.Builder builder;
        Uri alltutorialsUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(DsoContract.Tutorials.CONTENT_URI);
        Uri thistutorialUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(DsoContract.Tutorials.buildtutorialUri(
                        tutorial.id));

        if (isInsert) {
            builder = ContentProviderOperation.newInsert(alltutorialsUri);
        } else {
            builder = ContentProviderOperation.newUpdate(thistutorialUri);
        }

//        String speakerNames = "";
//        if (mSpeakerMap != null) {
//            // build human-readable list of speakers
//            mStringBuilder.setLength(0);
//            for (int i = 0; i < tutorial.speakers.length; ++i) {
//                if (mSpeakerMap.containsKey(tutorial.speakers[i])) {
//                    mStringBuilder
//                            .append(i == 0 ? "" : i == tutorial.speakers.length - 1 ? " and " : ", ")
//                            .append(mSpeakerMap.get(tutorial.speakers[i]).name.trim());
//                } else {
//                    LOGW(TAG, "Unknown speaker ID " + tutorial.speakers[i] + " in tutorial " + tutorial.id);
//                }
//            }
//            speakerNames = mStringBuilder.toString();
//        } else {
//            LOGE(TAG, "Can't build speaker names -- speaker map is null.");
//        }

//        int color = mDefaulttutorialColor;
//        try {
//            if (!TextUtils.isEmpty(tutorial.color)) {
//                color = Color.parseColor(tutorial.color);
//            }
//        } catch (IllegalArgumentException ex) {
//            LOGD(TAG, "Ignoring invalid formatted tutorial color: "+tutorial.color);
//        }

        builder.withValue(DsoContract.SyncColumns.UPDATED, System.currentTimeMillis())
                .withValue(DsoContract.Tutorials.Tutorial_ID, tutorial.id)
                .withValue(DsoContract.Tutorials.Tutorial_LEVEL, null)            // Not available
                .withValue(DsoContract.Tutorials.Tutorial_TITLE, tutorial.title)
                .withValue(DsoContract.Tutorials.Tutorial_ABSTRACT, tutorial.description)
                .withValue(DsoContract.Tutorials.Tutorial_HASHTAG, tutorial.hashtag)

                // Note: we store this comma-separated list of tags IN ADDITION
                // to storing the tags in proper relational format (in the tutorials_tags
                // relationship table). This is because when querying for tutorials,
                // we don't want to incur the performance penalty of having to do a
                // subquery for every record to figure out the list of tags of each tutorial.
                // Note: we store the human-readable list of speakers (which is redundant
                // with the tutorials_speakers relationship table) so that we can
                // display it easily in lists without having to make an additional DB query
                // (or another join) for each record.
                .withValue(DsoContract.Tutorials.Tutorial_KEYWORDS, null)             // Not available
                .withValue(DsoContract.Tutorials.Tutorial_URL, tutorial.url)
                .withValue(DsoContract.Tutorials.Tutorial_LIVESTREAM_ID,
                        tutorial.isLivestream ? tutorial.youtubeUrl : null)
                .withValue(DsoContract.Tutorials.Tutorial_MODERATOR_URL, null)    // Not available
                .withValue(DsoContract.Tutorials.Tutorial_REQUIREMENTS, null)     // Not available
                .withValue(DsoContract.Tutorials.Tutorial_YOUTUBE_URL,
                        tutorial.isLivestream ? null : tutorial.youtubeUrl)
                .withValue(DsoContract.Tutorials.Tutorial_PDF_URL, null)          // Not available
                .withValue(DsoContract.Tutorials.Tutorial_NOTES_URL, null)        // Not available
                .withValue(DsoContract.Tutorials.ROOM_ID, tutorial.room)
                .withValue(DsoContract.Tutorials.Tutorial_GROUPING_ORDER, tutorial.groupingOrder)
                .withValue(DsoContract.Tutorials.Tutorial_IMPORT_HASHCODE,
                        tutorial.getImportHashCode())
                .withValue(DsoContract.Tutorials.Tutorial_MAIN_TAG, tutorial.mainTag)
                .withValue(DsoContract.Tutorials.Tutorial_CAPTIONS_URL, tutorial.captionsUrl)
                .withValue(DsoContract.Tutorials.Tutorial_PHOTO_URL, tutorial.photoUrl)
                // Disabled since this isn't being used by this app.
                // .withValue(DsoContract.Tutorials.Tutorial_RELATED_CONTENT, tutorial.relatedContent)
                .withValue(DsoContract.Tutorials.Tutorial_COLOR, color);
        list.add(builder.build());
    }

    // The type order of a tutorial is the order# (in its category) of the tag that indicates
    // its type. So if we sort tutorials by type order, they will be neatly grouped by type,
    // with the types appearing in the order given by the tag category that represents the
    // concept of tutorial type.
    private int computeTypeOrder(Tutorial tutorial) {
        int order = Integer.MAX_VALUE;
        int keynoteOrder = -1;

        return order;
    }

    private interface tutorialHashcodeQuery {
        String[] PROJECTION = {
                BaseColumns._ID,
                DsoContract.Tutorials.Tutorial_ID,
                DsoContract.Tutorials.Tutorial_IMPORT_HASHCODE
        };
        int _ID = 0;
        int tutorial_ID = 1;
        int tutorial_IMPORT_HASHCODE = 2;
    };
}
