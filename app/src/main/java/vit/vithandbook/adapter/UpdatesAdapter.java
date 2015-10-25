package vit.vithandbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vit.vithandbook.R;

/**
 * Created by Hemant on 10/22/2015.
 */
public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdatesViewHolder> {

    Context activity;
    onItemClickListener itemClickListener;
    ArrayList<String> objects;

    class UpdatesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        UpdatesViewHolder(View v)
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
    public UpdatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.article_list_item, parent, false);
        return new UpdatesViewHolder(view);
    }
    public UpdatesAdapter(Context context, ArrayList<String> objects) {
        activity = context;
        this.objects = objects;
    }


    @Override
    public void onBindViewHolder(UpdatesViewHolder holder, int position) {
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