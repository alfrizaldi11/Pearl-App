package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailSchedule extends AppCompatActivity {

    TextView zcatatan, znama_treatment, zketerangan, zwaktu_treatment, zdetail_harga, button_hapus;
    ImageView zphoto_treatment;
    LinearLayout btn_back;

    DatabaseReference reference, reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);

        getUsernameLocal();

        zcatatan = findViewById(R.id.zcatatan);
        znama_treatment = findViewById(R.id.znama_treatment);
        zketerangan = findViewById(R.id.zketerangan);
        zwaktu_treatment = findViewById(R.id.zwaktu_treatment);
        zdetail_harga = findViewById(R.id.zdetail_harga);
        zphoto_treatment = findViewById(R.id.zphoto_treatment);

        button_hapus = findViewById(R.id.button_hapus);
        btn_back = findViewById(R.id.btn_back);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String detail_treatment = bundle.getString("id_treatment");


        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("mytreatment").child(detail_treatment);
        reference2 = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("mytreatmentfix").child(detail_treatment);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                znama_treatment.setText(dataSnapshot.child("nama_treatment").getValue().toString());
                zketerangan.setText(dataSnapshot.child("keterangan").getValue().toString());
                zwaktu_treatment.setText(dataSnapshot.child("waktu").getValue().toString());
                zdetail_harga.setText(dataSnapshot.child("harga_treatment").getValue().toString());
                zcatatan.setText(dataSnapshot.child("catatan").getValue().toString());
                Picasso.with(DetailSchedule.this)
                        .load(dataSnapshot.child("photo_treatment").getValue().toString()).centerCrop().fit()
                        .into(zphoto_treatment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),Schedule.class));
                    }
                });

                reference2.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),Schedule.class));
                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}