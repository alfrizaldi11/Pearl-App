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
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListPaket> listPaket;

    DatabaseReference reference;


    public PaketAdapter(Context c, ArrayList<ListPaket> p){
        context = c;
        listPaket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_paket,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.xnama_treatment.setText(listPaket.get(i).getNama_treatment());
        myViewHolder.xketerangan.setText(listPaket.get(i).getKeterangan());
        Picasso.with(context).load(listPaket.get(i).getPhoto_treatment()).fit()
                .into(myViewHolder.paket);

        final String getMypaket = listPaket.get(i).getNama_treatment();
        myViewHolder.paket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                Intent topaket = new Intent(context, DetailPaket.class);
                topaket.putExtra("nama_treatment", getMypaket);
                context.startActivity(topaket);
            }

        });

    }

    @Override
    public int getItemCount() {
        return listPaket.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_treatment, xketerangan;
        ImageView paket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_treatment = itemView.findViewById(R.id.xnama_treatment);
            xketerangan = itemView.findViewById(R.id.xketerangan);
            paket = itemView.findViewById(R.id.paket);

        }
    }


}
