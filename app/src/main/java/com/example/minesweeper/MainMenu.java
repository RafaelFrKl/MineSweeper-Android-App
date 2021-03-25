/**
 * Welcome UI Screen
 */
package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {

    //Creates an intent to switch to a different activity
    public static Intent makeMainMenu(Context c) {
        Intent intent = new Intent(c, MainMenu.class);
        return intent;
    }

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Creates button functionality
        registerClickCallback();
    }

    //Creates button functionality
    private void registerClickCallback(){
        //Get Play Game button
        ImageButton play = (ImageButton) findViewById(R.id.playimagebtn);
        //Creates Play button Functionality
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Goes to selected activity
                Intent i = GameScreen.makeGameScreen(MainMenu.this);
                startActivity(i);
            }
        });

        //Get Options button
        Button options = (Button) findViewById(R.id.optionsbtn);
        //Creates Options button Functionality
        options.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Goes to selected activity
                Intent i = OptionsScreen.makeOptionsScreen(MainMenu.this);
                startActivity(i);
            }
        });

        //Get Help button
        Button help = (Button) findViewById(R.id.helpbtn);
        //Creates Help button Functionality
        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Goes to selected activity
                Intent i = HelpScreen.makeHelpScreen(MainMenu.this);
                startActivity(i);
            }
        });
    }
}