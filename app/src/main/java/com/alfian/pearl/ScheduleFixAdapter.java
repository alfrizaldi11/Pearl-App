package com.alfian.pearl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ScheduleFixAdapter extends RecyclerView.Adapter<ScheduleFixAdapter.MyViewHolder> {

        Context context;
        ArrayList<ListSchedule> listSchedule;

    public ScheduleFixAdapter(Context c, ArrayList<ListSchedule> p){
            context = c;
            listSchedule = p;
            }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
            {
                return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_schedule_fix,
                viewGroup, false));
            }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
            {

                myViewHolder.nama_treatment_schedule.setText(listSchedule.get(i).getNama_treatment());
                myViewHolder.keterangan_treatment_schedule.setText(listSchedule.get(i).getKeterangan());
                myViewHolder.detail_harga_schedule.setText(String.valueOf(listSchedule.get(i).getHarga_treatment()));
                Picasso.with(context).load(listSchedule.get(i).getPhoto_treatment())
                .into(myViewHolder.photo_treatment_schedule);
                myViewHolder.waktu_treatment_schedule.setText(String.valueOf(listSchedule.get(i).getWaktu()));

            }

    @Override
    public int getItemCount() {
            return listSchedule.size();
            }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_treatment_schedule, keterangan_treatment_schedule, detail_harga_schedule, waktu_treatment_schedule;
        ImageView photo_treatment_schedule;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_treatment_schedule = itemView.findViewById(R.id.nama_treatment_schedule);
            keterangan_treatment_schedule = itemView.findViewById(R.id.keterangan_treatment_schedule);
            detail_harga_schedule = itemView.findViewById(R.id.detail_harga_schedule);
            photo_treatment_schedule = itemView.findViewById(R.id.photo_treatment_schedule);
            waktu_treatment_schedule = itemView.findViewById(R.id.waktu_treatment_schedule);
        }

    }


}