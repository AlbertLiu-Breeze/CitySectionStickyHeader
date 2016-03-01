package com.jusfoun.refreshscrollviewdemo.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.refreshscrollviewdemo.R;

/**
 * Created by Albert on 2016/2/24.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class HeadView extends RelativeLayout{
    private Context mContext;

    private TextView mScope;
    private EditText mEditText;
    private ImageView mShowPopu;
    private RelativeLayout mEditLayout,mScopeLayout,mShoupopuLayout;


    private boolean expanded = false,haslayout = false;
    private float mScaleX = 1f;
    private int leftmargin,rightmargin,minleftmargin,minrightmargin;
    public HeadView(Context context) {
        super(context);
        mContext = context;
        initView();
        initWidgetAction();
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initWidgetAction();
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
        initWidgetAction();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.head_view_layout, this, true);
        mScope = (TextView) findViewById(R.id.scope_name);
        mEditText = (EditText) findViewById(R.id.search_edittext);
        mShowPopu = (ImageView) findViewById(R.id.show_popu);
        mEditLayout = (RelativeLayout) findViewById(R.id.edit_text_layout);
        mScopeLayout = (RelativeLayout) findViewById(R.id.scope_layout);
        mShoupopuLayout = (RelativeLayout) findViewById(R.id.show_popu_layout);
    }

    private void initWidgetAction(){
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final int width = display.getWidth();
        ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (!haslayout){
                    int editWidth = mEditLayout.getWidth();
                    mScaleX = (float)(width - 20 ) / editWidth;
                    RelativeLayout.LayoutParams layoutParamsScope = (LayoutParams) mScopeLayout.getLayoutParams();
                    leftmargin = layoutParamsScope.leftMargin + layoutParamsScope.width + layoutParamsScope.rightMargin;
                    minleftmargin = layoutParamsScope.leftMargin;
                    RelativeLayout.LayoutParams layoutParamsShow = (LayoutParams) mShoupopuLayout.getLayoutParams();
                    rightmargin = layoutParamsShow.leftMargin + ((mShoupopuLayout.getVisibility()==GONE) ? 0 :layoutParamsShow.width) + layoutParamsShow.rightMargin;
                    minrightmargin = layoutParamsShow.rightMargin;
                    RelativeLayout.LayoutParams layoutParamsEdit = (LayoutParams) mEditLayout.getLayoutParams();
                    layoutParamsEdit.leftMargin = leftmargin;
                    layoutParamsEdit.rightMargin = rightmargin;
                    mEditLayout.setLayoutParams(layoutParamsEdit);
                    invalidate();
                    haslayout = true;
                }
            }
        });

        mScopeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeadViewListener != null){
                    mHeadViewListener.selectCity();
                }
            }
        });
    }



    public void expand(){
        if (!expanded){
            ValueAnimator animator = ObjectAnimator.ofFloat(0,1);
            animator.setDuration(300);
            animator.setInterpolator(new BounceInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float vale = (float) animation.getAnimatedValue();
                    ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) mEditLayout.getLayoutParams();
                    layoutParams.leftMargin = (int) (leftmargin * (1 - vale)) < minleftmargin ? minleftmargin : (int) (leftmargin * (1 - vale));
                    layoutParams.rightMargin = (int) (rightmargin * (1 - vale)) < minrightmargin ? minrightmargin : (int) (rightmargin * (1 - vale));
                    mEditLayout.setLayoutParams(layoutParams);
                    invalidate();
                }
            });
            animator.start();
            expanded = true;
        }
    }

    public void reset(){
        if (expanded){
            ValueAnimator animator = ObjectAnimator.ofFloat(0,1);
            animator.setDuration(300);
            //animator.setInterpolator(new BounceInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float vale = (float) animation.getAnimatedValue();
                    ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) mEditLayout.getLayoutParams();
                    layoutParams.leftMargin = (int) (leftmargin * vale) < minleftmargin ? minleftmargin : (int) (leftmargin * vale);
                    layoutParams.rightMargin = (int) (rightmargin * vale) < minrightmargin ? minrightmargin : (int) (rightmargin * vale);
                    mEditLayout.setLayoutParams(layoutParams);
                    invalidate();
                }
            });
            animator.start();
            expanded = false;
        }
    }

    private HeadViewListener mHeadViewListener;

    public void setmHeadViewListener(HeadViewListener mHeadViewListener) {
        this.mHeadViewListener = mHeadViewListener;
    }

    public interface HeadViewListener{
        void selectCity();
    }
}
