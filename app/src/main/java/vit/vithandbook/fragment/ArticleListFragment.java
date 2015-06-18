package vit.vithandbook.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import vit.vithandbook.R;
import vit.vithandbook.activity.ArticleActivity;

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


class ArticleListAdapter extends ArrayAdapter<String>
{
    Context activity ;
    int [] colors ;
    public ArrayList<String> objects;
    class ViewHolder
    {
        public TextView content , circle  ;
        int color ;
    }
    public ArticleListAdapter(Context context, int resource, ArrayList<String> objects)
    {
        super(context, resource, objects);
        activity = context ;
        this.objects = objects ;
        colors = activity.getResources().getIntArray(R.array.colors);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder ;
        if(view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.article_list_item, parent, false);
            holder = new ViewHolder();
            holder.content = (TextView) view.findViewById(R.id.tvContent);
            holder.circle = (TextView) view.findViewById(R.id.tvCircle);
            view.setTag(holder)
            ;
        }
        else
            holder = (ViewHolder)view.getTag();
        Random r = new Random();
        int nextindex = r.nextInt(3);
        holder.content.setText(objects.get(position));
        holder.color = colors[nextindex];
        ((GradientDrawable)holder.circle.getBackground()).setColor(holder.color);
        holder.circle.setText(String.valueOf(objects.get(position).charAt(0)));
        return view ;
    }

}

