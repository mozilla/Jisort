package com.mozilla.hackathon.kiboko.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mozilla.hackathon.kiboko.models.Question;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.provider.DsoContractHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGW;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.makeLogTag;

public class QuizHandler extends JSONHandler {
    private static final String TAG = makeLogTag(QuizHandler.class);
    private HashMap<String, Question> mQuiz = new HashMap<String, Question>();

    public QuizHandler(Context context) {
        super(context);
    }

    @Override
    public void process(JsonElement element) {
        for (Question quiz : new Gson().fromJson(element, Question[].class)) {
            mQuiz.put(quiz.id, quiz);
        }
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        Uri uri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Quizes.CONTENT_URI);
        // build a map of tutorial to tutorial import hashcode so we know what to update,
        // what to insert, and what to delete
        HashMap<String, String> tutorialHashCodes = loadQuizHashCodes();
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
        for (Question question : mQuiz.values()) {
            // Set the tutorial grouping order in the object, so it can be used in hash calculation
            question.groupingOrder = computeTypeOrder(question);
            // compute the incoming tutorial's hashcode to figure out if we need to update
            String hashCode = question.getID();
            tutorialsToKeep.add(question.id);
            // add tutorial, if necessary
            if (!incrementalUpdate || !tutorialHashCodes.containsKey(question.id) ||
                    !tutorialHashCodes.get(question.id).equals(hashCode)) {
                ++updatedtutorials;
                boolean isNew = !incrementalUpdate || !tutorialHashCodes.containsKey(question.id);
                buildQuiz(isNew, question, list);
            }
        }

        int deletedquizes = 0;
        if (incrementalUpdate) {
            for (String tutorialId : tutorialHashCodes.keySet()) {
                if (!tutorialsToKeep.contains(tutorialId)) {
                    buildDeleteOperation(tutorialId, list);
                    ++deletedquizes;
                }
            }
        }

        LOGD(TAG, "quizes: " + (incrementalUpdate ? "INCREMENTAL" : "FULL") + " update. " +
                updatedtutorials + " to update, " + deletedquizes + " to delete. New total: " +
                mQuiz.size());
    }

    private void buildDeleteOperation(String quizId, List<ContentProviderOperation> list) {
        Uri quizUri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Quizes.buildQuizUri(quizId));
        list.add(ContentProviderOperation.newDelete(quizUri).build());
    }

    private HashMap<String, String> loadQuizHashCodes() {
        Uri uri = DsoContractHelper.setUriAsCalledFromSyncAdapter(
                DsoContract.Quizes.CONTENT_URI);
        LOGD(TAG, "Loading quiz hashcodes for quizes import optimization.");
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, quizHashcodeQuery.PROJECTION,
                    null, null, null);
            if (cursor == null || cursor.getCount() < 1) {
                LOGW(TAG, "Warning: failed to load quiz hashcodes. Not optimizing tutorial import.");
                return null;
            }
            HashMap<String, String> hashcodeMap = new HashMap<String, String>();
            if (cursor.moveToFirst()) {
                do {
                    String quizId = cursor.getString(quizHashcodeQuery.KEY_ID);
                    String hashcode = cursor.getString(quizHashcodeQuery.KEY_IMPORT_HASHCODE);
                    hashcodeMap.put(quizId, hashcode == null ? "" : hashcode);
                } while (cursor.moveToNext());
            }
            LOGD(TAG, "quiz hashcodes loaded for " + hashcodeMap.size() + " quizes.");
            return hashcodeMap;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    StringBuilder mStringBuilder = new StringBuilder();

    private void buildQuiz(boolean isInsert,
                           Question question, ArrayList<ContentProviderOperation> list) {
        ContentProviderOperation.Builder builder;
        Uri allQuizsUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(DsoContract.Quizes.CONTENT_URI);
        Uri thisQuizUri = DsoContractHelper
                .setUriAsCalledFromSyncAdapter(DsoContract.Quizes.buildQuizUri(
                        question.id));
        if (isInsert) {
            builder = ContentProviderOperation.newInsert(allQuizsUri);
        } else {
            builder = ContentProviderOperation.newUpdate(thisQuizUri);
        }
        builder.withValue(DsoContract.SyncColumns.UPDATED, System.currentTimeMillis())
                .withValue(DsoContract.Quizes.KEY_ID, question.id)
                .withValue(DsoContract.Quizes.KEY_QUESTION, question.question)
                .withValue(DsoContract.Quizes.KEY_ANSWER, question.answer)
                .withValue(DsoContract.Quizes.KEY_OPTIONA, question.optiona)
                .withValue(DsoContract.Quizes.KEY_OPTIONB, question.optionb)
                .withValue(DsoContract.Quizes.KEY_OPTIONC, question.optionc)
                .withValue(DsoContract.Quizes.KEY_OPTIOND, question.optiond)
                .withValue(DsoContract.Quizes.KEY_IMPORT_HASHCODE,
                        question.getImportHashCode());
        list.add(builder.build());
    }

    // The type order of a tutorial is the order# (in its category) of the tag that indicates
    // its type. So if we sort tutorials by type order, they will be neatly grouped by type,
    // with the types appearing in the order given by the tag category that represents the
    // concept of tutorial type.
    private int computeTypeOrder(Question tutorial) {
        int order = Integer.MAX_VALUE;
        int quizOrder = -1;
        return order;
    }

    private interface quizHashcodeQuery {
        String[] PROJECTION = {
                BaseColumns._ID,
                DsoContract.Quizes.KEY_ID,
                DsoContract.Quizes.KEY_IMPORT_HASHCODE
        };
        int _ID = 0;
        int KEY_ID = 1;
        int KEY_IMPORT_HASHCODE = 2;
    }
}
