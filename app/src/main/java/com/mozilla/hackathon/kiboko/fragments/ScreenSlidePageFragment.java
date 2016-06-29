package com.mozilla.hackathon.kiboko.fragments;

/**
 * Created by mwadime on 6/9/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.widgets.NotifyingScrollView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGD;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {
    private static final String TAG = makeLogTag(ScreenSlidePageFragment.class);
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_PAGE_TITLE = "page_title";
    public static final String ARG_PAGE_DESCRIPTION = "page_description";
    public static final String ARG_PAGE_IMAGE = "page_image";

    private float mActionBarHeight;

    private GifDrawable gifDrawable;

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private String mPageDescription, mPageTitle, mPageImage;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber, String pageTitle, String pageDescription, String pageImage) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_PAGE_TITLE, pageTitle);
        args.putString(ARG_PAGE_DESCRIPTION, pageDescription);
        args.putString(ARG_PAGE_IMAGE, pageImage);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mPageDescription = getArguments().getString(ARG_PAGE_DESCRIPTION);
        mPageTitle = getArguments().getString(ARG_PAGE_TITLE);
        mPageImage = getArguments().getString(ARG_PAGE_IMAGE);

        mActionBarHeight = ((AppCompatActivity) getActivity()).getSupportActionBar().getHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ((NotifyingScrollView) rootView.findViewById(R.id.content)).setOnScrollChangedListener(mOnScrollChangedListener);
        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(R.id.step_title)).setText(mPageTitle);

        ((HtmlTextView) rootView.findViewById(R.id.step_description)).setHtmlFromString(mPageDescription,new HtmlTextView.LocalImageGetter());

        GifImageView gifImageView = (GifImageView) rootView.findViewById(R.id.step_image);
        gifImageView.setImageResource(getResId(mPageImage));
        gifDrawable = (GifDrawable) gifImageView.getDrawable();

        return rootView;
    }

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            float y = who.getScrollY();
            if (y >= mActionBarHeight && ((AppCompatActivity) getActivity()).getSupportActionBar().isShowing()) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            } else if ( y==0 && !((AppCompatActivity) getActivity()).getSupportActionBar().isShowing()) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            }

        }
    };

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    /**
     * Returns the page description represented by this fragment object.
     */
    public String getPageDescription() {
        return mPageDescription;
    }

    /**
     * Returns the page title represented by this fragment object.
     */
    public String getPageTitle() {
        return mPageTitle;
    }

    public int getResId(String source) {
        int id = getActivity().getResources().getIdentifier(source, "drawable", getActivity().getPackageName());

        if (id == 0) {
            // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
            id = getActivity().getResources().getIdentifier(source, "drawable", "android");
        }

        if (id == 0) {
            // prevent a crash if the resource still can't be found
            LOGD(TAG, "source could not be found: " + source);
            return getActivity().getResources().getIdentifier("blank", "drawable", getActivity().getPackageName());
        } else {

            return id;
        }
    }
}
