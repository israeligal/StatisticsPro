//package com.example.rami.statistics_pro.Tasks;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.PowerManager;
//import android.support.v4.content.AsyncTaskLoader;
//import android.util.Log;
//import android.widget.ProgressBar;
//
//import com.example.rami.statistics_pro.Utils.CsvUtils;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//// usually, subclasses of AsyncTask are declared inside the activity class.
//// that way, you can easily modify the UI thread from here
//// TODO CHANGE DOWNLOAD TASK TO DOWNLOADTASK LOADER
//
//public class DownloadTaskLoader extends AsyncTaskLoader<String, Integer, String> {
//    private Context context;
//    private PowerManager.WakeLock mWakeLock;
//    private ProgressBar mProgressBar;
//    private static String LOG_TAG = DownloadTask.class.getName();
//
//    public DownloadTaskLoader(Context context, ProgressBar progressBar) {
//        super(context);
//        this.context = context;
//        this.mProgressBar = progressBar;
//
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        mProgressBar.setProgress(values[0]);
//    }
//
//
//    @Override
//    protected void onPostExecute(String s) {
//        Log.d(LOG_TAG, "onPostExecute " + s);
//        mProgressBar.setVisibility(ProgressBar.GONE);
//        super.onPostExecute(s);
//    }
//
//    @Override
//    protected void onCancelled(String s) {
//        Log.d(LOG_TAG, "onCancelled " + s);
//        mProgressBar.setVisibility(ProgressBar.GONE);
//        super.onCancelled(s);
//    }
//
//    @Override
//    protected void onCancelled() {
//        Log.d(LOG_TAG, "onCancelled without string" );
//
//        mProgressBar.setVisibility(ProgressBar.GONE);
//
//    }
//
//    @Override
//    protected String loadInBackground(String... sUrl) {
//        InputStream input = null;
//        OutputStream output = null;
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(sUrl[0]);
//            Log.d(LOG_TAG, "url to download " + sUrl[0]);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            // expect HTTP 200 OK, so we don't mistakenly save error report
//            // instead of the file
//
//            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                Log.e(LOG_TAG, "bad connection response " + connection.getResponseCode());
//                return "Server returned HTTP " + connection.getResponseCode()
//                        + " " + connection.getResponseMessage();
//            }
//
//            // this will be useful to display download percentage
//            // might be -1: server did not report the length
//            int fileLength = connection.getContentLength();
//            Log.d(LOG_TAG, "file length "+ fileLength);
//
//            mProgressBar.setMax(fileLength);
//            // download the file
//            input = connection.getInputStream();
////            String path = context.getFilesDir();
//            String path = context.getFilesDir().getPath();
//            String file_path = path + "/" + CsvUtils.FILE_NAME;
//            output = new FileOutputStream(file_path);
//            Log.d(LOG_TAG, "file path: " + file_path);
//            byte data[] = new byte[4096];
//            long total = 0;
//            int count;
//            while ((count = input.read(data)) != -1) {
//                // allow canceling with back button
//                if (isCancelled()) {
//                    input.close();
//                    return null;
//                }
//                total += count;
//                // publishing the progress....
//                if (fileLength > 0) // only if total length is known
//                    publishProgress((int) (total * 100 / fileLength));
//                output.write(data, 0, count);
//
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG,"exceptin while reading file" + e);
//            return e.toString();
//        } finally {
//            try {
//                Log.d(LOG_TAG,"downloadTask finally section");
//                if (output != null)
//                    output.close();
//                if (input != null)
//                    input.close();
//            } catch (IOException ignored) {
//                Log.w(LOG_TAG,"downloadTask catch IOException "+ ignored);
//            }
//
//            if (connection != null)
//                connection.disconnect();
//        }
//
//
//
//        return "Downloaded Successfully";
//    }
//
//
//
//}