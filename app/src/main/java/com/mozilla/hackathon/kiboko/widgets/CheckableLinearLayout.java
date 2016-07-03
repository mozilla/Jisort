package com.mozilla.hackathon.kiboko.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.mozilla.hackathon.kiboko.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private boolean mChecked;
    private int mType;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedChangeListener;


    private static final int[] CHECKED_STATE_SET = {
            R.attr.is_checked
    };

    private static final int TYPE_RADIO_BUTTON = 0;
    private static final int TYPE_CHECK_BOX = 1;

    public CheckableLinearLayout(Context context) {
        this(context, null);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckableLinearLayout);

        mType = a.getInt(R.styleable.CheckableLinearLayout_type, TYPE_RADIO_BUTTON);
        boolean checked = a.getBoolean(R.styleable.CheckableLinearLayout_is_checked, false);
        setChecked(checked);

        a.recycle();
    }

    private void init(Context context, AttributeSet attrs) {

    }

    /**********************/
    /**   Handle clicks  **/
    /**********************/

    @Override
    public boolean performClick() {
        if (mType == TYPE_RADIO_BUTTON) {
            setChecked(true);
        } else if (mType == TYPE_CHECK_BOX) {
            toggle();
        }
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    /**************************/
    /**      Checkable       **/
    /**************************/

    public void toggle() {
        setChecked(!mChecked);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
//            setCheckedRecursive(this, checked);
            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }

            mBroadcasting = false;
        }
    }

    /**
     * Register a callback to be invoked when the checked state of this button changes.
     *
     * @param listener
     *            the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Interface definition for a callback.
     */
    public static interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a button has changed.
         *
         * @param button
         *            The button view whose state has changed.
         * @param isChecked
         *            The new checked state of button.
         */
        void onCheckedChanged(CheckableLinearLayout button, boolean isChecked);
    }

    private void setCheckedRecursive(ViewGroup parent, boolean checked) {
        int count = parent.getChildCount();
        for(int i = 0; i < count; i++ ) {
            View v = parent.getChildAt(i);
            if(v instanceof Checkable) {
                ((Checkable) v).setChecked(checked);
            }

            if(v instanceof ViewGroup) {
                setCheckedRecursive((ViewGroup)v, checked);
            }
        }
    }

    /**************************/
    /**   Drawable States    **/
    /**************************/

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    /**************************/
    /**   State persistency  **/
    /**************************/

    static class SavedState extends BaseSavedState {
        boolean checked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            checked = (Boolean)in.readValue(null);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(checked);
        }

        @Override
        public String toString() {
            return "CheckableLinearLayout.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " checked=" + checked + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.checked = isChecked();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;

        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked);
        requestLayout();
    }
}
