package hu.rik.android.dialogfragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements
        OptionsFragment.OptionsFragmentInterface {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        Button btnShowMsg = (Button) findViewById(R.id.btnShowMessage);
        btnShowMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(
                        getString(R.string.txt_msg));
            }
        });

        Button btnShowQuery = (Button) findViewById(R.id.btnShowQuery);
        btnShowQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OptionsFragment().show(
                        getFragmentManager(), OptionsFragment.TAG);
            }
        });

    }

    protected void showMessage(String msg) {
        MessageFragment dialog = new MessageFragment();
        Bundle b = new Bundle();
        b.putString(MessageFragment.KEY_MSG, msg);
        dialog.setArguments(b);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), MessageFragment.TAG);
    }

    @Override
    public void onOptionsFragmentResult(String fruit)
    {

        tvStatus.setText(fruit);
    }
}

