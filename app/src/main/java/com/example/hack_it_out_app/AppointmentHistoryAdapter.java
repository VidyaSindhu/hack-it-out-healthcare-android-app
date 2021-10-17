package com.example.hack_it_out_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.RecursiveAction;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.AppointmentHistoryViewHolder> {

    ArrayList<AppointmentHistoryModel> appointmentHistoryModelArrayList;
    Context context;


    @NonNull
    @Override
    public AppointmentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.appointment_layout_item_view, parent, false);
        return new AppointmentHistoryAdapter.AppointmentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHistoryViewHolder holder, int position) {
        holder.idAppointmentHistoryTextView.setText(String.valueOf(appointmentHistoryModelArrayList.get(position).getId()));
        holder.specialistAppointmentHistoryTextView.setText(appointmentHistoryModelArrayList.get(position).getSpecialist());
        holder.dateAppointmentHistoryTextView.setText(appointmentHistoryModelArrayList.get(position).getAppointmentOn());
        holder.serviceAppointmentTextView.setText(appointmentHistoryModelArrayList.get(position).getService());
        holder.priceAppointmentHistoryTextView.setText(String.valueOf(appointmentHistoryModelArrayList.get(position).getPrice()));

        if(appointmentHistoryModelArrayList.get(position).getStatus()=="done"){
            holder.appointmentStatusImageView.setImageDrawable(context.getDrawable(R.drawable.icon_done));
        }
        else if(appointmentHistoryModelArrayList.get(position).getStatus().equals("canceled"))
            holder.appointmentStatusImageView.setImageDrawable(context.getDrawable(R.drawable.ic_round_cancel_24));

    }

    @Override
    public int getItemCount() {
        return appointmentHistoryModelArrayList.size();
    }

    public class AppointmentHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView idAppointmentHistoryTextView, specialistAppointmentHistoryTextView, dateAppointmentHistoryTextView,
                serviceAppointmentTextView, priceAppointmentHistoryTextView;
        ImageView appointmentStatusImageView;
        public AppointmentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            idAppointmentHistoryTextView = itemView.findViewById(R.id.idAppointmentHistoryTextView);
            specialistAppointmentHistoryTextView = itemView.findViewById(R.id.specialistAppointmentHistoryTextView);
            dateAppointmentHistoryTextView = itemView.findViewById(R.id.dateAppointmentHistoryTextView);
            serviceAppointmentTextView = itemView.findViewById(R.id.serviceAppointmentTextView);
            priceAppointmentHistoryTextView = itemView.findViewById(R.id.priceAppointmentHistoryTextView);
            appointmentStatusImageView = itemView.findViewById(R.id.appointmentStatusImageView);

        }
    }
}
