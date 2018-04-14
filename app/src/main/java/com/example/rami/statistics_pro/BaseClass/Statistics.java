package com.example.rami.statistics_pro.BaseClass;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.R;

import java.util.ArrayList;


public abstract class Statistics {
    private final static String LOG_TAG = Statistics.class.getName();

    /** add number appearance statistics to view */
    public abstract int[]  statisticsNumberAppearance(ArrayList<ToggleButton> chosenNumbers);

    /** creates statistics from game raffles array list */
    public abstract void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view, ArrayList<ToggleButton> chosenNumbers);

    /** add additional statistics to view */
    public abstract void addAdditionalStatistics(ViewGroup mview);

    /** add all statistics to view */
    public void addStatistics(ViewGroup mview, ArrayList<ToggleButton> chosenNumbers){

            TableRow countTableRow = mview.findViewById(R.id.statistics_count_appearance_row);
            TableRow chosenTableRow = mview.findViewById(R.id.statistics_chosen_numbers_appearance_row);
            int[] numberAppearance = statisticsNumberAppearance(chosenNumbers);

            handleRow(countTableRow);
            handleRow(chosenTableRow);

            for (int i = 0; i < chosenTableRow.getChildCount() - 1; ++i) {
                TextView chosenTextView = (TextView) chosenTableRow.getChildAt(i);
                TextView countTextView = (TextView) countTableRow.getChildAt(i);

                // if its a chosen number text view
                if(i < numberAppearance.length){
                    handleRowText(chosenTextView, chosenNumbers.get(i).getText().toString());
                    handleRowText(countTextView, String.valueOf(numberAppearance[i]));
                }

                // else remove text view from screen
                else{
                    chosenTextView.setVisibility(View.GONE);
                    countTextView.setVisibility(View.GONE);
                }
            }

            addAdditionalStatistics(mview);

        }

    /** make the rows and his child visible */
    private  void handleRow(TableRow tableRow){
        tableRow.setVisibility(View.VISIBLE);
        TextView rowText = (TextView) tableRow.getChildAt(tableRow.getChildCount() - 1);
        rowText.setVisibility(View.VISIBLE);
    }

    /** sets row text style */
    private void handleRowText(TextView textView, String textViewString){
        textView.setText(textViewString);
        textView.setTextSize(16);
        textView.setVisibility(TextView.VISIBLE);
    }

}
