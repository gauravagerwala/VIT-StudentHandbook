package vit.vithandbook.helperClass;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.File;
import java.io.StringReader;

public class XmlParseHandler
{
    public Context context ;
    public LinearLayout container;

    public XmlParseHandler(Context context , LinearLayout container)
    {
        this.context = context ;
        this.container = container ;
    }
    public void parseXml(String xmlData)
    {
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            xmlData = "<p>ye hai mera pyara sa data </p>";
            XmlPullParser myparser = xmlFactoryObject.newPullParser();
            myparser.setInput(new StringReader(xmlData));
            int event = myparser.getEventType();
            String text = null ;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myparser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
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
            Log.d("xml error", e.getMessage());
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

    void AddImageView()
    {
        //File file = new File(context.getExternalFilesDir(null),)
    }
}
