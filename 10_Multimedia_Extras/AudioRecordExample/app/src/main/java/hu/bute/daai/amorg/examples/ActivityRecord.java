package hu.bute.daai.amorg.examples;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class ActivityRecord extends Activity {

	private static final String LOG_TAG = "AudioRecorderExample";
	private String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+
            "/audiorecordtest.3gp";
	private MediaPlayer mPlayer;
	private MediaRecorder mRecorder;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setViews();


        final AnimatorSet fabAnimator = (AnimatorSet) AnimatorInflater.
                loadAnimator(this, R.animator.center_scale_0);
	}
	
	private void setViews() {
		Button startRecButton = (Button) findViewById(R.id.startRecordingButton); 
		Button stopRecButton = (Button) findViewById(R.id.stopRecordingButton); 
		Button startPlayButton = (Button) findViewById(R.id.startPlayingButton); 
		Button stopPlayButton = (Button) findViewById(R.id.stopPlayingButton); 
		
		startRecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startRecording();
			}
		});
		stopRecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
		startPlayButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startPlaying();
			}
		});
		stopPlayButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopPlaying();
			}
		});

	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		try {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(
					MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(
					MediaRecorder.OutputFormat.THREE_GPP);
			File outputFile = new File(mFileName);
			if (outputFile.exists())
				outputFile.delete();
	    	outputFile.createNewFile();
			mRecorder.setOutputFile(mFileName);

			mRecorder.setAudioEncoder(
					MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.prepare();
			mRecorder.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
}