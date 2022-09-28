package com.mrt7l.helpers;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class DownloadTask {

    private static final String TAG = "Download Task";
    private Context context;

    private String downloadFileUrl = "", downloadFileName = "";
//    private final ProgressDialog progressDialog;
    long downloadID;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                openDownloadedAttachment(downloadID);
            }
        }
    };

     public DownloadTask(Context context, String downloadUrl) {
        this.context = context;

        this.downloadFileUrl = downloadUrl;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("جاري تحميل الفاتورة");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        downloadFileName = downloadFileUrl.substring(downloadFileUrl.lastIndexOf('/') + 1);//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);

        context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadFile(downloadFileUrl);

    }

     public void downloadFile(String url) {

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), downloadFileName);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)// Visibility of the download Notification
                    .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            downloadFileName
                    )
                    .setDestinationUri(Uri.fromFile(file))
                    .setTitle(downloadFileName)// Title of the Download Notification
                    .setDescription("جاري تحميل الفاتورة")// Description of the Download Notification
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network


            request.allowScanningByMediaScanner();
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.


        } catch (Exception e) {
            Log.d("Download", e.toString());
        }


    }

    void downloadCompleted(long downloadID) {

//        progressDialog.dismiss();

        new AlertDialog.Builder(context)
                .setTitle("Document")
                .setMessage("Document Downloaded Successfully")

                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        openDownloadedAttachment(downloadID);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        context.unregisterReceiver(onDownloadComplete);

    }

    Uri path;

    private void openDownloadedAttachment(final long downloadId) {
       DialogsHelper.removeSimpleProgressDialog();
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider",
                        new File(Objects.requireNonNull(Uri.parse(downloadLocalUri).getPath())));
                //path = Uri.parse(downloadLocalUri);
                File sharingFile = new File(Objects.requireNonNull(path.getPath()));
                Intent pdfIntent = new Intent(Intent.ACTION_SEND);
                Uri uri = Uri.parse("file://" + sharingFile.getAbsolutePath() + ".pdf");
                pdfIntent.putExtra(Intent.EXTRA_STREAM, path);

                pdfIntent.setDataAndType(path, downloadMimeType);

                pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    context.startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
            }
        }
        cursor.close();
    }

}