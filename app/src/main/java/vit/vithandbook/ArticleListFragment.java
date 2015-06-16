package vit.vithandbook;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ArticleListFragment extends BackHandlerFragment {

    ArrayList<String> topics ;
    String articleSubCategory ;

    android.support.v7.widget.RecyclerView recyclerView ;
    public ArticleListFragment() {
        // Required empty public constructor
    }

    public static ArticleListFragment newInstance(String mainCategory)
    {
        ArticleListFragment frag = new ArticleListFragment();
        frag.articleSubCategory = mainCategory ;
        return frag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        recyclerView  = (android.support.v7.widget.RecyclerView)view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        topics=new ArrayList<String>();
        topics.add("topic 1");
        topics.add("topic 2");
        recyclerView.setAdapter(new CardListAdapter(getActivity(),topics));
        return view ;
    }
    @Override
    public boolean onBackPressed()
    {

        return false ;
    }

}
