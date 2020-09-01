package com.example.minesweaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private char[] boardData;
    private LayoutInflater mInflater;
    private Board mBoard;
    private boolean first_click = true;
    private Context mContext;
    private int mMaxSize;
    private Chronometer mChronometer;
    private Statictics mStatictics;
    private String STATISTICS = "Statistics";


    public static class BoardViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final ImageView cellItemView;
        final BoardAdapter mAdapter;

        public BoardViewHolder(View itemView, BoardAdapter adapter) {
            super(itemView);
            this.cellItemView = itemView.findViewById(R.id.cell);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int col = getLayoutPosition() % mAdapter.get_number_of_rows();
            int row = getLayoutPosition() / mAdapter.get_number_of_rows();
            if (!mAdapter.mBoard.clicked) {
                mAdapter.generator(row, col);
                mAdapter.set_chronometer();
            } else
                mAdapter.open_cell(row, col);

            mAdapter.notifyDataSetChanged();

        }
    }

    public BoardAdapter(Context context, Board board, int maxSize, Chronometer chronometer) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBoard = board;
        this.boardData = mBoard.get_screen();
        mMaxSize = maxSize;
        mChronometer = chronometer;

    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_, parent, false);

        return new BoardViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        switch (boardData[position]) {
            case '*':
                holder.cellItemView.setImageResource(R.drawable.cell_foreground);
                break;
            case '0':
                holder.cellItemView.setImageResource(R.drawable.cell_0_foreground);
                break;
            case '1':
                holder.cellItemView.setImageResource(R.drawable.cell_1_foreground);
                break;
            case '2':
                holder.cellItemView.setImageResource(R.drawable.cell_2_foreground);
                break;
            case '3':
                holder.cellItemView.setImageResource(R.drawable.cell_3_foreground);
                break;
            case '4':
                holder.cellItemView.setImageResource(R.drawable.cell_4_foreground);
                break;
            case '5':
                holder.cellItemView.setImageResource(R.drawable.cell_5_foreground);
                break;
            case '6':
                holder.cellItemView.setImageResource(R.drawable.cell_6_foreground);
                break;
            case '7':
                holder.cellItemView.setImageResource(R.drawable.cell_7_foreground);
                break;
            case '8':
                holder.cellItemView.setImageResource(R.drawable.cell_8_foreground);
                break;
            case '#':
                holder.cellItemView.setImageResource(R.drawable.bomb_foreground);
                break;
            case '?':
                holder.cellItemView.setImageResource(R.drawable.question_foreground);
                break;
            case '!':
                holder.cellItemView.setImageResource(R.drawable.flag_foreground);
                holder.cellItemView.setBackgroundResource(R.drawable.flag_background);
                break;
        }
        holder.cellItemView.requestLayout();
        holder.cellItemView.getLayoutParams().height = mMaxSize;
        holder.cellItemView.getLayoutParams().width = mMaxSize;

    }

    @Override
    public int getItemCount() {
        return boardData.length;
    }

    boolean get_first_click() {
        return first_click;
    }

    void generator(int row, int col) {
        mBoard.generator(row, col);
        boardData = mBoard.get_screen();
        first_click = false;
    }

    void open_cell(int row, int col) {
        if (!mBoard.open_cell(row, col)) {
            boardData = mBoard.getBoard();
            notifyDataSetChanged();
            SharedPreferences preferences = ((Activity)mContext).getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String statistics = preferences.getString(STATISTICS, "");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Statictics st = gson.fromJson(statistics, Statictics.class);
            st.add_game(mBoard.gameType);
            SharedPreferences.Editor ed = preferences.edit();
            ed.putString(STATISTICS, new Gson().toJson(st));
            ed.apply();
            AlertDialog.Builder loseAlert = new
                    AlertDialog.Builder(mContext);

            loseAlert.setTitle("You lose");
            if (get_time().equals("59:59"))
                loseAlert.setMessage("Time: > 1 hour");
            else
                loseAlert.setMessage("Time:" + get_time());

            loseAlert.setNegativeButton("Main menu", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                        }

                    });
            loseAlert.setPositiveButton("New game", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, BoardActivity.class);
                            intent.putExtra("row", mBoard.get_rows());
                            intent.putExtra("col", mBoard.get_col());
                            intent.putExtra("bombs", mBoard.get_bombs());
                            ((Activity) mContext).finish();
                            mContext.startActivity(intent);
                        }

                    });
            loseAlert.show();
        }
        if (mBoard.check_win()) {
            SharedPreferences preferences = ((Activity)mContext).getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String statistics = preferences.getString(STATISTICS, "");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Statictics st = gson.fromJson(statistics, Statictics.class);
            st.add_win(mBoard.gameType);
            st.set_time(mBoard.gameType, (SystemClock.elapsedRealtime() - mChronometer.getBase()/1000.0));
            SharedPreferences.Editor ed = preferences.edit();
            ed.putString(STATISTICS, new Gson().toJson(st));
            ed.apply();
            AlertDialog.Builder winAlert = new
                    AlertDialog.Builder(mContext);

            winAlert.setTitle("You win!");

            if (get_time().equals("59:59"))
                winAlert.setMessage("Time: > 1 hour");
            else
                winAlert.setMessage("Time:" + get_time());

            winAlert.setNegativeButton("Main menu", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                        }

                    });

            winAlert.setPositiveButton("New game", new
                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, BoardActivity.class);
                            intent.putExtra("row", mBoard.get_rows());
                            intent.putExtra("col", mBoard.get_col());
                            intent.putExtra("bombs", mBoard.get_bombs());
                            intent.putExtra("mode", mBoard.gameType);

                            ((Activity) mContext).finish();
                            mContext.startActivity(intent);
                        }

                    });

            winAlert.show();
        }

        boardData = mBoard.get_screen();
    }

    int get_number_of_rows() {
        return mBoard.get_number_of_rows();
    }

    void set_chronometer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    String get_time() {
        mChronometer.stop();
        return mChronometer.getText().toString();
    }

}
