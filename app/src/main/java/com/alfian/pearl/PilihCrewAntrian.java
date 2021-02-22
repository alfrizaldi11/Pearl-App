package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PilihCrewAntrian extends AppCompatActivity {

    LinearLayout back;
    RecyclerView crew_antrian;
    DatabaseReference reference;

    TextView tv_no_crew2;

    ArrayList<ListCrew> list;
    CrewAntrianAdapter crewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_crew_antrian);

        back = findViewById(R.id.back);
        tv_no_crew2 = findViewById(R.id.tv_no_crew2);

        crew_antrian = findViewById(R.id.crew_antrian);
        crew_antrian.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListCrew>();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String mytanggal = bundle.getString("tanggal");

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("crew_jadwal").child(mytanggal).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListCrew p = dataSnapshot1.getValue(ListCrew.class);
                    list.add(p);
                }
                crewAdapter = new CrewAntrianAdapter(PilihCrewAntrian.this, list);
                crew_antrian.setAdapter(crewAdapter);

                if (list.isEmpty()) {
                    tv_no_crew2.setVisibility(View.VISIBLE);
                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}