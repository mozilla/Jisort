package com.mozilla.hackathon.kiboko.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mozilla.hackathon.kiboko.models.Tutorial;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.provider.DsoContract.Tutorials;
import com.mozilla.hackathon.kiboko.provider.DsoContractHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGW;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Created by Brian Mwadime on 25/06/2016.
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
        HashMap<String, String> tutorialHashCodes = loadTutorialHashCodes();
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
                buildTutorial(isNew, tutorial, list);
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

    private HashMap<String, String> loadTutorialHashCodes() {
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
                    String tutorialId = cursor.getString(tutorialHashcodeQuery.TUTORIAL_ID);
                    String hashcode = cursor.getString(tutorialHashcodeQuery.TUTORIAL_IMPORT_HASHCODE);
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

    private void buildTutorial(boolean isInsert,
                              Tutorial tutorial, ArrayList<ContentProviderOperation> list) {
        ContentProviderOperation.Builder builder;
        Uri alltutorialsUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(Tutorials.CONTENT_URI);
        Uri thistutorialUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(Tutorials.buildTutorialUri(
                        tutorial.id));

        if (isInsert) {
            builder = ContentProviderOperation.newInsert(alltutorialsUri);
        } else {
            builder = ContentProviderOperation.newUpdate(thistutorialUri);
        }

        builder.withValue(DsoContract.SyncColumns.UPDATED, System.currentTimeMillis())
                .withValue(Tutorials.TUTORIAL_ID, tutorial.id)
                .withValue(Tutorials.TUTORIAL_TAG, tutorial.tag)
                .withValue(Tutorials.TUTORIAL_HEADER, tutorial.header)
                .withValue(Tutorials.TUTORIAL_IMPORT_HASHCODE,
                        tutorial.getImportHashCode())
                // Note: we store this comma-separated list of tags IN ADDITION
                // to storing the tags in proper relational format (in the tutorials_tags
                // relationship table). This is because when querying for tutorials,
                // we don't want to incur the performance penalty of having to do a
                // subquery for every record to figure out the list of tags of each tutorial.
                // Note: we store the human-readable list of speakers (which is redundant
                // with the tutorials_speakers relationship table) so that we can
                // display it easily in lists without having to make an additional DB query
                // (or another join) for each record.
                 .withValue(Tutorials.TUTORIAL_STEPS, new Gson().toJson(tutorial.steps))
                .withValue(DsoContract.Tutorials.TUTORIAL_PHOTO_URL, tutorial.photoUrl);
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
                Tutorials.TUTORIAL_ID,
                Tutorials.TUTORIAL_IMPORT_HASHCODE
        };
        int _ID = 0;
        int TUTORIAL_ID = 1;
        int TUTORIAL_IMPORT_HASHCODE = 2;
    };
}
