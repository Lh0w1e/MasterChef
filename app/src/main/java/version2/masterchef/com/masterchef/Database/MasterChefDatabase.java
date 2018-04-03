package version2.masterchef.com.masterchef.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import version2.masterchef.com.masterchef.DatabaseTables.FavoritesTable;

/**
 * Created by Colinares on 9/27/2017.
 */
public class MasterChefDatabase extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    public static final String DB_NAME = "MasterChef.sqlite";

    private static MasterChefDatabase mInstance = null;

    public MasterChefDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static MasterChefDatabase getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new MasterChefDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoritesTable.SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
