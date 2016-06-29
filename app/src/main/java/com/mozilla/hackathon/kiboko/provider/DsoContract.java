package com.mozilla.hackathon.kiboko.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

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

        /** Last time this entry was updated or synchronized. */
        String UPDATED = "updated";
    }

    interface TutorialsColumns {

        /** Unique string identifying this tutorial. */
        String TUTORIAL_ID = "tutorial_id";
        /** Tutorial header. */
        String TUTORIAL_HEADER = "tutorial_header";

        String TUTORIAL_TAG = "tutorial_tag";

        String TUTORIAL_PHOTO_URL = "tutorial_photo_url";
        /** The Tutorials's steps. */
        String TUTORIAL_STEPS = "tutorial_steps";
        /** The hashcode of the data used to create this record. */
        String TUTORIAL_IMPORT_HASHCODE = "tutorial_import_hashcode";
    }

    public static final String CONTENT_AUTHORITY = "com.mozilla.hackathon.kiboko";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    

    private static final String PATH_TAGS = "tags";

    private static final String PATH_TUTORIALS = "tutorials";

    public static final String[] TOP_LEVEL_PATHS = {
            //PATH_TAGS,
            PATH_TUTORIALS,
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

        // ORDER BY clauses
//        public static final String SORT_BY_TYPE_THEN_TIME = TUTORIAL_GROUPING_ORDER + " ASC,"
//                + TUTORIAL_START + " ASC," + TUTORIAL_TITLE + " COLLATE NOCASE ASC";
//
//        public static final String LIVESTREAM_SELECTION =
//                TUTORIAL_LIVESTREAM_ID + " is not null AND " + TUTORIAL_LIVESTREAM_ID + "!=''";
//
//        public static final String LIVESTREAM_OR_YOUTUBE_URL_SELECTION = "(" +
//                TUTORIAL_LIVESTREAM_ID + " is not null AND " + TUTORIAL_LIVESTREAM_ID +
//                "!='') OR (" +
//                TUTORIAL_YOUTUBE_URL + " is not null AND " + TUTORIAL_YOUTUBE_URL + " != '')";

        // Builds selectionArgs for {@link STARTING_AT_TIME_INTERVAL_SELECTION}
        public static String[] buildAtTimeIntervalArgs(long intervalStart, long intervalEnd) {
            return new String[]{String.valueOf(intervalStart), String.valueOf(intervalEnd)};
        }
        


        /** Build {@link Uri} for requested {@link #TUTORIAL_ID}. */
        public static Uri buildTutorialUri(String tutorialId) {
            return CONTENT_URI.buildUpon().appendPath(tutorialId).build();
        }

        /**
         * Build {@link Uri} that references tutorials that match the query. The query can be
         * multiple words separated with spaces.
         *
         * @param query The query. Can be multiple words separated by spaces.
         * @return {@link Uri} to the tutorials
         */
//        public static Uri buildSearchUri(String query) {
//            if (null == query) {
//                query = "";
//            }
//            // convert "lorem ipsum dolor sit" to "lorem* ipsum* dolor* sit*"
//            query = query.replaceAll(" +", " *") + "*";
//            return CONTENT_URI.buildUpon()
//                    .appendPath(PATH_SEARCH).appendPath(query).build();
//        }
//
//        public static boolean isSearchUri(Uri uri) {
//            List<String> pathSegments = uri.getPathSegments();
//            return pathSegments.size() >= 2 && PATH_SEARCH.equals(pathSegments.get(1));
//        }

//        public static long[] getInterval(Uri uri) {
//            if (uri == null) {
//                return null;
//            }
//            List<String> segments = uri.getPathSegments();
//            if (segments.size() == 3 && segments.get(2).indexOf('-') > 0) {
//                String[] interval = segments.get(2).split("-");
//                return new long[]{Long.parseLong(interval[0]), Long.parseLong(interval[1])};
//            }
//            return null;
//        }

        /** Read {@link #TUTORIAL_ID} from {@link Tutorials} {@link Uri}. */
        public static String getTutorialId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

//        public static String getSearchQuery(Uri uri) {
//            List<String> segments = uri.getPathSegments();
//            if (2 < segments.size()) {
//                return segments.get(2);
//            }
//            return null;
//        }

//        public static boolean hasFilterParam(Uri uri) {
//            return uri != null && uri.getQueryParameter(QUERY_PARAMETER_TAG_FILTER) != null;
//        }

        /**
         * Build {@link Uri} that references all tutorials that have ALL of the indicated tags.
         * @param contentUri The base Uri that is used for adding the required tags.
         * @param requiredTags The tags that are used for creating the query parameter.
         * @return uri The uri updated to include the indicated tags.
         */
        @Deprecated
        public static Uri buildTagFilterUri(Uri contentUri, String[] requiredTags) {
            return buildCategoryTagFilterUri(contentUri, requiredTags,
                    requiredTags == null ? 0 : requiredTags.length);
        }

        /** Build {@link Uri} that references all tutorials that have ALL of the indicated tags. */
        @Deprecated
        public static Uri buildTagFilterUri(String[] requiredTags) {
            return buildTagFilterUri(CONTENT_URI, requiredTags);
        }

        /**
         * Build {@link Uri} that references all tutorials that have the following tags and
         * satisfy the requirement of containing ALL the categories
         * @param contentUri The base Uri that is used for adding the query parameters.
         * @param tags The various tags that can include topics, themes as well as types.
         * @param categories The number of categories that are required. At most this can be 3,
         *                   since a tutorial can belong only to one type + topic + theme.
         * @return Uri representing the query parameters for the filter as well as the categories.
         */
        public static Uri buildCategoryTagFilterUri(Uri contentUri, String[] tags, int categories) {
            StringBuilder sb = new StringBuilder();
            for (String tag : tags) {
                if (TextUtils.isEmpty(tag)) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(tag.trim());
            }
            if (sb.length() == 0) {
                return contentUri;
            } else {
                return contentUri.buildUpon()
                        .appendQueryParameter(QUERY_PARAMETER_TAG_FILTER, sb.toString())
                        .appendQueryParameter(QUERY_PARAMETER_CATEGORIES,
                                String.valueOf(categories))
                        .build();
            }
        }
    }

//    public static class SearchSuggest {
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_SUGGEST).build();
//
//        public static final String DEFAULT_SORT = SearchManager.SUGGEST_COLUMN_TEXT_1
//                + " COLLATE NOCASE ASC";
//    }

//    public static class SearchIndex {
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_INDEX).build();
//    }

    /**
     * Columns for an in memory table created on query using
     * the Tags table and the SearchTutorials table.
     */
//    public interface SearchTopicTutorialsColumns extends BaseColumns {
//        /* This column contains either a tag_id or a tutorial_id */
//        String TAG_OR_TUTORIAL_ID = "tag_or_tutorial_id";
//        /* This column contains the search snippet to be shown to the user.*/
//        String SEARCH_SNIPPET = "search_snippet";
//        /* Indicates whether this row is a topic tag or a tutorial_id. */
//        String IS_TOPIC_TAG = "is_topic_tag";
//    }

    private DsoContract() {
    }
}
