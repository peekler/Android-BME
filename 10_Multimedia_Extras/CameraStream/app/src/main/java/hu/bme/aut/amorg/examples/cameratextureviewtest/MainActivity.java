package hu.bme.aut.amorg.examples.cameratextureviewtest;

import hu.bme.aut.amorg.examples.cameratextureviewtest.CameraTextureView.PreviewUploader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements PreviewUploader{ 

	private CameraTextureView camTextureView;
	private LinearLayout layoutMain;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        camTextureView = (CameraTextureView) findViewById(R.id.camTextureView);
        
        Button btnTakePicture = (Button) findViewById(R.id.btnPhoto);
        btnTakePicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				camTextureView.takePhoto(pc);
			}
		});
        
        camTextureView.setPreviewUploader(this);
    }
    
    private PictureCallback pc =new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			ImageView v = new ImageView(MainActivity.this);
	        v.setImageBitmap(bitmap);
	        layoutMain.addView(v);
		}
	};

	@Override
	public void previewReady(byte[] preview) {
		AsyncTaskUploadImage taskUpload = new AsyncTaskUploadImage(
				MainActivity.this, preview);
		taskUpload.execute("http://atleast.aut.bme.hu/EklerP/preview/upload.php");
	}

}