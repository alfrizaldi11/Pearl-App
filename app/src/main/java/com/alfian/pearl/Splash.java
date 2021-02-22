package com.alfian.pearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Splash extends AppCompatActivity {

    ImageView app_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app_logo = findViewById(R.id.app_logo);

        //setting timer untuk 1 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //merubah activity splashact ke activity getstarted
                Intent gomainmenu = new Intent(Splash.this, Login.class);
                startActivity(gomainmenu);
                Animatoo.animateFade(Splash.this);
                finish();


            }
        },2000); // 1000 ms = 2 detik
    }
}