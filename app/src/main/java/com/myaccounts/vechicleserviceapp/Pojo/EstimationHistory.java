package com.myaccounts.vechicleserviceapp.Pojo;

public class EstimationHistory {
    private String TransDate="";
    private String TranNo="";
    private String JobCardNo="";
    private String CustomerName="";
    private String Mobile="";
    private String SpareParts="";
    private String Services="";
    private String Amount="";
    private int Rows=0;

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getTranNo() {
        return TranNo;
    }

    public void setTranNo(String tranNo) {
        TranNo = tranNo;
    }

    public String getJobCardNo() {
        return JobCardNo;
    }

    public void setJobCardNo(String jobCardNo) {
        JobCardNo = jobCardNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getSpareParts() {
        return SpareParts;
    }

    public void setSpareParts(String spareParts) {
        SpareParts = spareParts;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
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
}
