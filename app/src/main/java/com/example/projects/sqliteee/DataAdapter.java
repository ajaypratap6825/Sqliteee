package com.example.projects.sqliteee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<Data> uploads;

    public DataAdapter(Context context, List<Data> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list ,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data d = uploads.get(position);
        holder.text.setText(d.getTitle());
        holder.text2.setText(d.getDescription());
        holder.id.setText(String.valueOf(d.getId()));
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text, text2, id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text2 = itemView.findViewById(R.id.text2);
            id = itemView.findViewById(R.id.listId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),DetailActivity.class);
                    i.putExtra("ID",uploads.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
