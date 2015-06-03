package vit.vithandbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    boolean searchMode = false ;
    GridLayout mainNavGrid;
    int backFragmentint = -1 , currentFragmentint = 0 ;
    LinearLayout mainNavigator,searchLayout,mainHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainNavigator = (LinearLayout) findViewById(R.id.mainNavigator);
        mainNavGrid = (GridLayout)findViewById(R.id.mainNavGrid);
        searchLayout = (LinearLayout)findViewById(R.id.searchLayout);
        mainHeader = (LinearLayout)findViewById(R.id.mainHeader);
        setupDatabase();
        getFragmentManager().beginTransaction().add(R.id.mainNavigator,new MainNavigator(),"mainNavigator").addToBackStack(null).commit();
       // fillNavGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
                            Toast.makeText(getApplicationContext() , Integer.toString(mainNavigator.getHeight()),Toast.LENGTH_LONG).show();
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
                           Toast.makeText(getApplicationContext(), Integer.toString(mainNavigator.getHeight()), Toast.LENGTH_LONG).show();
                           searchLayout.setVisibility(View.GONE);
                           searchMode = false;
                       }
                   });
       }
       else if(backFragmentint == -1)
       {
           super.onBackPressed();
       }
        else
       {
           FragmentTransaction ft = getFragmentManager().beginTransaction() ;
           switch(currentFragmentint)
           {
               case 1 : ft.remove(getFragmentManager().findFragmentByTag("subSectionFragment"));break;
               case 2 : ft.remove(getFragmentManager().findFragmentByTag("articles"));break;
           }
           switch(backFragmentint)
           {
               case 1 : ft.show(getFragmentManager().findFragmentByTag("subSectionFragment"));break;
               case 0 : ft.show(getFragmentManager().findFragmentByTag("mainNavigator"));break;
           }
           ft.commit();
           currentFragmentint = backFragmentint ;
           backFragmentint -- ;
           if(currentFragmentint == 0)
               AnimateMainHeader(null,true);
       }
    }
    void setupDatabase()
    {
        SQLiteDatabase db = openOrCreateDatabase("Handbook",MODE_PRIVATE,null);
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
        db.setTransactionSuccessful();
        db.endTransaction();
        Cursor cursor  = db.rawQuery("SELECT * FROM articles",null);
        if(cursor.getCount()>0)
            return ;
        // to add data to database
    }

    public void navigate(View view)
    {
       AnimateMainHeader((ViewGroup)view,false);
       MainNavigator main = (MainNavigator)getFragmentManager().findFragmentByTag("mainNavigator");
       getFragmentManager().beginTransaction().hide(main).add(R.id.mainNavigator,SubSectionFragment.newInstance("subcat"),"subSectionFragment").addToBackStack(null).commit();
        currentFragmentint = 1 ;
        backFragmentint = 0 ;
    }
    void fillNavGrid()
    {
        int cardwidth = (int)(mainNavGrid.getWidth());
        Toast.makeText(this,Integer.toString(cardwidth),Toast.LENGTH_LONG).show();
        android.support.v7.widget.CardView card = (android.support.v7.widget.CardView)getLayoutInflater().inflate(R.layout.nav_cards,mainNavGrid,false);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams)card.getLayoutParams();
        params.width = cardwidth ;
        card.setLayoutParams(params);
        mainNavGrid.addView(card);
    }
    void AnimateMainHeader(ViewGroup view , boolean back )
    {
        int startColor = ((ColorDrawable)mainHeader.getBackground()).getColor();
        int endcolor ;
        if(back)
        {
            endcolor = getResources().getColor(R.color.mainHeader);
        }
        else {
            endcolor = ((ColorDrawable) ((LinearLayout) view.getChildAt(0)).getBackground()).getColor();
        }
        final ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endcolor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mainHeader.setBackgroundColor((int)animator.getAnimatedValue());
            }
        });
        animator.start();
    }
}
