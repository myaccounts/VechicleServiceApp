package com.myaccounts.vechicleserviceapp.Pojo;

public class EstimationPrintList {
    private String JobCardNo="";
    private String mDate="";
    private String Name="";
    private String MobileNo="";
    private String ServiceList="";
    private String SparePartList="";

    public String getJobCardNo() {
        return JobCardNo;
    }

    public void setJobCardNo(String jobCardNo) {
        JobCardNo = jobCardNo;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getServiceList() {
        return ServiceList;
    }

    public void setServiceList(String serviceList) {
        ServiceList = serviceList;
    }

    public String getSparePartList() {
        return SparePartList;
    }

    public void setSparePartList(String sparePartList) {
        SparePartList = sparePartList;
    }
}
