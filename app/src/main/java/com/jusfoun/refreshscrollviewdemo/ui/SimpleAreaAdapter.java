package com.jusfoun.refreshscrollviewdemo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.refreshscrollviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import model.AreaModel;

/**
 * Created by Albert on 2016/2/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SimpleAreaAdapter extends BaseAdapter {
    private Context mContext;
    private List<AreaModel> mData;
    public SimpleAreaAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.test_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.updateview(position);
        return convertView;
    }

    public void refresh(List<AreaModel> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetInvalidated();
    }

    class ViewHolder{
        TextView textView;
        AreaModel data;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.text);
        }

        public void updateview(int position){
            AreaModel model = mData.get(position);
            data = mData.get(position);
            textView.setText(model.getName());
        }
    }
}
