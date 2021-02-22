package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOne extends AppCompatActivity {

    LinearLayout button_back;
    EditText username, password, email, phone;
    DatabaseReference reference, reference_username;
    Button button_next;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        button_back = findViewById(R.id.button_back);
        button_next = findViewById(R.id.button_next);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ubah state menjadi loading
                button_next.setEnabled(false);
                button_next.setText("Loading...");

                final String myusername = username.getText().toString();
                final String mypassword = password.getText().toString();
                final String myemail = email.getText().toString();
                final String myphone = phone.getText().toString();

                if (myusername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_next.setEnabled(true);
                    button_next.setText("Next");
                }
                else {
                    if (mypassword.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi Password kosong
                        button_next.setEnabled(true);
                        button_next.setText("Next");
                    } else {
                        if (myemail.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Email kosong!", Toast.LENGTH_SHORT).show();
                            //ubah state menjadi Password kosong
                            button_next.setEnabled(true);
                            button_next.setText("Next");
                        } else {
                            if (myphone.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Nomor Telepon kosong!", Toast.LENGTH_SHORT).show();
                                //ubah state menjadi Password kosong
                                button_next.setEnabled(true);
                                button_next.setText("Next");
                            } else {
                                //mengambil username pada firebase
                                reference_username = FirebaseDatabase.getInstance().getReference()
                                        .child("customer").child(username.getText().toString()).child("info_customer");
                                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //Jika username tersedia
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(getApplicationContext(), "Username sudah tersedia", Toast.LENGTH_SHORT).show();

                                            //ubah state menjadi loading Active
                                            button_next.setEnabled(true);
                                            button_next.setText("Next");

                                        } else {
                                            //menyimpan data kepada local storage (handphone)
                                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(username_key, username.getText().toString());
                                            editor.apply();

                                            //simpan kepada database
                                            reference = FirebaseDatabase.getInstance().getReference()
                                                    .child("customer").child(username.getText().toString()).child("info_customer");
                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                                    dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                                    dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                                                    dataSnapshot.getRef().child("phone").setValue(phone.getText().toString());
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            //berpindah activity
                                            Intent gotoregistwo = new Intent(RegisterOne.this, RegisterTwo.class);
                                            startActivity(gotoregistwo);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }
                }
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