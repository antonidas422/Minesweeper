package com.example.minesweaper;

import java.text.DecimalFormat;

public class Statictics {
    private Double beginner_games;
    private Double intermediate_games;
    private Double pro_games;
    private Double special_games;

    private Double beginner_wins;
    private Double intermediate_wins;
    private Double pro_wins;
    private Double special_wins;

    private Double beginner_best_time;
    private Double intermediate_best_time;
    private Double pro_best_time;
    private Double special_best_time;

    public Statictics(){
        beginner_games = 0.0;
        intermediate_games = 0.0;
        pro_games = 0.0;
        special_games = 0.0;

        beginner_wins = 0.0;
        intermediate_wins = 0.0;
        pro_wins = 0.0;
        special_wins = 0.0;

        beginner_best_time = 0.0;
        intermediate_best_time = 0.0;
        pro_best_time = 0.0;
        special_best_time = 0.0;
    }

    public String getBeginner_games() {
        return beginner_games.toString();
    }

    public String getIntermediate_games() {
        return intermediate_games.toString();
    }

    public String getPro_games() {
        return pro_games.toString();
    }

    public String getSpecial_games() {
        return special_games.toString();
    }

    public String getBeginner_wins() {
        return beginner_wins.toString();
    }

    public String getIntermediate_wins() {
        return intermediate_wins.toString();
    }

    public String getPro_wins() {
        return pro_wins.toString();
    }

    public String getSpecial_wins() {
        return special_wins.toString();
    }

    public void add_game(char type) {
       switch (type){
           case 'b':
               ++beginner_games;
               break;
           case 'i':
               ++intermediate_games;
               break;
           case 'p':
               ++pro_games;
               break;
           case 's':
               ++special_games;
               break;
       }
    }

    public void add_win(char type) {
        switch (type){
            case 'b':
                ++beginner_games;
                ++beginner_wins;
                break;
            case 'i':
                ++intermediate_games;
                ++intermediate_wins;
                break;
            case 'p':
                ++pro_games;
                ++pro_wins;
                break;
            case 's':
                ++special_games;
                ++special_wins;
                break;
        }
    }

    public String getBeginner_best_time() {
        return new DecimalFormat(".##").format(beginner_best_time);
    }

    public String getIntermediate_best_time() {
        return new DecimalFormat(".##").format(intermediate_best_time);
    }

    public String getPro_best_time() {
        return new DecimalFormat(".##").format(pro_best_time);
    }

    public String getSpecial_best_time() {
        return new DecimalFormat(".##").format(special_best_time);
    }

    public void set_time(char type, Double time) {
        switch (type){
            case 'b':
                if(beginner_best_time > time || beginner_best_time == 0)
                    beginner_best_time = time;
                break;
            case 'i':
                if(intermediate_best_time > time || intermediate_best_time == 0)
                    beginner_best_time = time;
                break;
            case 'p':
                if(pro_best_time > time || pro_best_time == 0)
                    beginner_best_time = time;
                break;
            case 's':
                if(special_best_time > time || special_best_time == 0)
                    beginner_best_time = time;
                break;
        }
    }

    public String getBeginner_winRate() {
        if(beginner_games != 0) {
            return new DecimalFormat(".##").format(beginner_wins / beginner_games * 100.0);
        } else {
            return "0";
        }
    }

    public String getIntermediate_winRate() {
        if(intermediate_games != 0) {
            return new DecimalFormat(".##").format(intermediate_wins / intermediate_games * 100);
        } else {
            return "0";
        }
    }

    public String getPro_winRate() {
        if(pro_games != 0) {
            return new DecimalFormat(".##").format(pro_wins / pro_games * 100);
        } else {
            return "0";
        }
    }

    public String getSpecial_winRate() {
        if(special_games != 0) {
            return new DecimalFormat(".##").format(special_games / special_games * 100);
        } else {
            return "0";
        }
    }
}
