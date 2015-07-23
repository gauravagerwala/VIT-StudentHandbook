package vit.vithandbook.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
    ArrayList<Article> objects ;
    public SearchListAdapter(Context context,int Rid,ArrayList<Article> objects)
    {
        super(context,Rid,objects);
        this.context = context ;
        this.objects = objects ;
        colors = context.getResources().getIntArray(R.array.colors);
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
        int index = Math.abs(current.topic.hashCode())%7;
        //holder.relativeLayout.setBackgroundDrawable(new ColorDrawable(holder.color=colors[index]));
        Resources colors = context.getResources();
        switch (current.mainCategory) {
            case "Academics":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.academics));
                break;
            case "College":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.college));
                break;
            case "Hostel":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.hostel));
                break;
            case "Student Organisations":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.stud));
                break;
            case "Life Hacks":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.lifehack));
                break;
            case "Around Vit":
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.around));
                break;
            default:
                ((GradientDrawable)holder.mainCategory.getBackground()).setColor(colors.getColor(R.color.mainHeader));
                break;
        }
        holder.mainCategory.setText(current.mainCategory.substring(0,2));
        holder.subCategory.setText(current.subCategory);
        holder.topic.setText(current.topic);
        return view ;
    }

    public class SearchViewHolder
    {
        View rootView;
        public int color;
        public RelativeLayout relativeLayout;
        public TextView topic , mainCategory , subCategory;
        SearchViewHolder(View view)
        {
            topic = (TextView)view.findViewById(R.id.tv_topic);
            mainCategory = (TextView)view.findViewById(R.id.tv_main_category);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.rv_main);
            subCategory = (TextView)view.findViewById(R.id.tv_subtopic);
            rootView = view ;
        }
    }
}
