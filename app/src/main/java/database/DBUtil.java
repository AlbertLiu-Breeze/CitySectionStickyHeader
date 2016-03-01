package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.AreaModel;
import util.HanziToPinyin;

/**
 * Created by Albert on 2016/2/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class DBUtil {

    /**
     * 存储城市表
     * @param context
     * @param list
     */
    public static void saveArea(Context context,List<AreaModel> list){
        SQLiteDatabase db = DBSqlDataUtil.getInstance(context).getDB();
        db.beginTransaction();
        try {
            if (db.isOpen()){
                for (AreaModel model : list){
                    ContentValues values = new ContentValues();
                    values.put(DBConstant.AREA_ID,model.getId());
                    values.put(DBConstant.AREA_NAME,model.getName());
                    values.put(DBConstant.AREA_PINYIN,model.getPinyin());
                    db.replace(DBConstant.SELECT_AREA_TABLE_NAME,null,values);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            db.endTransaction();
        }


    }

    /**
     * 获取城市表
     * @param context
     * @return
     */
    public static List<AreaModel> getAreaList(Context context){
        SQLiteDatabase db = DBSqlDataUtil.getInstance(context).getDB();
        List<AreaModel> list = new ArrayList<>();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from " + DBConstant.SELECT_AREA_TABLE_NAME ,null);
            try {
                while (cursor.moveToNext()) {
                    String areaID = cursor.getString(cursor.getColumnIndex(DBConstant.AREA_ID));
                    String areaName = cursor.getString(cursor.getColumnIndex(DBConstant.AREA_NAME));
                    String areaPinyin = cursor.getString(cursor.getColumnIndex(DBConstant.AREA_PINYIN));
                    AreaModel model = new AreaModel();
                    model.setId(areaID);
                    model.setName(areaName);
                    model.setPinyin(areaPinyin);
                    model.setType(AreaModel.NORMAL);
                    model.setSectionletter(areaPinyin.substring(0, 1));
                    list.add(model);
                }
            }catch (Exception e){

            }finally {
                cursor.close();
            }
        }
        return list;
    }

    /**
     *
     * @param context
     * @param input 需要搜索的关键字
     * @return 命中的区域列表，进行了简单的排序
     */
    public static List<AreaModel> searchArea(Context context,String input){
        List<AreaModel> list = new ArrayList<>();
        input = HanziToPinyin.getInstance().getAllStr(input).toUpperCase();
        List<AreaModel> localList = getAreaList(context);
        for (AreaModel model : localList){
            if (model.getPinyin().contains(input)){
                int index = model.getPinyin().indexOf(input);
                model.setIndex(index);
                list.add(model);
            }
        }
        Collections.sort(list,new Comparator<AreaModel>() {

            @Override
            public int compare(AreaModel lhs, AreaModel rhs) {
                return (lhs.getIndex() + "").compareTo(rhs.getIndex() + "");
            }
        });
        return list;
    }
}
