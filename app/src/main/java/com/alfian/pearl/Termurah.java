package com.alfian.pearl;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Termurah extends Fragment {
    RecyclerView item_treatment_filter_termurah;
    DatabaseReference reference;

    ArrayList<ListTreatment> list;
    TreatmentAdapter treatmentAdapter;

    public Termurah() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_termurah, container, false);

        item_treatment_filter_termurah = view.findViewById(R.id.item_treatment_filter_termurah);
        item_treatment_filter_termurah.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
                item_treatment_filter_termurah.setAdapter(treatmentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return view;
    }
}