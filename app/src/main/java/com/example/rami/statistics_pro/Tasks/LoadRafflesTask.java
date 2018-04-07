package com.example.rami.statistics_pro.Tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;


// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
// TODO dont download file if existing

public class LoadRafflesTask extends AsyncTask<FileReader, Integer, String> {
    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private ProgressBar mProgressBar;
    private static String LOG_TAG = LoadRafflesTask.class.getName();
    private Game curGame;


    public LoadRafflesTask(Context context, ProgressBar progressBar, Game curGame) {
        this.context = context;
        this.mProgressBar = progressBar;
        this.curGame = curGame;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.setProgress(values[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d(LOG_TAG, "onPostExecute " + s);
        mProgressBar.setVisibility(ProgressBar.GONE);
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled(String s) {
        Log.d(LOG_TAG, "onCancelled " + s);
        mProgressBar.setVisibility(ProgressBar.GONE);
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        Log.d(LOG_TAG, "onCancelled without string" );

        mProgressBar.setVisibility(ProgressBar.GONE);

    }


    @Override
    protected String doInBackground(FileReader... filereaders) {
        try {
            CSVReader reader = new CSVReader(filereaders[0]);
            String[] nextLine;
            reader.readNext();// skip headers line
            mProgressBar.setMax(10000);
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                Raffle raffle = curGame.addRaffleFromCsv(nextLine);
                ContentValues raffleContentValues = raffle.raffleToContentValues();
                context.getContentResolver().insert(curGame.getSqlRaffleDb(), raffleContentValues);

//                ContentValues raffleNumbersContentValues = raffle.numbersToContentValues(); TODO fix content provider class java
//                mview.getContext().getContentResolver().insert(curGame.getSqlRaffleNumbersDb(), raffleNumbersContentValues);





            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            mProgressBar.setVisibility(View.GONE);

        }



        return "Raffles loaded Successfully";
    }
}