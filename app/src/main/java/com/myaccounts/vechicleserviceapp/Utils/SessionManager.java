package com.myaccounts.vechicleserviceapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


import java.util.HashMap;

public class SessionManager {
    public static final String KEY_CHECKBOX_STATUS = "CheckBoxStatus";
    public static final String KEY_Screen_Name = "screenName";

    public static final String KEY_SLECTED_STRING = "SelectedString";

    public static final String KEY_INTIME = "InTime";
    public static final String KEY_INDATE = "JCDate";

    public static final String KEY_SELECTED_DATE = "date";

    public static final String KEY_VEHICLE_ID = "ModelNo";
    public static final String KEY_VEHICLE_MAKE = "Make";
    public static final String KEY_JOBCARD_ID = "jobCardId";
    public static final String KEY_JOBCARD_JCDATE = "JCDate";
    public static final String KEY_JOBCARD_JCTIME = "JCTime";

    public static final String KEY_VEHICLE_NO = "vehicleNo";
    public static final String KEY_MOBILE_NO = "mobileNumber";

    public static final String KEY_Remarks_CheckList = "ChecklistRemarks";

    public static final String KEY_CUSTOMER_NAME = "customerName";
    public static final String KEY_CONTACT_NO = "contactNo";
//    public static final String KEY_NAME = "name";due to bugs with instead of name using customer_name
    public static final String KEY_PLACE = "place";
    public static final String KEY_BLOCK = "block";
    public static final String KEY_TECHINICIANNAME = "technicianName";
    public static final String KEY_ODO_READING = "odometerReading";
    public static final String KEY_NEXT_MILEAGE = "nextserviceMileage";
    public static final String KEY_AVG_KMS = "avgKms";
    public static final String KEY_MODEL = "model";
    public static final String KEY_MAKE = "make";
    public static final String KEY_VEHICLE_TYPE = "vehicleType";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_SERVICEID = "ServiceId";
    public static final String KEY_SPAREID = "SpareId";
    public static final String KEY_SERVICE_DETAILS_LIST = "finalServiceDetailsDetailList";
    public static final String KEY_SERVICE_DETAILS_TOTAL_AMOUNT = "totalAmount";
    public static final String KEY_SERVICE_DETAILS_FREE_AMOUNT = "freeAmount";
    public static final String KEY_NO_OF_SERVICES = "noOfServices";
    public static final String KEY_NO_OF_SERVICES_NEW = "noOfServices";
    public static final String KEY_SPARE_PARTS_DETAILS_LIST = "finalSparePartDetailList";
    public static final String KEY_SPARES_DETAILS_TOTAL_AMOUNT = "SparePartstotalAmount";
    public static final String KEY_NO_OF_SPARES = "noofSpares";


    private static final String PREF_NAME = "goWheelsPref";
    private static final String PREF_LOGIN = "goWheelsVehicleIdPref";


