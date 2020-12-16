package com.softcraft.calendar.fab;

import android.content.Context;
import android.util.AttributeSet;

public class FloatingActionButton extends androidx.appcompat.widget.AppCompatImageButton {

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.CENTER_INSIDE);
        //setScaleType(ScaleType.FIT_XY);
    }

}
