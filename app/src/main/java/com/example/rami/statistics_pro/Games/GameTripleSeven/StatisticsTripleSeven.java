package com.example.rami.statistics_pro.Games.GameTripleSeven;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class StatisticsTripleSeven implements Statistics {
    private final static String LOG_TAG = StatisticsTripleSeven.class.getName();
    private int[] mNumberAppearance;
    private Game game;
    StatisticsTripleSeven(GameTripleSeven gameTripleSeven){
        game = gameTripleSeven;
    }

//    public void time_stats_from_sql(Uri sql_raffle_db, ArrayList<ToggleButton> choosenNumbers, String timeFrom, String timeEnd, TableRow tableRow, ListView listView) {
//    }

    public void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view) {
        ProgressBar mProgressBar = new ProgressBar(view.getContext());
        mProgressBar.setVisibility(View.VISIBLE);
        view.addView(mProgressBar);

        mProgressBar.setMax(game.getGameRaffles().size());
        Log.d(LOG_TAG, "Raffles number: " + game.getGameRaffles().size());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if (timeFromFull == null || timeEndFull == null){
            timeFromFull = "01/03/2018";
            timeEndFull = "23/03/2018";
        }

        String timeFrom = timeFromFull.substring(0, 6)  + timeFromFull.substring(8);
        String timeEnd = timeEndFull.substring(0, 6) + timeEndFull.substring(8);
        Date timeFromDate = null;
        Date timeEndDate = null;
        mNumberAppearance = new int[game.getTOTAL_NUMBERS()];
        try {

            timeFromDate = sdf.parse(timeFrom);
            timeEndDate = sdf.parse(timeEnd);
        } catch (ParseException e) {
            Log.e(LOG_TAG,"Problem with timeFrom/timeEnd \ntimeFrom "+ timeFrom
            +"timeEnd " + timeEnd
            );
            e.printStackTrace();
        }
        for(Raffle curRaffle: game.getGameRaffles()){
            try {
                mProgressBar.setProgress(mProgressBar.getProgress() + 1);
                Date strDate = sdf.parse(curRaffle.getmRaffleDate());
                if ((strDate.after(timeFromDate) || strDate.equals(timeFromDate)) &&
                        (strDate.before(timeEndDate) || strDate.equals(timeEndDate))) {
                    for (int num:
                            curRaffle.getmRaffleResultNumbers()) {
                        mNumberAppearance[num - 1] += 1;

                    }
                }

            } catch (ParseException e) {
                Log.e(LOG_TAG, "Could not calc number appearance");
                e.printStackTrace();
            }
            finally {
                mProgressBar.setVisibility(View.GONE);
                view.removeView(mProgressBar);
            }

        }
        Log.d(LOG_TAG, "raffles number appearance result "+ Arrays.toString(mNumberAppearance));
    }

    public int[] statisticsNumberAppearance( ArrayList<ToggleButton> chosenNumbers) {
        int[] chosenNumberAppearance = new int[chosenNumbers.size()];
        int i = 0;
        for (ToggleButton toggleButton : chosenNumbers) {
            int num = Integer.parseInt(toggleButton.getText().toString());
            chosenNumberAppearance[i] = mNumberAppearance[num - 1];
        }


    return chosenNumberAppearance;
    }

}
