package com.jusfoun.refreshscrollviewdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jusfoun.refreshscrollviewdemo.R;

import java.util.List;

import database.DBUtil;
import model.AreaModel;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SelectCityActivity extends ActionBarActivity {
    private StickyListHeadersListView mListView;
    private ListView mSearchAreaList;
    private EditText mSearchEditText;
    private StickyAdapter mAdapter;
    private SimpleAreaAdapter mSearchAdapter;
    private JusfounSidebar mSideBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        mContext = this;
        initView();
        initWidgetAction();
    }

    private void initView(){
        mAdapter = new StickyAdapter(this);
        mSearchAdapter = new SimpleAreaAdapter(mContext);
        mListView = (StickyListHeadersListView) findViewById(R.id.list);
        mSearchAreaList = (ListView) findViewById(R.id.search_area_list);
        mSearchAreaList.setAdapter(mSearchAdapter);
        mSearchEditText = (EditText) findViewById(R.id.search_edittext);
        mSideBar = (JusfounSidebar) findViewById(R.id.sidebar);
    }

    private void initWidgetAction(){
        mListView.setAdapter(mAdapter);
        mSideBar.setListView(mListView.getWrappedList());
        mListView.getWrappedList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof StickyAdapter.ViewHolder) {
                    StickyAdapter.ViewHolder holder = (StickyAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }

                    AreaModel model = holder.getData();
                    if (model != null) {
                        if (AreaModel.NORMAL.equals(model.getType())) {
                            Toast.makeText(mContext, "this is " + model.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        mSideBar.setAdapter(mAdapter);


        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input  = s.toString();
                if (input.length() == 0){
                    mListView.setVisibility(View.VISIBLE);
                    mSearchAreaList.setVisibility(View.GONE);
                }else {
                    searchArea(input);
                }
            }
        });
    }

    private void searchArea(String input){
        List<AreaModel> list = DBUtil.searchArea(mContext,input);
        if (list.size() > 0){
            mSearchAreaList.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mSearchAdapter.refresh(list);
        }else {

        }
    }

}
