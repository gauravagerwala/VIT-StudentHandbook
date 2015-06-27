package vit.vithandbook.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vit.vithandbook.helperClass.BackConnect;
import vit.vithandbook.fragment.BackHandlerFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.R;
import vit.vithandbook.fragment.SubSectionFragment;
import vit.vithandbook.helperClass.DataBaseHelper;


public class MainActivity extends ActionBarActivity {

    boolean searchMode = false ;
    GridLayout mainNavGrid;
    BackHandlerFragment selectedFragment ;
    LinearLayout mainNavigator,searchLayout,mainHeader;
    public LinearLayout suggestionContainer ;
    BackConnect back;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainNavigator = (LinearLayout) findViewById(R.id.mainNavigator);
        mainNavGrid = (GridLayout)findViewById(R.id.mainNavGrid);
        searchLayout = (LinearLayout)findViewById(R.id.searchLayout);
        mainHeader = (LinearLayout)findViewById(R.id.mainHeader);
        suggestionContainer = (LinearLayout) findViewById(R.id.llSuggestion);
        back=new BackConnect(this);
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                setSuggestionColors();
                if (savedInstanceState == null) {
                    selectedFragment = new MainNavigator();
                    getFragmentManager().beginTransaction().add(R.id.mainNavigator, selectedFragment, "mainNavigator").commit();
                }
            }
                @Override
            protected Void doInBackground(Void... params)
            {
                setupDatabase();
                back.getUpdatedData();
                return  null ;
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

        return super.onOptionsItemSelected(item);
    }

    public void searchClick(View view) {
        if(!searchMode) {
            mainNavigator.animate().translationY(-mainNavigator.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mainNavigator.setVisibility(View.GONE);
                            searchLayout.setVisibility(View.VISIBLE);
                            searchMode =true ;
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        if(searchMode)
        {
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
        }
        else if(getFragmentManager().getBackStackEntryCount()==0)
            super.onBackPressed();
        else if(selectedFragment!=null &&  !selectedFragment.onBackPressed())
        {
            getFragmentManager().popBackStack();
            if(getFragmentManager().getBackStackEntryCount()==1)
            { AnimateMainHeader(null,true);}
        }
    }

    void setupDatabase()
    {
        
       /* SQLiteDatabase db = openOrCreateDatabase("Handbook",MODE_PRIVATE,null);
        db.beginTransaction();
        db.execSQL("CREATE TABLE IF NOT EXISTS articles (" +
                "main_category VARCHAR ," +
                "sub_category VARCHAR ," +
                "topic VARCHAR ," +
                "content VARCHAR ," +
                "tags VARCHAR ) ;");
        db.execSQL("CREATE TABLE IF NOT EXISTS images ( " +
                "id VARCHAR primary key ," +
                "tags varchar ) ;");
      /*  db.execSQL("CREATE TABLE IF NOT EXISTS subcategories (" +
                "name varchar primary key ," +
                "main_category varchar ) ;");
        db.execSQL("CREATE TABLE IF NOT EXISTS sub_subcategories (" +
                "name varchar primary key ," +
                "subcategory varchar ) ;");
        db.setTransactionSuccessful();
        db.endTransaction();
        Cursor cursor  = db.rawQuery("SELECT * FROM articles",null);
        cursor.close();
        db.close();*/
        DataBaseHelper helper = new DataBaseHelper(this);
        helper.createDataBase();
        // to add data to database
    }

    public void navigate(View view)
    {
       AnimateMainHeader((ViewGroup) view, false);
       MainNavigator main = (MainNavigator)getFragmentManager().findFragmentByTag("mainNavigator");
        String category = (String)view.getTag();
        selectedFragment = SubSectionFragment.newInstance(category);
       getFragmentManager().beginTransaction().
               setCustomAnimations(R.transition.fade_in,R.transition.fade_out,R.transition.fade_in,R.transition.fade_out)
               .hide(main).add(R.id.mainNavigator,selectedFragment,"subSectionFragment").addToBackStack(null).commit();
    }
    void AnimateMainHeader(ViewGroup view , boolean back )
    {
        int startColor = ((ColorDrawable)mainHeader.getBackground()).getColor();
        int startColorDark = getResources().getColor(R.color.mainHeaderDark);
        if(Build.VERSION.SDK_INT >= 21)
            startColorDark = getWindow().getStatusBarColor();
        int endcolor;
        int endColorDark;
        if(back)
        {
            endcolor = getResources().getColor(R.color.mainHeader);
            endColorDark = getResources().getColor(R.color.mainHeaderDark);
        }
        else {
            endcolor = ((ColorDrawable) ((LinearLayout) view.getChildAt(0)).getBackground()).getColor();
            switch (view.getTag().toString()){
                case "Academics" :
                    endColorDark = getResources().getColor(R.color.academicsDark);
                    break;
                case "College" :
                    endColorDark = getResources().getColor(R.color.collegeDark);
                    break;
                case "Hostel" :
                    endColorDark = getResources().getColor(R.color.hostelDark);
                    break;
                case "Student Organisations" :
                    endColorDark = getResources().getColor(R.color.studDark);
                    break;
                case "Life Hacks" :
                    endColorDark = getResources().getColor(R.color.lifehackDark);
                    break;
                case "Around Vit" :
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
                if(Build.VERSION.SDK_INT >=21)
                    getWindow().setStatusBarColor((int) animator1.getAnimatedValue());

            }
        });
        animator1.start();
        animator.start();

    }

    void setSuggestionColors()
    {
        Resources r = getResources();
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(0)).getBackground()).setColor(r.getColor(R.color.academics));
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(1)).getBackground()).setColor(r.getColor(R.color.college));
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(2)).getBackground()).setColor(r.getColor(R.color.hostel));
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(3)).getBackground()).setColor(r.getColor(R.color.stud));
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(4)).getBackground()).setColor(r.getColor(R.color.lifehack));
        ((GradientDrawable)((TextView)suggestionContainer.getChildAt(5)).getBackground()).setColor(r.getColor(R.color.around));

    }
    public void suggestionClick(View view)
    {
        // show apprpriate info based on the view's tag or id or whatever we decide
        String tag = (String)view.getTag();
        Toast.makeText(this,tag+" suggestions",Toast.LENGTH_LONG).show();
    }

}
