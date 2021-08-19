package com.myaccounts.vechicleserviceapp.Fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class NewServiceSelectedData {


    String serviceId;
    String serviceName;
    String qty;
    RecyclerView data;


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCrossImage(ImageView crossImage) {
        setCrossImage(crossImage);
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
