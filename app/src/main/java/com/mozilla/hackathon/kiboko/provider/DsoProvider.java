package com.mozilla.hackathon.kiboko.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.mozilla.hackathon.kiboko.provider.DsoContract.Quizes;
import com.mozilla.hackathon.kiboko.provider.DsoContract.Tutorials;
import com.mozilla.hackathon.kiboko.provider.DsoDatabase.Tables;
import com.mozilla.hackathon.kiboko.utils.SelectionBuilder;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mozilla.hackathon.kiboko.utils.LogUtils.makeLogTag;

public class DsoProvider extends ContentProvider {

    private static final String TAG = makeLogTag(DsoProvider.class);
    private DsoDatabase mOpenHelper;
    private DsoProviderUriMatcher mUriMatcher;

    /**
     * Providing important state information to be included in bug reports.
     * <p>
     * !!! Remember !!! Any important data logged to {@code writer} shouldn't contain personally
     * identifiable information as it can be seen in bugreports.
     */
    @Override
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        Context context = getContext();

        // Using try/catch block in case there are issues retrieving information to log.
        try {
            // Calling append in multiple calls is typically better than creating net new strings to
            // pass to method invocations.
            writer.print("Last sync attempted: ");
//            writer.println(new java.util.Date(SettingsUtils.getLastSyncAttemptedTime(context)));
//            writer.print("Last sync successful: ");
//            writer.println(new java.util.Date(SettingsUtils.getLastSyncSucceededTime(context)));
//            writer.print("Current sync interval: ");
//            writer.println(SettingsUtils.getCurSyncInterval(context));
        } catch (Exception exception) {
            writer.append("Exception while dumping state: ");
            exception.printStackTrace(writer);
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DsoDatabase(getContext());
        mUriMatcher = new DsoProviderUriMatcher();
        return true;
    }

    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        Context context = getContext();
        DsoDatabase.deleteDatabase(context);
        mOpenHelper = new DsoDatabase(getContext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType(Uri uri) {
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        return matchingUriEnum.contentType;
    }

    /**
     * Returns a tuple of question marks. For example, if {@code count} is 3, returns "(?,?,?)".
     */
    private String makeQuestionMarkTuple(int count) {
        if (count < 1) {
            return "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(?");
        for (int i = 1; i < count; i++) {
            stringBuilder.append(",?");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        String tagsFilter = uri.getQueryParameter(DsoContract.Tutorials.QUERY_PARAMETER_TAG_FILTER);
        String categories = uri.getQueryParameter(DsoContract.Tutorials.QUERY_PARAMETER_CATEGORIES);
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        // Avoid the expensive string concatenation below if not loggable.
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "uri=" + uri + " code=" + matchingUriEnum.code + " proj=" +
                    Arrays.toString(projection) + " selection=" + selection + " args="
                    + Arrays.toString(selectionArgs) + ")");
        }

        switch (matchingUriEnum) {
            default: {
                // Most cases are handled with simple SelectionBuilder.
                final SelectionBuilder builder = buildExpandedSelection(uri, matchingUriEnum.code);
                boolean distinct = DsoContractHelper.isQueryDistinct(uri);
                Cursor cursor = builder
                        .where(selection, selectionArgs)
                        .query(db, distinct, projection, sortOrder, null);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        if (matchingUriEnum.table != null) {
            db.insertOrThrow(matchingUriEnum.table, null, values);
            notifyChange(uri);
        }
        switch (matchingUriEnum) {
            case TUTORIALS: {
                return Tutorials.buildTutorialUri(values.getAsString(DsoContract.Tutorials.TUTORIAL_ID));
            }
            case QUIZES: {
                return Quizes.buildQuizUri(values.getAsString(DsoContract.Quizes.KEY_ID));
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        notifyChange(uri);
        return retVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if (uri == DsoContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    /**
     * Notifies the system that the given {@code uri} data has changed.
     * <p/>
     * We only notify changes if the uri wasn't called by the sync adapter, to avoid issuing a large
     * amount of notifications while doing a sync. The
     * {@link com.mozilla.hackathon.kiboko.sync.DsoDataHandler} notifies all top level
     * tutorials paths once the tutorial data sync is done.
     */
    private void notifyChange(Uri uri) {
        if (!DsoContractHelper.isUriCalledFromSyncAdapter(uri)) {
            Context context = getContext();
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Build a simple {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually enough to support {@link #insert},
     * {@link #update}, and {@link #delete} operations.
     */
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        DsoUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        // The main Uris, corresponding to the root of each type of Uri, do not have any selection
        // criteria so the full table is used. The others apply a selection criteria.
        switch (matchingUriEnum) {
            case TUTORIALS:
            case QUIZES:
                return builder.table(matchingUriEnum.table);
            case TUTORIALS_ID: {
                final String tutorialId = Tutorials.getTutorialId(uri);
                return builder.table(Tables.TUTORIALS)
                        .where(DsoContract.Tutorials.TUTORIAL_ID + "=?", tutorialId);
            }
            case QUIZES_ID: {
                final String quizId = Quizes.getQuizId(uri);
                return builder.table(Tables.QUIZES)
                        .where(DsoContract.Quizes.KEY_ID + "=?", quizId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + uri);
            }
        }
    }

    /**
     * Build an advanced {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually only used by {@link #query}, since it
     * performs table joins useful for {@link Cursor} data.
     */
    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        DsoUriEnum matchingUriEnum = mUriMatcher.matchCode(match);
        if (matchingUriEnum == null) {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        switch (matchingUriEnum) {
            case TUTORIALS: {
                return builder.table(Tables.TUTORIALS);
            }
            case QUIZES: {
                return builder.table(Tables.QUIZES);
            }
            case TUTORIALS_ID: {
                final String tutorialId = DsoContract.Tutorials.getTutorialId(uri);
                return builder.table(Tables.TUTORIALS)
                        .where(DsoContract.Tutorials.TUTORIAL_ID + "=?", tutorialId);
            }
            case QUIZES_ID: {
                final String quizId = DsoContract.Quizes.getQuizId(uri);
                return builder.table(Tables.QUIZES)
                        .where(DsoContract.Quizes.KEY_ID + "=?", quizId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        throw new UnsupportedOperationException("openFile is not supported for " + uri);
    }
}
