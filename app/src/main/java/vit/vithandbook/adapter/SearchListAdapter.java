package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

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
    onItemClickListener itemClickListener;


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
        final SearchViewHolder holder ;
        final Article current = getItem(position);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.card_search,parent,false);
            holder = new SearchViewHolder(view);
            view.setTag(holder);
        }
        else
            holder = (SearchViewHolder)view.getTag();
        int index = position%7;
        //holder.backMargin.setBackgroundDrawable(new ColorDrawable(holder.color = colors[index]));
        mainCat = current.mainCategory.substring(0, 2);
        holder.subCategory.setText(current.subCategory);
        holder.topic.setText(current.topic);
        switch(mainCat)
        {
            case "Ac":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[0]);
                holder.mainCategory.setImageResource(R.drawable.ic_academics_box);
               //holder.backMargin.setBackgroundDrawable(new ColorDrawable(holder.color = catColor[0]));
                break;
            case "Co":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[1]);
                holder.mainCategory.setImageResource(R.drawable.ic_college_box);
                break;
            case "Ho":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[2]);
                holder.mainCategory.setImageResource(R.drawable.ic_hostels_box);
                break;
            case "St":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[3]);
                holder.mainCategory.setImageResource(R.drawable.ic_student_organizations);
                break;
            case "Li":        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[4]);
                holder.mainCategory.setImageResource(R.drawable.ic_life_hacks);
                break;
            case "Ar":      ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[5]);
                holder.mainCategory.setImageResource(R.drawable.ic_around_vit);
                break;
            default:        ((GradientDrawable) holder.mainCategory.getBackground()).setColor(catColor[0]);
                holder.mainCategory.setImageResource(R.drawable.ic_academics_box);
                break;
        }
        holder.backMargin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View varg) {
                Log.e("Article Search", current.topic);
                itemClickListener.onItemClick(current.topic);
            }
        });
        return view ;
    }
    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class SearchViewHolder
    {
        View rootView, backMargin;
        public int color;
        public RelativeLayout relativeLayout;
        public TextView topic , subCategory;
        public ImageView mainCategory;
        public SearchViewHolder(View view)
        {
            backMargin = view.findViewById(R.id.ll_search_card);
            topic = (TextView)view.findViewById(R.id.tv_topic);
            mainCategory = (ImageView)view.findViewById(R.id.tv_main_category);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.rv_main);
            subCategory = (TextView)view.findViewById(R.id.tv_subtopic);
            rootView = view ;
        }
    }
 }

