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
        return null;
    }
}
