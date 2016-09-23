package com.mozilla.hackathon.kiboko.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.mozilla.hackathon.kiboko.BuildConfig;
import com.mozilla.hackathon.kiboko.services.SyncHelper;

import java.util.regex.Pattern;

import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGE;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGI;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.makeLogTag;

/**
 * Sync adapter for Google I/O data. Used for download sync only. For upload sync,
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = makeLogTag(SyncAdapter.class);

    private static final Pattern sSanitizeAccountNamePattern = Pattern.compile("(.).*?(.?)@");
    public static final String EXTRA_SYNC_USER_DATA_ONLY =
            "com.mozilla.hackathon.kiboko.EXTRA_SYNC_USER_DATA_ONLY";

    private final Context mContext;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;

        //noinspection ConstantConditions,PointlessBooleanExpression
        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable throwable) {
                    LOGE(TAG, "Uncaught sync exception, suppressing UI in release build.",
                            throwable);
                }
            });
        }
    }

    @Override
    public void onPerformSync(final Account account, Bundle extras, String authority,
                              final ContentProviderClient provider, final SyncResult syncResult) {
        final boolean uploadOnly = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);
        final boolean initialize = extras.getBoolean(ContentResolver.SYNC_EXTRAS_INITIALIZE, false);
        final boolean userScheduleDataOnly = extras.getBoolean(EXTRA_SYNC_USER_DATA_ONLY,
                false);

        final String logSanitizedAccountName = sSanitizeAccountNamePattern
                .matcher(account.name).replaceAll("$1...$2@");

        // This Adapter is declared not to support uploading in its xml file.
        // {@code ContentResolver.SYNC_EXTRAS_UPLOAD} is set by the system in some cases, but never
        // by the app. Conference data only is a download sync, user schedule data sync is both
        // ways and uses {@code EXTRA_SYNC_USER_DATA_ONLY}, session feedback data sync is
        // upload only and isn't managed by this SyncAdapter as it doesn't need periodic sync.
        if (uploadOnly) {
            return;
        }

        LOGI(TAG, "Beginning sync for account " + logSanitizedAccountName + "," +
                " uploadOnly=" + uploadOnly +
                " userScheduleDataOnly =" + userScheduleDataOnly +
                " initialize=" + initialize);

        // Sync from bootstrap and remote data, as needed
        new SyncHelper(mContext).performSync(syncResult, account, extras);
    }

}
