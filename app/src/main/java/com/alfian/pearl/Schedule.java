package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Schedule extends AppCompatActivity {

    LinearLayout btn_home, btn_antrian, btn_schedule, btn_profile;
    TextView button_batal;
    Button button_pilih_crew;

    DatabaseReference reference;

    RecyclerView item_treatment_schedule;
    ArrayList<ListSchedule> list;
    ScheduleAdapter scheduleAdapter;


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getUsernameLocal();


        btn_home = findViewById(R.id.btn_home);
        btn_antrian = findViewById(R.id.btn_antrian);
        btn_schedule = findViewById(R.id.btn_schedule);
        btn_profile = findViewById(R.id.btn_profile);

        button_batal = findViewById(R.id.button_batal);
        button_pilih_crew = findViewById(R.id.button_pilih_crew);

        item_treatment_schedule = findViewById(R.id.item_treatment_schedule);
        item_treatment_schedule.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListSchedule>();

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(Schedule.this,MainMenu.class);
                startActivity(gotohome);
                Animatoo.animateSlideRight(Schedule.this);
            }
        });

        btn_antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoantrian = new Intent(Schedule.this,PilihJadwalAntrian.class);
                startActivity(gotoantrian);
                Animatoo.animateSlideRight(Schedule.this);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(Schedule.this,Profile.class);
                startActivity(gotoprofile);
                Animatoo.animateSlideLeft(Schedule.this);
            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("mytreatment");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListSchedule p = dataSnapshot1.getValue(ListSchedule.class);
                    list.add(p);
                }
                scheduleAdapter = new ScheduleAdapter(Schedule.this, list);
                item_treatment_schedule.setAdapter(scheduleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        button_pilih_crew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_pilih_crew.setEnabled(false);
                button_pilih_crew.setText("Loading...");

                if (list.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memilih Treatment", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Tanggal");
                }
                else {
                    Intent topilihjadwal = new Intent(Schedule.this, PilihJadwal.class);
                    startActivity(topilihjadwal);

                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Tanggal");

                }

            }
        });


        button_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),MainMenu.class));
                    }
                });
            }
        });


    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}