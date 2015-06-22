package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import vit.vithandbook.R;


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder>{

    Context context;
    int [] colors ;
    onItemClickListener itemClickListener;
    ArrayList<String> objects;

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView circle , content ;
        CardViewHolder(View v)

        {
            super(v);
            circle = (TextView)v.findViewById(R.id.tvCircle);
            content = (TextView)v.findViewById(R.id.tvContent);
            v.setOnClickListener(this);
        }

        public  void onClick(View v)
        {
           String data = objects.get(getAdapterPosition());
           if(itemClickListener !=null) {
               itemClickListener.onItemClick(data);
           }
        }
    }

    public CardListAdapter(Context context , ArrayList<String> objects)
    {
        this.context=context;
        this.objects = objects ;
        colors = context.getResources().getIntArray(R.array.colors);
    }
    @Override
    public CardListAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.sub_section_card,parent,false);
       return new CardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position)
    {
        Random r = new Random();
        int nextindex = r.nextInt(7);
        holder.content.setText(objects.get(position));
        holder.circle.setBackgroundDrawable(new ColorDrawable(colors[nextindex]));
        holder.circle.setText(String.valueOf(objects.get(position).charAt(0)));
    }
    @Override
    public int getItemCount()
    {
        return objects.size();
    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
        this.itemClickListener = listener ;
    }
}
