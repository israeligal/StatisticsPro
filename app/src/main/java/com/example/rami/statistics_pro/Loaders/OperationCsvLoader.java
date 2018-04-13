package com.example.rami.statistics_pro.Loaders;
//
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.rami.statistics_pro.BaseClass.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;


public class OperationCsvLoader extends AsyncTaskLoader<String> {

    private final static String LOG_TAG = OperationCsvLoader.class.getName();
    private String mFilepath;
    private Game mGame;

    public OperationCsvLoader(Context context, String filepath, Game game) {
        super(context);
        mFilepath = filepath;
        mGame = game;
    }

    @Override
    public String loadInBackground() {
        CSVReader reader;

        try {

            reader = new CSVReader(new FileReader(mFilepath));


            reader.readNext();// skip headers line
            String[] nextLine = reader.readNext();
            String stringId = nextLine[mGame.getGameCsvContract().getRaffleIdNumber()];
            int numberOfRafflesInCsv = Integer.parseInt(stringId);
            int gameRafflesAmount = mGame.getGameRaffles().size();

            while (nextLine != null) {

                if (gameRafflesAmount == numberOfRafflesInCsv) {
                    Log.d(LOG_TAG, "Raffles already loaded");
                    break;
                } else if (gameRafflesAmount > numberOfRafflesInCsv) {
                    Log.w(LOG_TAG, "error with raffles amount" + "Raffles size: " +
                            gameRafflesAmount + "csv Raffles size: " + numberOfRafflesInCsv);
                    break;
                }
                Raffle raffle = mGame.addRaffleFromCsv(nextLine);
                ContentValues raffleContentValues = raffle.raffleToContentValues();
                getContext().getContentResolver().insert(mGame.getSqlRaffleDb()
                        , raffleContentValues);

                nextLine = reader.readNext();
            }
            Log.d(LOG_TAG, "loaded raffle into curgame and mysql");

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while loading raffles");
            e.printStackTrace();
            return null;
        }

        Log.d(LOG_TAG, "loaded raffle into curgame and mysql");
        return "Raffles loaded Successfully";
    }

    @Override
    protected void onStartLoading() {

        Log.d(LOG_TAG, "Start background process");


        forceLoad();
    }
}




