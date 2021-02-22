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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListAntrian> listAntrian;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    public AntrianAdapter(Context c, ArrayList<ListAntrian> p){
        context = c;
        listAntrian = p;


        getUsernameLocal();


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_antrian,
                viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {

        myViewHolder.username_antrian.setText(listAntrian.get(i).getUsername());
        myViewHolder.status_antrian.setText(listAntrian.get(i).getStatus());
        myViewHolder.no_antrian.setText(listAntrian.get(i).getId_antrian());
        myViewHolder.total_waktu.setText(listAntrian.get(i).getTotal_waktu());

    }

    @Override
    public int getItemCount() {
        return listAntrian.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username_antrian, status_antrian, no_antrian, total_waktu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username_antrian = itemView.findViewById(R.id.username_antrian);
            status_antrian = itemView.findViewById(R.id.status_antrian);
            no_antrian = itemView.findViewById(R.id.no_antrian);
            total_waktu = itemView.findViewById(R.id.total_waktu);


        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }



}
