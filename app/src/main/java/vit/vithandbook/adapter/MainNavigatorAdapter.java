package vit.vithandbook.adapter;
/*
        ~ VIT Handbook
        ~ Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
        ~ Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
        ~
        ~ This file is part of the VIT Handbook Project.*/
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vit.vithandbook.R;

public class MainNavigatorAdapter extends RecyclerView.Adapter<MainNavigatorAdapter.NavCardViewHolder> {

    Context context;
    String [] Categories ;
    TypedArray dta ;
    ArrayList<Drawable> drawables ;
    int [] colors, colors_dark ;
    onItemClickListener itemClickListener;

    class NavCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon ;
        LinearLayout main;
        TextView categoryName ;

        NavCardViewHolder(View v)
        {
            super(v);
            icon = (ImageView)v.findViewById(R.id.iv_icon);
            categoryName = (TextView)v.findViewById(R.id.tv_category_name);
            main = (LinearLayout)v.findViewById(R.id.ll_main);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            String data = Categories[getAdapterPosition()];
            if (itemClickListener != null) {
                itemClickListener.onItemClick(data);
            }
        }
    }

    public MainNavigatorAdapter(Context context)
    {
        this.context = context;
        drawables =new ArrayList<>();
        Resources r = context.getResources();
        Categories = r.getStringArray(R.array.main_categories);
        dta = r.obtainTypedArray(R.array.iconArray);
        for(int i = 0 ;i <6;i++)
        {
            drawables.add(r.getDrawable(dta.getResourceId(i,-1)));
        }
        colors = r.getIntArray(R.array.main_navigator_colors);
        //colors_dark = r.getIntArray(R.array.main_navigator_dark);
    }

    @Override
    public NavCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_navigator_card, parent, false);
        view.setTag(Categories[getItemCount()-1]);
        return new NavCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavCardViewHolder holder, int position) {

        holder.categoryName.setText(Categories[position]);
        holder.categoryName.setBackgroundColor(colors[position]);
        // holder.icon.setImageResource(Drawables.getResourceId(position,-1));
        holder.icon.setImageDrawable(drawables.get(position));
        holder.main.setBackgroundColor(colors[position]);
    }

    @Override
    public int getItemCount() {
        return Categories.length;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
