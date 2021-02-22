package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PilihCrew extends AppCompatActivity {
    RecyclerView item_pilih_crew;
    DatabaseReference reference;
    TextView button_back, tv_no_crew;

    ArrayList<ListCrew> list;
    CrewAdapter crewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_crew);


        button_back = findViewById(R.id.button_back);

        tv_no_crew = findViewById(R.id.tv_no_crew);

        item_pilih_crew = findViewById(R.id.item_pilih_crew);
        item_pilih_crew.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListCrew>();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String mytanggal = bundle.getString("tanggal");

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("crew_jadwal").child(mytanggal).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ListCrew p = dataSnapshot1.getValue(ListCrew.class);
                        list.add(p);
                    }
                    crewAdapter = new CrewAdapter(PilihCrew.this, list);
                    item_pilih_crew.setAdapter(crewAdapter);

                    if (list.isEmpty()) {
                        tv_no_crew.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}