package demo.android.app.hu.udpdemo;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends Activity {

    private boolean UDPServerEnabled = true;
    private DatagramSocket serverUDPSocket = null;

    private TextView tvStatus;
    private EditText edtPort;
    private EditText edtMsg;
    private Button btnStartServer;
    private Button btnStopServer;

    private int msgCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        edtPort = (EditText) findViewById(R.id.edtPort);
        edtMsg = (EditText) findViewById(R.id.edtMsg);

        btnStartServer = (Button)findViewById(R.id.btnStartServer);
        btnStopServer = (Button)findViewById(R.id.btnStopServer);
        btnStartServer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String txtPort = edtPort.getText().toString();
                if (!txtPort.equals("")) {
                    UDPServerEnabled = true;
                    int port = Integer.parseInt(txtPort);
                    startUDPServer(port);
                    btnStartServer.setEnabled(false);
                    btnStopServer.setEnabled(true);
                } else {
                    Toast.makeText(MainActivity.this, R.string.txtPortEmpty, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnStopServer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                stopUDPServer();
            }
        });

        Button btnSendMsg = (Button)findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String txtPort = edtPort.getText().toString();
                if (!txtPort.equals("")) {
                    int port = Integer.parseInt(txtPort);

                    String msg = edtMsg.getText().toString();
                    sendUDPMessage(port,(msg.equals("")) ? "[empty]" : msg);
                } else {
                    Toast.makeText(MainActivity.this, R.string.txtPortEmpty, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopUDPServer();
        super.onDestroy();
    }

    private void stopUDPServer() {
        btnStartServer.setEnabled(true);
        btnStopServer.setEnabled(false);
        UDPServerEnabled = false;
        if (serverUDPSocket != null) {
            try {
                serverUDPSocket.close();
            } catch(Exception e) {
                // no need to handle currently
            }
        }
    }

    private InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    private void startUDPServer(final int port) {
        new Thread() {
            public void run() {

                byte[] message = new byte[1500];
                try {
                    serverUDPSocket = new DatagramSocket(port);
                    while(UDPServerEnabled) {
                        DatagramPacket p = new DatagramPacket(message, message.length);
                        serverUDPSocket.receive(p);
                        msgCount++;
                        final String text = new String(message, 0, p.getLength());
                        runOnUiThread(new Runnable() {
                            public void run() {
                                tvStatus.setText("In("+msgCount+"): "+text);
                            }
                        });
                    }
                } catch(final Exception e) {
                    if (UDPServerEnabled) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        stopUDPServer();
                    }
                } finally {
                    if (serverUDPSocket != null) {
                        serverUDPSocket.close();
                    }
                }
            }
        }.start();
    }

    private void sendUDPMessage(final int port, final String msg) {
        new Thread() {
            public void run() {
                try {
                    DatagramSocket s = new DatagramSocket();
                    s.setBroadcast(true);
                    int msg_length=msg.length();
                    byte[] message = msg.getBytes();
                    DatagramPacket p = new DatagramPacket(message,
                            msg_length,
                            getBroadcastAddress(),
                            port);
                    s.send(p);
                } catch(final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }.start();
    }
}