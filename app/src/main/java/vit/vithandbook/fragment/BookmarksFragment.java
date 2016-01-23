package vit.vithandbook.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.activity.ArticleActivity;
import vit.vithandbook.adapter.BookmarksAdapter;
import vit.vithandbook.adapter.onItemClickListener;
import vit.vithandbook.helperClass.DataBaseHelper;
import vit.vithandbook.model.Article;

public class BookmarksFragment extends BackHandlerFragment {

    ArrayList<Article> topics;
    BookmarksAdapter bkAdapter;
    ProgressBar load;
    android.support.v7.widget.RecyclerView recyclerView;

    public static BookmarksFragment newInstance()
    {
        BookmarksFragment fragment = new BookmarksFragment();
        return fragment;
    }

    public BookmarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        load = (ProgressBar) view.findViewById(R.id.alprogressbar);
        recyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.bookmarks_rv_list);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                load.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                fetchBookmarkData();
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                load.setVisibility(View.GONE);
                bkAdapter = new BookmarksAdapter(getActivity(),topics);
                bkAdapter.setOnItemClickListener(new onItemClickListener() {
                    @Override
                    public void onItemClick(String data) {
                        onListItemClick(data);
                    }
                });
                recyclerView.setAdapter(bkAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }.execute();
        return view;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
    public void onListItemClick(String data) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("topic", data);
        startActivity(intent);
    }
    void fetchBookmarkData() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        topics = new ArrayList<>();
        try {
            db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH + DataBaseHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            cursor = db.rawQuery("SELECT articles.main_category , articles.sub_category , articles.topic FROM articles WHERE bookmark = ?", new String[]{"1"});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                topics.add(new Article(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
    }
}
