package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Li {

    @SerializedName("Key")
    @Expose
    public String key;
    @SerializedName("Value")
    @Expose
    public String value;

}
