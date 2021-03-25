/**
 * Welcome Screen UI
 */

package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//Used this to fix bug
//https://stackoverflow.com/questions/33780491/skipping-a-splash-screen
public class MainActivity extends AppCompatActivity {
    //Splash Screen Time
    private static int SPLASH_TIME_OUT = 12500;
    Skip thread;
    private TextView txt;
    private ImageView img;

    //On Creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.welcometext);
        img = (ImageView)findViewById(R.id.imgvw);

        thread = new Skip();
        thread.start();
        //Creates button functionality
        registerClickCallback();
    }

    private class Skip extends Thread {
        public boolean bRun = true;
        //Keeps Splash Screen on for some time
        @Override
        public void run () {
            try {
                //Calls animation files
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                //Title fades in
                txt.startAnimation(animFadeIn);
                //First image fades in and out
                img.startAnimation(animFadeOut);
                //Hides first image after animation
                img.setVisibility(View.INVISIBLE);
                //Wait time until Game takes you to menu
                sleep(SPLASH_TIME_OUT);
                if (bRun) {
                    Intent i = MainMenu.makeMainMenu(MainActivity.this);
                    startActivity(i);
                    finish();
                }
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Creates button functionality
    private void registerClickCallback() {
        //Get the button
        Button skip = (Button) findViewById(R.id.skipbtn);
        //Creates Functionality
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (thread != null && thread.isAlive())
                {
                    thread.bRun = false;
                }
                Intent i = MainMenu.makeMainMenu(MainActivity.this);
                startActivity(i);
                finish();
            }
        });
    }
}