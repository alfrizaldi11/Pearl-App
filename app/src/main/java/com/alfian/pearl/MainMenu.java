package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    LinearLayout btn_antrian, btn_schedule, btn_profile, btn_makeup, btn_women_treatment,
            btn_women_hair, btn_cut, btn_men_treatment, btn_men_hair, tosearch;
    TextView search;

    DatabaseReference reference;

    ArrayList<ListPaket> list;
    PaketAdapter paketAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_antrian = findViewById(R.id.btn_antrian);
        btn_schedule = findViewById(R.id.btn_schedule);
        btn_profile = findViewById(R.id.btn_profile);

        btn_makeup = findViewById(R.id.btn_makeup);
        btn_women_treatment = findViewById(R.id.btn_women_treatment);
        btn_women_hair = findViewById(R.id.btn_women_hair);
        btn_cut = findViewById(R.id.btn_cut);
        btn_men_treatment = findViewById(R.id.btn_men_treatment);
        btn_men_hair = findViewById(R.id.btn_men_hair);

        search = findViewById(R.id.search);

        final RecyclerView item_paket_place = findViewById(R.id.item_paket_place);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        item_paket_place.setLayoutManager(horizontalLayoutManager);
        list = new ArrayList<ListPaket>();


        btn_antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoantrian = new Intent(MainMenu.this,PilihJadwalAntrian.class);
                startActivity(gotoantrian);
                Animatoo.animateSlideLeft(MainMenu.this);
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoschedule = new Intent(MainMenu.this,Schedule.class);
                startActivity(gotoschedule);
                Animatoo.animateSlideLeft(MainMenu.this);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(MainMenu.this,Profile.class);
                startActivity(gotoprofile);
                Animatoo.animateSlideLeft(MainMenu.this);
            }
        });


        btn_makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomakeup = new Intent(MainMenu.this, WomenMenuMakeup.class);
                gotomakeup.putExtra("tolist", "women_makeup");
                startActivity(gotomakeup);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        btn_women_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotowomentreatment = new Intent(MainMenu.this, WomenMenuTreatment.class);
                gotowomentreatment.putExtra("tolist", "women_treatment");
                startActivity(gotowomentreatment);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        btn_women_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotowomenhair = new Intent(MainMenu.this, WomenMenuHair.class);
                gotowomenhair.putExtra("tolist", "women_hair");
                startActivity(gotowomenhair);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        btn_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocut = new Intent(MainMenu.this, MenMenuCut.class);
                gotocut.putExtra("tolist", "men_cut");
                startActivity(gotocut);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        btn_men_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomentreatment = new Intent(MainMenu.this, MenMenuTreatment.class);
                gotomentreatment.putExtra("tolist", "men_treatment");
                startActivity(gotomentreatment);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        btn_men_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomenhair = new Intent(MainMenu.this, MenMenuHair.class);
                gotomenhair.putExtra("tolist", "men_hair");
                startActivity(gotomenhair);
                Animatoo.animateSlideDown(MainMenu.this);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tosearch = new Intent(MainMenu.this, SearchTreatment.class);
                startActivity(tosearch);
                Animatoo.animateSlideLeft(MainMenu.this);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("treatment").child("paket");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListPaket p = dataSnapshot1.getValue(ListPaket.class);
                    list.add(p);
                }
                paketAdapter = new PaketAdapter(MainMenu.this, list);
                item_paket_place.setAdapter(paketAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}