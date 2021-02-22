package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PilihJadwal extends AppCompatActivity {
    //RecyclerView item_pilih_jadwal;
    //DatabaseReference reference;
    TextView button_back, tv_pilih_tanggal;
    CalendarView calendar_v;
    Button button_pilih_crew;

    int maxmonth = 1 ;

    DatePickerDialog.OnDateSetListener setListener;

    //ArrayList<ListJadwal> list;
    //JadwalAdapter jadwalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jadwal);


        button_back = findViewById(R.id.button_back);
        tv_pilih_tanggal = findViewById(R.id.tv_pilih_tanggal);

        calendar_v = findViewById(R.id.calendar_v);
        button_pilih_crew = findViewById(R.id.button_pilih_crew);

        //Calendar calendar = Calendar.getInstance();
        //final int year = calendar.get(Calendar.YEAR);
        //final int month = calendar.get(Calendar.MONTH);
        //final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //item_pilih_jadwal = findViewById(R.id.item_pilih_jadwal);
        //item_pilih_jadwal.setLayoutManager(new LinearLayoutManager(this));
        //list = new ArrayList<ListJadwal>();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);//not sure this is needed
        long endOfMonth = calendar.getTimeInMillis();
        calendar_v.setMaxDate(endOfMonth);
        calendar_v.setMinDate(System.currentTimeMillis()-1000);


        calendar_v.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
                tv_pilih_tanggal.setText(date);
            }
        });

        button_pilih_crew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_pilih_crew.setEnabled(false);
                button_pilih_crew.setText("Loading...");


                final String mytanggal = tv_pilih_tanggal.getText().toString();

                if (mytanggal.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memilih Tanggal", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Crew");
                }
                else {
                    Intent topilihcrew = new Intent(PilihJadwal.this, PilihCrew.class);
                    topilihcrew.putExtra("tanggal", mytanggal);
                    startActivity(topilihcrew);

                    button_pilih_crew.setEnabled(true);
                    button_pilih_crew.setText("Pilih Crew");

                }

            }
        });

        // mengambil data dari intent
        //Bundle bundle = getIntent().getExtras();
        //final String mycrew = bundle.getString("nama_crew");

        // mengambil data dari firebase
        //reference = FirebaseDatabase.getInstance().getReference();
        //reference.child("crew_jadwal").addListenerForSingleValueEvent(new ValueEventListener()
        //{
            //@Override
            //public void onDataChange(DataSnapshot dataSnapshot)
            //{
                //for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                //{
                    //ListJadwal p = dataSnapshot1.getValue(ListJadwal.class);
                    //list.add(p);
                //}
                //jadwalAdapter = new JadwalAdapter(PilihJadwal.this, list);
                //item_pilih_jadwal.setAdapter(jadwalAdapter);
            //}

            //@Override
            //public void onCancelled(DatabaseError databaseError)
            //{

            //}
        //});

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}