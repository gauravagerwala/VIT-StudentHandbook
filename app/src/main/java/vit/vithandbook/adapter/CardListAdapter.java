package vit.vithandbook.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vit.vithandbook.R;


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    Context context;
    int[] color_dark,color_light;
    onItemClickListener itemClickListener;
    ArrayList<String> objects;

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView circle, content;

        CardViewHolder(View v)

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

    public CardListAdapter(Context context, ArrayList<String> objects) {
        this.context = context;
        this.objects = objects;
        color_dark = context.getResources().getIntArray(R.array.sub_dark_colors);
        color_light = context.getResources().getIntArray(R.array.sub_light_colors);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_section_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if(position<getItemCount()) {
            int index = Math.abs(position)%11;
            holder.content.setText(objects.get(position));
            //make this darker
            holder.content.setBackgroundDrawable(new ColorDrawable(color_dark[index]));
            holder.circle.setBackgroundDrawable(new ColorDrawable(color_light[index]));
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
