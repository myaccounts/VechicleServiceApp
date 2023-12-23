package com.myaccounts.vechicleserviceapp.Utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.myaccounts.vechicleserviceapp.network.OkHttpStack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import okhttp3.OkHttpClient;

public class GoWheelsApp extends MultiDexApplication {


    private static GoWheelsApp mInstance;

    //private JobScheduler jobScheduler;

    public static final String TAG = GoWheelsApp.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    /**
     * Get Application instance.
     *
     * @return Application instance.
     */
    public static synchronized GoWheelsApp getInstance() {
        return mInstance;
    }

    private static final int GPS_REQUEST_CODE = 1000;
    private static int kJobId = 0;
    private static Properties properties;

    private static final long DATA_REFRESH_DURATION = 3600000L;
    ;//10000L;//

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        printHashKey();
    }



    @NonNull
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }

        return mRequestQueue;

    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }

    }

    private void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                AppLog.d("KeyHash:", keyHash);
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
   
}
