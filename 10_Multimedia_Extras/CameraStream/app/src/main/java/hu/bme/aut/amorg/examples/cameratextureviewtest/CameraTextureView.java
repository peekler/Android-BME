package hu.bme.aut.amorg.examples.cameratextureviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.TextureView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CameraTextureView extends TextureView implements
		TextureView.SurfaceTextureListener {

	public interface PreviewUploader {
		public void previewReady(byte[] preview);
	}

	private Camera camera;
	private PreviewUploader previewUploader = null;
	private long ts_previewLastUploaded = 0;
	private final long PREVIEW_UPLOAD_DELAY = 500;

	public CameraTextureView(Context context) {
		super(context);
		setSurfaceTextureListener(this);
	}

	public CameraTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		camera = Camera.open();

		Camera.Parameters parameters = camera.getParameters();
		//parameters.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
		parameters.setPreviewFpsRange(1, 5);
		camera.setParameters(parameters);

		// Camera.Size previewSize = camera.getParameters().getPreviewSize();
		// this.setLayoutParams(new LinearLayout.LayoutParams(
		// previewSize.width, previewSize.height, Gravity.CENTER));

		try {
			camera.setPreviewTexture(surface);
		} catch (IOException t) {
		}

		camera.setPreviewCallback(prevc);

		camera.startPreview();
		ts_previewLastUploaded = 0;



		// this.setAlpha(0.5f);
		//this.setRotation(20.0f);
	}

	private PreviewCallback prevc = new PreviewCallback() {
		@Override
		public void onPreviewFrame(final byte[] data, final Camera camera) {
			//Log.d("Preview SIZE", "Preview size: "+data.length);

			if (previewUploader != null
					&& System.currentTimeMillis() - ts_previewLastUploaded > PREVIEW_UPLOAD_DELAY) {
				ts_previewLastUploaded = System.currentTimeMillis();
				try {
					Camera.Parameters parameters = camera.getParameters();
					Size size = parameters.getPreviewSize();
					YuvImage image = new YuvImage(data,
							parameters.getPreviewFormat(), size.width,
							size.height, null);
					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					image.compressToJpeg(
							new Rect(0, 0, image.getWidth(), image.getHeight()),
							30, bos);
					
					Bitmap bitmap = BitmapFactory.decodeByteArray(
						bos.toByteArray(), 0, bos.toByteArray().length);
					bitmap = Bitmap.createScaledBitmap(bitmap, 320, 240, false);
					bos.reset();
					bitmap.compress(CompressFormat.JPEG, 30, bos);
					bitmap.recycle();


					previewUploader.previewReady(bos.toByteArray());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// Ignored, the Camera does all the work for us
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// Called whenever a new frame is available and displayed in the
		// TextureView
	}

	public void takePhoto(PictureCallback jpegCallBack) {
		camera.takePicture(null, null, jpegCallBack);
	}

	public void setPreviewUploader(PreviewUploader previewUploader) {
		this.previewUploader = previewUploader;
	}
}
