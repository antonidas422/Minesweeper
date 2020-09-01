package com.example.minesweaper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;





class BoardFlagAdapter extends RecyclerView.Adapter<BoardFlagAdapter.BoardFlagViewHolder> {
    private char[] boardData;
    private LayoutInflater mInflater;
    private Board mBoard;
    private int mMaxSize;
    private TextView mBombsCount;




    public static class BoardFlagViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public final ImageView cellItemView;
        final BoardFlagAdapter mAdapter;
        public BoardFlagViewHolder(View itemView, BoardFlagAdapter adapter) {
            super(itemView);
            this.cellItemView = itemView.findViewById(R.id.cell);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int col = getLayoutPosition() % mAdapter.get_number_of_rows();
            int row = getLayoutPosition() / mAdapter.get_number_of_rows();

            mAdapter.set_flag(row, col);

            int bombsCount = mAdapter.get_bombs_count();
            if(bombsCount>=100) {
                mAdapter.get_bombs_count_view().setText(bombsCount);
            }
            else {
                if(bombsCount>=10){
                    String res = "0"+ bombsCount;
                    mAdapter.get_bombs_count_view().setText(res);
                }
                else {
                    if(bombsCount>=0) {
                        String res = "00" + bombsCount;
                        mAdapter.get_bombs_count_view().setText(res);
                    }
                }
            }

            mAdapter.notifyDataSetChanged();

        }
    }
    public BoardFlagAdapter(Context context, Board board, int maxSize, TextView bombsCount) {
        mInflater = LayoutInflater.from(context);
        mBoard = board;
        this.boardData = mBoard.get_screen();
        mMaxSize = maxSize;
        mBombsCount = bombsCount;

    }

    @NonNull
    @Override
    public BoardFlagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_, parent, false);

        return new BoardFlagViewHolder(mItemView, this);
    }
    

    @Override
    public void onBindViewHolder(@NonNull BoardFlagViewHolder holder, int position) {
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

    int get_number_of_rows(){
        return mBoard.get_number_of_rows();
    }

    void set_screen() {
        boardData = mBoard.get_screen();
    }

    void set_flag(int r, int c) {
        mBoard.set_flag(r,c);
        this.set_screen();
    }

    int get_bombs_count() {
        return mBoard.get_bombs_count();
    }

    TextView get_bombs_count_view() {
        return mBombsCount;
    }
    public void update(){
        notifyDataSetChanged();
    }
}
