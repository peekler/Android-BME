package demo.android.app.hu.ttsvoicerecogdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    public static final String TAG = "MainActivity";

    private EditText etData;
    private TextView tvDetectedText;
    private TextToSpeech tts;
    private android.speech.SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        etData = (EditText) findViewById(R.id.etData);
        tvDetectedText = (TextView) findViewById(R.id.tvDetectedText);

        Button btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(etData.getText().toString());
            }
        });

        Button btnDetect = (Button) findViewById(R.id.btnDetect);
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                        "hu-HU");
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        "demo.android.app.hu.ttsvoicerecogdemo");

                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                sr.startListening(intent);
            }
        });


        sr = android.speech.SpeechRecognizer
                .createSpeechRecognizer(this);
        sr.setRecognitionListener(new SpeechRecognizer());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (sr != null) {
                sr.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setSpeechRate((float) 0.8);
            // tts.setPitch(1.0f); tts.setPitch(1.1f);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Intent installIntent = new Intent();
                installIntent
                        .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            } else {
                speak("Speech system works perfectly!");
            }

        } else {
            Intent installIntent = new Intent();
            installIntent
                    .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    class SpeechRecognizer implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList<String> data = results
                    .getStringArrayList(
                            android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
            tvDetectedText.setText("");


            boolean timeSaid = false;
            for (String text : data) {
                tvDetectedText.append(text + "\n");

                if (text.contains("time")) {
                    timeSaid = true;
                }
            }

            speak("The time is: "+new Date(System.currentTimeMillis()).toString());
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
