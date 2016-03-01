package util;

import android.app.Application;
import android.content.Context;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.refreshscrollviewdemo.R;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.DBUtil;
import model.AreaModel;

/**
 * Created by Albert on 2016/2/25.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class DemoApplication extends Application {
    public static List<AreaModel> mSearchScopeList;
    public static Context application = null;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    //进行首字母排序
    public static List<AreaModel> getSortList(){
        List<AreaModel> list = new ArrayList<>();
        list.addAll(getLocationList());
        Collections.sort(list, new Comparator<AreaModel>() {

            @Override
            public int compare(AreaModel lhs, AreaModel rhs) {
                String lhsHeader = lhs.getPinyin().substring(0,1);
                String rhsHeader = rhs.getPinyin().substring(0,1);
                return lhsHeader.compareToIgnoreCase(rhsHeader);
            }
        });

        return list;
    }

    public static List<AreaModel> getLocationList() {
        if (mSearchScopeList == null) {
            mSearchScopeList = new ArrayList<>();
            List<AreaModel> dbList = DBUtil.getAreaList(application);
            if (dbList.size() > 0){
                mSearchScopeList.addAll(dbList);
                return mSearchScopeList;
            }
            try {
                InputStream is = application.getResources().openRawResource(R.raw.provice_city_area);
                String value = LibIOUtil.convertStreamToJson(is);
                Type listType = new TypeToken<ArrayList<AreaModel>>() {
                }.getType();
                List<AreaModel> list = new Gson().fromJson(value, listType);
                mSearchScopeList.clear();
                //精确到二级级别
                for (AreaModel model : list) {
                    if (model.getChildren() != null){
                        for (AreaModel submodel : model.getChildren()){
                            String pinyin =  PinyinHelper.convertToPinyinString(submodel.getName(), "", PinyinFormat.WITHOUT_TONE).toUpperCase();
                            submodel.setPinyin(pinyin);
                            submodel.setType(AreaModel.NORMAL);
                            submodel.setSectionletter(pinyin.substring(0, 1));
                            mSearchScopeList.add(submodel);

                        }
                    }else {
                        String pinyin = HanziToPinyin.getInstance().getAllStr(model.getName());
                        model.setPinyin(pinyin);
                        model.setType(AreaModel.NORMAL);
                        model.setSectionletter(pinyin.substring(0, 1));
                        mSearchScopeList.add(model);
                    }

                }
                DBUtil.saveArea(application,mSearchScopeList);

            } catch (Exception exception) {

            } finally {

            }
            return mSearchScopeList;
        } else {
            return mSearchScopeList;
        }
    }
}
