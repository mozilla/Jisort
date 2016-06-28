package com.mozilla.hackathon.kiboko.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.mozilla.hackathon.kiboko.settings.SettingsUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    private static final SimpleDateFormat[] ACCEPTED_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.US)
    };

    private static final SimpleDateFormat VALID_IFMODIFIEDSINCE_FORMAT =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public static Date parseTimestamp(String timestamp) {
        for (SimpleDateFormat format : ACCEPTED_TIMESTAMP_FORMATS) {
            // TODO: We shouldn't be forcing the time zone when parsing dates.
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                return format.parse(timestamp);
            } catch (ParseException ex) {
                continue;
            }
        }

        // All attempts to parse have failed
        return null;
    }

    public static boolean isValidFormatForIfModifiedSinceHeader(String timestamp) {
        try {
            return VALID_IFMODIFIEDSINCE_FORMAT.parse(timestamp)!=null;
        } catch (Exception ex) {
            return false;
        }
    }

    public static long timestampToMillis(String timestamp, long defaultValue) {
        if (TextUtils.isEmpty(timestamp)) {
            return defaultValue;
        }
        Date d = parseTimestamp(timestamp);
        return d == null ? defaultValue : d.getTime();
    }

    /**
     * Format a {@code date} honoring the app preference for using Conference or device timezone.
     * {@code Context} is used to lookup the shared preference settings.
     */

//    public static String formatShortDate(Context context, Date date) {
//        StringBuilder sb = new StringBuilder();
//        Formatter formatter = new Formatter(sb);
//        return DateUtils.formatDateRange(context, formatter, date.getTime(), date.getTime(),
//                DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_NO_YEAR,
//                SettingsUtils.getDisplayTimeZone(context).getID()).toString();
//    }

//    public static String formatShortTime(Context context, Date time) {
//        // Android DateFormatter will honor the user's current settings.
//        DateFormat format = android.text.format.DateFormat.getTimeFormat(context);
//        // Override with Timezone based on settings since users can override their phone's timezone
//        // with Pacific time zones.
//        TimeZone tz = SettingsUtils.getDisplayTimeZone(context);
//        if (tz != null) {
//            format.setTimeZone(tz);
//        }
//        return format.format(time);
//    }

    /**
     * Returns "Today", "Tomorrow", "Yesterday", or a short date format.
     */
//    public static String formatHumanFriendlyShortDate(final Context context, long timestamp) {
//        long localTimestamp, localTime;
//        long now = UIUtils.getCurrentTime(context);
//
//        TimeZone tz = SettingsUtils.getDisplayTimeZone(context);
//        localTimestamp = timestamp + tz.getOffset(timestamp);
//        localTime = now + tz.getOffset(now);
//
//        long dayOrd = localTimestamp / 86400000L;
//        long nowOrd = localTime / 86400000L;
//
//        if (dayOrd == nowOrd) {
//            return context.getString(R.string.day_title_today);
//        } else if (dayOrd == nowOrd - 1) {
//            return context.getString(R.string.day_title_yesterday);
//        } else if (dayOrd == nowOrd + 1) {
//            return context.getString(R.string.day_title_tomorrow);
//        } else {
//            return formatShortDate(context, new Date(timestamp));
//        }
//    }
}

