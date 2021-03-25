/**
 * Contains game play logic
 */
//When mine is found change the flag to found
package com.example.minesweeper.model;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

import com.example.minesweeper.GameScreen;

public class GameLogic {
    /*
       Singleton Support
    */
    private static GameLogic instance;
    private GameLogic(){
        //Private to prevent anyone else from instantiating
    }
    public static GameLogic getInstance(){
        if (instance == null){
            instance = new GameLogic();
        }
        return instance;
    }
    /*
        Normal Object Code
     */
    //Counts scans done
    public static int Scan(int ColSize, int RowSize,int col, int row, Button[][] buttons){
        int mines_found = 0;
        //Checks Row for Mines
        for (int j =0; j < ColSize; j++){
            if (buttons[row][j].getTag() == "mine") {
                mines_found++;
            }
        }
        //Checks Col for Mines
       for (int i=0; i < RowSize; ++i){
            if (buttons[i][col].getTag() == "mine") {
                mines_found++;
            }
        }
        return mines_found;
    }
    //Counts scans done
    public static void Update(int ColSize, int RowSize,int col, int row, Button[][] buttons){
        //Updates Col
        for (int j =0; j < ColSize; j++){
            if (buttons[row][j].getTag() == "scanned") {
                //Grabs Character from button, converts to string, converts to int
                int numScan = Integer.parseInt(buttons[row][j].getText().toString());
                //Decreases by one
                numScan--;
                //Reverses the process
                buttons[row][j].setText("" + numScan);
            }
        }
        //Updates Row
        for (int i=0; i < RowSize; ++i){
            if (buttons[i][col].getTag() == "scanned") {
                //Grabs Character from button, converts to string, converts to int
                int numScan = Integer.parseInt(buttons[i][col].getText().toString());
                //Decreases by one
                numScan--;
                //Reverses the process
                buttons[i][col].setText("" + numScan);
            }
        }
    }
}