package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestModel {

    @SerializedName("FromDate")
    @Expose
    public String fromDate;
    @SerializedName("ToDate")
    @Expose
    public String toDate;
    @SerializedName("Duration")
    @Expose
    public String duration;


    public RequestModel(String fromDate, String toDate, String duration) {
        super();
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.duration = duration;
    }

}
