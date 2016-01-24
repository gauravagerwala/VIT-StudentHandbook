package vit.vithandbook.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.fragment.BackHandlerFragment;
import vit.vithandbook.fragment.BookmarksFragment;
import vit.vithandbook.fragment.FeedbackFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.fragment.MapFragment;
import vit.vithandbook.fragment.UpdatesFragment;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.model.Article;


public class MainActivity extends AppCompatActivity {

    boolean searchMode = false;
    DrawerLayout drawerLayout;
    public BackHandlerFragment selectedFragment;
    public RelativeLayout relativeLayout;
    public AppBarLayout appBarLayout;
    SearchListAdapter ald ;
    public CollapsingToolbarLayout collapsingToolbarLayout ;
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
        relativeLayout = (RelativeLayout) findViewById(R.id.rv_header);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        getFragmentManager().beginTransaction().add(R.id.frame_layout_main, selectedFragment, "mainNavigator").commit();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Categories");
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.black));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_categories:
                        BackHandlerFragment fragment_main = new MainNavigator();
                        collapsingToolbarLayout.setTitle("Categories");
                        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.black));
                        appBarLayout.setExpanded(true);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .replace(R.id.frame_layout_main, fragment_main, "mainNavigator").commit();
                        selectedFragment = fragment_main;
                        break;
                    case R.id.drawer_map:
                        if(getFragmentManager().getBackStackEntryCount() > 1)
                            getFragmentManager().popBackStack();
                        if(getFragmentManager().getBackStackEntryCount() > 0)
                            getFragmentManager().popBackStack();
                        BackHandlerFragment fragment_map = new MapFragment();
                        collapsingToolbarLayout.setTitle("Map");
                        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                        if (Build.VERSION.SDK_INT >= 21)
                            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                        collapsingToolbarLayout.setTitle("Map");
                        appBarLayout.setExpanded(false);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .replace(R.id.frame_layout_main, fragment_map, "MapFragment").commit();
                        selectedFragment = fragment_map;
                        break;
                    case R.id.drawer_updates:
                        if(getFragmentManager().getBackStackEntryCount() > 1)
                            getFragmentManager().popBackStack();
                        if(getFragmentManager().getBackStackEntryCount() > 0)
                            getFragmentManager().popBackStack();
                        BackHandlerFragment fragment_updates = new UpdatesFragment();
                        collapsingToolbarLayout.setTitle("Updates");
                        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                        if (Build.VERSION.SDK_INT >= 21)
                            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                        appBarLayout.setExpanded(true);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .replace(R.id.frame_layout_main, fragment_updates, "MapFragment").commit();
                        selectedFragment = fragment_updates;
                        break;
                    case R.id.drawer_bookmarks:
                        if(getFragmentManager().getBackStackEntryCount() > 1)
                             getFragmentManager().popBackStack();
                        if(getFragmentManager().getBackStackEntryCount() > 0)
                            getFragmentManager().popBackStack();
                        BackHandlerFragment fragment_bookmark = new BookmarksFragment();
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
                        collapsingToolbarLayout.setTitle("Bookmarks");
                        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                        appBarLayout.setExpanded(true);
                        if (Build.VERSION.SDK_INT >= 21)
                            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .replace(R.id.frame_layout_main, fragment_bookmark, "BookmarkFragment").commit();
                        selectedFragment = fragment_bookmark;
                        break;
                    case R.id.drawer_feedback:
                        if(getFragmentManager().getBackStackEntryCount() > 1)
                            getFragmentManager().popBackStack();
                        if(getFragmentManager().getBackStackEntryCount() > 0)
                            getFragmentManager().popBackStack();
                        BackHandlerFragment fragment_feedback = new FeedbackFragment();
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
                        collapsingToolbarLayout.setTitle("Feedback");
                        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
                        appBarLayout.setExpanded(false);
                        if (Build.VERSION.SDK_INT >= 21)
                            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                        getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                                .replace(R.id.frame_layout_main, fragment_feedback, "FeedbackFragment").commit();
                        selectedFragment = fragment_feedback;
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
        if (id == R.id.action_search) {
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
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
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){ //replace this with actual function which returns if the drawer is open
            this.drawerLayout.closeDrawer(GravityCompat.START);     // replace this with actual function which closes drawer
        }
        else if ( getFragmentManager().getBackStackEntryCount() == 0)
        {
            super.onBackPressed();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 1)
        {
            /*Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity), "Back to mainNavigator", Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            snackbar.show();*/
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
            collapsingToolbarLayout.setTitle("Categories");
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.mainHeader));
            if (Build.VERSION.SDK_INT >= 21)
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));
            getFragmentManager().popBackStack();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 2)
        {
            /*Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity), "Back to subSection", Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            snackbar.show();*/
            /*relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
            collapsingToolbarLayout.setTitle("Categories");*/
            getFragmentManager().popBackStack();
        }
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

}