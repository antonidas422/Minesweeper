package com.example.minesweaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {
    private String STATISTICS = "Statistics";
    private TextView titleBeginner;
    private TextView beginnerGames;
    private TextView beginnerWins;
    private TextView beginnerWinRate;
    private TextView beginnerBestTime;

    private TextView titleIntermediate;
    private TextView intermediateGames;
    private TextView intermediateWins;
    private TextView intermediateWinRate;
    private TextView intermediateBestTime;

    private TextView titlePro;
    private TextView proGames;
    private TextView proWins;
    private TextView proWinRate;
    private TextView proBestTime;

    private TextView titleSpecial;
    private TextView specialGames;
    private TextView specialWins;
    private TextView specialWinRate;
    private TextView specialBestTime;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String statistics = preferences.getString(STATISTICS, "");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Statictics st = gson.fromJson(statistics, Statictics.class);

        titleBeginner = v.findViewById(R.id.title_beginner);
        beginnerGames = v.findViewById(R.id.beginner_games);
        beginnerWins = v.findViewById(R.id.beginner_wins);
        beginnerWinRate = v.findViewById(R.id.beginner_winRate);
        beginnerBestTime = v.findViewById(R.id.beginner_best_time);

        titleIntermediate = v.findViewById(R.id.title_intermediate);
        intermediateGames = v.findViewById(R.id.intermediate_games);
        intermediateWins = v.findViewById(R.id.intermediate_wins);
        intermediateWinRate = v.findViewById(R.id.intermediate_winRate);
        intermediateBestTime = v.findViewById(R.id.intermediate_best_time);

        titlePro = v.findViewById(R.id.title_pro);
        proGames = v.findViewById(R.id.pro_games);
        proWins = v.findViewById(R.id.pro_wins);
        proWinRate = v.findViewById(R.id.pro_winRate);
        proBestTime = v.findViewById(R.id.pro_best_time);

        titleSpecial = v.findViewById(R.id.title_special);
        specialGames = v.findViewById(R.id.special_games);
        specialWins = v.findViewById(R.id.special_wins);
        specialWinRate = v.findViewById(R.id.special_winRate);
        specialBestTime = v.findViewById(R.id.special_best_time);

        titleBeginner.setText("Beginner");
        beginnerGames.setText("Games: " + st.getBeginner_games());
        beginnerWins.setText("Wins: " + st.getBeginner_wins());
        beginnerWinRate.setText("WinRate: " + st.getBeginner_winRate());
        beginnerBestTime.setText("Best time:" + st.getBeginner_best_time());

        titleIntermediate.setText("Intermediate");
        intermediateGames.setText("Games: " +st.getIntermediate_games());
        intermediateWins.setText("Wins: " +st.getIntermediate_wins());
        intermediateWinRate.setText("WinRate: " +st.getIntermediate_winRate());
        intermediateBestTime.setText("Best time:" +st.getIntermediate_best_time());

        titlePro.setText("Pro");
        proGames.setText("Games: " +st.getPro_games());
        proWins.setText("Wins: " +st.getPro_wins());
        proWinRate.setText("WinRate: " +st.getPro_winRate());
        proBestTime.setText("Best time:" +st.getPro_best_time());

        titleSpecial.setText("Special");
        specialGames.setText("Games: " +st.getSpecial_games());
        specialWins.setText("Wins: " +st.getSpecial_wins());
        specialWinRate.setText("WinRate: " +st.getSpecial_winRate());
        specialBestTime.setText("Best time:" +st.getSpecial_best_time());

        return v;
    }
}
