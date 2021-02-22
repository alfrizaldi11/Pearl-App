package com.alfian.pearl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ResourceBundle;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListSchedule> listSchedule;

    public ScheduleAdapter(Context c, ArrayList<ListSchedule> p){
        context = c;
        listSchedule = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_schedule,
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

        final String getId_treatment = listSchedule.get(i).getId_treatment();

        myViewHolder.cancel_item_schedule.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent gototreatment = new Intent(context, DetailSchedule.class);
                        gototreatment.putExtra("id_treatment", getId_treatment);
                        context.startActivity(gototreatment);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return listSchedule.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_treatment_schedule, keterangan_treatment_schedule, detail_harga_schedule, waktu_treatment_schedule;
        ImageView photo_treatment_schedule, cancel_item_schedule;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_treatment_schedule = itemView.findViewById(R.id.nama_treatment_schedule);
            keterangan_treatment_schedule = itemView.findViewById(R.id.keterangan_treatment_schedule);
            detail_harga_schedule = itemView.findViewById(R.id.detail_harga_schedule);
            photo_treatment_schedule = itemView.findViewById(R.id.photo_treatment_schedule);
            waktu_treatment_schedule = itemView.findViewById(R.id.waktu_treatment_schedule);
            cancel_item_schedule = itemView.findViewById(R.id.cancel_item_schedule);


        }

    }


}
