package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import vit.vithandbook.R;
import vit.vithandbook.model.Article;

/**
 * Created by pulkit on 09/07/2015.
 */
public class SearchListAdapter extends ArrayAdapter<Article>
{
    Context context ;
    int[] colors;
    String mainCat = "";
    int[] catColor;
    ArrayList<Article> objects ;


    public SearchListAdapter(Context context,int Rid,ArrayList<Article> objects)
    {
        super(context,Rid,objects);
        this.context = context ;
        colors = context.getResources().getIntArray(R.array.colors);
        this.objects = objects ;
        colors = context.getResources().getIntArray(R.array.colors);
        catColor = context.getResources().getIntArray(R.array.main_navigator_colors);
    }
    public void setData(ArrayList<Article> data)
    {
        clear();
        addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SearchViewHolder holder ;
        Article current = getItem(position);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.search_card,parent,false);
            holder = new SearchViewHolder(view);
            view.setTag(holder);
        }
        else
            holder = (SearchViewHolder)view.getTag();
        int index = position%7;
        holder.mainCategory.setText(current.mainCategory.substring(0,2));
        holder.backMargin.setBackgroundDrawable(new ColorDrawable(holder.color = colors[index]));
        mainCat = current.mainCategory.substring(0, 2);
        holder.mainCategory.setText(mainCat);
        holder.subCategory.setText(current.subCategory);
        holder.topic.setText(current.topic);
        switch(mainCat)
        {
            case "Ac":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[0]);
                break;
            case "Co":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[1]);
                break;
            case "Ho":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[2]);
                break;
            case "St":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[3]);
                break;
            case "Li":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[4]);
                break;
            case "Ar":      ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[5]);
                break;
            default:        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[0]);
                break;
        }
        return view ;
    }

    public class SearchViewHolder
    {
        View rootView, backMargin;
        public int color;
        public RelativeLayout relativeLayout;
        public TextView topic , mainCategory , subCategory;
        SearchViewHolder(View view)
        {
            backMargin = view.findViewById(R.id.ll_search_card);
            topic = (TextView)view.findViewById(R.id.tv_topic);
            mainCategory = (TextView)view.findViewById(R.id.tv_main_category);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.rv_main);
            subCategory = (TextView)view.findViewById(R.id.tv_subtopic);
            rootView = view ;
        }
    }
 }

