package vit.vithandbook;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hemant on 20-06-2015.
 */
public class BackConnect {
    String dateTime;
    public String getDateTime() {
        dateTime = null;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        dateTime = sdfDate.format(now);
        return dateTime;
    }
    public void getIData() throws IOException {
        String iData = null;
        HttpClient connect = new DefaultHttpClient();
        URL handbookBack = new URL("http://www.handbook-entry.herokuapp.com/api/updates");
        HttpURLConnection conn = (HttpURLConnection)handbookBack.openConnection();
        try
        {   conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(getDateTime());
            wr.flush();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            in.read();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }

    }

}
