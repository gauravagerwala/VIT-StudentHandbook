package vit.vithandbook.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.adapter.SearchListAdapter;
import vit.vithandbook.helperClass.AutoCompleteWatcher;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.model.Article;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar ;
    ActionBar action ;
    SearchListAdapter ald;
    ListView lv_search ;
    EditText edtSeach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainHeader));
        setSupportActionBar(toolbar);
        action = getSupportActionBar();
        lv_search=(ListView)findViewById(R.id.lv_search);
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.search_bar);
        action.setDisplayShowTitleEnabled(false);
        edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch);
        edtSeach.addTextChangedListener(new AutoCompleteWatcher(this));
        edtSeach.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public class searchTask extends AsyncTask<String,Void,ArrayList<Article>>
    {
        Context activity ;
        @Override
        protected void onPreExecute()
        {
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
            if(ald==null)
            {
                ald = new SearchListAdapter(activity,R.layout.search_card,results);
                lv_search.setAdapter(ald);
            }
            else {
                ald.setData(results);
            }
        }
        public void cancelAndClear()
        {
            cancel(true);
            if(ald!=null)
                ald.clear();
        }
    }
}
