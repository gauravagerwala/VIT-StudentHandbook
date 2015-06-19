package vit.vithandbook.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import vit.vithandbook.R;
import vit.vithandbook.helperClass.XmlParseHandler;


public class ArticleActivity extends ActionBarActivity {

    String topic ;
    LinearLayout mainArticleLAyout ;
    XmlParseHandler parser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mainArticleLAyout = (LinearLayout)findViewById(R.id.mainArticleLayout);
        parser = new XmlParseHandler(this,mainArticleLAyout);
        initalizeActionBar();
        parser.parseXml(null);
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
}

