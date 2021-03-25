/**
 * Options Menu Screen UI
 */
package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsScreen extends AppCompatActivity {
    public static final String PREFS_NAME = "AppPrefs";
    public static final String PREF_MINE_NAME = "Number of Mines";
    public static final String PREF_ROW_NAME = "Row Size";
    public static final String PREF_COL_NAME = "Col Size";

    //private int MinesValue;

    //Creates an intent to switch to a different activity
    public static Intent makeOptionsScreen(Context c) {
        Intent intent = new Intent(c, OptionsScreen.class);
        return intent;
    }

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        createRadioBtnMineSize();
        createRadioBtnGridSize();

        //Saves Mine value even when exiting program

        int savedMineValue =  getNumMines(this);
        //Saves Grid value even when exiting program
        int savedRowSize = getRowSize(this);
        int savedColSize = getColSize(this);
        Toast.makeText(this, "Saved value: " + savedMineValue + "  mines, "
                        + savedRowSize+ "x" + savedColSize,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void createRadioBtnMineSize(){
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup_mine_size);
        //Grabs Data from mine_size.xml array into the options
        int[] numMines = getResources().getIntArray(R.array.mine_size);
        //Create the dynamic buttons
        for (final int numMine : numMines) {
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.mines, numMine));
            //Change text color of radiogroup and add a shadow background
            button.setTextColor(Color.rgb(255,255,255));
            button.setShadowLayer((float)1.0, (float)4.0, (float)3.0,Color.BLACK);

            //On-click callback
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OptionsScreen.this, "You clicked " + numMine, Toast.LENGTH_SHORT).show();
                    //Saves Option:number of mines selected
                    saveNumMines(numMine);
                }
            });
            //Add to radio group
            group.addView(button);
            //Select default button:
            if (numMine == getNumMines(this)){
                button.setChecked(true);
            }
        }
    }

    private void createRadioBtnGridSize(){
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup_grid_size);
        //Grabs Data from mine_size.xml array into the options
        int[] numRows = getResources().getIntArray(R.array.row_size);
        int[] numCols = getResources().getIntArray(R.array.col_size);
        //Create the dynamic buttons
        for (int i = 0; i < numRows.length; i++) {
            final int numRow = numRows[i];
            final int numCol = numCols[i];
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.grid_size, numRow, numCol));
            //Change text color of radiogroup and add a shadow background
            button.setTextColor(Color.rgb(255,255,255));
            button.setShadowLayer((float)1.0, (float)4.0, (float)3.0,Color.BLACK);

            //On-click callback
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OptionsScreen.this, "You clicked " + numRow + "x"
                            + numCol, Toast.LENGTH_SHORT).show();
                    //Saves Option: Grid Size selected
                    saveGridSize(numRow, numCol);
                }
            });
            //Add to radio group
            group.addView(button);
            //Select default button:
            if (numRow == getRowSize(this) && numCol == getColSize(this)) {
                button.setChecked(true);
            }
        }
    }

    //Saves Option: number of mines selected
    private void saveNumMines(int numMine){
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_MINE_NAME, numMine);
        editor.apply();
    }
    //Get value of number of mines
    static public int getNumMines(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Grabs default value from mine_size.xml
        int defaultMinesValue = context.getResources().getInteger(R.integer.default_num_mines);
        //Changes default value
        return prefs.getInt(PREF_MINE_NAME, defaultMinesValue);
    }
    //Saves Option: Grid Size selected
    public void saveGridSize(int numRow, int numCol){
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_ROW_NAME, numRow);
        editor.putInt(PREF_COL_NAME, numCol);
        editor.apply();
    }
    //Get value of Row size
    static public int getRowSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Grabs default Row value from grid_size.xml
        int defaultRowValue = context.getResources().getInteger(R.integer.default_size_row);
        //Changes default value
        return prefs.getInt(PREF_ROW_NAME, defaultRowValue);
    }
    //Get value of Col size
    static public int getColSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Grabs default col value from grid_size.xml
        int defaultColValue = context.getResources().getInteger(R.integer.default_size_col);
        //Changes default value
        return prefs.getInt(PREF_COL_NAME, defaultColValue);
    }
}