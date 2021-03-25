/**
 * Help Screen UI
 */
package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpScreen extends AppCompatActivity {

    //Creates an intent to switch to a different activity
    public static Intent makeHelpScreen(Context c) {
        Intent intent = new Intent(c, HelpScreen.class);
        return intent;
    }

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        //Makes Hyperlinks clickable
        TextView tv = (TextView) findViewById(R.id.helptext);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}