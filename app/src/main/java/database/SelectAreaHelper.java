package database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Albert on 2016/2/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:选择地区数据库helper
 */
public class SelectAreaHelper extends SQLiteOpenHelper {
    public SelectAreaHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SelectAreaHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAreaTable = "CREATE TABLE IF NOT EXISTS " + DBConstant.SELECT_AREA_TABLE_NAME
                + "(" + DBConstant.AREA_ID + " TEXT PRIMARY KEY,"
                + DBConstant.AREA_PINYIN + " TEXT,"
                + DBConstant.AREA_NAME + " TEXT);";
        db.execSQL(createAreaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table if exists " + DBConstant.SELECT_AREA_TABLE_NAME);
        }catch (Exception e){

        }

        onCreate(db);
    }
}
