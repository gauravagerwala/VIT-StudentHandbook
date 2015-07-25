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
import java.util.HashMap;
import java.util.List;

import vit.vithandbook.R;
import vit.vithandbook.adapter.ArticleListAdapter;
import vit.vithandbook.adapter.ExpandableListAdapter;
import vit.vithandbook.helperClass.DataBaseHelper;

/**
 * Created by Tanay on 23-07-2015.
 */
public class ExpandableListFragement extends BackHandlerFragment {

    List<String> listDataHeader;
    public String mainCategory;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    ExpandableListView eListView;

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public static ExpandableListFragement newInstance(String mainCategory) {
        ExpandableListFragement frag = new ExpandableListFragement();
        frag.mainCategory = mainCategory;
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exp_list_view, container, false);
        eListView = (ExpandableListView) view.findViewById(R.id.exp_list);
        prepareListData();
        eListView.setDividerHeight(0);
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        eListView.setAdapter(listAdapter);

        /*
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
        */
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }






/*
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
    }*/
}
