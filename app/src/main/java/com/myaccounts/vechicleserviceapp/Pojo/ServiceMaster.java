package com.myaccounts.vechicleserviceapp.Pojo;

public class ServiceMaster {

    private String Rate="";

    private String freeRate="";
    private String Result="";
    private String ServiceId="";
    private String SubServiceId="";
    private String SubServiceName="";
    private String ServiceName="";
    private String ActiveStatus="";
    private String WheeltyreDetails="";
    private String SubService="";
    private boolean isSelected;

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

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getWheeltyreDetails() {
        return WheeltyreDetails;
    }

    public void setWheeltyreDetails(String wheeltyreDetails) {
        WheeltyreDetails = wheeltyreDetails;
    }

    public String getSubService() {
        return SubService;
    }

    public void setSubService(String subService) {
        SubService = subService;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
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


    public String getFreeRate() {
        return freeRate;
    }

    public void setFreeRate(String freeRate) {
        this.freeRate = freeRate;
    }


}
