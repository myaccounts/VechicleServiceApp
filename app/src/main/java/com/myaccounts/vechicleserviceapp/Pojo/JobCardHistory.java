package com.myaccounts.vechicleserviceapp.Pojo;

import android.support.v4.app.Fragment;

public class JobCardHistory {
    private String mJobCardNo="";
    private String mDate="";
    private String mVehicleNo="";
    private String mTimeIn="";
    private String mTineOut="";
    private String mspareParts="";
    private String mserviceCharge="";
    private String Status="";
    private String Rows="";
    private int Sno=0;

    public int getSno() {
        return Sno;
    }

    public void setSno(int sno) {
        Sno = sno;
    }

    public String getRows() {
        return Rows;
    }

    public void setRows(String rows) {
        Rows = rows;
    }

    public String getmJobCardNo() {
        return mJobCardNo;
    }

    public void setmJobCardNo(String mJobCardNo) {
        this.mJobCardNo = mJobCardNo;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmVehicleNo() {
        return mVehicleNo;
    }

    public void setmVehicleNo(String mVehicleNo) {
        this.mVehicleNo = mVehicleNo;
    }

    public String getmTimeIn() {
        return mTimeIn;
    }

    public void setmTimeIn(String mTimeIn) {
        this.mTimeIn = mTimeIn;
    }

    public String getmTineOut() {
        return mTineOut;
    }

    public void setmTineOut(String mTineOut) {
        this.mTineOut = mTineOut;
    }

    public String getMspareParts() {
        return mspareParts;
    }

    public void setMspareParts(String mspareParts) {
        this.mspareParts = mspareParts;
    }

    public String getMserviceCharge() {
        return mserviceCharge;
    }

    public void setMserviceCharge(String mserviceCharge) {
        this.mserviceCharge = mserviceCharge;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
