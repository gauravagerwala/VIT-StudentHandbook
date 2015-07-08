package vit.vithandbook.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.adapter.ArticleListAdapter;
import vit.vithandbook.adapter.CardListAdapter;
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
    GridLayout mainNavGrid;
    public BackHandlerFragment selectedFragment;
    ListView searchList;
    SearchListAdapter ald ;
    LinearLayout mainNavigator, searchLayout, mainHeader;
    public LinearLayout suggestionContainer;
    EditText searchbox;
    ProgressBar load,searchloadbar;
    BackConnect back;
    public int SuggestionHeight = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mainHeader.setVisibility(View.GONE);
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                setupDatabase();
                return  null ;
            }

            @Override
            protected void onPostExecute(Void res) {
                mainHeader.setVisibility(View.VISIBLE);
                setSuggestionColors();
                if (savedInstanceState == null) {
                    selectedFragment = new MainNavigator();
                    getFragmentManager().beginTransaction().add(R.id.mainNavigator, selectedFragment, "mainNavigator").commit();
                }
            }
        }.execute();

    }

    void initialize()
    {
        mainNavigator = (LinearLayout) findViewById(R.id.mainNavigator);
        mainNavGrid = (GridLayout) findViewById(R.id.mainNavGrid);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        mainHeader = (LinearLayout) findViewById(R.id.mainHeader);
        searchList = (ListView)findViewById(R.id.rvSearch);
        suggestionContainer = (LinearLayout) findViewById(R.id.suggestionContainer);
        searchloadbar = (ProgressBar)findViewById(R.id.searchprogressbar);
        searchbox = (EditText)findViewById(R.id.search_box);
        searchbox.addTextChangedListener(new AutoCompleteWatcher(this));
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(SuggestionHeight<0) {
            SuggestionHeight = suggestionContainer.getHeight();
            Log.d("height", Integer.toString(SuggestionHeight));
        }
    }

    public void navigate(View view) {
        AnimateMainHeader((ViewGroup) view, false);
        MainNavigator main = (MainNavigator) getFragmentManager().findFragmentByTag("mainNavigator");
        String category = (String) view.getTag();
        BackHandlerFragment fragment = SubSectionFragment.newInstance(category);
        getFragmentManager().beginTransaction().
                setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                .hide(main).add(R.id.mainNavigator,fragment, "subSectionFragment").addToBackStack(null).commit();
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

    void setSuggestionColors() {
        Resources r = getResources();
        LinearLayout suggestionContainerinner = (LinearLayout)suggestionContainer.findViewById(R.id.llSuggestion);
        ((GradientDrawable) (suggestionContainerinner.getChildAt(0)).getBackground()).setColor(r.getColor(R.color.academics));
        ((GradientDrawable) (suggestionContainerinner.getChildAt(1)).getBackground()).setColor(r.getColor(R.color.college));
        ((GradientDrawable) (suggestionContainerinner.getChildAt(2)).getBackground()).setColor(r.getColor(R.color.hostel));
        ((GradientDrawable) (suggestionContainerinner.getChildAt(3)).getBackground()).setColor(r.getColor(R.color.stud));
        ((GradientDrawable) (suggestionContainerinner.getChildAt(4)).getBackground()).setColor(r.getColor(R.color.lifehack));
        ((GradientDrawable) (suggestionContainerinner.getChildAt(5)).getBackground()).setColor(r.getColor(R.color.around));

    }

    public void suggestionClick(View view) {
        // show apprpriate info based on the view's tag or id or whatever we decide
        String tag = (String) view.getTag();
        String capTag = tag.substring(0,1).toUpperCase() + tag.substring(1);
        Toast.makeText(this, capTag + " Suggestions", Toast.LENGTH_LONG).show();
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
