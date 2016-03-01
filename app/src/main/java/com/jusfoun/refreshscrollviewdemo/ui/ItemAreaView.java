package com.jusfoun.refreshscrollviewdemo.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.refreshscrollviewdemo.R;

/**
 * Created by Albert on 2016/2/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class ItemAreaView extends LinearLayout {
    private Context mContext;
    private TextView mAreaName;
    public ItemAreaView(Context context) {
        super(context);
        mContext = context;
        initView();
        initWidgetAction();
    }

    public ItemAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initWidgetAction();
    }

    public ItemAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemAreaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
        initWidgetAction();
    }


    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.hot_city_item_layout, this, true);
        mAreaName = (TextView) findViewById(R.id.area_name);
    }

    private void initWidgetAction() {

    }

    public void setAreaName(String name){
        mAreaName.setText(name);
    }

}
