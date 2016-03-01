package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Albert on 2016/2/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class DBSqlDataUtil {
    private static DBSqlDataUtil dbSqlDataUtil = null;

    private SelectAreaHelper dbHelper;
    private SQLiteDatabase db;

    public DBSqlDataUtil(Context context) {
        dbHelper = new SelectAreaHelper(context, DBConstant.SELECT_AREA_TABLE_NAME, null, DBConstant.AREA_TABLE_VERSION);
        db = dbHelper.getReadableDatabase();
    }

    public static DBSqlDataUtil getInstance(Context mContext) {
        if (dbSqlDataUtil == null) {
            dbSqlDataUtil = new DBSqlDataUtil(mContext);
        }
        return dbSqlDataUtil;
    }

    public SQLiteDatabase getDB() {
        return db;
    }
}
