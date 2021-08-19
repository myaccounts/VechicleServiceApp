package com.myaccounts.vechicleserviceapp.Pojo;

public class ServiceList {
    private String mIssueType = "";
    private String ServiceId="";
    private String ServiceName="";
    private String ServiceCharge="";
    private String mQty="";
    private String mMrp="";
    private String mTotalVal="";
    private String mRemarks="";
    private String mResult="";
    private int RowNo = 0;

    private String SubServiceId="";
    private String SubServiceName="";

    public String getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public int getRowNo() {
        return RowNo;
    }

    public void setRowNo(int rowNo) {
        RowNo = rowNo;
    }

    public String getmQty() {
        return mQty;
    }

    public void setmQty(String mQty) {
        this.mQty = mQty;
    }
    public String getmIssueType() {
        return mIssueType;
    }

    public void setmIssueType(String mIssueType) {
        this.mIssueType = mIssueType;
    }

    public String getmMrp() {
        return mMrp;
    }

    public void setmMrp(String mMrp) {
        this.mMrp = mMrp;
    }

    public String getmTotalVal() {
        return mTotalVal;
    }

    public void setmTotalVal(String mTotalVal) {
        this.mTotalVal = mTotalVal;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String mResult) {
        this.mResult = mResult;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getSubServiceId() {
        return SubServiceId;
    }

    public void setSubServiceId(String subServiceId) {
        SubServiceId = subServiceId;
    }

    public String getSubServiceName() {
        return SubServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        SubServiceName = subServiceName;
    }
}
