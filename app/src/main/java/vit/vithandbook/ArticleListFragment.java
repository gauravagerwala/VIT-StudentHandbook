package vit.vithandbook;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleListFragment extends BackHandlerFragment {

    ArrayList<String> topics ;
    String articleSubCategory ;

    //android.support.v7.widget.RecyclerView recyclerView ;
    ListView listView ;
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
        listView = (ListView)view ;
        //recyclerView  = (android.support.v7.widget.RecyclerView)view;
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        topics=new ArrayList<String>();
        topics.add("topic 1");
        topics.add("topic 2");
        //recyclerView.setAdapter(new CardListAdapter(getActivity(), topics));
        listView.setAdapter(new SubSectionAdapter(getActivity(),R.layout.sub_section_item,topics));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent,view,position,id);
            }
        });
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
        int color = ((SubSectionAdapter.ViewHolder)view.getTag()).color;
        intent.putExtra("topic",topics.get(position));
        intent.putExtra("color",color);
        startActivity(intent);
    }
}
