package version2.masterchef.com.masterchef.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import version2.masterchef.com.masterchef.Constants.Constants;

/**
 * Created by Colinares on 9/27/2017.
 */
public class MasterChefDatabaseFromAssets extends SQLiteOpenHelper {
    public final String TAG = MasterChefDatabaseFromAssets.class.getSimpleName();
    public static final String DB_NAME = Constants.DATABASE_NAME;
    public static String DB_PATH = "";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public MasterChefDatabaseFromAssets(Context context){
        super(context, DB_NAME, null, 1);

        if(Build.VERSION.SDK_INT >= 15){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }else{
            DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
        }

        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void checkAndCopyDatabase(){
        boolean dbExist = checkDatabase();
        if(dbExist){
            Log.e(TAG,"DB already exist");
        }else{
            this.getReadableDatabase();
        }

        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error copying DB");
        }
    }

    public void openDatabase(){
        String myPath = DB_PATH+DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public synchronized void close(){
        if(mDatabase != null){
            mDatabase.close();
        }
        super.close();
    }

    public boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH+DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void copyDatabase() throws IOException{

        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buff = new byte[1024];
        int length;
        while ((length = inputStream.read(buff)) > 0){
            outputStream.write(buff, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    public Cursor selectQuery(String query){
        return mDatabase.rawQuery(query,null);

    }

}








