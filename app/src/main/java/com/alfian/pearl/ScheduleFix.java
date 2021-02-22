package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ScheduleFix extends AppCompatActivity {

    LinearLayout btn_home, btn_antrian, btn_schedule, btn_profile;
    TextView xnama_crew, xtanggal, xtotal_waktu, xtotal_harga;
    ImageView xphoto_crew;
    Button button_simpan;

    DatabaseReference reference, reference2, reference3, reference4, ref, ref2;

    RecyclerView item_treatment_schedule_fix;
    ArrayList<ListSchedule> list;
    ScheduleFixAdapter scheduleFixAdapter;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    String mystatus = "Menunggu Antrian";
    String id_schedule = "";

    int maxid = 0;
    int batas= 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_fix);

        getUsernameLocal();


        xnama_crew = findViewById(R.id.xnama_crew);
        xtanggal = findViewById(R.id.xtanggal);
        xphoto_crew = findViewById(R.id.xphoto_crew);

        xtotal_waktu = findViewById(R.id.xtotal_waktu);
        xtotal_harga = findViewById(R.id.xtotal_harga);

        btn_home = findViewById(R.id.btn_home);
        btn_antrian = findViewById(R.id.btn_antrian);
        btn_schedule = findViewById(R.id.btn_schedule);
        btn_profile = findViewById(R.id.btn_profile);

        button_simpan = findViewById(R.id.button_simpan);

        item_treatment_schedule_fix = findViewById(R.id.item_treatment_schedule_fix);
        item_treatment_schedule_fix.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListSchedule>();


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(ScheduleFix.this,MainMenu.class);
                startActivity(gotohome);
            }
        });

        btn_antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoantrian = new Intent(ScheduleFix.this,Antrian.class);
                startActivity(gotoantrian);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(ScheduleFix.this,Profile.class);
                startActivity(gotoprofile);
            }
        });

        Bundle bundle = getIntent().getExtras();
        final String mycrew = bundle.getString("nama_crew");
        final String myantrian = bundle.getString("tanggal");

        ref = FirebaseDatabase.getInstance().getReference().child("antrian").child(mycrew).child(myantrian);
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                    maxid= (int) dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        ref2 = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("mytreatment");
        ref2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int TotalWaktu = 0;
                int TotalHarga = 0;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListSchedule p = dataSnapshot1.getValue(ListSchedule.class);
                    TotalWaktu += Integer.parseInt(String.valueOf(p.getWaktu()));
                    TotalHarga += Integer.parseInt(String.valueOf(p.getHarga_treatment()));
                }
                xtotal_waktu.setText(String.valueOf(TotalWaktu));
                xtotal_harga.setText(String.valueOf(TotalHarga));

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

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
                scheduleFixAdapter = new ScheduleFixAdapter(ScheduleFix.this, list);
                item_treatment_schedule_fix.setAdapter(scheduleFixAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("info_schedule");
        reference2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                xnama_crew.setText(dataSnapshot.child("nama_crew").getValue().toString());
                xtanggal.setText(dataSnapshot.child("tanggal").getValue().toString());
                Picasso.with(ScheduleFix.this)
                        .load(dataSnapshot.child("photo_crew").getValue().toString()).centerCrop().fit()
                        .into(xphoto_crew);

                id_schedule = dataSnapshot.child("id_schedule").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        button_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_simpan.setEnabled(false);
                button_simpan.setText("Loading...");

                if (list.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memilih Treatment", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi list kosong
                    button_simpan.setEnabled(true);
                    button_simpan.setText("Simpan");
                }
                else {
                    if (maxid >= batas) {
                        Toast.makeText(getApplicationContext(), "Antrian Penuh!", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi list kosong
                        button_simpan.setEnabled(true);
                        button_simpan.setText("Simpan");

                        onBackPressed();
                    } else {

                        Bundle bundle = getIntent().getExtras();
                        final String myantrian = bundle.getString("tanggal");
                        final String mycrew = bundle.getString("nama_crew");

                        reference3 = FirebaseDatabase.getInstance().getReference().child("antrian").child(mycrew).child(myantrian).child(String.valueOf(maxid + 1));
                        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                reference3.getRef().child("id_antrian").setValue(String.valueOf(maxid + 1));
                                reference3.getRef().child("id_schedule").setValue(id_schedule);
                                reference3.getRef().child("status").setValue(mystatus);
                                reference3.getRef().child("username").setValue(String.valueOf(username_key_new));
                                reference3.getRef().child("total_waktu").setValue(xtotal_waktu.getText().toString());
                                reference3.getRef().child("total_harga").setValue(xtotal_harga.getText().toString());

                                button_simpan.setEnabled(false);
                                button_simpan.setText("Konfirmasi");

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });


                        reference4 = FirebaseDatabase.getInstance()
                                .getReference().child("customer").child(username_key_new).child("myschedule");
                        reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                reference4.getRef().child("mytreatmentfix").child(String.valueOf(id_schedule)).setValue(list);
                                reference4.getRef().child("myinfofix").child(String.valueOf(id_schedule)).child("id_schedule").setValue(id_schedule);
                                reference4.getRef().child("myinfofix").child(String.valueOf(id_schedule)).child("jadwal").setValue(xtanggal.getText().toString());
                                reference4.getRef().child("myinfofix").child(String.valueOf(id_schedule)).child("nama_crew").setValue(xnama_crew.getText().toString());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            }
                        });
                        reference2.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            }
                        });
                        Notification();

                    }
                }
            }
        });

    }

    private void Notification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Pearl Salon and Barbershop")
                .setSmallIcon(R.drawable.logoheader)
                .setAutoCancel(true)
                .setContentTitle("Customer Wajib datang 20 Menit")
                .setContentText("Setelah status antrian sebelumnya Sedang di Layani");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}