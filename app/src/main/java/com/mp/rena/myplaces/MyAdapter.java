package com.mp.rena.myplaces;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private String[] data;

    public MyAdapter(String[] data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout l = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(l);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(ViewGroup itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rowTextView);
        }

    }
}