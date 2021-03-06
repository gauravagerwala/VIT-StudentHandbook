package vit.vithandbook.helperClass;

/**
 * Created by pulkit on 27/06/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    public static String DB_PATH;
    public static String DB_NAME = "Handbook";
    private SQLiteDatabase myDataBase;
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        Log.d("path", DB_PATH + DB_NAME);
    }

    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.d("exists", "true");
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
                createSearchTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDataBase() {
        boolean checkdb = false;
        try {
            String myPath = context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return checkdb;
    }

    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open("articleSQL");
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("dbcopied", "copied");
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void createSearchTable() {

        Cursor cursor = null;
        try {
            myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            myDataBase.execSQL("create virtual table search using fts3 (" +
                    "`_id` integer ," +
                    "`type` integer ," +
                    "`content` text ); ");
            cursor = myDataBase.rawQuery("select _id ,topic,sub_category,tags from articles", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String content = ";" + cursor.getString(1) + ";" + cursor.getString(2) + ";" + cursor.getString(3);
                int _id = cursor.getInt(0);
                myDataBase.execSQL("insert into search(_id,type,content) values(?,?,?)", new Object[]{_id, 1, content});

                cursor.moveToNext();
            }
            Log.d("dbsearch ","search on");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            myDataBase.close();
        }
    }

}
