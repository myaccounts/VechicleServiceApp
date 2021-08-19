package com.myaccounts.vechicleserviceapp.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiran on 2/27/2017.
 */

public class SaveViewUtil {


    private static final File rootDir = new File(Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "/Files");
    private static Bitmap bitmap;
    private static Context context;
    private static String mImageName;
    ;
    private static String sigValue;
//    ContextWrapper cw;


    /**
     * Save picture to file
     */
    public static boolean saveScreen(View view, Context cont) {
        //determine if SDCARD is available
        context = cont;
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        sigValue = "1";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        if (!rootDir.exists()) {
            if (!rootDir.mkdirs()) {
                rootDir.mkdir();
            }
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
        try {
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
            File mediaFile;
            mImageName = "MI_" + timeStamp + ".jpg";
            File mypath=new File(directory,mImageName);
            Log.d("mimagenmae", mImageName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(mypath));
            sharedPreferences(sigValue);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            view.setDrawingCacheEnabled(false);
            bitmap = null;

        }
    }


    private static void requestStoragePermission() {


    }

    private static boolean isReadStorageAllowed() {


        return false;
    }


    public static String saveToInternalStorage(View view, Context cont) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static void sharedPreferences(String value) {
        if (sigValue.equalsIgnoreCase("1")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("App_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor curentEdit = sharedPreferences.edit();
            curentEdit.putString("CUSTOMER_SIGNATURE", encodeTobase64(SaveViewUtil.bitmap));
            //  curentEdit.putString("BitMap_Image",rootDir.getPath());
            curentEdit.commit();
        } else {

            SharedPreferences sharedPreferences = context.getSharedPreferences("App_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor curentEdit = sharedPreferences.edit();
            curentEdit.putString("PRODUCT_PHOTO", encodeTobase64(SaveViewUtil.bitmap));
            //  curentEdit.putString("BitMap_Image",rootDir.getPath());
            curentEdit.commit();
        }
    /*    SharedPreferences sharedPreferences = context.getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor curentEdit = sharedPreferences.edit();
        curentEdit.putString("PRODUCT_PHOTO", encodeTobase64(SaveViewUtil.bitmap));
        //  curentEdit.putString("BitMap_Image",rootDir.getPath());
        curentEdit.commit();*/
    }

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

}
