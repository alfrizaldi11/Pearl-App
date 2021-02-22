package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WomenMenuMakeup extends AppCompatActivity {

    LinearLayout btn_back;

    DatabaseReference reference1, reference2;

    ImageView header_womenmenu;


    RecyclerView item_treatment_place;
    ArrayList<ListTreatment> list;
    TreatmentAdapter treatmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_menu_makeup);


        btn_back = findViewById(R.id.btn_back);

        header_womenmenu = findViewById(R.id.header_womenmenu);

        item_treatment_place = findViewById(R.id.item_treatment_place);
        item_treatment_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListTreatment>();


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String listtreatment = bundle.getString("tolist");

        reference1 = FirebaseDatabase.getInstance().getReference().child("treatment").child("header").child(listtreatment);
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru
                Picasso.with(WomenMenuMakeup.this)
                        .load(dataSnapshot.child("url_header")
                                .getValue().toString()).centerCrop().fit()
                        .into(header_womenmenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference("treatment").child("detail");
        reference2.orderByChild("group").equalTo("Women-Makeup").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListTreatment p = dataSnapshot1.getValue(ListTreatment.class);
                    list.add(p);
                }
                treatmentAdapter = new TreatmentAdapter(WomenMenuMakeup.this, list);
                item_treatment_place.setAdapter(treatmentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}