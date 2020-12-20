package org.dolphinemu.dolphinemu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;

import android.os.Environment;
import android.os.Handler;

public class DownloadUtils implements Runnable
{
  private Handler mHandler;
  private DownloadCallback mCallback;
  private File mDownloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
  private final String mUrl;
  private HttpURLConnection mUrlConnection;
  private File mFile;
  private boolean mIsRunning = false;
  private boolean mIsStopIntentional = false;

  /**
   * Default contructor.
   * <br><br>
   * Call start() to start the download.
   *
   * @param handler Handler that will handle download status callbacks.
   * @param callback Listener of download status callbacks.
   * @param url The url of the file to download.
   * @param path The path to download the file to.
   */
  public DownloadUtils(Handler handler, DownloadCallback callback, String url, File path)
  {
    mHandler = handler;
    mCallback = callback;
    mUrl = url;
    mDownloadPath = path;
  }

  /**
   * Alternate constructor, when no callbacks are needed (e.g. background task).
   * <br><br>
   * Call start() to start the download.
   *
   * @param url The url of the file to download.
   * @param path The path to download the file to.
   */
  public DownloadUtils(String url, File path)
  {
    mUrl = url;
    mDownloadPath = path;
  }

  /**
   * Alternate constructor. getExternalStoragePublicDirectory() is deprecated so consider using
   * the default constructor, getting the path from a context. The download path is the Downloads folder.
   * <br><br>
   * Call start() to start the download.
   *
   * @param handler Handler that will handle download status callbacks.
   * @param callback The listener of download status callbacks.
   * @param url The url of the file to download.
   *
   * @deprecated
   */
  public DownloadUtils(Handler handler, DownloadCallback callback, String url)
  {
    mHandler = handler;
    mCallback = callback;
    mUrl = url;
  }

  /**
   * Alternate constructor, getExternalStoragePublicDirectory() is deprecated so consider using
   * the default constructor, getting the path from a context. The download path is the Downloads folder.
   * <br><br>
   * Call start() to start the download.
   *
   * @param url The url of the file to download.
   *
   * @deprecated
   */
  public DownloadUtils(String url)
  {
    mUrl = url;
  }

  /**
   * Start download on a new thread.
   */
  public void start()
  {
    Thread downloadThread = new Thread(this);
    downloadThread.start();
  }

  /**
   * Cancel the current downloads by disconnecting from the url.
   * Report cancelled status back to the listener if any.
   */
  public void cancel()
  {
    mIsStopIntentional = true;
    if (mUrlConnection != null)
      mUrlConnection.disconnect();
  }

  /**
   * Get download status.
   */
  public boolean isRunning()
  {
    return mIsRunning;
  }

  @Override
  public void run()
  {
    downloadFile();
  }

  private void downloadFile()
  {
    try {
      URL url = new URL(mUrl);

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      mUrlConnection = urlConnection;
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();
      mIsRunning = true;
      if (mHandler != null) mHandler.post(() -> mCallback.onDownloadStart());

      String filename = "download.apk";
      String fieldContentDisp = urlConnection.getHeaderField("Content-Disposition");
      if (fieldContentDisp != null && fieldContentDisp.contains("filename=")) {
        filename = fieldContentDisp.substring(fieldContentDisp.indexOf("filename=") + 9);
      }
      File file = new File(mDownloadPath, filename);
      mFile = file;

      FileOutputStream fileOutput = new FileOutputStream(file);
      InputStream inputStream = urlConnection.getInputStream();

      float totalSize = urlConnection.getContentLength();
      int downloadedSize = 0;

      byte[] buffer = new byte[1024];
      int bufferLength = 0;

      while ((bufferLength = inputStream.read(buffer)) > 0) {
        fileOutput.write(buffer, 0, bufferLength);
        downloadedSize += bufferLength;

        int progress = (int) (downloadedSize / totalSize * 100);
        if (mHandler != null) mHandler.post(() -> mCallback.onDownloadProgress(progress));
      }

      fileOutput.close();
      urlConnection.disconnect();
      mIsRunning = false;
      if (mHandler != null) mHandler.post(() -> mCallback.onDownloadComplete(mFile));
    }
    catch (Exception e)
    {
      mIsRunning = false;
      if (mHandler != null)
      {
        if (mIsStopIntentional)
        {
          mHandler.post(() -> mCallback.onDownloadCancelled());
          mIsStopIntentional = false;
        }
        else mHandler.post(() -> mCallback.onDownloadError());
      }
      deleteFile();
    }
  }

  /**
   * Delete downloaded file.
   */
  private void deleteFile()
  {
    if (mFile != null)
      mFile.delete();
  }

  /**
   * This setter is here for convenience as you should always use the constructor.
   *
   * @param handler Handler that will handle download status callbacks.
   */
  public void setHandler(Handler handler)
  {
    mHandler = handler;
  }

  /**
   * This setter is here for convenience as you should always use the constructor.
   *
   * @param listener The listener of download status callbacks.
   */
  public void setCallbackListener(DownloadCallback listener)
  {
    mCallback = listener;
  }

  /**
   * This setter is here for convenience as you should always use the constructor.
   *
   * @param path The path to download the file to.
   */
  public void setDownloadPath(String path)
  {
    mDownloadPath = new File(path);
  }
}
