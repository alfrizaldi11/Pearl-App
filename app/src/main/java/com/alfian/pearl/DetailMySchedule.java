package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailMySchedule extends AppCompatActivity {
    DatabaseReference reference;

    RecyclerView item_treatment_schedule_fix;
    ArrayList<ListSchedule> list;
    ScheduleFixAdapter scheduleFixAdapter;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUsernameLocal();


        setContentView(R.layout.activity_detail_my_schedule);
        item_treatment_schedule_fix = findViewById(R.id.item_treatment_schedule_fix);
        item_treatment_schedule_fix.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListSchedule>();


        Bundle bundle = getIntent().getExtras();
        final String myidschedule = bundle.getString("id_schedule");

        reference = FirebaseDatabase.getInstance().getReference().child("customer").child(username_key_new).child("myschedule").child("mytreatmentfix").child(myidschedule);
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
                scheduleFixAdapter = new ScheduleFixAdapter(DetailMySchedule.this, list);
                item_treatment_schedule_fix.setAdapter(scheduleFixAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }
    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}