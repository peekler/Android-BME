package demo.android.app.hu.bindservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;

/**
 * Created by Peter on 2014.10.21..
 */
public class MyBoundService extends Service {

    private IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        return myBinder;
    }



  public long getFreeSpace() {
        StatFs statFs = new StatFs(
            Environment.getExternalStorageDirectory().getAbsolutePath()
        );
        statFs.restat(Environment.getExternalStorageDirectory().getAbsolutePath());
        long available = ((long)statFs.getAvailableBlocks() * (long)statFs.getBlockSize());
        return available/1024/1024;
    }

    public class MyLocalBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }
}
