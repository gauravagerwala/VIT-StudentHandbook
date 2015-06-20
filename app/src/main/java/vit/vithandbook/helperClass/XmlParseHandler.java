package vit.vithandbook.helperClass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vit.vithandbook.R;

/**
 * Created by pulkit on 15/06/2015.
 */

public class XmlParseHandler
{
    public Context context ;
    public LinearLayout container;
    //ImageSaver saver ;

    public XmlParseHandler(Context context , LinearLayout container)
    {
        this.context = context ;
        this.container = container ;
        //saver = new ImageSaver(context);
    }
    public void parseXml(String xmlData)
    {
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            //sample xml data
            xmlData = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p><img src = 'name.png'/><p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>";
            XmlPullParser myparser = xmlFactoryObject.newPullParser();
            myparser.setInput(new StringReader(xmlData));
            int event = myparser.getEventType();
            String text = null ;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myparser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if("img".equals(name))
                        {
                            String imagename = myparser.getAttributeValue(null,"src");
                            AddImageView(imagename);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myparser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if ("p".equals(name)) {
                            AddTextView(text);
                        }
                        break;
                }
                event = myparser.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void AddTextView(String content)
    {
        TextView view = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setTextSize(20);
        view.setText(content);
        container.addView(view);
    }

    public void AddImageView(String filename) {
        final ImageView view = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        File file = new File(context.getExternalFilesDir(null)+"/Images");
       // saver.setParameters(new File(context.getExternalFilesDir(null)+"/Images"),view,filename);
        if(!file.exists())
        {
            file.mkdir();
        }
        if(file.exists())
        {
            File image = new File(file,filename);
            if(image.exists())
            {
                Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
                view.setImageBitmap(bmp);
            }
            else
            {
                //Picasso.with(context).load("http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/3d-transparent-glass-icons-arrows/006764-3d-transparent-glass-icon-arrows-arrowhead-solid-right.png")
                //        .into(saver);
                LoadImageFromNet(filename,true,view);

            }
        }
        else
        {
           // Picasso.with(context).load("http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/3d-transparent-glass-icons-arrows/006764-3d-transparent-glass-icon-arrows-arrowhead-solid-right.png")
            //.error(R.mipmap.ic_launcher).into(view);
            LoadImageFromNet(filename,false,view);


        }
        container.addView(view);
    }

    public void LoadImageFromNet(String name , boolean std,ImageView view)
    {
        InputStream input = null ;
        Bitmap myBitmap = null;
        try {


            URL url = new URL("http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/3d-transparent-glass-icons-arrows/006764-3d-transparent-glass-icon-arrows-arrowhead-solid-right.png");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(2000);
            connection.connect();
             input = connection.getInputStream();
             myBitmap = BitmapFactory.decodeStream(input);
            view.setImageBitmap(myBitmap);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"connection error loading image" ,Toast.LENGTH_LONG).show();
            view.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        if(std) {
            try {
                File file = new File(context.getExternalFilesDir(null), "/Images/" + name);
                FileOutputStream fout = new FileOutputStream(file);
                //int read;
                //byte[] data = new byte[1024];
                //while ((read = input.read(data)) != -1)
                 //   fout.write(data, 0, read);
                myBitmap.compress(Bitmap.CompressFormat.PNG,85,fout);
                fout.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

// picasso target class
class ImageSaver implements Target
{

    Context context ;
    ImageView view ;
    String imagename ;
    File storepath ;
    boolean savetodisk ;
    public ImageSaver(Context context)
    {
        this.context = context ;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from)
    {
        try
        {
                File image = new File(storepath,imagename);
                FileOutputStream fOut = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();

            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(bitmap);
                }
            });
        } catch (Exception e)
        {
            Log.d("store error", e.getMessage());
        }
    }
    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        view.setImageResource(R.mipmap.ic_launcher);
    }
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
        }
    }
  public void setParameters(File file , ImageView view , String name )
  {
      storepath = file ;
      this.view = view ;
      imagename = name ;
  }
}


