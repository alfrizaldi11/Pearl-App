package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchTreatment extends AppCompatActivity {

    EditText search;
    ImageView btn_searching;
    RecyclerView item_treatment_search;
    LinearLayout back;
    FloatingActionButton button_floating;

    DatabaseReference reference;
    private FirebaseRecyclerOptions<ListTreatment> options;
    private FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_treatment);

        back = findViewById(R.id.back);

        button_floating = findViewById(R.id.button_floating);

        reference = FirebaseDatabase.getInstance().getReference().child("treatment").child("detail");

        search = findViewById(R.id.search);
        btn_searching = findViewById(R.id.btn_searching);
        item_treatment_search = findViewById(R.id.item_treatment_search);
        item_treatment_search.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        item_treatment_search.setHasFixedSize(true);

        LoadData("");

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null)
                {
                    LoadData(s.toString());
                }
                else
                {
                    LoadData("");
                }
            }
        });

        button_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tofilter = new Intent(SearchTreatment.this,Filter.class);
                startActivity(tofilter);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void LoadData(String data) {
        Query query = reference.orderByChild("nama_treatment").startAt(data).endAt(data + "\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<ListTreatment>().setQuery(query, ListTreatment.class).build();
        adapter=new FirebaseRecyclerAdapter<ListTreatment, TreatmentAdapter.MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull TreatmentAdapter.MyViewHolder holder, int position, @NonNull ListTreatment model) {

                holder.xnama_treatment.setText(model.getNama_treatment());
                holder.xketerangan.setText(model.getKeterangan());
                holder.xharga_treatment.setText(String.valueOf(model.getHarga_treatment()));
                Picasso.with(SearchTreatment.this).load(model.getPhoto_treatment()).into(holder.xphoto_treatment);

                final String getNamaTreatment = model.getNama_treatment();

                holder.button_detail_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gototreatmentdetail = new Intent(SearchTreatment.this, DetailTreatment.class);
                        gototreatmentdetail.putExtra("nama_treatment", getNamaTreatment);
                        startActivity(gototreatmentdetail);
                    }
                });

            }

            @NonNull
            @Override
            public TreatmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treatment, parent, false);
                return new TreatmentAdapter.MyViewHolder(v);

            }
        };
        adapter.startListening();
        item_treatment_search.setAdapter(adapter);

        }

}