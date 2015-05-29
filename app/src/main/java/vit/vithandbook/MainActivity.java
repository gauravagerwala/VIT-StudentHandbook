package vit.vithandbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    boolean searchMode = false ;
    LinearLayout mainNavigator,searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainNavigator = (LinearLayout) findViewById(R.id.mainNavigator);
        searchLayout = (LinearLayout)findViewById(R.id.searchLayout);
        setupDatabase();
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
        Toast.makeText(this,"to add",Toast.LENGTH_LONG).show();
    }
}