    SharedPreferences pref, lpref;
    Editor editor, leditor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String TAG = "SessionManager";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        lpref = _context.getSharedPreferences(PREF_LOGIN, PRIVATE_MODE);
        leditor = lpref.edit();
    }

    public void storeVehicleId(String vehicleId, String make) {
        Log.d("ANUSHA "," ************ "+vehicleId);
        Log.d("ANUSHA "," ************ "+make);
        leditor.putString(KEY_VEHICLE_ID, vehicleId);
        leditor.putString(KEY_VEHICLE_MAKE, make);
        leditor.commit();
    }

    public void storeSelectedDate(String date) {
        Log.d("ANUSHA "," ************ "+date);
        Log.d("ANUSHA ","@@@@ selectedDate storeSelectedDate "+date);
//        Log.d("ANUSHA "," ************ "+make);
        leditor.putString(KEY_SELECTED_DATE, date);
        leditor.commit();
    }
    /*public HashMap<String, String> getSelectedDate() {
            HashMap<String, String> user = new HashMap<String, String>();

            user.put(KEY_SELECTED_DATE, lpref.getString(KEY_SELECTED_DATE, null));

            return user;

    }*/

    public void storeJobCardId(String jobCardId, String jcDate,String jcTime,String vehicleNo, String mobileNumber, String customerName,String technicianName) {

        leditor.putString(KEY_JOBCARD_ID, jobCardId);
        leditor.putString(KEY_JOBCARD_JCDATE, jcDate);
        leditor.putString(KEY_JOBCARD_JCTIME, jcTime);
        leditor.putString(KEY_VEHICLE_NO, vehicleNo);
        leditor.putString(KEY_MOBILE_NO, mobileNumber);
        leditor.putString(KEY_CUSTOMER_NAME, customerName);
        leditor.putString(KEY_TECHINICIANNAME, technicianName);
        leditor.commit();
    }

    public void storefirsFragmentDetails(String vehicleNo, String contactNo, String name, String place, String block, String odometerReading, String nextserviceMileage, String avgKms, String model, String make,String remarks, String vehicleType,String modelId,String technicianName) {
        leditor.putString(KEY_VEHICLE_NO, vehicleNo);
        leditor.putString(KEY_CONTACT_NO, contactNo);
        leditor.putString(KEY_CUSTOMER_NAME, name);
        leditor.putString(KEY_PLACE, place);
        leditor.putString(KEY_BLOCK, block);
        leditor.putString(KEY_TECHINICIANNAME, technicianName);
        leditor.putString(KEY_ODO_READING, odometerReading);
        leditor.putString(KEY_NEXT_MILEAGE, nextserviceMileage);
        leditor.putString(KEY_AVG_KMS, avgKms);
        leditor.putString(KEY_MODEL, model);
        leditor.putString(KEY_MAKE, make);
        leditor.putString(KEY_REMARKS,remarks);
        leditor.putString(KEY_VEHICLE_TYPE, vehicleType);
        leditor.putString(KEY_VEHICLE_ID, modelId);

        Log.d("ANUSHA "," "+"model "+block);
        Log.d("ANUSHA "," "+"model "+lpref.getString(KEY_VEHICLE_ID,null));
        Log.d("ANUSHA "," "+"model "+lpref.getString(KEY_BLOCK,null));
        Log.d("ANUSHA "," "+"model "+lpref.getString(KEY_TECHINICIANNAME,null));
        leditor.commit();
    }

    public void storeRemarksCheckListDetails(String vehicleNo){
        leditor.putString(KEY_Remarks_CheckList, vehicleNo);
        Log.d("ANUSHA "," "+"model "+lpref.getString(KEY_Remarks_CheckList,null));
        leditor.commit();
    }

    public void storeSecondFragmentDetails(String finalServiceDetailsDetailList, String totalAmount,String ServiceId) {
        Log.d("ANUSHA "," "+"storeSecondFragmentDetails "+finalServiceDetailsDetailList);
        leditor.putString(KEY_SERVICE_DETAILS_LIST, finalServiceDetailsDetailList);
        leditor.putString(KEY_SERVICE_DETAILS_TOTAL_AMOUNT, totalAmount);
//        leditor.putString(KEY_SERVICEID,ServiceId);
        Log.d("ANUSHA "," "+"storeSecondFragmentDetails "+pref.getString(KEY_SERVICE_DETAILS_LIST,null));
        leditor.commit();
    }
    public void storeSecondFragmentFreeDetails(String finalServiceFreeAmount){
        Log.d("ANUSHA "," *** "+"storeSecondFragmentDetails finalServiceFreeAmount"+finalServiceFreeAmount);
        leditor.putString(KEY_SERVICE_DETAILS_FREE_AMOUNT, finalServiceFreeAmount);
        leditor.commit();
        Log.d("ANUSHA "," *** "+"storeSecondFragmentDetails finalServiceFreeAmount"+pref.getString(KEY_SERVICE_DETAILS_FREE_AMOUNT,null));
    }

    public void storeThirdSparePartsDetails(String finalSparePartDetailList, String totalAmount) {
        Log.d("ANUSHA "," "+"storeThirdSparePartsDetails "+finalSparePartDetailList);
        leditor.putString(KEY_SPARE_PARTS_DETAILS_LIST, finalSparePartDetailList);
        leditor.putString(KEY_SPARES_DETAILS_TOTAL_AMOUNT, totalAmount);
        Log.d("ANUSHA "," "+"storeThirdSparePartsDetails "+pref.getString(KEY_SPARE_PARTS_DETAILS_LIST,null));
       // leditor.putString(KEY_SPAREID,spareId);
        leditor.commit();
    }

    public void storeNoService(String finalrows) {

        leditor.putString(KEY_NO_OF_SERVICES, finalrows);
        leditor.commit();
    }
    public void storeNoServicesNew(String finalRows){
        leditor.putString(KEY_NO_OF_SERVICES_NEW,finalRows);
        leditor.commit();
    }

    public void storeNoSpares(String finalrows) {

        leditor.putString(KEY_NO_OF_SPARES, finalrows);
        leditor.commit();
    }

    public void storeEditDetails(String CustName, String ModelId, String CustVehiclemakemodel, String CustMobileNo,String remarks
    ,String INDATE,String INTIME) {

        leditor.putString(KEY_CUSTOMER_NAME, CustName);
        leditor.putString(KEY_VEHICLE_ID, ModelId);
        leditor.putString(KEY_VEHICLE_MAKE, CustVehiclemakemodel);
        leditor.putString(KEY_CONTACT_NO, CustMobileNo);
        leditor.putString(KEY_REMARKS,remarks);
        leditor.putString(KEY_INDATE, INDATE);
        leditor.putString(KEY_INTIME,INTIME);
        leditor.commit();

    }


    public HashMap<String, String> getVehicleDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_VEHICLE_ID, lpref.getString(KEY_VEHICLE_ID, null));
        user.put(KEY_VEHICLE_MAKE, lpref.getString(KEY_VEHICLE_MAKE, null));

        user.put(KEY_VEHICLE_NO, lpref.getString(KEY_VEHICLE_NO, null));
        user.put(KEY_CONTACT_NO, lpref.getString(KEY_CONTACT_NO, null));
        user.put(KEY_CUSTOMER_NAME, lpref.getString(KEY_CUSTOMER_NAME, null));
        user.put(KEY_PLACE, lpref.getString(KEY_PLACE, null));
        user.put(KEY_BLOCK, lpref.getString(KEY_BLOCK, null));
        user.put(KEY_TECHINICIANNAME, lpref.getString(KEY_TECHINICIANNAME, null));
        user.put(KEY_ODO_READING, lpref.getString(KEY_ODO_READING, null));
        user.put(KEY_NEXT_MILEAGE, lpref.getString(KEY_NEXT_MILEAGE, null));
        user.put(KEY_AVG_KMS, lpref.getString(KEY_AVG_KMS, null));
        user.put(KEY_MODEL, lpref.getString(KEY_MODEL, null));
        user.put(KEY_MAKE, lpref.getString(KEY_MAKE, null));
        user.put(KEY_REMARKS, lpref.getString(KEY_REMARKS, null));
        user.put(KEY_VEHICLE_TYPE, lpref.getString(KEY_VEHICLE_TYPE, null));

        user.put(KEY_SERVICEID, lpref.getString(KEY_SERVICEID, null));
        user.put(KEY_SERVICE_DETAILS_LIST, lpref.getString(KEY_SERVICE_DETAILS_LIST, null));
        user.put(KEY_SERVICE_DETAILS_TOTAL_AMOUNT, lpref.getString(KEY_SERVICE_DETAILS_TOTAL_AMOUNT, null));
        user.put(KEY_SERVICE_DETAILS_FREE_AMOUNT, lpref.getString(KEY_SERVICE_DETAILS_FREE_AMOUNT, null));
        user.put(KEY_NO_OF_SERVICES, lpref.getString(KEY_NO_OF_SERVICES, null));

      //  user.put(KEY_SPAREID, lpref.getString(KEY_SPAREID, null));
        user.put(KEY_SPARE_PARTS_DETAILS_LIST, lpref.getString(KEY_SPARE_PARTS_DETAILS_LIST, null));
        user.put(KEY_SPARES_DETAILS_TOTAL_AMOUNT, lpref.getString(KEY_SPARES_DETAILS_TOTAL_AMOUNT, null));
        user.put(KEY_NO_OF_SPARES, lpref.getString(KEY_NO_OF_SPARES, null));
        user.put(KEY_JOBCARD_ID, lpref.getString(KEY_JOBCARD_ID, null));

        user.put(KEY_CUSTOMER_NAME, lpref.getString(KEY_CUSTOMER_NAME, null));
        user.put(KEY_MOBILE_NO, lpref.getString(KEY_MOBILE_NO, null));
        user.put(KEY_JOBCARD_JCDATE, lpref.getString(KEY_JOBCARD_JCDATE, null));
        user.put(KEY_JOBCARD_JCTIME, lpref.getString(KEY_JOBCARD_JCTIME, null));

//        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
//        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // return user
        return user;
    }


    public void clearSession() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        lpref.edit().clear().commit();

        // After logout redirect user to Login Activity
//        Intent i = new Intent(_context, MainActivity.class);
//
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
    }
    public HashMap<String, String> getVehicleIdDetails() {
        HashMap<String, String> user1 = new HashMap<String, String>();

        user1.put(KEY_VEHICLE_ID, lpref.getString(KEY_VEHICLE_ID, null));
        user1.put(KEY_VEHICLE_MAKE, lpref.getString(KEY_VEHICLE_MAKE, null));
        return user1;
    }

    public void storeCheckBoxStatus(boolean status) {

        leditor.putBoolean(KEY_CHECKBOX_STATUS, status);
        leditor.commit();
    }

    public void storeSelectedString(String status) {

        leditor.putString(KEY_SLECTED_STRING, status);
        leditor.commit();
    }
    public String getSelectedString() {
        String selectedStr=lpref.getString(KEY_SLECTED_STRING, null);
        return selectedStr;
    }


}
