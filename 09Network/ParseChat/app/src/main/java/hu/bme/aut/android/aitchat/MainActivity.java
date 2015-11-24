package hu.bme.aut.android.aitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TABLE_MESSAGES = "messages";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_MESSAGE = "message";
    @Bind(R.id.etMessage)
    protected EditText etMessage;
    @Bind(R.id.btnCreate)
    protected Button btnCreate;
    @Bind(R.id.btnRefresh)
    protected Button btnRefresh;
    @Bind(R.id.tvMessages)
    protected TextView tvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        ParseObject testMessage = new ParseObject("messages");
//        testMessage.put("username", "John Doe");
//        testMessage.put("message", "test message");
//        testMessage.saveInBackground();
    }

    @OnClick(R.id.btnCreate)
    protected void sendMessage() {
        ParseObject objMsg = new ParseObject(TABLE_MESSAGES);
        objMsg.put(KEY_USERNAME, ParseUser.getCurrentUser().getUsername());
        objMsg.put(KEY_MESSAGE, etMessage.getText().toString());

        objMsg.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Message sent",
                            Toast.LENGTH_SHORT).show();

                    ParsePush push = new ParsePush();
                    push.setChannel("AITChat");
                    push.setMessage(ParseUser.getCurrentUser().getUsername()+
                                    ": "+etMessage.getText());
                    push.sendInBackground(new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this,
                                        "Push sent",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Error in push: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this,
                            "Send failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @OnClick(R.id.btnRefresh)
    protected void refreshMessages() {
        ParseQuery<ParseObject> pq = ParseQuery.getQuery(TABLE_MESSAGES);
        pq.setLimit(500);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects,
                             ParseException e) {
                StringBuilder sb = new StringBuilder();
                for (ParseObject po : parseObjects) {
                    sb.append(po.getString(KEY_USERNAME) + ": " +
                            po.getString(KEY_MESSAGE) + "\n");
                }

                tvMessages.setText(sb.toString());
            }
        });
    }
}
