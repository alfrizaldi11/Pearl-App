package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Antrian extends AppCompatActivity {

    TextView qtanggal, qnama_crew, tv_no_antrian;
    ImageView qphoto_crew;
    LinearLayout back;

    RecyclerView antrian_place;
    DatabaseReference reference, reference2, reference3;

    ArrayList<ListAntrian> list;
    AntrianAdapter antrianAdapter;

    String tanggal = "";
    String nama_crew = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian);

        back = findViewById(R.id.back);
        tv_no_antrian = findViewById(R.id.tv_no_antrian);

        qtanggal = findViewById(R.id.qtanggal);
        qnama_crew = findViewById(R.id.qnama_crew);
        qphoto_crew = findViewById(R.id.qphoto_crew);

        antrian_place = findViewById(R.id.antrian_place);
        antrian_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListAntrian>();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String crews = bundle.getString("nama_crew");
        final String mytanggal = bundle.getString("tanggal");

        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("antrian").child(crews).child(mytanggal).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListAntrian p = dataSnapshot1.getValue(ListAntrian.class);
                    list.add(p);
                }
                antrianAdapter = new AntrianAdapter(Antrian.this, list);
                antrian_place.setAdapter(antrianAdapter);

                if (list.isEmpty()) {
                    tv_no_antrian.setVisibility(View.VISIBLE);
                }
                else {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        // mengambil data dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference();
        reference2.child("crew_jadwal").child(mytanggal).child(crews).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                qtanggal.setText(dataSnapshot.child("tanggal").getValue().toString());
                qnama_crew.setText(dataSnapshot.child("nama_crew").getValue().toString());
                Picasso.with(Antrian.this)
                        .load(dataSnapshot.child("photo_crew").getValue().toString()).centerCrop().fit()
                        .into(qphoto_crew);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        // mengambil data dari firebase
        //reference3 = FirebaseDatabase.getInstance().getReference();
        //reference3.child("crew").child(crews).addListenerForSingleValueEvent(new ValueEventListener()
        //{
            //@Override
            //public void onDataChange(DataSnapshot dataSnapshot)
            //{
                //Picasso.with(Antrian.this)
                        //.load(dataSnapshot.child("photo_crew").getValue().toString()).centerCrop().fit()
                        //.into(qphoto_crew);

            //}

            //@Override
            //public void onCancelled(DatabaseError databaseError)
            //{

            //}
        //});

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}