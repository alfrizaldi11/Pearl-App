package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Filter extends AppCompatActivity {
    Button termahal, termurah;
    LinearLayout back;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        termurah = findViewById(R.id.termurah);
        termahal = findViewById(R.id.termahal);

        back = findViewById(R.id.back);

        text = findViewById(R.id.text);


        termurah.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                termurah.setTextColor(Color.parseColor("#94D3AC"));
                termahal.setTextColor(Color.parseColor("#8D8D8D"));

                text.setVisibility(View.INVISIBLE);


                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new Termurah());
                ft.commit();
            }
        });

        termahal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                termurah.setTextColor(Color.parseColor("#8D8D8D"));
                termahal.setTextColor(Color.parseColor("#94D3AC"));

                text.setVisibility(View.INVISIBLE);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new Termahal());
                ft.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}