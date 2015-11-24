package demo.android.app.hu.socketdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends Activity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        simpleSocketDownload();
                    }
                }.start();
            }
        });
    }

    private void simpleSocketDownload() {

        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {

            socket = new Socket("hwsw.hu", 80);
            socket.setSoTimeout(10000);

            os = socket.getOutputStream();
            os.write("GET index.html HTTP/1.0\n".getBytes());
            os.write("From: john@doe.com\n".getBytes());
            os.write("User-Agent: Android eloadas/1.0\n".getBytes());
            os.write("\n".getBytes());
            os.flush();

            is = socket.getInputStream();

            int chByte;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((chByte = is.read()) != -1) {
                bos.write(chByte);
            }

            final String result = new String(bos.toByteArray());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvStatus.setText(result);
                }
            });

        } catch (final Exception e) {
            e.printStackTrace();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvStatus.setText(e.getMessage());
                }
            });

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
