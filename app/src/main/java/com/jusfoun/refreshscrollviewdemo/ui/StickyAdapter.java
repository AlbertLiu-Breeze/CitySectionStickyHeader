package com.jusfoun.refreshscrollviewdemo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.jusfoun.refreshscrollviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import model.AreaModel;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import util.DemoApplication;

public class StickyAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {

    public final static int NORMAL = 0;
    public final static int HOT = 1;

    private final Context mContext;
    private int[] mSectionIndices;
    private String[] mSectionLetters;
    private LayoutInflater mInflater;

    private List<AreaModel> mAreaList;

    public StickyAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mAreaList = DemoApplication.getSortList();
        dummyHotArea();
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();


    }

    private void dummyHotArea(){
        AreaModel model = new AreaModel();
        model.setSectionletter("Hello");
        model.setType(AreaModel.HOT);
        List<AreaModel> children = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            AreaModel submodel = new AreaModel();
            submodel.setName("is" + i);
            children.add(submodel);
        }
        model.setChildren(children);
        mAreaList.add(0,model);
    }

    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        String lastFirstChar = mAreaList.get(0).getSectionletter();
        sectionIndices.add(0);
        for (int i = 1; i < mAreaList.size(); i++) {
            if (!mAreaList.get(i).getSectionletter().equals(lastFirstChar) ) {
                lastFirstChar = mAreaList.get(i).getSectionletter();
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private String[] getSectionLetters() {
        String[] letters = new String[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = mAreaList.get(mSectionIndices[i]).getSectionletter();
        }
        return letters;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        AreaModel model = mAreaList.get(position);
        if (AreaModel.NORMAL.equals(model.getType())){
            type = NORMAL;
        }else if (AreaModel.HOT.equals(model.getType())){
            type = HOT;
        }
        return type;
    }

    @Override
    public int getCount() {
        return mAreaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAreaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        AreaModel model = mAreaList.get(position);
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type){
                case NORMAL:
                    convertView = mInflater.inflate(R.layout.test_list_item_layout, parent, false);
                    break;
                case HOT:
                    convertView = mInflater.inflate(R.layout.hot_city_layout, parent, false);
                    break;
                default:
                    break;
            }
            /*if (AreaModel.NORMAL.equals(model.getType())){
                convertView = mInflater.inflate(R.layout.test_list_item_layout, parent, false);
            }else if (AreaModel.HOT.equals(model.getType())){
                convertView = mInflater.inflate(R.layout.hot_city_layout, parent, false);
            }*/
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.updateView(position);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        // set header text as first char in name
        //CharSequence headerChar = mCountries[position].subSequence(0, 1);
        CharSequence headerChar = mAreaList.get(position).getSectionletter();
        holder.text.setText(headerChar);

        return convertView;
    }

    /**
     * Remember that these have to be static, postion=1 should always return
     * the same Id that is.
     */
    @Override
    public long getHeaderId(int position) {
        // return the first character of the country as ID because this is what
        // headers are based upon
        return mAreaList.get(position).getSectionletter().subSequence(0, 1).charAt(0);
    }

    @Override
    public int getPositionForSection(int section) {
        if (mSectionIndices.length == 0) {
            return 0;
        }
        
        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    public void clear() {
        mSectionIndices = new int[0];
        mSectionLetters = new String[0];
        notifyDataSetChanged();
    }

    public void restore() {
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        //TODO 完善itemview的点击事件
        TextView text;
        FlowLayout mFlowLayout;
        AreaModel data;

        public ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
            mFlowLayout = (FlowLayout) view.findViewById(R.id.hot_city);
        }

        public AreaModel getData() {
            return data;
        }

        public void updateView(int position){
            AreaModel model = mAreaList.get(position);
            data = mAreaList.get(position);
            text.setText(model.getName());
            if (AreaModel.HOT.equals(model.getType())){
                mFlowLayout.removeAllViews();
                for (final AreaModel submodel : model.getChildren()){
                    ItemAreaView view = new ItemAreaView(mContext);
                    view.setAreaName(submodel.getName());
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "this is " + submodel.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    mFlowLayout.addView(view);
                }
            }else if (AreaModel.NORMAL.equals(model.getType())){

            }
        }
    }

}
