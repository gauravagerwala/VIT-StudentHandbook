package vit.vithandbook;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pulkit on 15/06/2015.
 */

class CardViewHolder extends RecyclerView.ViewHolder
{
    TextView circle , content ;
    CardViewHolder(View v)
    {
        super(v);
        circle = (TextView)v.findViewById(R.id.tvCircle);
        content = (TextView)v.findViewById(R.id.tvContent);
    }
}

public class CardListAdapter extends RecyclerView.Adapter<CardViewHolder>{

    Context context;
    int [] colors ;
    ArrayList<String> objects;

    public CardListAdapter(Context context , ArrayList<String> objects)
    {
        this.context=context;
        this.objects = objects ;
        colors = context.getResources().getIntArray(R.array.colors);
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.sub_section_item,parent,false);
       return new CardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position)
    {
        Random r = new Random();
        int nextindex = r.nextInt(3);
        holder.content.setText(objects.get(position));
        ((GradientDrawable)holder.circle.getBackground()).setColor(colors[nextindex]);
        holder.circle.setText(String.valueOf(objects.get(position).charAt(0)));
    }
    @Override
    public int getItemCount()
    {
        return objects.size();
    }
}
