package hu.rik.android.intentdemo;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.tvFile)
    TextView tvFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        if (getIntent().getData() != null) {
            String path = getIntent().getData().getPath();
            tvFile.setText(path);
        }

    }

    @OnClick(R.id.btnIntentTest)
    public void submit(View v) {
        Intent intentTest = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:+36201234567")
        );

        /*Intent intentTest = new Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
        );*/

        /*Intent intentTest = new Intent(Intent.ACTION_WEB_SEARCH);
        intentTest.putExtra(SearchManager.QUERY, "Balaton");*/

        startActivity(intentTest);
    }

}
