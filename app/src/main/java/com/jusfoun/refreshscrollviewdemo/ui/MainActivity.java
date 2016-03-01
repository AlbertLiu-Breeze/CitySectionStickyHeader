package com.jusfoun.refreshscrollviewdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jusfoun.refreshscrollviewdemo.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class MainActivity extends ActionBarActivity {

    private PtrClassicFrameLayout mPtrFrame;
    private ScrollView mScrollView;
    private HeadView mHeadView;
    private Context mContext;

    private TextView textview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initWidgetAction();
    }

    private void initView(){
        mScrollView = (ScrollView) findViewById(R.id.rotate_header_scroll_view);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_web_view_frame);
        mHeadView = (HeadView) findViewById(R.id.headview);
        textview1 = (TextView) findViewById(R.id.textview1);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
    }

    private void initWidgetAction(){
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 100);
            }
        });

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {

                float y = mScrollView.getScrollY();
                if (y > 20) {
                    //触发headview动画
                    mHeadView.expand();
                } else {
                    mHeadView.reset();
                }
            }


        });

        mHeadView.setmHeadViewListener(new HeadView.HeadViewListener() {
            @Override
            public void selectCity() {
                Intent intent = new Intent(mContext, SelectCityActivity.class);
                //startActivityForResult(intent,1);
                startActivity(intent);
            }
        });


    }
}
