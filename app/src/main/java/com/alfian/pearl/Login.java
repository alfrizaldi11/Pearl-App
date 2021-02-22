package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_login;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_new_account = findViewById(R.id.btn_new_account);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisone = new Intent(Login.this, RegisterOne.class);
                startActivity(gotoregisone);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ubah state menjadi loading
                btn_login.setEnabled(false);
                btn_login.setText("Loading...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    btn_login.setEnabled(true);
                    btn_login.setText("Login");
                }
                else {
                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi Password kosong
                        btn_login.setEnabled(true);
                        btn_login.setText("Login");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("customer").child(username).child("info_customer");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //ambil data password dari firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan password firebase
                                    if (password.equals(passwordFromFirebase)) {

                                        //simpan username (key) pada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //berpindah activity
                                        Intent gotomainmenu = new Intent(Login.this, MainMenu.class);
                                        startActivity(gotomainmenu);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                                        //ubah state menjadi Password salah
                                        btn_login.setEnabled(true);
                                            btn_login.setText("Login");
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                                    //ubah state menjadi Username tidak ada
                                    btn_login.setEnabled(true);
                                    btn_login.setText("Login");
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });


    }
}