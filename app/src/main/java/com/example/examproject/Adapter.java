package com.example.examproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<Plant> plantsList;

    public Adapter(Context context, Activity activity, List<Plant> plantsList) {
        this.context = context;
        this.activity = activity;
        this.plantsList = plantsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(plantsList.get(position).getName());
        holder.description.setText(plantsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return plantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, description;
        RelativeLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.plant_name);
            description = itemView.findViewById(R.id.plant_desc);
            layout = itemView.findViewById(R.id.plant_layout);
        }
    }
}
