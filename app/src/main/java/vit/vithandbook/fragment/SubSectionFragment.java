package vit.vithandbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vit.vithandbook.adapter.CardListAdapter;
import vit.vithandbook.R;
import vit.vithandbook.adapter.onItemClickListener;


public class SubSectionFragment extends BackHandlerFragment {

    public  String mainCategory ;
    ArrayList<String> Subtopics ;
    CardListAdapter rvAdapter ;
    android.support.v7.widget.RecyclerView recyclerView;

    public static SubSectionFragment newInstance(String mainCategory)
    {
        SubSectionFragment frag = new SubSectionFragment() ;
        frag.mainCategory = mainCategory ;
        return frag ;
    }
    public SubSectionFragment()
    {

    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle SavedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sub_section,container,false);
        Subtopics = new ArrayList<String>();
        Subtopics.add("Hostel Fees");
        Subtopics.add("Mess Refund and other stuff");
        Subtopics.add("Hostel Fees");
        Subtopics.add("Hostel Fees");
        rvAdapter = new CardListAdapter(getActivity(),Subtopics);
        rvAdapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(String data) {
                rvItemClick(data);
            }
        });
        recyclerView =(android.support.v7.widget.RecyclerView) view.findViewById(R.id.subSectionList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        return view ;
    }
    @Override
    public boolean onBackPressed()
    {
        return false ;
    }

    public void rvItemClick(String data)
    {
        Fragment hideFragment = getActivity().getFragmentManager().findFragmentByTag("subSectionFragment");
        Fragment articleFragment = ArticleListFragment.newInstance(data);
       getActivity().getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
       .hide(hideFragment).add(R.id.mainNavigator,articleFragment,"articleListFragment").addToBackStack(null).commit();
    }
}

