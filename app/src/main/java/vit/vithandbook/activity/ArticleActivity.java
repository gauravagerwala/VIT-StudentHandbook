package vit.vithandbook.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import vit.vithandbook.R;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.helperClass.XmlParseHandler;


public class ArticleActivity extends ActionBarActivity {

    String topic ;
    LinearLayout mainArticleLAyout ;
    XmlParseHandler parser ;
    ProgressBar load ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mainArticleLAyout = (LinearLayout)findViewById(R.id.mainArticleLayout);
        load=(ProgressBar)findViewById(R.id.aaProgressbar);
        parser = new XmlParseHandler(this,mainArticleLAyout);
        initalizeActionBar();
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute()
            {
                load.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {

                fetchContent();
                return null ;
            }
            @Override
            protected void onPostExecute(Void res)
            {
                load.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home : this.finish(); return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    void initalizeActionBar()
    {
        topic = getIntent().getStringExtra("topic");
        int color = getIntent().getIntExtra("color",0);
        setTitle(topic);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    void fetchContent()
    {
        SQLiteDatabase db = null ;
        Cursor cursor = null ;
        try
        {
            db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH+DataBaseHelper.DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
            cursor = db.rawQuery("SELECT `content` from `articles` Where topic = ? Limit 1",new String[]{topic});
            cursor.moveToFirst();
            String xmlData = cursor.getString(0);
            parser.parseXml(xmlData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
    }
}

