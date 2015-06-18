package vit.vithandbook.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.activity.ArticleActivity;
import vit.vithandbook.adapter.ArticleListAdapter;

public class ArticleListFragment extends BackHandlerFragment {

    ArrayList<String> topics ;
    String articleSubCategory ;
    
    ListView listView ;
    public ArticleListFragment() {
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
        topics=new ArrayList<String>();
        topics.add("topic 1");
        topics.add("topic 2");
        listView.setAdapter(new ArticleListAdapter(getActivity(),R.layout.article_list_item,topics));
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
        int color = ((ArticleListAdapter.ViewHolder)view.getTag()).color;
        intent.putExtra("topic",topics.get(position));
        intent.putExtra("color",color);
        startActivity(intent);
    }
}


