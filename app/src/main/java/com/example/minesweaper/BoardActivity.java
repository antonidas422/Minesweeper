package com.example.minesweaper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otaliastudios.zoom.ZoomLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardActivity extends AppCompatActivity {
    private RecyclerView boardView;
    private RecyclerView.Adapter mAdapter;
    private BoardFlagAdapter mFlagAdapter;
    private RecyclerView.Adapter mQuestionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Board mBoard;
    private Chronometer mChronometer;
    private TextView mBombsCount;
    private int mMaxSize;
    private int row;
    private int col;
    private int bombs;
    private char gameType;
    private SaveViewModel mSaveViewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mSaveViewModel = ViewModelProviders.of(
                BoardActivity.this).get(SaveViewModel.class);

        if(savedInstanceState!=null){
            boardView = findViewById(R.id.board);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String str = savedInstanceState.getString("board");
            mBoard = gson.fromJson(str, Board.class);
            mChronometer = findViewById(R.id.chronometer);
            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 36000000)
                        chronometer.stop();
                }
            });
            mChronometer.setBase(savedInstanceState.getLong("chrono"));
            mChronometer.start();
            mMaxSize = savedInstanceState.getInt("maxSize");
            col = savedInstanceState.getInt("col");
            mBoard.swapScreen();
            mAdapter = new BoardAdapter(this, mBoard, mMaxSize, mChronometer);
            layoutManager = new GridLayoutManager(this, mBoard.get_col());
            boardView.setLayoutManager(layoutManager);
            boardView.setAdapter(mAdapter);
            bombs = mBoard.get_bombs_count();
            mBombsCount = (TextView) findViewById(R.id.bombCount);
            if (mBoard.bombsCount >= 100) {
                mBombsCount.setText(mBoard.bombsCount);
            } else {
                if (mBoard.bombsCount >= 10) {
                    String res = "0" + mBoard.bombsCount;
                    mBombsCount.setText(res);
                } else {
                    if (mBoard.bombsCount >= 0) {
                        String res = "00" + mBoard.bombsCount;
                        mBombsCount.setText(res);
                    }
                }
            }
        }else {

            Intent intent = getIntent();

            String str = intent.getStringExtra("board");
            mChronometer = findViewById(R.id.chronometer);

            if(str==null) {

                row = intent.getIntExtra("row", 10);
                col = intent.getIntExtra("col", 10);
                bombs = intent.getIntExtra("bombs", 10);
                gameType = intent.getCharExtra("mode", 's');
                mBoard = new Board(row, col, bombs, gameType);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                mBoard = gson.fromJson(str, Board.class);
                mChronometer.setBase(SystemClock.elapsedRealtime() - mBoard.mTime);
                mChronometer.start();
            }

            int max_size;
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            mMaxSize = size.x / mBoard.col;

            boardView = findViewById(R.id.board);





            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 36000000)
                        chronometer.stop();
                }
            });

            mAdapter = new BoardAdapter(this, mBoard, mMaxSize, mChronometer);
            boardView.setAdapter(mAdapter);

            layoutManager = new GridLayoutManager(this, mBoard.col);

            boardView.setLayoutManager(layoutManager);

            bombs = mBoard.bombs;
            mBombsCount = (TextView) findViewById(R.id.bombCount);
            if (mBoard.bombsCount >= 100) {
                mBombsCount.setText(Integer.toString(mBoard.bombsCount));
            } else {
                if (mBoard.bombsCount >= 10) {
                    String res = "0" + mBoard.bombsCount;
                    mBombsCount.setText(res);
                } else {
                    if (mBoard.bombsCount >= 0) {
                        String res = "00" + mBoard.bombsCount;
                        mBombsCount.setText(res);
                    }
                }
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
 //       Gson chronoGson = builder.create();

//        chronoGson.toJson(mChronometer);
        outState.putLong("chrono", mChronometer.getBase());
        outState.putString("board", new Gson().toJson(mBoard));
        outState.putInt("maxSize", mMaxSize);
        outState.putInt("col", col);
        //mBoard = gson.fromJson(gson.toString(), Board.class);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    public void leave(View view) {

            mSaveViewModel.getMaxId().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer == null) {
                        mBoard.id = 1;
                    } else {
                        mBoard.id = integer + 1;
                    }
                }
            });
        mBoard.date = new SimpleDateFormat("dd.MM.yy H:mm").format(new Date());
        mBoard.mTime = SystemClock.elapsedRealtime() - mChronometer.getBase();
        AlertDialog.Builder quitAlert = new
                AlertDialog.Builder(this);

        quitAlert.setMessage("Do you want to leave the game");

        quitAlert.setNegativeButton("No", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BoardActivity.this, BoardActivity.class);
                        intent.putExtra("board",new Gson().toJson(mBoard));
                        startActivity(intent);
                    }

                });

        quitAlert.setPositiveButton("Save game and leave", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(mBoard.id > 10){
                            mSaveViewModel.deleteAll(mBoard.id - 10);
                        }
                        mSaveViewModel.insert(mBoard);


                        Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                });

        quitAlert.show();
    }

    public void smile(View view) {
        mBoard = new Board(row,col, bombs, gameType);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mAdapter = new BoardAdapter(this, mBoard, mMaxSize, mChronometer);
        boardView.setAdapter(mAdapter);
    }

    public void flag(View view) {

        if(mBoard.clicked()) {
            boardView.setAdapter(new BoardFlagAdapter(this, mBoard, mMaxSize, mBombsCount));
        }

    }

    public void bomb(View view) {
        boardView.setAdapter(new BoardAdapter(this, mBoard, mMaxSize, mChronometer));
    }

    public void question(View view) {
        if(mBoard.clicked()) {
            boardView.setAdapter(new BoardQuestionAdapter(this, mBoard, mMaxSize));
        }
    }
}
