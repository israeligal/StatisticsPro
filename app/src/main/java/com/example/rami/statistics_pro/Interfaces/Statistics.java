package com.example.rami.statistics_pro.Interfaces;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.R;

import java.util.ArrayList;


public abstract class Statistics {
    private final static String LOG_TAG = Statistics.class.getName();


    public abstract int[]  statisticsNumberAppearance(ArrayList<ToggleButton> chosenNumbers);

    public abstract void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view, ArrayList<ToggleButton> chosenNumbers);

    public abstract void addAdditionalStatistics(ViewGroup mview);

    public void addAppearanceRow(ViewGroup mview, ArrayList<ToggleButton> chosenNumbers){

            TableRow countTableRow = mview.findViewById(R.id.statistics_count_appearance_row);
            TableRow chosenTableRow = mview.findViewById(R.id.statistics_chosen_numbers_appearance_row);
            int[] numberAppearance = statisticsNumberAppearance(chosenNumbers);

            handleRow(countTableRow);
            handleRow(chosenTableRow);
            for (int i = 0; i < numberAppearance.length; ++i) {
                TextView chosenTextView = (TextView) chosenTableRow.getChildAt(i);
                TextView countTextView = (TextView) countTableRow.getChildAt(i);
                handleRowText(chosenTextView, chosenNumbers.get(i).getText().toString());
                handleRowText(countTextView, String.valueOf(numberAppearance[i]));
            }
            addAdditionalStatistics(mview);

        }
    private  void handleRow(TableRow tableRow){
        tableRow.setVisibility(View.VISIBLE);
        TextView rowText = (TextView) tableRow.getChildAt(tableRow.getChildCount() - 1);
        rowText.setVisibility(View.VISIBLE);
    }
    private void handleRowText(TextView textView, String textViewString){
        textView.setText(textViewString);
        float textSize = textView.getResources().getDimension(R.dimen.chosenNumbersAppearance);
        Log.d(LOG_TAG, "handleRowText: textSize "+ textSize);
        textView.setTextSize(16);
        textView.setVisibility(TextView.VISIBLE);
    }

}
