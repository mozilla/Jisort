package com.mozilla.hackathon.kiboko.provider;

/**
* The list of {@code Uri}s recognised by the {@code ContentProvider} of the app.
* <p />
* It is important to order them in the order that follows {@link android.content.UriMatcher}
* matching rules: wildcard {@code *} applies to one segment only and it processes matching per
* segment in a tree manner over the list of {@code Uri} in the order they are added.
*/
public enum DsoUriEnum {
    TUTORIALS(400, "tutorials", DsoContract.Tutorials.CONTENT_TYPE_ID, false, DsoDatabase.Tables.TUTORIALS),
    TUTORIALS_SEARCH(403, "tutorials/search/*", DsoContract.Tutorials.CONTENT_TYPE_ID, false, null),
    TUTORIALS_ID(405, "tutorials/*", DsoContract.Tutorials.CONTENT_TYPE_ID, true, null),
    QUIZES(500, "quizes", DsoContract.Quizes.CONTENT_TYPE_ID, false, DsoDatabase.Tables.QUIZES),
    QUIZES_SEARCH(503, "quizes/search/*", DsoContract.Quizes.CONTENT_TYPE_ID, false, null),
    QUIZES_ID(505, "quizes/*", DsoContract.Quizes.CONTENT_TYPE_ID, true, null);
    public int code;

    /**
     * The path to the {@link android.content.UriMatcher} will use to match. * may be used as a
     * wild card for any text, and # may be used as a wild card for numbers.
     */
    public String path;

    public String contentType;

    public String table;

    DsoUriEnum(int code, String path, String contentTypeId, boolean item, String table) {
        this.code = code;
        this.path = path;
        this.contentType = item ? DsoContract.makeContentItemType(contentTypeId)
                : DsoContract.makeContentType(contentTypeId);
        this.table = table;
    }


}
