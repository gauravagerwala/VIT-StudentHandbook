package vit.vithandbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hemant on 20-06-2015.
 */
public class BackConnect {
    Context context;
    String PREF_KEY = "handbook_prefs";

    public BackConnect(Context context)
    {
        this.context=context;
    }
    public String getDateTime()
    {
        SharedPreferences prefs =  context.getSharedPreferences(PREF_KEY,context.MODE_PRIVATE);
        String date = prefs.getString("last_sync",null);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String dateTime = df.format(new Date());
        prefs.edit().putString("last_sync",dateTime);
        if(date!=null)
            return date ;
        else
         return dateTime ;

    }
    public void getUpdatedData()
    {
         String jsonReply = null;
        try {
            String dateTime = getDateTime();
            String postData = "timestamp=" + dateTime;
            URL url = new URL("http://handbook-entry.herokuapp.com/api/updates");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();
            final int resposnecode = conn.getResponseCode();
            if(resposnecode!=200)
            {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Error fetching updates error code : " +  Integer.toString(resposnecode),Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line ;
            while((line = in.readLine())!=null)
            {
                jsonReply+=line;
            }
            parseJsonData(jsonReply);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void parseJsonData(String jsonData) throws Exception
    {
        SQLiteDatabase db = context.openOrCreateDatabase("Handbook",context.MODE_PRIVATE,null);
        db.beginTransaction();
       JSONArray array = new JSONArray(jsonData);
       for(int i = 0 ; i< array.length();i++)
       {
           JSONObject obj = array.getJSONObject(i);
           ContentValues cv = getCV(obj);
           db.insertOrThrow("articles",null,cv);
       }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    ContentValues getCV(JSONObject object) throws Exception
    {
        ContentValues values = new ContentValues();
        values.put("main_category",object.getString("main_category"));
        values.put("sub_category",object.getString("sub_category"));
        values.put("topic",object.getString("topic"));
        values.put("content",object.getString("content"));
        values.put("tags",object.getString("tags"));
        return values;
    }
}
