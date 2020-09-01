package com.example.minesweaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class LoadGameAdapter extends RecyclerView.Adapter<LoadGameAdapter.LoadGameViewHolder> {
    private Context mContext;
    private List<Board> mSaveList;

    public static class LoadGameViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final CardView mCardView;
        public final TextView mTitle;
        public final TextView mDate;
        public final TextView mMode;
        public int mPosition;
        final LoadGameAdapter mAdapter;
        public LoadGameViewHolder(View v, LoadGameAdapter adapter){
            super(v);
            mAdapter = adapter;
            mCardView = v.findViewById(R.id.load_card);
            mTitle = v.findViewById(R.id.save_title);
            mDate = v.findViewById(R.id.save_date);
            mMode = v.findViewById(R.id.save_mode);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mAdapter.mContext, BoardActivity.class);
            intent.putExtra("board", new Gson().toJson(mAdapter.getSave(mPosition)));
            mAdapter.mContext.startActivity(intent);
        }
    }

    public LoadGameAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public LoadGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.save_vardview, parent, false);
        return new LoadGameAdapter.LoadGameViewHolder(mItemView, this);
    }
    @Override
    public void onBindViewHolder(@NonNull LoadGameAdapter.LoadGameViewHolder holder, int position){
        Board board = mSaveList.get(getItemCount() - position - 1);
        holder.mPosition = position;
        holder.mTitle.setText("Save " + Integer.toString(board.id));
        holder.mDate.setText(board.date);
        switch (board.gameType){
            case 'b':
                holder.mMode.setText("Beginner");
                break;
            case 'i':
                holder.mMode.setText("Intermediate");
                break;
            case 'p':
                holder.mMode.setText("Pro");
                break;
            case 's':
                holder.mMode.setText("Special");
                break;
        }
    }
    @Override
    public int getItemCount(){
        if(mSaveList!=null) {
            return mSaveList.size();
        }else {
            return 0;
        }
    }
    public Board getSave(int position){
        return mSaveList.get(position);
    }
    public void update(List<Board> data){
        mSaveList = data;
        notifyDataSetChanged();
    }
}
