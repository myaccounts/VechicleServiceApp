package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailsRequestModel {

    @SerializedName("VehicleNo")
    @Expose
    public String vechileId;

    public CustomerDetailsRequestModel(String vechileId) {
        super();
        this.vechileId = vechileId;
    }
}
