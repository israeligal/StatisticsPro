package com.example.rami.statistics_pro.Loaders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.R;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;


public class OperationSearchLoader extends AsyncTaskLoader<String> {
    private Game mGame;
    private Activity mActivity;
    ProgressBar mProgressBar;
    private final static String LOG_TAG = OperationSearchLoader.class.getName();
    private Bundle mArgs;

    public OperationSearchLoader(Context context, Game game, Bundle args, Activity activity) {
        super(context);
        mGame = game;
        mArgs = args;
        mActivity = activity;
    }

    @Override
    public String loadInBackground() {

        String filePath = mArgs.getString("filePath");
        CSVReader reader;

        try {

            assert filePath != null;
            reader = new CSVReader(new FileReader(filePath));


            reader.readNext();// skip headers line
            String[] nextLine = reader.readNext();
            String stringId = nextLine[mGame.getGameCsvContract().getRaffleIdNumber()];
            int numberOfRaffles = Integer.parseInt(stringId);

            while (nextLine != null) {
                mProgressBar.setProgress(mProgressBar.getProgress() + 1);

                if(mGame.getGameRaffles().size() == numberOfRaffles){
                    Log.d(LOG_TAG, "Raffles already loaded");
                    break;
                }
                else if(mGame.getGameRaffles().size() > numberOfRaffles){
                    Log.w(LOG_TAG, "error with raffles amount" + "Raffles size: " +
                            mGame.getGameRaffles().size() + "csv Raffles size: "+ numberOfRaffles);
                    break;
                }
                Raffle raffle = mGame.addRaffleFromCsv(nextLine);
                ContentValues raffleContentValues = raffle.raffleToContentValues();
                getContext().getContentResolver().insert(mGame.getSqlRaffleDb(), raffleContentValues);

                nextLine = reader.readNext();
            }
            Log.d(LOG_TAG, "loaded raffle into curgame and mysql");

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while loading raffles");
            e.printStackTrace();
            return null;
        }

        finally {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
        Log.d(LOG_TAG, "loaded raffle into curgame and mysql");
        return "Raffles loaded Successfully";
    }
    @Override
    protected void onStartLoading() {
        ViewGroup mview = mActivity.findViewById(R.id.choose_numbers_fragment_layout);
        mProgressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setMax(10000);
        mview.addView(mProgressBar);
        Log.d(LOG_TAG,"Start background process");

        //Think of this as AsyncTask onPreExecute() method,start your progress bar,and at the end call forceLoad();
        forceLoad();
    }



}
