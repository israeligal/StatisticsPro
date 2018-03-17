package com.example.rami.statistics_pro.Utils;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

public class CsvUtils {
    private static File csvFile;
    static String FILE_NAME = "statisticsPro.csv";
    private static String csv_url = "http://www.pais.co.il/777/Pages/RequestsHandler.ashx?Command=Statistics" +
            "_ResultsArchive&Lottery=3&SearchBy=Date&From=13/03/2002&To=17/03/2018&Subgame=undefined&stmp=1521303625540";
    private static FileInputStream inputStream;
    private static Date date;

    //TODO change csv url dates, make sure progressbar works
    //TODO make sure file is downloaded
    //TODO change readcsvfile return value

    public static boolean readCsvFile(Context context) {
        ProgressBar mProgressBar;

        try {
            System.out.println("inputStrean");
            inputStream = new FileInputStream(FILE_NAME);
        } catch (FileNotFoundException file_not_found) {
            // execute this when the downloader must be fired
            mProgressBar = setProgressBar(context);
            System.out.println("file not found");
            mProgressBar.setVisibility(View.VISIBLE);
            final DownloadTask downloadTask = new DownloadTask(context, mProgressBar);
            AsyncTask<String,Integer,String> task = downloadTask.execute(CsvUtils.csv_url);
            System.out.println(task.getStatus());
//            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    downloadTask.cancel(true);
//                }
//            });
        }
        return true;
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
