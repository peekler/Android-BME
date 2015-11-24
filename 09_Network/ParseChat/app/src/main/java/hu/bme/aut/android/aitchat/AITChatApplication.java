package hu.bme.aut.android.aitchat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by peter on 2015. 11. 09..
 */
public class AITChatApplication extends Application {

    public static final String APP_ID =
            "RNIdjPNSQtMM9kQTIk9YfuRc2tZpSJD03REzUaks";
    public static final String CLIENT_ID =
            "7VDdgWQbeHcgBoQMibkuV7FlRPlGzLp2zhzIMihT";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, APP_ID,
                CLIENT_ID);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("AITChat");
    }
}
