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
import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.fragment.BackHandlerFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.fragment.SubSectionFragment;
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
                    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    collapsingToolbarLayout.setTitle("VIT Handbook");
                    collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.mainHeader));
                }
            }
        }.execute();

    }

    void initialize()
    {
        mainNavigator = (LinearLayout) findViewById(R.id.mainNavigator);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        mainHeader = (LinearLayout) findViewById(R.id.mainHeader);
        searchList = (ListView)findViewById(R.id.rvSearch);
        searchloadbar = (ProgressBar)findViewById(R.id.searchprogressbar);
        searchbox = (EditText)findViewById(R.id.search_box);
        searchbox.addTextChangedListener(new AutoCompleteWatcher(this));
        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClick(view);
            }
        });
        back = new BackConnect(this);
        ald = new SearchListAdapter(this,R.layout.search_card,new ArrayList<Article>());
        searchList.setAdapter(ald);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onSearchItemClick(adapterView,view,i,l);
            }
        });
    }

    public void onSearchItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, ArticleActivity.class);
        int color = ((SearchListAdapter.SearchViewHolder) view.getTag()).color;
        intent.putExtra("topic", ((Article)parent.getAdapter().getItem(position)).topic);
        intent.putExtra("color", color);
        startActivity(intent);
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

        return super.onOptionsItemSelected(item);
    }

    public void searchClick(View view) {
        if (!searchMode) {
            mainNavigator.animate().translationY(-mainNavigator.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mainNavigator.setVisibility(View.GONE);
                            searchLayout.setVisibility(View.VISIBLE);
                            searchMode = true;
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        if (searchMode) {
            mainNavigator.setVisibility(View.VISIBLE);
            mainNavigator.animate().translationY(0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            searchLayout.setVisibility(View.GONE);
                            searchMode = false;
                        }
                    });
        } else if (getFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed();
        else  {

            getFragmentManager().popBackStack();

            if (getFragmentManager().getBackStackEntryCount() == 1)
            {
                AnimateMainHeader(null, true);
            }
        }
    }

    void setupDatabase()
    {
        DataBaseHelper helper = new DataBaseHelper(this);
        helper.createDataBase();
    }



    void AnimateMainHeader(ViewGroup view, boolean back) {
        int startColor = ((ColorDrawable) mainHeader.getBackground()).getColor();
        int startColorDark = getResources().getColor(R.color.mainHeaderDark);
        if (Build.VERSION.SDK_INT >= 21)
            startColorDark = getWindow().getStatusBarColor();
        int endcolor;
        int endColorDark;
        if (back) {
            endcolor = getResources().getColor(R.color.mainHeader);
            endColorDark = getResources().getColor(R.color.mainHeaderDark);
        } else {
            endcolor = ((ColorDrawable) ((LinearLayout) view.getChildAt(0)).getBackground()).getColor();
            switch (view.getTag().toString()) {
                case "Academics":
                    endColorDark = getResources().getColor(R.color.academicsDark);
                    break;
                case "College":
                    endColorDark = getResources().getColor(R.color.collegeDark);
                    break;
                case "Hostel":
                    endColorDark = getResources().getColor(R.color.hostelDark);
                    break;
                case "Student Organisations":
                    endColorDark = getResources().getColor(R.color.studDark);
                    break;
                case "Life Hacks":
                    endColorDark = getResources().getColor(R.color.lifehackDark);
                    break;
                case "Around Vit":
                    endColorDark = getResources().getColor(R.color.aroundDark);
                    break;
                default:
                    endColorDark = getResources().getColor(R.color.mainHeaderDark);
            }

        }
        final ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endcolor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mainHeader.setBackgroundColor((int) animator.getAnimatedValue());

            }
        });
        final ValueAnimator animator1 = ValueAnimator.ofObject(new ArgbEvaluator(), startColorDark, endColorDark);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Build.VERSION.SDK_INT >= 21)
                    getWindow().setStatusBarColor((int) animator1.getAnimatedValue());

            }
        });
        animator1.start();
        animator.start();

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
