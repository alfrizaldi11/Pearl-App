package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    LinearLayout btn_home, btn_antrian, btn_schedule;
    TextView button_logout, name_profile, username_profile;
    ImageView photo_home_user;
    Button button_edit_profile;

    DatabaseReference reference, reference2, reference3;

    RecyclerView item_myschedule;
    ArrayList<ListMySchedule> list;
    MyScheduleAdapter myScheduleAdapter;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name_profile = findViewById(R.id.name_profile);
        username_profile = findViewById(R.id.username_profile);
        photo_home_user = findViewById(R.id.photo_home_user);

        btn_home = findViewById(R.id.btn_home);
        btn_antrian = findViewById(R.id.btn_antrian);
        btn_schedule = findViewById(R.id.btn_schedule);

        button_edit_profile = findViewById(R.id.button_edit_profile);

        getUsernameLocal();

        button_logout = findViewById(R.id.button_logout);

        item_myschedule = findViewById(R.id.item_myschedule);
        item_myschedule.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListMySchedule>();

        reference = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("info_customer");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                name_profile.setText(dataSnapshot.child("firstname").getValue().toString());
                username_profile.setText(dataSnapshot.child("username").getValue().toString());
                Picasso.with(Profile.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit()
                        .into(photo_home_user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("myinfofix");
        reference2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListMySchedule p = dataSnapshot1.getValue(ListMySchedule.class);
                    list.add(p);
                }
                myScheduleAdapter = new MyScheduleAdapter(Profile.this, list);
                item_myschedule.setAdapter(myScheduleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(Profile.this,MainMenu.class);
                startActivity(gotohome);
                Animatoo.animateSlideRight(Profile.this);
            }
        });

        btn_antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoantrian = new Intent(Profile.this,PilihJadwalAntrian.class);
                startActivity(gotoantrian);
                Animatoo.animateSlideRight(Profile.this);
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoschedule = new Intent(Profile.this,Schedule.class);
                startActivity(gotoschedule);
                Animatoo.animateSlideRight(Profile.this);
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menghapus isi / nilai / value dari username local
                //menyimpan data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                //berpindah activity
                Intent gotologin = new Intent(Profile.this, Login.class);
                startActivity(gotologin);
                finish();
            }
        });

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //berpindah activity
                Intent gotoeditprofile = new Intent(Profile.this, EditProfile.class);
                startActivity(gotoeditprofile);
            }
        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }


}