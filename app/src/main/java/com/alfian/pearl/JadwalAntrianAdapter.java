package com.alfian.pearl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class JadwalAntrianAdapter extends RecyclerView.Adapter<JadwalAntrianAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListJadwal> listJadwal;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    public JadwalAntrianAdapter(Context c, ArrayList<ListJadwal> p){
        context = c;
        listJadwal = p;
        getUsernameLocal();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_jadwal,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {

        myViewHolder.jadwal.setText(listJadwal.get(i).getTanggal());

        final String getMyJadwal = listJadwal.get(i).getTanggal();
        final String getMycrew = listJadwal.get(i).getNama_crew();
        //final String getIdjadwal = listJadwal.get(i).getId_jadwal();

        myViewHolder.item_jadwals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gototreatment = new Intent(context, Antrian.class);
                gototreatment.putExtra("tanggal", getMyJadwal);
                gototreatment.putExtra("nama_crew", getMycrew);
                //gototreatment.putExtra("id_jadwal", getIdjadwal);
                context.startActivity(gototreatment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView jadwal;
        LinearLayout item_jadwals;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jadwal = itemView.findViewById(R.id.jadwal);
            item_jadwals = itemView.findViewById(R.id.item_jadwals);

        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
