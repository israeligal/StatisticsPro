package com.example.rami.statistics_pro.Games.GameTripleSeven;


import android.net.Uri;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableRow;

import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatisticsTripleSeven implements Statistics {
    private final static String LOG_TAG = StatisticsTripleSeven.class.getName();
    private int[] mNumberAppearance;
    private Game game;
    StatisticsTripleSeven(GameTripleSeven gameTripleSeven){
        game = gameTripleSeven;
    }

    public void time_stats_from_sql(Uri sql_raffle_db, ArrayList<CheckBox> choosenNumbers, String timeFrom, String timeEnd, TableRow tableRow, ListView listView) {
    }

    public void time_stats_from_list(String timeFromFull, String timeEndFull) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
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
                Date strDate = sdf.parse(curRaffle.getmRaffleDate());
                if ((strDate.after(timeFromDate) || strDate.equals(timeFromDate)) &&
                        (strDate.before(timeEndDate) || strDate.equals(timeEndDate))) {
                    for (int num:
                            curRaffle.getmRaffleResultNumbers()) {
                        mNumberAppearance[num - 1] += 1;

                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }

    public int[] statisticsNumberAppearance( ArrayList<CheckBox> chosenNumbers) {
        int[] chosenNumberAppearance = new int[chosenNumbers.size()];
        int i = 0;
        for (CheckBox checkBox: chosenNumbers) {
            int num = Integer.parseInt(checkBox.getText().toString());
            chosenNumberAppearance[i] = mNumberAppearance[num - 1];
        }


    return chosenNumberAppearance;
    }

}
