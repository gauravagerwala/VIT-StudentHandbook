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

        import java.util.ArrayList;

        import vit.vithandbook.R;
        import vit.vithandbook.adapter.UpdatesAdapter;
        import vit.vithandbook.adapter.onItemClickListener;


public class UpdatesFragment extends BackHandlerFragment {

    ArrayList<String> topics;
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
                //fetchBookmarkData();
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                load.setVisibility(View.GONE);
                upAdapter = new UpdatesAdapter(getActivity(),topics);
                upAdapter.setOnItemClickListener(new onItemClickListener() {
                    @Override
                    public void onItemClick(String data) {
                        //onListItemClick(data);

                    }
                });
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
}
