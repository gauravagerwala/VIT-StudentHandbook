package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import vit.vithandbook.R;

public class ArticleListAdapter extends ArrayAdapter<String>
{
    Context activity ;
    int [] colors ;
    public ArrayList<String> objects;
    public class ViewHolder
    {
        public TextView content , circle  ;
        public int color ;
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
