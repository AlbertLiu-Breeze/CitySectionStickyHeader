/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jusfoun.refreshscrollviewdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class JusfounSidebar extends View {
    private Paint paint;

    private float height;
    private ListView mListView;
    private Context context;
    private StickyAdapter mAdapter;
    
    public void setListView(ListView listView){
        mListView = listView;
    }

    public void setAdapter(StickyAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public JusfounSidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;        
        init();
    }

    public Context getcontext(){
        return context;
    }
    private String[] sections;

    private void init(){        
        sections= new String[]{"1","A","B","C","D","E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.DKGRAY);
        paint.setTextAlign(Align.CENTER);
        paint.setTextSize(16);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float center = getWidth() / 2;
        height = getHeight() / sections.length;
        for (int i = sections.length - 1; i > -1; i--) {
            canvas.drawText(sections[i], center, height * (i+1), paint);
        }
    }
    
    private int sectionForPoint(float y) {
        int index = (int) (y / height);
        if(index < 0) {
            index = 0;
        }
        if(index > sections.length - 1){
            index = sections.length - 1;
        }
        return index;
    }
    
    private void setHeaderTextAndscroll(MotionEvent event){
         if (mListView == null) {
                //check the mListView to avoid NPE. but the mListView shouldn't be null
                //need to check the call stack later             
                return;
            }         
        String headerString = sections[sectionForPoint(event.getY())];

        String[] adapterSections = (String[]) mAdapter.getSections();

        try {
            for (int i = adapterSections.length - 1; i > -1; i--) {
                if(adapterSections[i].toString().equals(headerString)){
                    mListView.setSelection(mAdapter.getPositionForSection(i));
                    break;
                }
            }

        } catch (Exception e) {

        }
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:{
            setHeaderTextAndscroll(event);
            return true;
        }
        case MotionEvent.ACTION_MOVE:{
            setHeaderTextAndscroll(event);
            return true;
        }
        case MotionEvent.ACTION_UP:
            setHeaderTextAndscroll(event);
            setBackgroundColor(Color.TRANSPARENT);
            return true;
        case MotionEvent.ACTION_CANCEL:

            setBackgroundColor(Color.TRANSPARENT);
            return true;
        }
        return super.onTouchEvent(event);
    }

}
