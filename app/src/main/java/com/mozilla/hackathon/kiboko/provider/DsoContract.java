package com.mozilla.hackathon.kiboko.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link DsoProvider}. Unless otherwise noted, all
 * time-based fields are milliseconds since epoch and can be compared against
 * {@link System#currentTimeMillis()}.
 * <p>
 * The backing {@link android.content.ContentProvider} assumes that {@link android.net.Uri}
 * are generated using stronger {@link java.lang.String} identifiers, instead of
 * {@code int} {@link android.provider.BaseColumns#_ID} values, which are prone to shuffle during
 * sync.
 */
public final class DsoContract {

    public static final String CONTENT_TYPE_APP_BASE = "mozilladso2016.";

    public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd."
            + CONTENT_TYPE_APP_BASE;

    public interface SyncColumns {
        /**
         * Last time this entry was updated or synchronized.
         */
        String UPDATED = "updated";
    }

    interface TutorialsColumns {
        /**
         * Unique string identifying this tutorial.
         */
        String TUTORIAL_ID = "tutorial_id";
        /**
         * Tutorial header.
         */
        String TUTORIAL_HEADER = "tutorial_header";
        String TUTORIAL_TAG = "tutorial_tag";
        String TUTORIAL_PHOTO_URL = "tutorial_photo_url";
        /**
         * The Tutorials's steps.
         */
        String TUTORIAL_STEPS = "tutorial_steps";
        /**
         * The hashcode of the data used to create this record.
         */
        String TUTORIAL_IMPORT_HASHCODE = "tutorial_import_hashcode";
    }

    interface QuizColumns {
        String KEY_ID = "id";
        String KEY_QUESTION = "question";
        String KEY_ANSWER = "answer"; //correct option
        String KEY_OPTIONA = "optiona"; //option a
        String KEY_OPTIONB = "optionb"; //option b
        String KEY_OPTIONC = "optionc"; //option c
        String KEY_OPTIOND = "optiond"; //option d
        /**
         * The hashcode of the data used to create this record.
         */
        String KEY_IMPORT_HASHCODE = "quiz_import_hashcode";
    }

    public static final String CONTENT_AUTHORITY = "com.mozilla.hackathon.kiboko";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_TUTORIALS = "tutorials";
    private static final String PATH_QUIZ = "quizes";
    private static final String PATH_ACTIONS = "actions";

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_TUTORIALS,
            PATH_QUIZ,
            PATH_ACTIONS
    };

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    /**
     * Each tutorial has zero or more
     */
    public static class Tutorials implements TutorialsColumns,
            SyncColumns, BaseColumns {

        public static final String QUERY_PARAMETER_TAG_FILTER = "filter";
        public static final String QUERY_PARAMETER_CATEGORIES = "categories";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TUTORIALS).build();

        public static final String CONTENT_TYPE_ID = "tutorial";

        // Builds selectionArgs for {@link STARTING_AT_TIME_INTERVAL_SELECTION}
        public static String[] buildAtTimeIntervalArgs(long intervalStart, long intervalEnd) {
            return new String[]{String.valueOf(intervalStart), String.valueOf(intervalEnd)};
        }

        /**
         * Build {@link Uri} for requested {@link #TUTORIAL_ID}.
         */
        public static Uri buildTutorialUri(String tutorialId) {
            return CONTENT_URI.buildUpon().appendPath(tutorialId).build();
        }

        /**
         * Read {@link #TUTORIAL_ID} from {@link Tutorials} {@link Uri}.
         */
        public static String getTutorialId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Quizes implements QuizColumns,
            SyncColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUIZ).build();

        public static final String CONTENT_TYPE_ID = "quiz";

        public static String[] buildAtTimeIntervalArgs(long intervalStart, long intervalEnd) {
            return new String[]{String.valueOf(intervalStart), String.valueOf(intervalEnd)};
        }

        /**
         * Build {@link Uri} for requested {@link #KEY_ID}.
         */
        public static Uri buildQuizUri(String keyId) {
            return CONTENT_URI.buildUpon().appendPath(keyId).build();
        }

        /**
         * Read {@link #KEY_ID} from {@link Quizes} {@link Uri}.
         */
        public static String getQuizId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Actions implements SyncColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIONS).build();
        public static final String CONTENT_TYPE_ID = "action";
    }

    private DsoContract() {
    }
}
