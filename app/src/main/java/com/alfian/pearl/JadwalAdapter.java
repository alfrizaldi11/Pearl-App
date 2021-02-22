package com.alfian.pearl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListJadwal> listJadwal;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    Integer nomor_transaksi = new Random().nextInt();


    public JadwalAdapter(Context c, ArrayList<ListJadwal> p){
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

    private String getFormate(String date) {
        String formattedDate = null;

        try {
            String[] dates = date.split("\\(|\\)");

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            formattedDate = simpleDateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return formattedDate;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.jadwal.setText(listJadwal.get(i).getTanggal());

        final String getMyJadwal = listJadwal.get(i).getTanggal();
        final String getMyCrew = listJadwal.get(i).getNama_crew();
        myViewHolder.item_jadwals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                reference = FirebaseDatabase.getInstance()
                        .getReference().child("customer").child(username_key_new).child("myschedule").child("info_schedule");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reference.getRef().child("id_schedule").setValue(nomor_transaksi);
                        reference.getRef().child("jadwal").setValue(getMyJadwal);

                        Intent toresult = new Intent(context, PilihCrew.class);
                        toresult.putExtra("tanggal", getMyJadwal);
                        toresult.putExtra("nama_crew", getMyCrew);
                        context.startActivity(toresult);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
