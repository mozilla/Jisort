package com.mozilla.hackathon.kiboko.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.mozilla.hackathon.kiboko.provider.DsoContract.Tutorials;
import com.mozilla.hackathon.kiboko.provider.DsoContract.TutorialsColumns;
import com.mozilla.hackathon.kiboko.provider.DsoContract.QuizColumns;
import com.mozilla.hackathon.kiboko.sync.DsoDataHandler;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGW;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Created by brian Mwadime on 25/06/2016.
 */
public class DsoDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(DsoDatabase.class);

    private static final String DATABASE_NAME = "mozilladso.db";

    // NOTE: carefully update onUpgrade() when bumping database versions to make
    // sure user data is saved.
    
    private static final int VER_2015_RELEASE_A = 208;
    private static final int VER_2015_RELEASE_B = 210;
    private static final int CUR_DATABASE_VERSION = VER_2015_RELEASE_B;

    private final Context mContext;

    interface Tables {
        String TUTORIALS = "tutorials";
        String MY_SCHEDULE = "myschedule";
        String QUIZES = "quizes";

        // When tables get deprecated, add them to this list (so they get correctly deleted
        // on database upgrades).
        enum DeprecatedTables {
            SANDBOX("sandbox");

            String tableName;

            DeprecatedTables(String tableName) {
                this.tableName = tableName;
            }
        };
    }

    private interface Triggers {
        // Deletes from dependent tables when corresponding tutorials are deleted.
        String TUTORIALS_TAGS_DELETE = "tutorials_tags_delete";

        // When triggers get deprecated, add them to this list (so they get correctly deleted
        // on database upgrades).
        interface DeprecatedTriggers {
            String TUTORIALS_TRACKS_DELETE = "tutorials_tracks_delete";
        };
    }


    public interface TutorialsSteps {
        String TUTORIAL_ID = "tutorial_id";
        String STEP_ID = "step_id";
    }

    /** {@code REFERENCES} clauses. */
    private interface References {
        String TUTORIAL_ID = "REFERENCES " + Tables.TUTORIALS + "(" + Tutorials.TUTORIAL_ID + ")";
    }

    public DsoDatabase(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Tables.TUTORIALS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DsoContract.SyncColumns.UPDATED + " INTEGER NOT NULL,"
                + TutorialsColumns.TUTORIAL_IMPORT_HASHCODE + " TEXT NOT NULL DEFAULT '',"
                + TutorialsColumns.TUTORIAL_ID + " TEXT NOT NULL,"
                + TutorialsColumns.TUTORIAL_TAG + " TEXT NOT NULL,"
                + TutorialsColumns.TUTORIAL_HEADER + " TEXT,"
                + TutorialsColumns.TUTORIAL_PHOTO_URL + " TEXT,"
                + TutorialsColumns.TUTORIAL_STEPS + " TEXT,"
                + "UNIQUE (" + TutorialsColumns.TUTORIAL_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.QUIZES + " ( "
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DsoContract.SyncColumns.UPDATED + " INTEGER NOT NULL,"
                + QuizColumns.KEY_IMPORT_HASHCODE + " TEXT NOT NULL DEFAULT '',"
                + QuizColumns.KEY_ID + " TEXT NOT NULL, "
                + QuizColumns.KEY_QUESTION + " TEXT,"
                + QuizColumns.KEY_ANSWER + " TEXT,"
                + QuizColumns.KEY_OPTIONA +" TEXT,"
                + QuizColumns.KEY_OPTIONB +" TEXT,"
                + QuizColumns.KEY_OPTIONC + " TEXT,"
                + QuizColumns.KEY_OPTIOND + " TEXT,"
                + "UNIQUE (" + QuizColumns.KEY_ID + ") ON CONFLICT REPLACE)");

        upgradeFrom2015Ato2015B(db);
    }

    private void upgradeFrom2015Ato2015B(SQLiteDatabase db) {
        // Note: SpeakersColumns.SPEAKER_URL is unused in 2015. The columns added here are used
        // instead.
//        db.execSQL("ALTER TABLE " + Tables.SPEAKERS
//                + " ADD COLUMN " + SpeakersColumns.SPEAKER_PLUSONE_URL + " TEXT");
//        db.execSQL("ALTER TABLE " + Tables.SPEAKERS
//                + " ADD COLUMN " + SpeakersColumns.SPEAKER_TWITTER_URL + " TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LOGD(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

        // Current DB version. We update this variable as we perform upgrades to reflect
        // the current version we are in.
        int version = oldVersion;

        // Indicates whether the data we currently have should be invalidated as a
        // result of the db upgrade. Default is true (invalidate); if we detect that this
        // is a trivial DB upgrade, we set this to false.
        boolean dataInvalidated = true;

        // Check if we can upgrade from release 2015 A to release 2015 B.
        if (version == VER_2015_RELEASE_A) {
            LOGD(TAG, "Upgrading database from 2015 release A to 2015 release B.");
            upgradeFrom2015Ato2015B(db);
            version = VER_2015_RELEASE_B;
        }

        LOGD(TAG, "After upgrade logic, at version " + version);

        // Drop tables that have been deprecated.
        for (Tables.DeprecatedTables deprecatedTable : Tables.DeprecatedTables.values()) {
            db.execSQL(("DROP TABLE IF EXISTS " + deprecatedTable.tableName));
        }

        // At this point, we ran out of upgrade logic, so if we are still at the wrong
        // version, we have no choice but to delete everything and create everything again.
        if (version != CUR_DATABASE_VERSION) {
            LOGW(TAG, "Upgrade unsuccessful -- destroying old data during upgrade");
//            db.execSQL("DROP TRIGGER IF EXISTS " + Triggers.TUTORIALS_TAGS_DELETE);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.TUTORIALS);

            onCreate(db);
            version = CUR_DATABASE_VERSION;
        }

        if (dataInvalidated) {
            LOGD(TAG, "Data invalidated; resetting our data timestamp.");
            DsoDataHandler.resetDataTimestamp(mContext);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
