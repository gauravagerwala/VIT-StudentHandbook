package vit.vithandbook.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.fragment.BackHandlerFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.helperClass.AutoCompleteWatcher;
import vit.vithandbook.helperClass.BackConnect;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.model.Article;


public class MainActivity extends ActionBarActivity {

    boolean searchMode = false;
    public BackHandlerFragment selectedFragment;
    ListView searchList;
    SearchListAdapter ald ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    Toolbar toolbar ;
    LinearLayout mainNavigator, searchLayout, mainHeader;
    EditText searchbox;
    ProgressBar load,searchloadbar;
    BackConnect back;
    public int SuggestionHeight = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    initialize();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
               // mainHeader.setVisibility(View.GONE);
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                setupDatabase();
                return  null ;
            }

            @Override
            protected void onPostExecute(Void res) {
               // mainHeader.setVisibility(View.VISIBLE);
              //  setSuggestionColors();
                if (savedInstanceState == null) {
                    selectedFragment = new MainNavigator();
                    getFragmentManager().beginTransaction().add(R.id.frame_layout_main, selectedFragment, "mainNavigator").commit();
                    toolbar = (Toolbar)findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeButtonEnabled(true);
                  //  toolbar.setBackgroundColor();
                    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    collapsingToolbarLayout.setTitle("VIT Handbook");
                    collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                    collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.white));
                }
            }
        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        else if ( id == android.R.id.home)
        {
            onBackPressed();
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if ( getFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed();
        else
        {
            getFragmentManager().popBackStack();
        }
    }

    void setupDatabase()
    {
        DataBaseHelper helper = new DataBaseHelper(this);
        helper.createDataBase();
    }

    public class searchTask extends AsyncTask<String,Void,ArrayList<Article>>
    {
        Context activity ;
        @Override
        protected void onPreExecute()
        {
            searchloadbar.setVisibility(View.VISIBLE);
        }

        public searchTask(Context obj)
        {
         activity=obj;
        }
        @Override
        protected ArrayList<Article> doInBackground(String ... params)
        {
            ArrayList<Article> topics = new ArrayList<>();
            SQLiteDatabase db = null;
            Cursor cursor =null;
            try
            {
                Log.d("data", params[0]);
                db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH + DataBaseHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
                cursor = db.rawQuery("SELECT articles.main_category , articles.sub_category , articles.topic FROM articles " +
                        "INNER JOIN search" +
                        " ON articles._id = search._id " +
                        "WHERE search.content match '"+params[0]+"*'",null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    topics.add(new Article(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                    cursor.moveToNext();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                cursor.close();
                db.close();
            }
            return topics;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> results)
        {
            searchloadbar.setVisibility(View.GONE);
            ald.setData(results);
        }
        public void cancelAndClear()
        {
            cancel(true);
            if(ald!=null)
            ald.clear();
        }
    }
}
