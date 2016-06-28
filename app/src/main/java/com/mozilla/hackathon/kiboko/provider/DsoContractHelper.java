package com.mozilla.hackathon.kiboko.provider;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Provides helper methods for specifying query parameters on {@code Uri}s.
 */
public class DsoContractHelper {

    public static final String QUERY_PARAMETER_DISTINCT = "distinct";

    private static final String QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER = "callerIsSyncAdapter";


    public static boolean isUriCalledFromSyncAdapter(Uri uri) {
        return uri.getBooleanQueryParameter(QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER, false);
    }

    public static Uri setUriAsCalledFromSyncAdapter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER, "true")
                .build();
    }

    public static boolean isQueryDistinct(Uri uri){
        return !TextUtils.isEmpty(uri.getQueryParameter(QUERY_PARAMETER_DISTINCT));
    }

    public static String formatQueryDistinctParameter(String parameter){
        return DsoContractHelper.QUERY_PARAMETER_DISTINCT + " " + parameter;
    }

}

