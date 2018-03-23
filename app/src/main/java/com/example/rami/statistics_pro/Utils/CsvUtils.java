package com.example.rami.statistics_pro.Utils;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CsvUtils {
    private static File csvFile;
    static String FILE_NAME = "statisticsPro.csv";

    public static FileInputStream getInputStream() {
        return inputStream;
    }

    public static void setInputStream(FileInputStream inputStream) {
        CsvUtils.inputStream = inputStream;
    }

    private static FileInputStream inputStream;
    private static Date date;
    private static String LOG_TAG = CsvUtils.class.getName();
    //TODO  make sure progressbar works

    public static FileReader readCsvFile(View view, String csv_url) {
        FileReader fileReader = null;
        String path = view.getContext().getFilesDir().getPath();
        try {
            fileReader = new FileReader(path + "/" + FILE_NAME);
            Log.d(LOG_TAG,"csv file found");
            return fileReader;
        } catch (FileNotFoundException file_not_found) {
            // execute this when the downloader must be fired
            Log.d(LOG_TAG,"csv file was not found, downloading file");
            ProgressBar mProgressBar = setProgressBar(view.getContext());
//            mProgressBar.setVisibility(View.VISIBLE);
            final DownloadTask downloadTask = new DownloadTask(view.getContext(), mProgressBar);
            AsyncTask<String,Integer,String> task = downloadTask.execute(csv_url);
            ((LinearLayout)view).addView(mProgressBar);

            try {
                String success = task.get();
                if (success.equals("Downloaded Successfully")){
                    fileReader = new FileReader(path + "/" + FILE_NAME);
                }
                return fileReader;

            }catch  (FileNotFoundException file_not_found_after_download){
                Log.e(LOG_TAG, "csv file was not downloaded successfully");
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    return null;
    }

    private static ProgressBar setProgressBar(Context context) {
        // instantiate it within the onCreate method
        ProgressBar mProgressBar = new ProgressBar(context);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        mProgressBar.cancelLongPress();
        return mProgressBar;
    }

    public static boolean saveCsvFile(){



        return false;
    }
}
