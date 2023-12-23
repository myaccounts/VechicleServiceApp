package com.myaccounts.vechicleserviceapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.myaccounts.vechicleserviceapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class BackendServiceCall {
    public Context mContext;
    private static final String TAG = BackendServiceCall.class.getSimpleName();
    private OnServiceCallCompleteListener mListener;
    private boolean mIsProgressDialogShow;
    private ProgressDialog pDialog;
    private static final String DEVICE_OFFLINE_MESSAGE = "Device is offline...";

    public void setOnServiceCallCompleteListener(OnServiceCallCompleteListener listener) {
        this.mListener = listener;
    }

    public BackendServiceCall(Context context, boolean isProgressDialogShow) {
        this.mContext = context;
        this.mIsProgressDialogShow = isProgressDialogShow;
    }

    public static boolean isOnline(Context context) {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void makeJSONOArryPostRequest(String url, JSONObject jsonObject, final Request.Priority priority) {
        try {
            if (AppUtil.isNetworkAvailable(mContext)) {
                // Tag used to cancel the request
                String tag_json_obj = "json_obj_req";
//                Log.e("backend servise jcimage"+TAG, url+jsonObject.toString());
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Loading Please Wait....");
                pDialog.setCancelable(false);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setIndeterminate(true);
                pDialog.setCanceledOnTouchOutside(false);
                if (mIsProgressDialogShow) {
                    pDialog.show();
                    pDialog.setContentView(R.layout.my_progress);
                }
                try {
                    final JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                       Log.e("backend servise "+TAG, response.toString());

                                    if (pDialog.isShowing() && pDialog != null) {
                                        pDialog.dismiss();
                                    }
                                    mListener.onJSONArrayResponse(response);
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage()+"Error Code->"+error.networkResponse.statusCode);
                            // hide the progress dialog
                            pDialog.dismiss();
                            mListener.onErrorResponse(error);
                        }
                    }) {
                        @Override
                        public Priority getPriority() {
                            return priority;
                        }
                    };

          /*  RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(jsonObjReq);*/
                    // Adding request to request queue
                    GoWheelsApp.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                } catch (Exception e)
                {
                   // setRefreshing(boolean);
                    e.printStackTrace();
                }

            } else {
//            throw new RuntimeException(DEVICE_OFFLINE_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void makeJSONOArryGetRequest(String url, final Request.Priority priority) {

        if (AppUtil.isNetworkAvailable(mContext)) {
            // Tag used to cancel the request
            String tag_json_obj = "json_obj_req";
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading Please Wait....");
            pDialog.setCancelable(false);
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            if (mIsProgressDialogShow) {
                pDialog.show();
                pDialog.setContentView(R.layout.my_progress);
            }
            try {
                final JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //    AppLog.d(TAG, response.toString());
                                if (pDialog.isShowing() && pDialog != null) {

                                    pDialog.dismiss();
                                }
                                mListener.onJSONArrayResponse(response);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                        pDialog.dismiss();
                        mListener.onErrorResponse(error);
                    }
                }) {
                    @Override
                    public Priority getPriority() {
                        return priority;
                    }
                };
                GoWheelsApp.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        /*    RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(jsonObjReq);*/
            // Adding request to request queue

        } else {
            // throw new RuntimeException(DEVICE_OFFLINE_MESSAGE);
        }
    }


    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21


        return BitmapFactory.decodeFile(photoPath, bmOptions);

    }

    public byte[] getFileDataFromDrawable(File imagePath) {

        Bitmap bitmap = resizeBitmap(imagePath + "", 200, 200);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
