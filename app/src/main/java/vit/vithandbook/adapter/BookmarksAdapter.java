package vit.vithandbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    ArrayList<String> objects;

    Context context ;
    int[] colors;
    String mainCat = "";
    int[] catColor;
    onItemClickListener itemClickListener;


    class BookmarksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        BookmarksViewHolder(View v)
        {
            super(v);
           /* circle = (TextView) v.findViewById(R.id.tvCircle);
            content = (TextView) v.findViewById(R.id.tvContent);
            v.setOnClickListener(this);*/
        }

        public void onClick(View v) {
            String data = objects.get(getAdapterPosition());
            if (itemClickListener != null) {
                itemClickListener.onItemClick(data);
            }
        }
    }

    @Override
    public BookmarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.article_list_item, parent, false);
        return new BookmarksViewHolder(view);
    }
    public BookmarksAdapter(Context context, ArrayList<String> objects) {
        activity = context;
        this.objects = objects;
        colors = context.getResources().getIntArray(R.array.colors);
        this.objects = objects ;
        colors = context.getResources().getIntArray(R.array.colors);
        catColor = context.getResources().getIntArray(R.array.main_navigator_colors);
    }

    @Override
    public void onBindViewHolder(BookmarksViewHolder holder, int position) {
        if(position<getItemCount()) {

        }
    }

    public void setData(ArrayList<String> data)
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
