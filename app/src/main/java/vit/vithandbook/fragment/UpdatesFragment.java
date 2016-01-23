package vit.vithandbook.fragment;

/**
 * Created by Hemant on 10/22/2015.
 */

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vit.vithandbook.R;
import vit.vithandbook.adapter.UpdatesAdapter;


public class UpdatesFragment extends BackHandlerFragment {

    ArrayList<String> news;
    UpdatesAdapter upAdapter;
    ProgressBar load;
    int updatePointer;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    android.support.v7.widget.RecyclerView recyclerView;

    public static UpdatesFragment newInstance()
    {
        UpdatesFragment fragment = new UpdatesFragment();
        return fragment;
    }

    public UpdatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        updatePointer = 0;
        news = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_updates, container, false);
        load = (ProgressBar) view.findViewById(R.id.ulprogressbar);
        recyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.updates_rv_list);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
//                load.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                fetchUpdatesData(updatePointer);
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                load.setVisibility(View.GONE);
                upAdapter = new UpdatesAdapter(getActivity(),news);
                recyclerView.setAdapter(upAdapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                            if(loading){
                                if((visibleItemCount + pastVisiblesItems)>=totalItemCount){
                                    loading = false;
                                    fetchUpdatesData(updatePointer);
                                }
                            }
                        }
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });
            }
        }.execute();
        return view;
    }

    @Override
    public boolean onBackPressed() {

        return false;
    }
    void fetchUpdatesData(int skip) {
        String jsonReply = "";
        try {
            URL url = new URL("http://192.168.1.104:3000/api/news?skip="+Integer.toString(skip));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                jsonReply += line;
            }
            Log.d("json", jsonReply);
            parseJson(jsonReply);
            updatePointer += 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void parseJson(String json)
    {
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                news.add(obj.getString("news_text"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
