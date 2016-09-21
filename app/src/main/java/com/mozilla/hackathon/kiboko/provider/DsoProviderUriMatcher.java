package com.mozilla.hackathon.kiboko.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class DsoProviderUriMatcher {

    /**
     * All methods on a {@link UriMatcher} are thread safe, except {@code addURI}.
     */
    private UriMatcher mUriMatcher;

    private SparseArray<DsoUriEnum> mEnumsMap = new SparseArray<>();

    /**
     * This constructor needs to be called from a thread-safe method as it isn't thread-safe itself.
     */
    public DsoProviderUriMatcher() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        buildUriMatcher();
    }

    private void buildUriMatcher() {
        final String authority = DsoContract.CONTENT_AUTHORITY;

        DsoUriEnum[] uris = DsoUriEnum.values();
        for (DsoUriEnum uri : uris) {
            mUriMatcher.addURI(authority, uri.path, uri.code);
        }

        buildEnumsMap();
    }

    private void buildEnumsMap() {
        DsoUriEnum[] uris = DsoUriEnum.values();
        for (DsoUriEnum uri : uris) {
            mEnumsMap.put(uri.code, uri);
        }
    }

    /**
     * Matches a {@code uri} to a {@link DsoUriEnum}.
     *
     * @return the {@link DsoUriEnum}, or throws new UnsupportedOperationException if no match.
     */
    public DsoUriEnum matchUri(Uri uri) {
        final int code = mUriMatcher.match(uri);
        try {
            return matchCode(code);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    /**
     * Matches a {@code code} to a {@link DsoUriEnum}.
     *
     * @return the {@link DsoUriEnum}, or throws new UnsupportedOperationException if no match.
     */
    public DsoUriEnum matchCode(int code) {
        DsoUriEnum dsoUriEnum = mEnumsMap.get(code);
        if (dsoUriEnum != null) {
            return dsoUriEnum;
        } else {
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
    }
}
