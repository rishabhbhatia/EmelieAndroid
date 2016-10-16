package com.satiate.emelie.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.base.EmelieFragment;
import com.satiate.emelie.utils.Const;

/**
 * Created by Rishabh Bhatia on 10/14/2016.
 */

public class CameraUtils {

    public static boolean checkForCameraPermission(EmelieActivity activity, EmelieFragment fragment,
                                                   int REQUEST_CODE_ASK_PERMISSIONS)
    {
        try {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

            int hasStoragePermission = ContextCompat.checkSelfPermission(activity,
                    permissions[0]);
            int hasCameraPermission = ContextCompat.checkSelfPermission(activity,
                    permissions[1]);

            Log.d(Const.TAG, "Has storage permission is: " + hasStoragePermission);
            Log.d(Const.TAG, "Has camera permission is: " + hasCameraPermission);


            if (hasStoragePermission != PackageManager.PERMISSION_GRANTED)
            {
                fragment.requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            } else if (hasCameraPermission != PackageManager.PERMISSION_GRANTED)
            {
                fragment.requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            } else {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable)
    {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
