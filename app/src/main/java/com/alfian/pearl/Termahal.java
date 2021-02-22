package com.alfian.pearl;

import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class Termahal extends Fragment {
    RecyclerView item_treatment_filter_termahal;
    DatabaseReference reference;

    ArrayList<ListTreatment> list;
    TreatmentAdapter treatmentAdapter;

    public Termahal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_termahal, container, false);

        item_treatment_filter_termahal = view.findViewById(R.id.item_treatment_filter_termahal);
        item_treatment_filter_termahal.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list = new ArrayList<ListTreatment>();


        reference = FirebaseDatabase.getInstance().getReference("treatment").child("detail");
        reference.orderByChild("harga_treatment").endAt(2000).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ListTreatment p = dataSnapshot1.getValue(ListTreatment.class);
                    list.add(p);
                }
                treatmentAdapter = new TreatmentAdapter(getContext(), list);
                Collections.reverse(list);
                item_treatment_filter_termahal.setAdapter(treatmentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return view;
    }
}