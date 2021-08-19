package com.myaccounts.vechicleserviceapp.Pojo;

public class SparePartEst {
    private String TranNo="";
    private String JobCard="";
    private String Customer="";
    private String MobileNo="";
    private String SparePartCount="";
    private String ServiceCount="";
    private String Amount="0";
    private int Rows=0;

    public String getTranNo() {
        return TranNo;
    }

    public void setTranNo(String tranNo) {
        TranNo = tranNo;
    }

    public String getJobCard() {
        return JobCard;
    }

    public void setJobCard(String jobCard) {
        JobCard = jobCard;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getSparePartCount() {
        return SparePartCount;
    }

    public void setSparePartCount(String sparePartCount) {
        SparePartCount = sparePartCount;
    }

    public String getServiceCount() {
        return ServiceCount;
    }

    public void setServiceCount(String serviceCount) {
        ServiceCount = serviceCount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public int getRows() {
        return Rows;
    }

    public void setRows(int rows) {
        Rows = rows;
    }

    public void setRows(String slNo) {
        return;
    }
}
