package vit.vithandbook;

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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hemant on 20-06-2015.
 */
public class BackConnect {
    String dateTime;
    HttpClient client;
    public String getDateTime() {
        dateTime = null;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        dateTime = sdfDate.format(now);
        return dateTime.toString();
    }
    /*  HttpResponse respon = client.execute(fetch);
        int status = respon.getStatusLine().getStatusCode();
        if (status == 200) {
            HttpEntity en = respon.getEntity();
            String gotData = EntityUtils.toString(en);
            article = new JSONArray(gotData);*/
    public String getIData() throws IOException {
        String jsonReply = null;
        JSONArray article = null;
        HttpClient connect = new DefaultHttpClient();
        URL url = new URL("http://handbook-entry.herokuapp.com/api/updates");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.connect();
        DataOutputStream dout;
        int status = conn.getResponseCode();
        jsonReply = "YAY";
        try
        {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            dout = new DataOutputStream(conn.getOutputStream ());
            dout.writeBytes(URLEncoder.encode(getDateTime(), "UTF-8"));
            dout.flush();
            dout.close();
            switch (status) {
                case 200:
                case 201:
                    InputStream response = conn.getInputStream();
                    jsonReply = convertStreamToString(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return jsonReply;
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
