package version2.masterchef.com.masterchef.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.DatabaseTables.FavoritesTable;

/**
 * Created by Colinares on 10/9/2017.
 */
public class MasterChefDatabaseFunction {

    private static Context mContext;
    private static SQLiteDatabase mDb;
    private static Cursor mCursor;

    public static void init(Context context) {
        mContext = context;
        mDb = MasterChefDatabase.getInstance(mContext).getWritableDatabase();
    }

    //method for inserting new data.
    public static void insert(String tableName, ContentValues contentValues){
        mDb.insert(tableName, null, contentValues);
    }

    public static void delete(long id){
        mDb.delete(FavoritesTable.TABLE_NAME, "column_recipe_id = ?", new String[]{String.valueOf((id))});
    }

    public static boolean update(int id, String status){

        ContentValues update = new ContentValues();

        update.put(FavoritesTable.FAVORITE_STATUS, status);


        int result = mDb.update(FavoritesTable.TABLE_NAME, update, FavoritesTable.FAVORITE_COLUMN_RECIPE_ID + " = ?", new String[]{String.valueOf(id)});

        if(result > 0){
            return true;
        }else {
            return false;
        }
    }
}
