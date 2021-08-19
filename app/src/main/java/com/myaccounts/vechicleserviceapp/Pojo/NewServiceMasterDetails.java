package com.myaccounts.vechicleserviceapp.Pojo;

public class NewServiceMasterDetails {

    private String mRate = "";
    private String mResult = "";
    private String mServiceId = "";
    private String mServiceName = "";
    private String mSubServiceId = "";
    private String mSubServiceName = "";
    private String mIssueType = "";
    private String mQty = "";
    private String mAvlQty = "";
    private boolean isSelected;
    private int RowNo = 0;
    private String mSetSerStatus="";

    private String Sno = "";
    private String remarks = "";

    private String totalValue = "";

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private String freeMrp = "";

    public String getmRate() {
        return mRate;
    }

    public void setmRate(String mRate) {
        this.mRate = mRate;
    }

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String mResult) {
        this.mResult = mResult;
    }

    public String getmServiceId() {
        return mServiceId;
    }

    public void setmServiceId(String mServiceId) {
        this.mServiceId = mServiceId;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public String getmSubServiceId() {
        return mSubServiceId;
    }

    public void setmSubServiceId(String mSubServiceId) {
        this.mSubServiceId = mSubServiceId;
    }

    public String getmSubServiceName() {
        return mSubServiceName;
    }

    public void setmSubServiceName(String mSubServiceName) {
        this.mSubServiceName = mSubServiceName;
    }//setSerStatus

    public String getmSetSerStatus() {
        return mSetSerStatus;
    }

    public void setmSetSerStatus(String mSetSerStatus) {
        this.mSetSerStatus = mSetSerStatus;
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

    public String getmAvlQty() {
        return mAvlQty;
    }

    public void setmAvlQty(String mAvlQty) {
        this.mAvlQty = mAvlQty;
    }


    public String getmIssueType() {
        return mIssueType;
    }

    public void setmIssueType(String mIssueType) {
        this.mIssueType = mIssueType;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }


    public String getFreeMrp() {
        return freeMrp;
    }

    public void setFreeMrp(String freeMrp) {
        this.freeMrp = freeMrp;
    }

}
