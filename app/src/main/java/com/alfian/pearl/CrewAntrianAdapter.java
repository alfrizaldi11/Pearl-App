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

public class CrewAntrianAdapter extends RecyclerView.Adapter<CrewAntrianAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListCrew> listCrew;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    public CrewAntrianAdapter(Context c, ArrayList<ListCrew> p){
        context = c;
        listCrew = p;


        getUsernameLocal();


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_crew,
                viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {

        myViewHolder.nama_crew.setText(listCrew.get(i).getNama_crew());
        Picasso.with(context).load(listCrew.get(i).getPhoto_crew()).centerCrop().fit()
                .into(myViewHolder.photo_crew);

        final String getNamaCrew = listCrew.get(i).getNama_crew();
        final String getTanggal = listCrew.get(i).getTanggal();

        myViewHolder.item_crew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gototreatment = new Intent(context, Antrian.class);
                gototreatment.putExtra("nama_crew", getNamaCrew);
                gototreatment.putExtra("tanggal", getTanggal);
                context.startActivity(gototreatment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCrew.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_crew;
        ImageView photo_crew;
        LinearLayout item_crew;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_crew = itemView.findViewById(R.id.nama_crew);
            photo_crew = itemView.findViewById(R.id.photo_crew);
            item_crew = itemView.findViewById(R.id.item_crew);


        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }



}