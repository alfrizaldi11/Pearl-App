package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class DetailPaket extends AppCompatActivity {
    TextView xnama_treatment, catatan, xdeskripsi, xwaktu_treatment, xdetail_harga;
    ImageView header_detail;
    Button button_detail_reservation;
    LinearLayout back;

    DatabaseReference reference, reference2, reference3;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String nama_treatment = "";
    String keterangan = "";
    String photo_treatment = "";

    Integer nomor_transaksi = new Random().nextInt();
    Integer idmyschedule = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_treatment);

        xnama_treatment = findViewById(R.id.xnama_treatment);
        catatan = findViewById(R.id.catatan);

        xdeskripsi = findViewById(R.id.xdeskripsi);
        xwaktu_treatment = findViewById(R.id.xwaktu_treatment);
        xdetail_harga = findViewById(R.id.xdetail_harga);
        header_detail = findViewById(R.id.header_detail);

        getUsernameLocal();


        button_detail_reservation = findViewById(R.id.button_detail_reservation);
        back = findViewById(R.id.back);



        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String detail_paket = bundle.getString("nama_treatment");


        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("treatment").child("paket").child(detail_paket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                xdeskripsi.setText(dataSnapshot.child("deskripsi").getValue().toString());
                xwaktu_treatment.setText(dataSnapshot.child("waktu").getValue().toString());
                xdetail_harga.setText(dataSnapshot.child("harga_treatment").getValue().toString());
                xnama_treatment.setText(dataSnapshot.child("nama_treatment").getValue().toString());
                Picasso.with(DetailPaket.this)
                        .load(dataSnapshot.child("photo_treatment").getValue().toString()).centerCrop().fit()
                        .into(header_detail);

                nama_treatment = dataSnapshot.child("nama_treatment").getValue().toString();
                keterangan = dataSnapshot.child("keterangan").getValue().toString();
                photo_treatment = dataSnapshot.child("photo_treatment").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        button_detail_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                button_detail_reservation.setEnabled(false);
                button_detail_reservation.setText("Loading...");

                final String mycatatan = catatan.getText().toString();

                if (mycatatan.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_detail_reservation.setEnabled(true);
                    button_detail_reservation.setText("Reservation");

                } else {

                    reference2 = FirebaseDatabase.getInstance()
                            .getReference().child("customer").child(username_key_new).child("myschedule")
                            .child("mytreatment").child(String.valueOf(nomor_transaksi));
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            reference2.getRef().child("id_treatment").setValue(String.valueOf(nomor_transaksi));
                            reference2.getRef().child("nama_treatment").setValue(nama_treatment);
                            reference2.getRef().child("keterangan").setValue(keterangan);
                            reference2.getRef().child("photo_treatment").setValue(photo_treatment);
                            reference2.getRef().child("waktu").setValue(Integer.valueOf(xwaktu_treatment.getText().toString()));
                            reference2.getRef().child("harga_treatment").setValue(Integer.valueOf(xdetail_harga.getText().toString()));
                            dataSnapshot.getRef().child("catatan").setValue(catatan.getText().toString());

                            Intent gotoschedule = new Intent(DetailPaket.this, Schedule.class);
                            startActivity(gotoschedule);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
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