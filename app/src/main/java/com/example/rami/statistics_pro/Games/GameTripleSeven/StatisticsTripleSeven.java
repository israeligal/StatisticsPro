package com.example.rami.statistics_pro.Games.GameTripleSeven;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
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
    private int mLastTimeAppearedTogether;
    private int mnumberOfTimesAppearedTogether;
    private Game game;
    StatisticsTripleSeven(GameTripleSeven gameTripleSeven){
        game = gameTripleSeven;
    }

//    public void time_stats_from_sql(Uri sql_raffle_db, ArrayList<ToggleButton> choosenNumbers, String timeFrom, String timeEnd, TableRow tableRow, ListView listView) {
//    }

    public void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view) {


        Log.d(LOG_TAG, "Raffles Amount: " + game.getGameRaffles().size());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        String timeFrom = timeFromFull.substring(0, 6)  + timeFromFull.substring(8);
        String timeEnd = timeEndFull.substring(0, 6) + timeEndFull.substring(8);
        Log.d(LOG_TAG, "timeFrom " + timeFrom + " timeEnd " + timeEnd);
        Date timeFromDate, timeEndDate;
        mNumberAppearance = new int[game.getTOTAL_NUMBERS()];
        try {
            timeFromDate = sdf.parse(timeFrom);
            timeEndDate = sdf.parse(timeEnd);

        } catch (ParseException e) {
            Log.e(LOG_TAG,"Time formats problem timeFrom "+ timeFrom + " timeEnd " + timeEnd);
            e.printStackTrace();
            return;
        }
        Date raffleDate;
        for(Raffle curRaffle: game.getGameRaffles()){
            try {
                raffleDate = sdf.parse(curRaffle.getmRaffleDate());

                if (raffleDate.compareTo(timeFromDate) >= 0 && raffleDate.compareTo(timeEndDate) <= 0) {

                        for (int num: curRaffle.getmRaffleResultNumbers()) {

                        mNumberAppearance[num - 1] += 1;
                    }
                }

            } catch (ParseException e) {
                Log.e(LOG_TAG, "Could not calc number appearance");
                e.printStackTrace();
                return;
            }


        }
        int j = 0, sum =0;
        while(j < mNumberAppearance.length) {   // The indexer needs to be less than 10, not A itself.
            sum += mNumberAppearance[j];   // either sum = sum + ... or sum += ..., but not both
            j++;           // You need to increment the index at the end of the loop.
        }
        Log.d(LOG_TAG, "numberAppearance sum" + sum);
        Log.d(LOG_TAG, "raffles number appearance result "+ Arrays.toString(mNumberAppearance));
    }

    public int[] statisticsNumberAppearance( ArrayList<ToggleButton> chosenNumbers) {
        int[] chosenNumberAppearanceArray = new int[chosenNumbers.size()];
        Log.d(LOG_TAG,Arrays.toString(mNumberAppearance));
        for (int i = 0; i < chosenNumbers.size();i++) {
            int num = Integer.parseInt(chosenNumbers.get(i).getText().toString());
            Log.d(LOG_TAG, "number chosen " + num);

            chosenNumberAppearanceArray[i] = mNumberAppearance[num - 1];
        }


        Log.d(LOG_TAG,Arrays.toString(chosenNumberAppearanceArray));
        return chosenNumberAppearanceArray;
    }

}
