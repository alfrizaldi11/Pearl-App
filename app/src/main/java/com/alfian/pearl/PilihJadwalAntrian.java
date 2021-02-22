package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PilihJadwalAntrian extends AppCompatActivity {
    //RecyclerView tanggal_antrian;
    //DatabaseReference reference;
    LinearLayout back, btn_home, btn_schedule, btn_profile;
    TextView tv_tanggal;
    CalendarView calendar_view;
    Button button_pilih_crew;

    //ArrayList<ListJadwal> list;
    //JadwalAntrianAdapter jadwalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jadwal_antrian);

        back = findViewById(R.id.back);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        calendar_view = findViewById(R.id.calendar_view);
        button_pilih_crew = findViewById(R.id.button_pilih_crew);

        btn_home = findViewById(R.id.btn_home);
        btn_schedule = findViewById(R.id.btn_schedule);
        btn_profile = findViewById(R.id.btn_profile);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);//not sure this is needed
        long endOfMonth = calendar.getTimeInMillis();
        calendar_view.setMaxDate(endOfMonth);
        calendar_view.setMinDate(System.currentTimeMillis()-1000);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(PilihJadwalAntrian.this,MainMenu.class);
                startActivity(gotohome);
                Animatoo.animateSlideRight(PilihJadwalAntrian.this);
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoschedule = new Intent(PilihJadwalAntrian.this,Schedule.class);
                startActivity(gotoschedule);
                Animatoo.animateSlideLeft(PilihJadwalAntrian.this);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(PilihJadwalAntrian.this,Profile.class);
                startActivity(gotoprofile);
                Animatoo.animateSlideLeft(PilihJadwalAntrian.this);
            }
        });

        //tanggal_antrian = findViewById(R.id.tanggal_antrian);
        //tanggal_antrian.setLayoutManager(new LinearLayoutManager(this));
        //list = new ArrayList<ListJadwal>();

        // mengambil data dari intent
        //Bundle bundle = getIntent().getExtras();
        //final String mycrews = bundle.getString("nama_crew");

        // mengambil data dari firebase
        //reference = FirebaseDatabase.getInstance().getReference();
        //reference.child("jadwal_crew").child(mycrews).addListenerForSingleValueEvent(new ValueEventListener()
        //{
            //@Override
            //public void onDataChange(DataSnapshot dataSnapshot)
            //{
                //for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                //{
                    //ListJadwal p = dataSnapshot1.getValue(ListJadwal.class);
                    //list.add(p);
                //}
                //jadwalAdapter = new JadwalAntrianAdapter(PilihJadwalAntrian.this, list);
                //tanggal_antrian.setAdapter(jadwalAdapter);
            //}

            //@Override
            //public void onCancelled(DatabaseError databaseError)
            //{

            //}
        //});

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date = i+"-"+(i1+1)+"-"+i2;
                SimpleDateFormat spf=new SimpleDateFormat("yyyy-m-d");
                Date newDate= null;
                try {
                    newDate = spf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf= new SimpleDateFormat("yyyy-mm-dd");
                date = spf.format(newDate);
                tv_tanggal.setText(date);
            }
        });

        button_pilih_crew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_pilih_crew.setEnabled(false);
                button_pilih_crew.setText("Loading...");


                final String mytanggal = tv_tanggal.getText().toString();

                if (mytanggal.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memilih Tanggal", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Crew");
                }
                else {
                    Intent topilihcrew = new Intent(PilihJadwalAntrian.this, PilihCrewAntrian.class);
                    topilihcrew.putExtra("tanggal", mytanggal);
                    startActivity(topilihcrew);

                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Crew");

                }

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