package com.example.rami.statistics_pro.Utils;


import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.rami.statistics_pro.Tasks.DownloadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CsvUtils {

    public static String FILE_NAME = "statisticsProRaffles.csv";

    public static FileInputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(FileInputStream inputStream) {
        CsvUtils.inputStream = inputStream;
    }

    private static FileInputStream inputStream;
    private static Date date;
    private static String LOG_TAG = CsvUtils.class.getName();

    public static String readCsvFile(View view, String csv_url) {
        String path = view.getContext().getFilesDir().getPath();
        String filePath = path + "/" + FILE_NAME;;
        File file = new File(filePath);
        if(file.exists()){
            Log.d(LOG_TAG, "Raffles csv file exists");
            String fileDate = DateFormat.format("dd/MM/yyyy", new Date(file.lastModified())).toString();
            String todayDate = DateFormat.format("dd/MM/yyyy", Calendar.getInstance().getTime()).toString();

            if (fileDate.equals(todayDate)){
                Log.d(LOG_TAG, "Date of raffles file is matching today");
                return filePath;
            }
        }

        // execute this when the downloader must be fired
        Log.d(LOG_TAG,"Raffles csv file was not found or not dated, downloading file");
        ProgressBar mProgressBar = setProgressBar(view.getContext());
        final DownloadTask downloadTask = new DownloadTask(view.getContext(), mProgressBar);
        AsyncTask<String,Integer,String> task = downloadTask.execute(csv_url);
        ((LinearLayout)view).addView(mProgressBar);

        try {
            String success = task.get();
            if (success.equals("Downloaded Successfully") && file.exists()){
                return filePath;
            }

        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, "csv file was not downloaded successfully");
        return null;


    }

    private static ProgressBar setProgressBar(Context context) {
        ProgressBar mProgressBar = new ProgressBar(context);
        mProgressBar.cancelLongPress();
        return mProgressBar;
    }

    public static boolean saveCsvFile(){



        return false;
    }
}
