package com.example.minesweaper;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "save_table")
@TypeConverters({ArrayConverter.class})
public class Board {
    @PrimaryKey
    public Integer id;
    @NonNull
    @ColumnInfo(name = "save")

    public char[][] board;
    public char[][] screen;
    public int bombs;
    public int win;
    public int opened_cells;
    public boolean clicked;
    public int bombsCount;
    public long mTime;
    public char gameType;
    public boolean orientation;
    public int row;
    public int col;
    public String date;

    public Board(int row, int col, int bombs, char gameType)
    {
        win = row*col-bombs;
        clicked = false;
        board = new char[row][col];
        screen = new char[row][col];
        this.bombs = bombs;
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<col;++j)
            {
                board[i][j] = '0';
                screen[i][j] = '*';
            }
        }
        bombsCount = bombs;
        this.gameType = gameType;
        orientation = true;
        this.row = row;
        this.col = col;

    }
    public char[] get_screen() {
        char[] result = new char[screen[0].length * screen.length];
        for(int i=0; i<screen.length; ++i)
        {
            for(int j=0; j<screen[0].length; ++j) {
                result[screen[0].length*i+j]=screen[i][j];
            }
        }
        return result;
    }

    public char[] getBoard() {
        char[] result = new char[screen[0].length * screen.length];
        for(int i=0; i<board.length; ++i)
        {
            for(int j=0; j<board[0].length; ++j) {
                result[board[0].length*i+j]=board[i][j];
            }
        }
        return result;
    }

    public int get_number_of_rows(){
        return board[0].length;
    }


    public void generator(int row, int col) {
        clicked = true;
        int count=0;
        while(count!=bombs) {
            int r = (int) (Math.random() * (board.length - 1));
            int c = (int) (Math.random() * (board[0].length - 1));
            int rows[] = {row - 1, row, row + 1};
            int cols[] = {col - 1, col, col + 1};

            Arr_m check = new Arr_m();

            if (check.in(rows, r) && check.in(cols, c))
                continue;

            if (board[r][c] != '#') {
                ++count;
                board[r][c] = '#';
                for (int i = r - 1; i <= r + 1; ++i) {
                    for (int j = c - 1; j <= c + 1; ++j) {
                        if (i >= 0 && j >= 0 && i < board.length && j < board[0].length) {
                            if(board[i][j] != '#')
                                board[i][j] = (char) (Character.getNumericValue(board[i][j]) + 49);
                        }
                    }
                }
            }
        }
        this.open_cell(row,col);
    }

    public void print()
    {
        for(char[] arr : screen)
        {
            for(char el : arr) {
                System.out.print(el + " ");
            }
            System.out.print("\n");
        }

    }
    public boolean open_cell(int r, int c) {
        if(board[r][c]!=screen[r][c] && screen[r][c]!='!') {
            if (board[r][c] == '#')
                return false;
            screen[r][c] = board[r][c];
            ++opened_cells;
            if (board[r][c] == '0') {
                for (int i = r - 1; i <= r + 1; ++i) {
                    for (int j = c - 1; j <= c + 1; ++j) {
                        if (i >= 0 && j >= 0 && i < board.length && j < board[0].length) {
                            if (board[i][j] == '0' && screen[i][j] == '*') {
                                this.open_cell(i, j);
                            }
                            if (board[i][j] != '0' && screen[i][j] == '*') {
                                screen[i][j] = board[i][j];
                                ++opened_cells;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public void swapScreen(){
        char[][] newBoard = new char[board[0].length][board.length];
        char[][] newScreen = new char[board[0].length][board.length];
        if(orientation){
            orientation = false;
            for(int i=0; i<board[0].length; ++i){
                for (int j=0; j<board.length; ++j){
                    newBoard[i][j]=board[j][board[0].length-1-i];
                }
            }
            for(int i=0; i<screen[0].length; ++i){
                for (int j=0; j<screen.length; ++j){
                    newScreen[i][j]=screen[j][screen[0].length-1-i];
                }
            }
        }else {
            orientation = true;
            for(int i=0; i<board[0].length; ++i){
                for (int j=0; j<board.length; ++j){
                    newBoard[i][j]=board[board.length-1-j][i];
                }
            }
            for(int i=0; i<screen[0].length; ++i){
                for (int j=0; j<screen.length; ++j){
                    newScreen[i][j]=screen[screen.length-1-j][i];
                }
            }
        }
        board = newBoard;
        screen = newScreen;
    }
    public boolean check_win() {
        return opened_cells == win;
    }

    boolean clicked() {
        return clicked;
    }

    void set_flag(int r, int c) {
        if(screen[r][c]=='!') {
            screen[r][c] = '*';
            ++bombsCount;
        }
        else {
            if (screen[r][c] == '*' || screen[r][c] == '?')
                screen[r][c] = '!';
            --bombsCount;
        }
    }

    void set_question(int r, int c) {
        if(screen[r][c]=='?')
            screen[r][c]='*';
        else {
            if (screen[r][c] == '*')
                screen[r][c] = '?';
        }
    }
    void set_time(long time){
        mTime = time;
    }
    int get_bombs(){
        return bombs;
    }
    int get_rows(){
        return board.length;
    }
    int get_col(){
        return board[0].length;
    }
    int get_bombs_count(){
        return bombsCount;
    }
    long getTime(){
        return mTime;
    }

    public char getGameType() {
        return gameType;
    }
}

class Arr_m {
    public Arr_m(){}
    public boolean in(int[] arr, int c){
        for(int e : arr){
            if(e==c)
                return true;
        }
        return false;
    }
}

