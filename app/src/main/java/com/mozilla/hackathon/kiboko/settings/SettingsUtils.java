package com.mozilla.hackathon.kiboko.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Utilities and constants related to app settings_prefs.
 */
public class SettingsUtils {
    private static final String TAG = makeLogTag(SettingsUtils.class);
    /**
     * Boolean preference indicating whether the app has
     * {@code com.google.samples.apps.iosched.ui.BaseActivity.performDataBootstrap installed} the
     * {@code R.raw.bootstrap_data bootstrap data}.
     */
    public static final String PREF_DATA_BOOTSTRAP_DONE = "pref_data_bootstrap_done";
    /**
     * Boolean indicating if the app can collect Analytics.
     */
    public static final String PREF_ANALYTICS_ENABLED = "pref_analytics_enabled";
    /**
     * Boolean preference indicating whether the app is in fun mode
     */
    public static final String PREF_FUNMODE_ENABLED = "pref_funmode_enabled";

    public static final String PREF_WELCOME_DONE = "pref_welcome_done";
    /**
     * Mark that the app has finished loading the {@code R.raw.bootstrap_data bootstrap data}.
     *
     * @param context Context to be used to edit the {@link android.content.SharedPreferences}.
     */
    public static void markDataBootstrapDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_DATA_BOOTSTRAP_DONE, true).apply();
    }
    /**
     * Mark that the app has shown the welcome screen {@link com.mozilla.hackathon.kiboko.activities.WelcomeActivity}.
     *
     * @param context Context to be used to edit the {@link android.content.SharedPreferences}.
     */
    public static void markWelcomeDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_WELCOME_DONE, true).apply();
    }
    /**
     * Return true when the {@code R.raw.bootstrap_data_json bootstrap data} has been marked loaded.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isDataBootstrapDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_DATA_BOOTSTRAP_DONE, false);
    }

    /**
     * Return true when the {@link com.mozilla.hackathon.kiboko.activities.WelcomeActivity} has been shown.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isWelcomeDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_WELCOME_DONE, false);
    }
    /**
     * Set fun mode.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setFunMode(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_FUNMODE_ENABLED, newValue).apply();
    }
    /**
     * Return true if analytics are enabled, false if user has disabled them.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isAnalyticsEnabled(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_ANALYTICS_ENABLED, true);
    }
    /**
     * Return true if fun mode is enabled, false if user has disabled them.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isFunModeEnabled(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_FUNMODE_ENABLED, true);
    }
    /**
     * Helper method to register a settings_prefs listener. This method does not automatically handle
     * {@code unregisterOnSharedPreferenceChangeListener() un-registering} the listener at the end
     * of the {@code context} lifecycle.
     *
     * @param context  Context to be used to lookup the {@link android.content.SharedPreferences}.
     * @param listener Listener to register.
     */
    public static void registerOnSharedPreferenceChangeListener(final Context context,
                                                                SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Helper method to un-register a settings_prefs listener typically registered with
     * {@code registerOnSharedPreferenceChangeListener()}
     *
     * @param context  Context to be used to lookup the {@link android.content.SharedPreferences}.
     * @param listener Listener to un-register.
     */
    public static void unregisterOnSharedPreferenceChangeListener(final Context context,
                                                                  SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }
}

