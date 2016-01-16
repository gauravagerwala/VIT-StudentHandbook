package vit.vithandbook.fragment;

/**
 * Created by Hemant on 10/22/2015.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import vit.vithandbook.R;
import vit.vithandbook.adapter.UpdatesAdapter;
import vit.vithandbook.helperClass.DataBaseHelper;


public class UpdatesFragment extends BackHandlerFragment {

    ArrayList<String> updates;
    UpdatesAdapter upAdapter;
    ProgressBar load;
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
        View view = inflater.inflate(R.layout.card_updates, container, false);
        load = (ProgressBar) view.findViewById(R.id.alprogressbar);
        recyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.updates_rv_list);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                load.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                fetchUpdatesData();
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                load.setVisibility(View.GONE);
                upAdapter = new UpdatesAdapter(getActivity(),updates);
                recyclerView.setAdapter(upAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }.execute();
        return view;
    }

    @Override
    public boolean onBackPressed() {

        return false;
    }
    void fetchUpdatesData() {
        //Fetch articles from the back-end
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL("/news"); //TODO Enter correct url
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.connect();
            int status = urlConnection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //NewsUpdates update = new Gson().fromJson(result.toString(), NewsUpdates.class);
        try {
            JSONObject jsonRootObject = new JSONObject(result.toString());

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Updates");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

            }
        } catch (JSONException e) {e.printStackTrace();}
    }
}
