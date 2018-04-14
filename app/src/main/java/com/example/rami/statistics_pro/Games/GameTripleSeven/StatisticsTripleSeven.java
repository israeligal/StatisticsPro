package com.example.rami.statistics_pro.Games.GameTripleSeven;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.BaseClass.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.BaseClass.Statistics;
import com.example.rami.statistics_pro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class StatisticsTripleSeven extends Statistics {
    private final static String LOG_TAG = StatisticsTripleSeven.class.getName();
    private int[] mNumberAppearance;
    private String mLastTimeAppearedTogether;
    private int mNumberOfTimesAppearedTogether;
    private Game game;
    StatisticsTripleSeven(GameTripleSeven gameTripleSeven){
        game = gameTripleSeven;
        mNumberOfTimesAppearedTogether = 0;
        mLastTimeAppearedTogether = null;
        mNumberAppearance = new int[game.getTOTAL_NUMBERS()];
    }


    public void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view, ArrayList<ToggleButton> toggleBtnChosenNumbers) {

        ArrayList<Integer> chosenNumbers = getNumbersFromToggleBtns(toggleBtnChosenNumbers);
        mNumberOfTimesAppearedTogether = 0;
        mLastTimeAppearedTogether = null;
        mNumberAppearance = new int[game.getTOTAL_NUMBERS()];

        Log.d(LOG_TAG, "Raffles Amount: " + game.getGameRaffles().size());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        String timeFrom = timeFromFull.substring(0, 6)  + timeFromFull.substring(8);
        String timeEnd = timeEndFull.substring(0, 6) + timeEndFull.substring(8);
        Log.d(LOG_TAG, "timeFrom " + timeFrom + " timeEnd " + timeEnd);
        Date timeFromDate, timeEndDate;
        try {
            timeFromDate = sdf.parse(timeFrom);
            timeEndDate = sdf.parse(timeEnd);

        } catch (ParseException e) {
            Log.e(LOG_TAG,"Time formats problem timeFrom "+ timeFrom + " timeEnd " + timeEnd);
            e.printStackTrace();
            return;
        }
        Date raffleDate;
        int chosenNumbersFoundInRaffle = 0;
        int i = 0;

        for(Raffle curRaffle: game.getGameRaffles()){
            try {
                raffleDate = sdf.parse(curRaffle.getmRaffleDate());
                if (raffleDate.compareTo(timeFromDate) >= 0 && raffleDate.compareTo(timeEndDate) <= 0) {
                    i++;
                    for (int num: curRaffle.getmRaffleResultNumbers()) {
                            if(chosenNumbers.contains(num)){
                                chosenNumbersFoundInRaffle++;
                            }
                        mNumberAppearance[num - 1] += 1;
                    }

                    if(chosenNumbersFoundInRaffle == chosenNumbers.size()){
                            if(mLastTimeAppearedTogether == null){
                                mLastTimeAppearedTogether = curRaffle.getmRaffleDate();
                            }
                        mNumberOfTimesAppearedTogether++;
                    }
                }
                chosenNumbersFoundInRaffle = 0;

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

    @Override
    public void addAdditionalStatistics(ViewGroup mview) {

        TableLayout tableLayout = mview.findViewById(R.id.additional_statistics_table_layout);
        tableLayout.removeViews(1,tableLayout.getChildCount() - 1);
        TextView timesAppearedTogether = (TextView)LayoutInflater.from(mview.getContext())
                .inflate(R.layout.additional_statistics_info_text_view, mview, false);
        TextView LastTimeAppearedTogether = (TextView)LayoutInflater.from(mview.getContext())
                .inflate(R.layout.additional_statistics_info_text_view, mview, false);

        Resources resources = mview.getResources();
        int timesAppearedColor = resources.getColor(R.color.statistics_chosen_numbers_appearance_row);
        int LastAppearedColor = resources.getColor(R.color.statistics_count_appearance_row);

        timesAppearedTogether.setText(resources.getString(R.string.times_numbers_appeared_togther, mNumberOfTimesAppearedTogether));
        tableLayout.addView(timesAppearedTogether);
        timesAppearedTogether.setBackgroundColor(timesAppearedColor);

        if(mLastTimeAppearedTogether != null){
            LastTimeAppearedTogether.setText(resources.getString(R.string.last_time_appeared_together, mLastTimeAppearedTogether));
            LastTimeAppearedTogether.setBackgroundColor(LastAppearedColor);
            tableLayout.addView(LastTimeAppearedTogether);
        }
        else{
            LastTimeAppearedTogether.setText("");
        }
    }




    private ArrayList<Integer> getNumbersFromToggleBtns(ArrayList<ToggleButton> toggleBtnChosenNumbers) {
        ArrayList<Integer> numList = new ArrayList<>();
        for (ToggleButton toggleButton : toggleBtnChosenNumbers){
            String numStr = toggleButton.getText().toString();
            numList.add(Integer.parseInt(numStr));
        }
        return numList;
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
