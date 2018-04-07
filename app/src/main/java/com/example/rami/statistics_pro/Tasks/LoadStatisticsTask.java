package com.example.rami.statistics_pro.Tasks;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;
import com.example.rami.statistics_pro.R;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
// TODO dont download file if existing

public class LoadStatisticsTask extends AsyncTaskLoader<String> {


    private static String LOG_TAG = LoadRafflesTask.class.getName();
    private Game curGame;


    public LoadStatisticsTask(Context context) {
        super(context);

    }

    @Override
    public String loadInBackground() {
//        try {
//            String timeFrom = timeFromEditText.getText().toString();
//            String timeTo = timeUntilEditText.getText().toString();
//            TableRow tableRow = mview.findViewById(R.id.statistics_chosen_numbers_appearance_row);
//            LinearLayout linearLayout = mview.findViewById(R.id.statistics_linear_layout);
//
//            Statistics statistics = curGame.getStatistics();
//            statistics.time_stats_from_list(timeFrom, timeTo, mview);
//            int[] numberAppearance = statistics.statisticsNumberAppearance(chosenNumbers);
//            Log.d(LOG_TAG, "numberAppearance " + Arrays.toString(numberAppearance));
//
//            tableRow.setVisibility(View.VISIBLE);
//            TextView rowText = (TextView) tableRow.getChildAt(tableRow.getChildCount() - 1);
//            rowText.setVisibility(View.VISIBLE);
//            for (int i = 0; i < numberAppearance.length; ++i) {// TODO put this inside game related class
//                TextView textView = (TextView) tableRow.getChildAt(i);
//                int num = numberAppearance[i];
//                textView.setText(String.valueOf(num));
//                textView.setTextSize(24);
//                textView.setVisibility(TextView.VISIBLE);
//
////            textView.setBackgroundColor(Color.BLUE);
//                Log.d(LOG_TAG, "set text to" + num);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            mProgressBar.setVisibility(View.GONE);
//
//        }


        return "Raffles loaded Successfully";
    }





}

