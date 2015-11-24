package hu.rik.android.weatherreport;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Peter on 2015.01.15..
 */
public class AsyncTaskHttpGet extends AsyncTask<String, Void, String> {

    private Context ctx;

    public AsyncTaskHttpGet(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                int ch;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((ch=is.read()) != -1) {
                    bos.write(ch);
                }
                result = new String(bos.toByteArray());
            }
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent i = new Intent(MainActivity.FILTER_WEATHER);
        i.putExtra(MainActivity.KEY_RESULT, result);
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(i);
    }
}
