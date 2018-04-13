package com.example.rami.statistics_pro.Utils;


import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;
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


    /**
     * Checks if file already exist and if it was created today.
     * if it does returns its path, else download from the web and returns path.
     * Intended only for small downloads
     *
     * @param context context reference
     * @param csv_url csv file url
     * @return string to file path
     */
    public static String readCsvFile(Context context, String csv_url) {

        String path = context.getFilesDir().getPath();
        String filePath = path + "/" + FILE_NAME;;
        File file = new File(filePath);
        if(file.exists()){
            String fileDate = DateFormat.format("dd/MM/yyyy", new Date(file.lastModified())).toString();
            String todayDate = DateFormat.format("dd/MM/yyyy", Calendar.getInstance().getTime()).toString();

            if (fileDate.equals(todayDate)){
                return filePath;
            }
        }

        final DownloadTask downloadTask = new DownloadTask(context);
        AsyncTask<String,Integer,String> task = downloadTask.execute(csv_url);

        try {
            String success = task.get();
            if (success.equals("Downloaded Successfully") && file.exists()){
                return filePath;
            }

        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;


    }



    public static boolean saveCsvFile(){



        return false;
    }
}
