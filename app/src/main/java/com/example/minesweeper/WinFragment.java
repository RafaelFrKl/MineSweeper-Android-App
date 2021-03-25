/**
 * Win popup
 */

package com.example.minesweeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class WinFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Create the view to show
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.win_message, null);
        //Create a button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Take to Main Menu
                //Intent i = MainMenu.makeMainMenu(GameScreen.this);
                //startActivity(i);
                getActivity().finish();
                Log.i("TAG", "You Won!");
            }
        };
        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Game Over")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
