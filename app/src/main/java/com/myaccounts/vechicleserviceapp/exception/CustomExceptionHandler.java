package com.myaccounts.vechicleserviceapp.exception;

import android.util.Log;

import com.myaccounts.vechicleserviceapp.Utils.Downloader;
import com.myaccounts.vechicleserviceapp.Utils.ExceptionStringValues;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

public class CustomExceptionHandler implements
        Thread.UncaughtExceptionHandler {

   // private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    private static final String TAG = CustomExceptionHandler.class.getSimpleName();

   // public SharedPreferences registeredPreferences;

    public CustomExceptionHandler() {

       // myContext = context;

        //registeredPreferences = context.getSharedPreferences(RegistrationActivity.REGISTERED_PREF_NAME, context.MODE_PRIVATE);

    }

    public void uncaughtException(Thread thread, Throwable exception) {

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();

        exception.printStackTrace();

        Calendar calender = Calendar.getInstance();

        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        ++month;
        int year = calender.get(Calendar.YEAR);

        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minutes = calender.get(Calendar.MINUTE);
        int second = calender.get(Calendar.SECOND);

       /* TelephonyManager mngr = (TelephonyManager)myContext.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = mngr.getDeviceId();*/


        if(ExceptionStringValues.getIMEINO() != null) {

            errorReport.append(

                    "DAY " + day + " " + "Month " + month + " " + "Year " + year + " " + "Hour" + " " + hour + " "
                            + "Minutes" + " " + minutes + " " + "Seconds" + " " + second + " "
                            + "IMEI NO " + " " + ExceptionStringValues.getIMEINO() + " "
                            + "VERSION CODE " + " " + ExceptionStringValues.getVersionCode() + " "
                            + "File Name" + " " + exception.getStackTrace()[0].getFileName() + " "
                            + "Line Number" + " " + exception.getStackTrace()[0].getLineNumber() + " "
                            + "Method Name" + exception.getStackTrace()[0].getMethodName() + " "
                            + "Exception Name" + " " + exception.getClass().getName()
            );

        }else{

            errorReport.append(
                    "DAY " + day + " " + "Month " + month + " " + "Year " + year + " " + "Hour" + " " + hour + " "
                            + "Minutes" + " " + minutes + " " + "Seconds" + " " + second + " "
                            + "IMEI NO " + " " + "null" + " "
                            + "File Name" + " " + exception.getStackTrace()[0].getFileName() + " "
                            + "Line Number" + " " + exception.getStackTrace()[0].getLineNumber() + " "
                            + "Method Name" + exception.getStackTrace()[0].getMethodName() + " "
                            + "Exception Name" + " " + exception.getClass().getName()
            );

        }


       /* errorReport.append("\nDEVICE INFORMATION\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\nFIRMWARE \n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);*/

        /*errorReport.append("\n***********UserInfo******************\n");
        errorReport.append("UserName :");
        registeredPreferences.getString(RegistrationActivity.USER_NAME, "");
        errorReport.append("Email id :");
        registeredPreferences.getString(RegistrationActivity.EMAIL_ID, "");
        errorReport.append("Phone No :");
        registeredPreferences.getString(RegistrationActivity.PHONE_NO, "");*/

        //errorReport.append(LINE_SEPARATOR);


        System.out.println("errorReport: " + errorReport.toString());

        Thread theread = new Thread(new UploadExceptionThread(errorReport.toString().replaceAll(" ", "%20")));

        theread.start();

    }

    class UploadExceptionThread implements Runnable{


        private String error;

        public UploadExceptionThread(String error) {

            this.error = error;

        }

        @Override
        public void run() {

           /* System.out.println("run");


            try {

                ServerDataBaseUtils.insertException(myContext, error);

            }catch (SQLException e){

                System.out.println("Not successfully inserted");

            }*/

            StringBuffer barcode_url = new StringBuffer("http://myaccountsonline.co.in/MyServices/Service1.svc/Error_Info/");

            //http://myaccountsonline.co.in/MyServices/Service1.svc/Register_Insert/{UserName},{Emailid},{Phone},{Emess}


            //barcode_url.append( registeredPreferences.getString(RegistrationActivity.USER_NAME, "") + "," + registeredPreferences.getString(RegistrationActivity.EMAIL_ID, "") + "," + registeredPreferences.getString(RegistrationActivity.PHONE_NO, "") + "," + error );



            barcode_url.append( "null" +  ","+"null" + ","+ "null" + "," + error );


            String response = null;

           // if (Downloader.isNetworkAvailable(myContext)) {

                response = Downloader.postDataToServer(barcode_url.toString());

            //}

            System.out.println("Exception Response :"+response);

            if (response != null && response.equalsIgnoreCase("\"1\"")) {

                Log.i(TAG, "Exception inserted");

             /*   android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);*/

            }else{

               /* android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);*/

            }

        }
    }

    /*private class UploadExceptionAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

           StringBuffer barcode_url = new StringBuffer("http://myaccountsonline.co.in/MyServices/Service1.svc/Register_Insert/");

           *//* http://myaccountsonline.co.in/MyServices/Service1.svc/Register_Insert/{UserName},{Emailid},{Phone},{Emess}*//*

            barcode_url.append( registeredPreferences.getString(RegistrationActivity.USER_NAME, "") + "," + registeredPreferences.getString(RegistrationActivity.EMAIL_ID, "") + "," + registeredPreferences.getString(RegistrationActivity.PHONE_NO, "") + "," + params[0]);

            String response = null;

            if (Downloader.isNetworkAvailable(myContext)) {

                response = Downloader.postDataToServer(barcode_url.toString());

            }


            return response;


        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            Log.i(TAG, "Response :" + s);

            if (s != null && s.equalsIgnoreCase("\"1\"")) {

                    Log.i(TAG,"Exception inserted");

                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(10);

            }

        }

    }*/

}