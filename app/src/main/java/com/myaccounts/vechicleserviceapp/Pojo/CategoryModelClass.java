package com.myaccounts.vechicleserviceapp.Pojo;

public class CategoryModelClass {

    String ActiveStatus;
    String Result;
    String ServiceId;
    String ServiceName;
    String SubService;

    public CategoryModelClass() {

    }
    public CategoryModelClass(String ActiveStatus, String Result, String ServiceId, String ServiceName, String SubService) {
        this.ActiveStatus = ActiveStatus;
        this.Result = Result;
        this.ServiceId = ServiceId;
        this.ServiceName = ServiceName;
        this.SubService = SubService;

    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
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

    public String getSubService() {
        return SubService;
    }

    public void setSubService(String subService) {
        SubService = subService;
    }

}
