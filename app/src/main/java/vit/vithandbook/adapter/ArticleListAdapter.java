package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import vit.vithandbook.R;
import vit.vithandbook.model.Article;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    Context activity;
    int[] colors;
    onItemClickListener itemClickListener;
    ArrayList<String> objects;


    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView circle, content;
    ArticleViewHolder(View v)
    {
        super(v);
        circle = (TextView) v.findViewById(R.id.tvCircle);
        content = (TextView) v.findViewById(R.id.tvContent);
        v.setOnClickListener(this);
    }

    public void onClick(View v) {
        String data = objects.get(getAdapterPosition());
        if (itemClickListener != null) {
            itemClickListener.onItemClick(data);
        }
    }
}

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(view);
    }
    public ArticleListAdapter(Context context, ArrayList<String> objects) {
        activity = context;
        this.objects = objects;
        colors = activity.getResources().getIntArray(R.array.sub_light_colors);
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        if(position<getItemCount()) {
            int index = Math.abs(position)%11;
            holder.content.setText(objects.get(position));
            ((GradientDrawable) holder.circle.getBackground()).setColor(colors[index]);
            holder.circle.setText(String.valueOf(Character.toUpperCase(objects.get(position).charAt(0))));
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
