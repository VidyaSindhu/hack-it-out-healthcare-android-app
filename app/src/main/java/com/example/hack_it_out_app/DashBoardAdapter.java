package com.example.hack_it_out_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder>{

    ArrayList<Service> services;
    Context context;
    OnItemClickListener onItemClickListener;

    public DashBoardAdapter(ArrayList<Service> services, Context context, OnItemClickListener onItemClickListener) {
        this.services = services;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.service_item_view, parent, false);
        return new DashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        holder.bind(services.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder {
        TextView nameServiceItemTextView, priceServiceItemTextView, durationServiceTextView;
        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            nameServiceItemTextView = itemView.findViewById(R.id.nameServiceItemTextView);
            priceServiceItemTextView = itemView.findViewById(R.id.priceServiceItemTextView);
            durationServiceTextView = itemView.findViewById(R.id.durationHeadingServiceTextView);

        }
        public void bind(final Service service, final OnItemClickListener onItemClickListener) {
            nameServiceItemTextView.setText(service.getName());
            priceServiceItemTextView.setText(String.valueOf(service.getPrice()));
            durationServiceTextView.setText(String.valueOf(service.getDuration()) + " hr");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(service);
                }
            });
        }
    }
}
