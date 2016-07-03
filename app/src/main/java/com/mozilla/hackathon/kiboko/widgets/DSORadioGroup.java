package com.mozilla.hackathon.kiboko.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by mwadime on 7/3/2016.
 */
public class DSORadioGroup extends RelativeLayout {

    private ArrayList<View> mCheckables = new ArrayList<View>();

    public DSORadioGroup(Context context) {
        super(context);
    }

    public DSORadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DSORadioGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        parseChild(child);
    }

    public void parseChild(final View child)
    {
        if(child instanceof Checkable)
        {
            mCheckables.add(child);
            child.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    for(int i = 0; i < mCheckables.size();i++)
                    {
                        Checkable view = (Checkable) mCheckables.get(i);
                        if(view == v)
                        {
                            ((Checkable)view).setChecked(true);
                        }
                        else
                        {
                            ((Checkable)view).setChecked(false);
                        }
                    }
                }
            });
        }
        else if(child instanceof ViewGroup)
        {
            parseChildren((ViewGroup)child);
        }
    }

    public void parseChildren(final ViewGroup child)
    {
        for (int i = 0; i < child.getChildCount();i++)
        {
            parseChild(child.getChildAt(i));
        }
    }
}
