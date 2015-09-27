package vit.vithandbook.activity;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.fragment.BackHandlerFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.fragment.MapFragment;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.model.Article;


public class MainActivity extends ActionBarActivity {

    boolean searchMode = false;
    DrawerLayout drawerLayout;
    public BackHandlerFragment selectedFragment;
    public AppBarLayout appBarLayout;
    ListView searchList;
    SearchListAdapter ald ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    Toolbar toolbar ;
    ProgressBar load,searchloadbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    initialize();
                }
            }
        }.execute();

    }

    private void initialize(){
        selectedFragment = new MainNavigator();
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        getFragmentManager().beginTransaction().add(R.id.frame_layout_main, selectedFragment, "mainNavigator").commit();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Categories");
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.white));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_categories:
                        Fragment fragment_main = new MainNavigator();
                        appBarLayout.setExpanded(true);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .hide(selectedFragment).add(R.id.frame_layout_main, fragment_main, "mainNavigator").addToBackStack(null).commit();
                        break;
                    case R.id.drawer_map:
                        Fragment fragment_map = new MapFragment();
                        appBarLayout.setExpanded(false);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .hide(selectedFragment).add(R.id.frame_layout_main, fragment_map, "MapFragment").addToBackStack(null).commit();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
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
