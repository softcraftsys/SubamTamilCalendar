package com.softcraft.calendar.ServiceAndOthers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CalenderTextView extends TextView {

    private Typeface mTypeface;

    public CalenderTextView(Context context) {
        super(context);
    }
    public CalenderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
       if(isInEditMode()) {
        }
    }
    public CalenderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()) {
        }
    }
    public CalenderTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(isInEditMode()) {
        }

    }
    private void initView(Context context, AttributeSet attrs){
    // do the needed stuff here
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "shree0802.ttf");
        }
        setTypeface(mTypeface);
    }
}
