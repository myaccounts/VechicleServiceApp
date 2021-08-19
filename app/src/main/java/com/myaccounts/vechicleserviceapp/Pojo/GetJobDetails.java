package com.myaccounts.vechicleserviceapp.Pojo;

public class GetJobDetails {
    /*private String mJobCardNo;
    private String mName;
    private String mdate;
    private String mOdometerReading;
    private String mavgkms;
    private String mModel;
    private String mServiceList;
    private String mVehicleNo;
    private String mMobileNo;
    private String mTimein;
    private String mRegNo;
    private String mMake;
    private String mDocuments;
    private String mRemarks;
    private String mImage;
    private String mSignature;
    private String mServiceName;
    private String mSelectedServiceData;*/
    private String mServiceName="";
    private String mSubServiceName="";
    private String mSubServiceValue="";
    private String mSubServiceValueStr="";
    private String mRemarks="";
    private String mSelectedServiceId="";
    private String mSelectedUserId="";

    public String getmSubServiceValueStr() {
        return mSubServiceValueStr;
    }

    public void setmSubServiceValueStr(String mSubServiceValueStr) {
        this.mSubServiceValueStr = mSubServiceValueStr;
    }



    public String getmSelectedUserId() {
        return mSelectedUserId;
    }

    public void setmSelectedUserId(String mSelectedUserId) {
        this.mSelectedUserId = mSelectedUserId;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }

    public String getmSelectedServiceId() {
        return mSelectedServiceId;
    }

    public void setmSelectedServiceId(String mSelectedServiceId) {
        this.mSelectedServiceId = mSelectedServiceId;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public String getmSubServiceName() {
        return mSubServiceName;
    }

    public void setmSubServiceName(String mSubServiceName) {
        this.mSubServiceName = mSubServiceName;
    }

    public String getmSubServiceValue() {
        return mSubServiceValue;
    }

    public void setmSubServiceValue(String mSubServiceValue) {
        this.mSubServiceValue = mSubServiceValue;
    }
}
