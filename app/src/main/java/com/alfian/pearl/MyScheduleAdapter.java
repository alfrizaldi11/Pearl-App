package com.alfian.pearl;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class MyScheduleAdapter extends RecyclerView.Adapter<MyScheduleAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListMySchedule> listMySchedules;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    public MyScheduleAdapter(Context c, ArrayList<ListMySchedule> p){
        context = c;
        listMySchedules = p;
        getUsernameLocal();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_myschedule,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {

        myViewHolder.vid_schedule.setText(listMySchedules.get(i).getId_schedule());
        myViewHolder.vnama_crew.setText(listMySchedules.get(i).getNama_crew());
        myViewHolder.vtanggal.setText(listMySchedules.get(i).getJadwal());

        final String getMyIdSchedule = listMySchedules.get(i).getId_schedule();
        myViewHolder.myschedule_linear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gotomytreatment = new Intent(context, DetailMySchedule.class);
                gotomytreatment.putExtra("id_schedule", getMyIdSchedule);
                context.startActivity(gotomytreatment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMySchedules.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vid_schedule, vnama_crew, vtanggal;
        LinearLayout myschedule_linear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vid_schedule = itemView.findViewById(R.id.vid_schedule);
            vtanggal = itemView.findViewById(R.id.vtanggal);
            vnama_crew = itemView.findViewById(R.id.vnama_crew);
            myschedule_linear = itemView.findViewById(R.id.myscedule_linear);

        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
