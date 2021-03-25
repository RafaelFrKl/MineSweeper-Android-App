/**
 * Game Screen UI
 */
package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.minesweeper.model.GameLogic;

import static com.example.minesweeper.OptionsScreen.PREFS_NAME;
import static com.example.minesweeper.OptionsScreen.PREF_COL_NAME;
import static com.example.minesweeper.OptionsScreen.PREF_MINE_NAME;
import static com.example.minesweeper.OptionsScreen.PREF_ROW_NAME;

public class GameScreen extends AppCompatActivity {
    //Calling Shared Preferences part 1
    SharedPreferences sharedPreferences;
    //Shared Preferences Default Value
    public static final int DEFAULT = 0;

    //private static final int NUM_ROWS = 4;
    //private static final int NUM_COLS = 6;
    //Declares Array of Buttons
    Button buttons[][];
    Button button;
    //Declare global variables
    int scan = 0;
    int mine = 0;
    int maxmine = 0;
    MediaPlayer mp;
    MediaPlayer sp;
    Vibrator v;


    //Creates an intent to switch to a different activity
    public static Intent makeGameScreen(Context c) {
        Intent intent = new Intent(c, GameScreen.class);
        return intent;
    }

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        //Calling Shared Preferences part 2
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Getting Row + Col from Options through Shared Preferences
        int RowSize = sharedPreferences.getInt(PREF_ROW_NAME, DEFAULT);
        int ColSize = sharedPreferences.getInt(PREF_COL_NAME, DEFAULT);
        mine = sharedPreferences.getInt(PREF_MINE_NAME, DEFAULT);
        maxmine = sharedPreferences.getInt(PREF_MINE_NAME, DEFAULT);
        //Creates Array of Buttons
        buttons = new Button[RowSize][ColSize];
        //Sound Effect
        mp = MediaPlayer.create(this, R.raw.sound_effect);
        sp = MediaPlayer.create(this, R.raw.sound_scan);
        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //Populates table with Dynamic Buttons
        populateButtons();
        refreshScreen();
    }

    //Create Table with Dynamic Buttons
    private void populateButtons() {
        //Getting Row + Col from Options through Shared Preferences
        int RowSize = sharedPreferences.getInt(PREF_ROW_NAME, DEFAULT);
        int ColSize = sharedPreferences.getInt(PREF_COL_NAME, DEFAULT);
        //Find the Table
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        //Insert Rows
        for(int row = 0; row < RowSize; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f)); //Makes Table fill space
            table.addView(tableRow);
            //Populate Rows with Buttons
            for(int col = 0; col < ColSize; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f)); //Makes Buttons fill space
                //Make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);
                //Make buttons clickable
                button.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                }));

                tableRow.addView(button);
                buttons[row][col] = button; //Array of buttons
            }
        }
        //Adds Random Mines
        randMines(RowSize, ColSize);
    }
    //Adds functionality to Table Buttons
    private void gridButtonClicked(int col, int row) {
        //Getting Row + Col from Options through Shared Preferences
        int RowSize = sharedPreferences.getInt(PREF_ROW_NAME, DEFAULT);
        int ColSize = sharedPreferences.getInt(PREF_COL_NAME, DEFAULT);
        //What Row + Col was clicked
        //Toast.makeText(this, "Clicked: " + col + "," + row,
        //        Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col]; //Array of buttons is able to now be manipulated
        //Lock Button Sizes:
        lockButtonSizes();
        //If you click on a mine, image is revealed underneath
        if (button.getTag() == "mine") {
            //play sound effect
            mp.start();
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            //sets tag to found
            button.setTag("found");
            mine--;
            //Scale Image to Button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.money_bag);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap)); //set icon as background to button
            //Updates Scanned Values and decreases them by one
            GameLogic.Update(ColSize, RowSize, col, row, buttons);
            //Once player finds all mines popup dialogue appears
            if(mine == 0){
                FragmentManager manager = getSupportFragmentManager();
                WinFragment dialog = new WinFragment();
                dialog.show(manager, "WinDialog");

                Log.i("TAG", "Just showed the dialog");
            }
        } else if (button.getTag() == "nonscan" || button.getTag() == "found") {
            //play sound effect
            sp.start();
            scan++;
            //updates mines found
            int mines_found = GameLogic.Scan(ColSize, RowSize, col, row, buttons);
            //Change text on button
            button.setText("" + mines_found);
            button.setTag("scanned");
        }
        //Refreshes Screen
        refreshScreen();
    }
    private void lockButtonSizes(){
        //Getting Row + Col from Options through Shared Preferences
        int RowSize = sharedPreferences.getInt(PREF_ROW_NAME, DEFAULT);
        int ColSize = sharedPreferences.getInt(PREF_COL_NAME, DEFAULT);
        for(int row = 0; row < RowSize; row++) {
            for (int col = 0; col < ColSize; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    //Adds Random Mines
    private void randMines(int rows, int cols) {
        //First set a tag for every button
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setTag("nonscan");
            }
        }

        //Get value Mines from Options activity through Shared Preferences
        int MineSize = sharedPreferences.getInt(PREF_MINE_NAME, DEFAULT);
        //Random number generator to place mines
        for (int i=0; i < MineSize; ++i) {
            int j = (int) (Math.random() * rows);
            int k = (int) (Math.random() * cols);
            if (buttons[j][k].getTag() != "mine") {
                buttons[j][k].setTag("mine");
            } else{
                --i;
            }
        }
    }
   //updates value after setting options
    private void refreshScreen() {
        //Refresh num Mines display:
        TextView tvNumScans = (TextView) findViewById(R.id.scanstext);
        //Set it as a string
        tvNumScans.setText(getString(R.string.scans_used, scan));

        //Refresh num Mines display:
        TextView tvNumMines = (TextView) findViewById(R.id.minestext);
        //Set it as a string
        tvNumMines.setText(getString(R.string.mines_left, mine, maxmine));
    }

   //refreshes screen
    @Override
    protected void onResume() {
        super.onResume();
        refreshScreen();
    }
}