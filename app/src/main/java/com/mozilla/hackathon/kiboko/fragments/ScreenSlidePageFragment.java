package com.mozilla.hackathon.kiboko.fragments;

/**
 * Created by mwadime on 6/9/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_PAGE_TITLE = "page_title";
    public static final String ARG_PAGE_DESCRIPTION = "page_description";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private String mPageDescription, mPageTitle;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber, String pageTitle, String pageDescription) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_PAGE_TITLE, pageTitle);
        args.putString(ARG_PAGE_DESCRIPTION, pageDescription);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(R.id.step_title)).setText(mPageTitle);

        ((HtmlTextView) rootView.findViewById(R.id.step_description)).setHtmlFromString(mPageDescription,new HtmlTextView.LocalImageGetter());

        return rootView;
    }

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
}
