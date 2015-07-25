package vit.vithandbook.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.adapter.ArticleListAdapter;
import vit.vithandbook.adapter.ExpandableListAdapter;
import vit.vithandbook.helperClass.DataBaseHelper;

/**
 * Created by Tanay on 23-07-2015.
 */
public class ExpandableListFragement extends BackHandlerFragment {

    ArrayList<String> topics;
    String articleSubCategory;

    ExpandableListView eListView;

    @Override
    public boolean onBackPressed() {
        return false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exp_list_view, container, false);
        eListView = (ExpandableListView) view.findViewById(R.id.exp_list);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                fetchArticleData();
                // sample data remove to test with actual data
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                //eListView.setAdapter(new ExpandableListAdapter(getActivity(),topics));


            }
        }.execute();
        return view;
    }

    void fetchArticleData() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = SQLiteDatabase.openDatabase(DataBaseHelper.DB_PATH + DataBaseHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            cursor = db.rawQuery("SELECT distinct `topic` from `articles` Where sub_category = ? ", new String[]{articleSubCategory});
            topics = new ArrayList<String>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                topics.add(cursor.getString(0));
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
