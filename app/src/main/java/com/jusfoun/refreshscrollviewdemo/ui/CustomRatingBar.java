package com.jusfoun.refreshscrollviewdemo.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RatingBar;

/**
 * Created by Albert on 2016/2/29.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class CustomRatingBar extends RatingBar {
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRatingBar(Context context) {
        super(context);
    }
}
