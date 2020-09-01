package com.example.minesweaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGameFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private int specialRow = 8;
    private int specialCol = 8;
    private int specialBombs = 10;
    SeekBar mSeekBarCol;
    SeekBar mSeekBarRow;
    SeekBar mSeekBarBombs;
    TextView mSpecialRow;
    TextView mSpecialCol;
    TextView mSpecialBombs;

    public NewGameFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_game, container, false);
        Button beginner = (Button) v.findViewById(R.id.button_beginner);
        Button intermediate = (Button) v.findViewById(R.id.button_intermediate);
        Button pro = (Button) v.findViewById(R.id.button_pro);
        Button special = v.findViewById(R.id.button_special);

        mSeekBarRow = v.findViewById(R.id.seekBar_width);
        mSeekBarCol =  v.findViewById(R.id.seekBar_length);
        mSeekBarBombs = v.findViewById(R.id.seekBar_bombs);

        mSpecialRow = v.findViewById(R.id.special_row);
        mSpecialCol = v.findViewById(R.id.special_col);
        mSpecialBombs = v.findViewById(R.id.special_bombs);

        mSpecialRow.setText("Rows: " + Integer.toString(specialRow));
        mSpecialCol.setText("Cols: " + Integer.toString(specialCol));
        mSpecialBombs.setText("Bombs: " + Integer.toString(specialBombs));

        mSeekBarRow.setOnSeekBarChangeListener(this);
        mSeekBarCol.setOnSeekBarChangeListener(this);
        mSeekBarBombs.setOnSeekBarChangeListener(this);

        beginner.setOnClickListener(this);
        intermediate.setOnClickListener(this);
        pro.setOnClickListener(this);
        special.setOnClickListener(this);



        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), BoardActivity.class);
        switch (v.getId()){
            case R.id.button_beginner:
                intent.putExtra("row", 8);
                intent.putExtra("col", 8);
                intent.putExtra("bombs", 10);
                intent.putExtra("mode", 'b');
                startActivity(intent);
                break;
            case R.id.button_intermediate:
                intent.putExtra("row", 16);
                intent.putExtra("col", 16);
                intent.putExtra("bombs", 40);
                intent.putExtra("mode", 'i');
                startActivity(intent);
                break;
            case R.id.button_pro:
                intent.putExtra("row", 30);
                intent.putExtra("col", 16);
                intent.putExtra("bombs", 99);
                intent.putExtra("mode", 'p');
                startActivity(intent);
                break;
            case R.id.button_special:
                intent.putExtra("row", specialRow);
                intent.putExtra("col", specialCol);
                intent.putExtra("bombs", specialBombs);
                intent.putExtra("mode", 's');
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBar_width:
                mSeekBarCol.setMax(progress);
                mSeekBarBombs.setMax((int) (progress*mSeekBarCol.getProgress()*0.6));
                specialRow = progress;
                mSpecialRow.setText("Rows: " + Integer.toString(progress));
                break;
            case R.id.seekBar_length:
                mSeekBarBombs.setMax((int) (progress*mSeekBarRow.getProgress()*0.6));
                specialCol = progress;
                mSpecialCol.setText("Cols: " + Integer.toString(progress));
                break;
            case R.id.seekBar_bombs:
                specialBombs = progress;
                mSpecialBombs.setText("Bombs: " + Integer.toString(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
