package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.fragments.ScreenSlidePageFragment;
import com.mozilla.hackathon.kiboko.models.Step;
import com.mozilla.hackathon.kiboko.models.Tutorial;
import com.mozilla.hackathon.kiboko.provider.DsoContract;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because {@link ViewPager}
 * automatically plays such an animation when calling {@link ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 * @see ScreenSlidePageFragment
 */
public class TutorialSlideActivity extends DSOActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = makeLogTag(TutorialSlideActivity.class);
    private static final Uri BASE_APP_URI = Uri.parse("android-app://mozilladso.com/tutorials/");
    private static final int LOADER_ID = 0x01;
    private List<Step> jsonSteps = new ArrayList<Step>();
    /**
     * The number of pages (wizard steps) to show.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private Button mPrev;
    private Button mNext;
    private String mTopic;
    TextView txtCaption;

    private Tutorial tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_tutorial_slide);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if(!(bundle == null)){
            if(bundle.getString("title") != null) {
                setTitle((String)bundle.get("title"));
            }

            if(bundle.getString("topic") != null)
            {
                mTopic = (String)bundle.get("topic");
            }
        }else {
            onNewIntent(getIntent());
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mNext = (Button) findViewById(R.id.mNext);
        mPrev = (Button) findViewById(R.id.mPrev);
        txtCaption = (TextView) findViewById(R.id.mCaption);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(mPager.getCurrentItem() > mPagerAdapter.getCount() - 1)){
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                }else{
                    mNext.setEnabled(true);
                    mPrev.setEnabled(false);
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)){
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }else{
                    mNext.setEnabled(false);
                    mPrev.setEnabled(true);
                }
            }
        });

        txtCaption.setText(getString(R.string.tutorial_template_step, 1, mPager.getAdapter().getCount()));

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                txtCaption.setText(getString(R.string.tutorial_template_step, position + 1, mPager.getAdapter().getCount()));
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String tutorialId = data.substring(data.lastIndexOf("/") + 1);
            Uri contentUri = DsoContract.Tutorials.CONTENT_URI.buildUpon()
                    .appendPath(tutorialId).build();
            showTutorial(contentUri);
        }
    }

    private void showTutorial(Uri tutorialUri) {
        LOGD("Tutorial Uri", tutorialUri.toString());

        String[] projection = { DsoContract.Tutorials.TUTORIAL_ID,
                DsoContract.Tutorials.TUTORIAL_TAG,
                DsoContract.Tutorials.TUTORIAL_HEADER,
                DsoContract.Tutorials.TUTORIAL_PHOTO_URL,
                DsoContract.Tutorials.TUTORIAL_STEPS};

        // Read all data for contactId
        String selection = DsoContract.Tutorials.TUTORIAL_ID + " = ?";
        String[] selectionArgs = new String[]{mTopic};

//        Cursor cursor = getContentResolver().query(tutorialUri, projection, null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            // always close the cursor
//            cursor.close();
//        } else {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "No match for deep link " + tutorialUri.toString(),
//                    Toast.LENGTH_SHORT);
//            toast.show();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define the columns to retrieve
        String[] projectionFields = new String[] {
                DsoContract.Tutorials.TUTORIAL_ID,
                DsoContract.Tutorials.TUTORIAL_TAG,
                DsoContract.Tutorials.TUTORIAL_HEADER,
                DsoContract.Tutorials.TUTORIAL_PHOTO_URL,
                DsoContract.Tutorials.TUTORIAL_STEPS };

        // Read all data for contactId
        String selection = DsoContract.Tutorials.TUTORIAL_TAG + " =?";
        String[] selectionArgs = new String[]{mTopic};

        CursorLoader cursorLoader = new CursorLoader(TutorialSlideActivity.this,
                DsoContract.Tutorials.CONTENT_URI, // URI
                projectionFields, // projection fields
                selection, // the selection criteria
                selectionArgs, // the selection args
                null // the sort order
        );
        // Return the loader for use
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int idIndex =
                            cursor.getColumnIndex(DsoContract.Tutorials._ID);
                    int stepsIndex =
                            cursor.getColumnIndex(DsoContract.Tutorials.TUTORIAL_STEPS);

                    String jsonArray = cursor.getString(stepsIndex);
                    LOGD(TAG, jsonArray);
                    Type listType = new TypeToken<List<Step>>(){}.getType();

                    jsonSteps = (List<Step>) new Gson().fromJson(jsonArray,listType);
                    mPagerAdapter.notifyDataSetChanged();

                    txtCaption.setText(getString(R.string.tutorial_template_step, 1, mPager.getAdapter().getCount()));
                }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position, jsonSteps.get(position).title, jsonSteps.get(position).description, jsonSteps.get(position).gifUrl);
        }

        @Override
        public int getCount() {
            return jsonSteps.size();
        }
    }
}
