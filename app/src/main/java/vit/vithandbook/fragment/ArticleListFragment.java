package vit.vithandbook.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import vit.vithandbook.R;
import vit.vithandbook.activity.ArticleActivity;
import vit.vithandbook.adapter.ArticleListAdapter;
import vit.vithandbook.adapter.CardListAdapter;
import vit.vithandbook.adapter.onItemClickListener;
import vit.vithandbook.helperClass.DataBaseHelper;

public class ArticleListFragment extends BackHandlerFragment {

    ArrayList<String> topics ;
    String articleSubCategory ;
    ProgressBar load ;
    
    ListView listView ;
    public ArticleListFragment() {
    }

    public static ArticleListFragment newInstance(String SubCategory)
    {
        ArticleListFragment frag = new ArticleListFragment();
        frag.articleSubCategory = SubCategory ;
        return frag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        listView = (ListView)view.findViewById(R.id.lvArticle) ;
        load = (ProgressBar)view.findViewById(R.id.alprogressbar);
        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected void onPreExecute()
            {
                load.setVisibility(View.VISIBLE);
            }
            @Override
            protected Void doInBackground(Void... params) {
                fetchArticleData();
                // sample data remove to test with actual data
                return null;
            }
            @Override
            protected void onPostExecute(Void res)
            {
                load.setVisibility(View.GONE);
                listView.setAdapter(new ArticleListAdapter(getActivity(),R.layout.article_list_item,topics));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onListItemClick(parent,view,position,id);
                    }
                });
            }
        }.execute();
        return view ;
    }
    @Override
    public boolean onBackPressed()
    {

        return false ;
    }

    public void onListItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(),ArticleActivity.class);
        int color = ((ArticleListAdapter.ViewHolder)view.getTag()).color;
        intent.putExtra("topic",topics.get(position));
        intent.putExtra("color",color);
        startActivity(intent);
    }

    void fetchArticleData()
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH+DataBaseHelper.DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
            cursor = db.rawQuery("SELECT distinct `topic` from `articles` Where sub_category = ?", new String[]{articleSubCategory});
            topics = new ArrayList<String>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                topics.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
    }
}


