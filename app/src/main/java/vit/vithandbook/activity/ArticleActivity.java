package vit.vithandbook.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.helperClass.XmlParseHandler;
import vit.vithandbook.model.Article;


public class ArticleActivity extends ActionBarActivity {

    String topic;
    String subtopicName = "";
    String mainCategory = "";
    LinearLayout mainArticleLayout;
    XmlParseHandler parser;
    int bookymark = 0;
    ProgressBar load;
    String xmlData;
    TextView title,subtopic,circletopic;
    Menu menu;
    boolean bookmarked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        mainArticleLayout = (LinearLayout) findViewById(R.id.mainArticleLayout);
        load = (ProgressBar) findViewById(R.id.aaProgressbar);
        parser = new XmlParseHandler(this, mainArticleLayout);
        topic = getIntent().getStringExtra("topic");
        title = (TextView) findViewById(R.id.tv_title);
        subtopic = (TextView) findViewById(R.id.tv_subtopic);
        circletopic = (TextView) findViewById(R.id.tv_circle_topic);
        fetchContent();
        load.setVisibility(View.GONE);
        initialize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        this.menu = menu;
        Log.e("menu",String.valueOf(bookymark));
        if(bookymark == 1)
            menu.getItem(1).setIcon(R.drawable.ic_star_black_24dp);
        else
            menu.getItem(1).setIcon(R.drawable.ic_star_border_black_24dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.article_share:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //share.setType(Constants.INTENT_TYPE_PLAIN);
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.android_share_message_subject) + "\n\n" + mainCategory +"\n" + subtopicName + "\n" + topic + "\n\n" + parser.final_content + "\n\n" + getString(R.string.android_share_message_end));
                share.setType("text/plain");
                //share.putExtra(Intent.EXTRA_TEXT, getString(R.string.android_share_message_start, Constants.API_BASE_URL));
                //share.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.android_share_message_start));
                startActivity(Intent.createChooser(share, "Share Article using"));
                return true;
            case R.id.article_bookmark:
                bookymark = Math.abs(bookymark - 1);
                if (bookymark == 1)
                    menu.getItem(1).setIcon(R.drawable.ic_star_black_24dp);
                else
                    menu.getItem(1).setIcon(R.drawable.ic_star_border_black_24dp);
                SQLiteDatabase db = null;
                try {
                    db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH + DataBaseHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
                    db.execSQL("UPDATE articles SET 'bookmark' = ? WHERE topic = ?", new Object[]{bookymark, topic});
                    Log.e("Article Activity", "The final value of Bookmark:" + String.valueOf(bookymark));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void initialize() {
        setTitle("");
        title.setText(topic);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_light)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subtopic.setText(subtopicName);
        circletopic.setText(mainCategory.substring(0, 2));
        Resources colors = getResources();
        switch (mainCategory) {
            case "Academics":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.academics));
                break;
            case "College":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.college));
                break;
            case "Hostel":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.hostel));
                break;
            case "Student Organizations":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.stud));
                break;
            case "Life Hacks":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.lifehack));
                break;
            case "Around VIT and Vellore":
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.around));
                break;
            default:
                ((GradientDrawable)circletopic.getBackground()).setColor(colors.getColor(R.color.stud));
        }
    }

    void fetchContent() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH + DataBaseHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            cursor = db.rawQuery("SELECT `content` , `sub_category` , `main_category`, `bookmark` from `articles` Where topic = ? Limit 1", new String[]{topic});
            cursor.moveToFirst();
            bookymark = cursor.getInt(3);
            xmlData = cursor.getString(0);
            subtopicName = cursor.getString(1);
            mainCategory = cursor.getString(2);
            parser.parseXml(xmlData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
    }

}
