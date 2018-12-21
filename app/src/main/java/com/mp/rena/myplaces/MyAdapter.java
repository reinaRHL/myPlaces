package com.mp.rena.myplaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private ArrayList<Places> data;
    private Context context;


    public MyAdapter(Context context, ArrayList<Places> data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout l = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(l);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Places place = data.get(position);
        holder.textView.setText(place.address);
        holder.textView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, place.lat,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable("place", place);

                intent.putExtras(extra);
                context.startActivity(intent);
            }
        });
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //delete alert
                Toast.makeText(context, "testtt",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(ViewGroup itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rowTextView);
        }

    }
}
