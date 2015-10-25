package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vit.vithandbook.R;
import vit.vithandbook.model.Article;

/**
 * Created by Hemant on 10/16/2015.
 */
public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder> {

    Context activity;
//    onItemClickListener itemClickListener;
    ArrayList<Article> objects;

    Context context ;
    int[] colors;
    String mainCat = "";
    int[] catColor;
    onItemClickListener itemClickListener;


    class BookmarksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView, backMargin;
        public int color;
        public RelativeLayout relativeLayout;
        public TextView topic , subCategory;
        public ImageView mainCategory;
        public BookmarksViewHolder(View view)
        {
            super(view);
            backMargin = view.findViewById(R.id.ll_bookmarks_card);
            topic = (TextView)view.findViewById(R.id.tv_topic);
            mainCategory = (ImageView)view.findViewById(R.id.tv_main_category);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.rv_main);
            subCategory = (TextView)view.findViewById(R.id.tv_subtopic);
            rootView = view ;
        }


        public void onClick(View v) {
            Article data = objects.get(getAdapterPosition());
            if (itemClickListener != null) {
                itemClickListener.onItemClick(data.topic);
            }
        }
    }

    @Override
    public BookmarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_bookmarks, parent, false);
        return new BookmarksViewHolder(view);
    }
    public BookmarksAdapter(Context context, ArrayList<Article> objects) {
        activity = context;
        this.objects = objects;
        colors = context.getResources().getIntArray(R.array.colors);
        colors = context.getResources().getIntArray(R.array.colors);
        catColor = context.getResources().getIntArray(R.array.main_navigator_colors);
    }

    @Override
    public void onBindViewHolder(BookmarksViewHolder holder, final int position) {
        if(position<getItemCount()) {
          int index = position%7;
            //holder.backMargin.setBackgroundDrawable(new ColorDrawable(holder.color = colors[index]));
            mainCat = objects.get(position).mainCategory.substring(0, 2);
            holder.subCategory.setText(objects.get(position).subCategory);
            holder.topic.setText(objects.get(position).topic);
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
                    Log.e("Article Search", objects.get(position).topic);
                    itemClickListener.onItemClick(objects.get(position).topic);
                }
            });
        }
    }

    public void setData(ArrayList<Article> data)
    {
        objects = data ;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemClickListener = listener;
    }

}
