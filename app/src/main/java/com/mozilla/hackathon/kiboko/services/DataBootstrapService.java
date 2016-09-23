package com.mozilla.hackathon.kiboko.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mozilla.hackathon.kiboko.BuildConfig;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.io.JSONHandler;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.settings.SettingsUtils;
import com.mozilla.hackathon.kiboko.sync.DsoDataHandler;
import com.mozilla.hackathon.kiboko.utils.LogUtils;

import java.io.IOException;

import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGE;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGI;
import static com.mozilla.hackathon.kiboko.utils.LogUtils.LOGW;

/**
 * An {@code IntentService} that performs the one-time data bootstrap. It takes the prepackaged
 * mozilla dso data from the R.raw.bootstrap_data resource, and populates the database. This data
 * contains the turtorials.
 */
public class DataBootstrapService extends IntentService {

    private static final String TAG = LogUtils.makeLogTag(DataBootstrapService.class);

    /**
     * Start the {@link DataBootstrapService} if the bootstrap is either not done or complete yet.
     *
     * @param context The context for starting the {@link IntentService} as well as checking if the
     *                shared preference to mark the process as done is set.
     */
    public static void startDataBootstrapIfNecessary(Context context) {
        if (!SettingsUtils.isDataBootstrapDone(context)) {
            LOGW(TAG, "One-time data bootstrap not done yet. Doing now.");
            context.startService(new Intent(context, DataBootstrapService.class));
        }
    }

    /**
     * Creates a DataBootstrapService.
     */
    public DataBootstrapService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context appContext = getApplicationContext();

        if (SettingsUtils.isDataBootstrapDone(appContext)) {
            LOGD(TAG, "Data bootstrap already done.");
            return;
        }
        try {
            LOGD(TAG, "Starting data bootstrap process.");
            // Load data from bootstrap raw resource.
            String bootstrapJson = JSONHandler
                    .parseResource(appContext, R.raw.bootstrap_data);

            // Apply the data we read to the database with the help of the DsoDataHandler.
            DsoDataHandler dataHandler = new DsoDataHandler(appContext);
            dataHandler.applyDSOData(new String[]{bootstrapJson},
                    BuildConfig.BOOTSTRAP_DATA_TIMESTAMP, false);

            SyncHelper.performPostSyncChores(appContext);

            LOGI(TAG, "End of bootstrap -- successful. Marking bootstrap as done.");
            SettingsUtils.markDataBootstrapDone(appContext);
//
            getContentResolver().notifyChange(Uri.parse(DsoContract.CONTENT_AUTHORITY),
                    null, false);

        } catch (IOException ex) {
            // This is serious -- if this happens, the app won't work :-(
            // This is unlikely to happen in production, but IF it does, we apply
            // this workaround as a fallback: we pretend we managed to do the bootstrap
            // and hope that a remote sync will work.
            LOGE(TAG, "*** ERROR DURING BOOTSTRAP! Problem in bootstrap data?", ex);
            LOGE(TAG,
                    "Applying fallback -- marking boostrap as done; sync might fix problem.");
            SettingsUtils.markDataBootstrapDone(appContext);
        }
    }
}

