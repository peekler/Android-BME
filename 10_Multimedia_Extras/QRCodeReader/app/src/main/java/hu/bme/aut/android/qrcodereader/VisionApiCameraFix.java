package hu.bme.aut.android.qrcodereader;

import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.google.android.gms.vision.CameraSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class VisionApiCameraFix {
    /*
     * IF YOU WANT TO JUST ACCESS THE CAMERA INSTANCE SO THAT YOU CAN SET ANY OF THE PARAMETERS, VISIT THE FOLLOWING LINK:
     * https://gist.github.com/Gericop/364dd12b105fdc28a0b6
     */

    /**
     * Custom annotation to allow only valid focus modes.
     */
    @StringDef({
            Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE,
            Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO,
            Camera.Parameters.FOCUS_MODE_AUTO,
            Camera.Parameters.FOCUS_MODE_EDOF,
            Camera.Parameters.FOCUS_MODE_FIXED,
            Camera.Parameters.FOCUS_MODE_INFINITY,
            Camera.Parameters.FOCUS_MODE_MACRO
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface FocusMode {
    }

    /**
     * <p>
     * Sets the Mobile Vision API provided {@link com.google.android.gms.vision.CameraSource}'s
     * focus mode. Use {@link Camera.Parameters#FOCUS_MODE_CONTINUOUS_PICTURE} or
     * {@link Camera.Parameters#FOCUS_MODE_CONTINUOUS_VIDEO} for continuous autofocus.
     * </p>
     * <p>
     * Note that the CameraSource's {@link CameraSource#start()} or
     * {@link CameraSource#start(SurfaceHolder)} has to be called and the camera image has to be
     * showing prior using this method as the CameraSource only creates the camera after calling
     * one of those methods and the camera is not available immediately. You could implement some
     * kind of a callback method for the SurfaceHolder that notifies you when the imaging is ready
     * or use a direct action (e.g. button press) to set the focus mode.
     * </p>
     * <p>
     * Check out <a href="https://github.com/googlesamples/android-vision/blob/master/face/multi-tracker/app/src/main/java/com/google/android/gms/samples/vision/face/multitracker/ui/camera/CameraSourcePreview.java#L84">CameraSourcePreview.java</a>
     * which contains the method <code>startIfReady()</code> that has the following line:
     * <blockquote><code>mCameraSource.start(mSurfaceView.getHolder());</code></blockquote><br>
     * After this call you can use our <code>cameraFocus(...)</code> method because the camera is ready.
     * </p>
     *
     * @param cameraSource The CameraSource built with {@link com.google.android.gms.vision.CameraSource.Builder}.
     * @param focusMode    The focus mode. See {@link android.hardware.Camera.Parameters} for possible values.
     * @return true if the camera's focus is set; false otherwise.
     * @see com.google.android.gms.vision.CameraSource
     * @see android.hardware.Camera.Parameters
     */
    public static boolean cameraFocus(@NonNull CameraSource cameraSource, @FocusMode @NonNull String focusMode) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        Camera.Parameters params = camera.getParameters();

                        if (!params.getSupportedFocusModes().contains(focusMode)) {
                            return false;
                        }

                        params.setFocusMode(focusMode);
                        camera.setParameters(params);
                        return true;
                    }

                    return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return false;
    }
}