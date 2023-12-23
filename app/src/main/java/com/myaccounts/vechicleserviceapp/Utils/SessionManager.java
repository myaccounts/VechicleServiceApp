package com.myaccounts.vechicleserviceapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;


import java.io.File;
import java.util.HashMap;

public class SessionManager {
    public static final String KEY_CHECKBOX_STATUS = "CheckBoxStatus";
    public static final String KEY_Screen_Name = "screenName";

    public static final String KEY_SLECTED_STRING = "SelectedString";

    public static final String KEY_INTIME = "InTime";
    public static final String KEY_INDATE = "JCDate";

    public static final String KEY_SELECTED_DATE = "date";
    public static final String KEY_SAVED_DATE = "saveddate";

    public static final String KEY_VEHICLE_ID = "ModelNo";
    public static final String KEY_VEHICLE_MAKE = "Make";
    public static final String KEY_JOBCARD_ID = "jobCardId";
    public static final String KEY_JOBCARD_JCDATE = "JCDate";
    public static final String KEY_JOBCARD_JCTIME = "JCTime";

    public static final String KEY_CAPTURE_IMAGE1="JobCard_Image1";
    public static final String KEY_CAPTURE_IMAGE1_LOCAL="local_image1";

    public static final String KEY_SIGNATURE_IMAGE1_LOCAL="local_signature";

    public static final String KEY_SIGNATURE_IMAGE1="Sigature_Image1";

    public static final String KEY_VEHICLE_NO = "vehicleNo";
    public static final String KEY_MOBILE_NO = "mobileNumber";

    public static final String KEY_SIGNATURE_FILE_PATH = "signature_filepath";

    public static final String KEY_Remarks_CheckList = "ChecklistRemarks";

    public static final String KEY_CUSTOMER_NAME = "customerName";
    public static final String KEY_CONTACT_NO = "contactNo";
//    public static final String KEY_NAME = "name";due to bugs with instead of name using customer_name
    public static final String KEY_SIGNATURE_FILE="FILE_NAME";
    public static final String KEY_IMAGE="IMAGE";
    public static final String KEY_URI="URI";

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

    public static final String KEY_SERVICES_QTY="servicesQty";
    public static final String KEY_SPAREPARTS_QTY="sparepartsQty";

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
        leditor.putString(KEY_VEHICLE_ID, vehicleId);
        leditor.putString(KEY_VEHICLE_MAKE, make);
        leditor.commit();
    }

    public void storeSelectedDate(String date) {
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

    public void storeSignatureDetails(File f, String image, Uri fileUri) {

        leditor.putString(KEY_SIGNATURE_FILE, f.getAbsolutePath());
        leditor.putString(KEY_IMAGE, image);
        leditor.putString(KEY_URI, fileUri.toString());
        leditor.commit();
    }
    public HashMap<String, String> getstoreSignatureDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_SIGNATURE_FILE, lpref.getString(KEY_SIGNATURE_FILE, null));
        user.put(KEY_IMAGE, lpref.getString(KEY_IMAGE, null));
        user.put(KEY_URI, lpref.getString(KEY_URI, null));
        return user;
    }

    public void storeSignatureFilePath(String signatureFilePath) {

        leditor.putString(KEY_SIGNATURE_FILE_PATH, signatureFilePath);
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

        leditor.commit();
    }

    public void storeRemarksCheckListDetails(String vehicleNo){
        leditor.putString(KEY_Remarks_CheckList, vehicleNo);
        leditor.commit();
    }

    public void storeSecondFragmentDetails(String finalServiceDetailsDetailList, String totalAmount,String ServiceId, String totalServiceQty) {
        leditor.putString(KEY_SERVICE_DETAILS_LIST, finalServiceDetailsDetailList);
        leditor.putString(KEY_SERVICE_DETAILS_TOTAL_AMOUNT, totalAmount);
        leditor.putString(KEY_SERVICES_QTY, totalServiceQty);
//        leditor.putString(KEY_SERVICEID,ServiceId);
        leditor.commit();

    }
    public void storeSecondFragmentFreeDetails(String finalServiceFreeAmount){
        leditor.putString(KEY_SERVICE_DETAILS_FREE_AMOUNT, finalServiceFreeAmount);
        leditor.commit();
    }

    public void storeThirdSparePartsDetails(String finalSparePartDetailList, String totalAmount, String qtySpares) {
        leditor.putString(KEY_SPARE_PARTS_DETAILS_LIST, finalSparePartDetailList);
        leditor.putString(KEY_SPARES_DETAILS_TOTAL_AMOUNT, totalAmount);
        leditor.putString(KEY_SPAREPARTS_QTY, qtySpares);
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
    public HashMap<String, String> getSignatureFilepath() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_SIGNATURE_FILE_PATH, lpref.getString(KEY_SIGNATURE_FILE_PATH, null));
        return user;
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
        user.put(KEY_SERVICES_QTY, lpref.getString(KEY_SERVICES_QTY, null));
        user.put(KEY_SPAREPARTS_QTY, lpref.getString(KEY_SPAREPARTS_QTY, null));



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
//    storeSignatureImage
    public void storeSignatureImage(String capture_image1) {
        leditor.putString(KEY_SIGNATURE_IMAGE1,capture_image1);
        leditor.commit();

    }
    public HashMap<String, String> getSignatureImage() {
        HashMap<String, String> user2= new HashMap<String, String>();

        user2.put(KEY_SIGNATURE_IMAGE1,lpref.getString(KEY_SIGNATURE_IMAGE1, null));
        return user2;
    }
    public void storeCaptureImage(String capture_image1) {
        leditor.putString(KEY_CAPTURE_IMAGE1,capture_image1);
        leditor.commit();

    }
    public HashMap<String, String> getCaptureImage() {
        HashMap<String, String> user2= new HashMap<String, String>();

        user2.put(KEY_CAPTURE_IMAGE1,lpref.getString(KEY_CAPTURE_IMAGE1, null));
        return user2;
    }

    public void storeCaptureImage1LocalPath(String capture_image1) {
        leditor.putString(KEY_CAPTURE_IMAGE1_LOCAL,capture_image1);
        leditor.commit();
    }
    public HashMap<String, String> getCaptureImage1LocalPath() {
        HashMap<String, String> user22= new HashMap<String, String>();

        user22.put(KEY_CAPTURE_IMAGE1_LOCAL,lpref.getString(KEY_CAPTURE_IMAGE1_LOCAL, null));
        return user22;
    }

    public void storeSignatureImage1LocalPath(String absolutePath) {
        leditor.putString(KEY_SIGNATURE_IMAGE1_LOCAL,absolutePath);
        leditor.commit();
    }
    public HashMap<String, String> getSignatureImage1LocalPath() {
        HashMap<String, String> user22= new HashMap<String, String>();

        user22.put(KEY_SIGNATURE_IMAGE1_LOCAL,lpref.getString(KEY_SIGNATURE_IMAGE1_LOCAL, null));
        return user22;
    }

    public void storeUpdatedRemarks(String remarks) {
        leditor.putString(KEY_REMARKS,remarks);
        leditor.commit();
    }

}
