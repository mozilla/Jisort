package com.mozilla.hackathon.kiboko.services;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.settings.SettingsUtils;
import com.mozilla.hackathon.kiboko.sync.SyncAdapter;

import java.io.IOException;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * A helper class for dealing with conference data synchronization. All operations occur on the
 * thread they're called from, so it's best to wrap calls in an {@link android.os.AsyncTask}, or
 * better yet, a {@link android.app.Service}.
 */
public class SyncHelper {

    private static final String TAG = makeLogTag(SyncHelper.class);

    private Context mContext;



    /**
     *
     * @param context Can be Application, Activity or Service context.
     */
    public SyncHelper(Context context) {
        mContext = context;

    }

    public static void requestManualSync(Account mChosenAccount) {
        requestManualSync(mChosenAccount, false);
    }

    public static void requestManualSync(Account mChosenAccount, boolean userDataSyncOnly) {
        if (mChosenAccount != null) {
            LOGD(TAG, "Requesting manual sync for account " + mChosenAccount.name
                    + " userDataSyncOnly=" + userDataSyncOnly);
            Bundle b = new Bundle();
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            if (userDataSyncOnly) {
                b.putBoolean(SyncAdapter.EXTRA_SYNC_DATA_ONLY, true);
            }
            ContentResolver
                    .setSyncAutomatically(mChosenAccount, DsoContract.CONTENT_AUTHORITY, true);
            ContentResolver.setIsSyncable(mChosenAccount, DsoContract.CONTENT_AUTHORITY, 1);

            boolean pending = ContentResolver.isSyncPending(mChosenAccount,
                    DsoContract.CONTENT_AUTHORITY);
            if (pending) {
                LOGD(TAG, "Warning: sync is PENDING. Will cancel.");
            }
            boolean active = ContentResolver.isSyncActive(mChosenAccount,
                    DsoContract.CONTENT_AUTHORITY);
            if (active) {
                LOGD(TAG, "Warning: sync is ACTIVE. Will cancel.");
            }

            if (pending || active) {
                LOGD(TAG, "Cancelling previously pending/active sync.");
                ContentResolver.cancelSync(mChosenAccount, DsoContract.CONTENT_AUTHORITY);
            }

            LOGD(TAG, "Requesting sync now.");
            ContentResolver.requestSync(mChosenAccount, DsoContract.CONTENT_AUTHORITY, b);
        } else {
            LOGD(TAG, "Can't request manual sync -- no chosen account.");
        }
    }

    /**
     * Attempts to perform data synchronization. There are 3 types of data: conference, user
     * schedule and user feedback.
     * <p />
     * The conference data sync is handled by {@link RemoteConferenceDataFetcher}. For more details
     * about conference data, refer to the documentation at
     * https://github.com/google/iosched/blob/master/doc/SYNC.md. The user schedule data sync is
     * handled by {@link AbstractUserDataSyncHelper}. The user feedback sync is handled by
     * {@link FeedbackSyncHelper}.
     *
     *
     * @param syncResult The sync result object to update with statistics.
     * @param account The account associated with this sync
     * @param extras Specifies additional information about the sync. This must contain key
     *               {@code SyncAdapter.EXTRA_SYNC_USER_DATA_ONLY} with boolean value
     * @return true if the sync changed the data.
     */
    public boolean performSync(@Nullable SyncResult syncResult, Account account, Bundle extras) {
        boolean dataChanged = false;

        if (!SettingsUtils.isDataBootstrapDone(mContext)) {
            LOGD(TAG, "Sync aborting (data bootstrap not done yet)");
            // Start the bootstrap process so that the next time sync is called,
            // it is already bootstrapped.
            DataBootstrapService.startDataBootstrapIfNecessary(mContext);
            return false;
        }


        return dataChanged;
    }

    /**
     * Checks if the remote server has new conference data that we need to import. If so, download
     * the new data and import it into the database.
     *
     * @return Whether or not data was changed.
     * @throws IOException if there is a problem downloading or importing the data.
     */
    private boolean doDsoDataSync() throws IOException {
        if (!isOnline()) {
            LOGD(TAG, "Not attempting remote sync because device is OFFLINE");
            return false;
        }

        LOGD(TAG, "Starting remote sync.");

        return false;

    }

    public static void performPostSyncChores(final Context context) {
        // Update search index.
        LOGD(TAG, "Updating search index.");
        context.getContentResolver().update(DsoContract.SearchIndex.CONTENT_URI,
                new ContentValues(), null, null);
    }

    /**
     * Checks if there are changes on User's Data to sync with/from remote AppData folder.
     *
     * @return Whether or not data was changed.
     * @throws IOException if there is a problem uploading the data.
     */
    private boolean doUserDataSync(String accountName) throws IOException {
        if (!isOnline()) {
            LOGD(TAG, "Not attempting userdata sync because device is OFFLINE");
            return false;
        }

        LOGD(TAG, "Starting user data sync.");

       return false;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void increaseIoExceptions(SyncResult syncResult) {
        if (syncResult != null && syncResult.stats != null) {
            ++syncResult.stats.numIoExceptions;
        }
    }

    public static class AuthException extends RuntimeException {

    }

    private static long calculateRecommendedSyncInterval(final Context context) {
        return 1;
    }

    public static void updateSyncInterval(final Context context, final Account account) {

    }
}
