package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TatResponseModel {

    @SerializedName("Li")
    @Expose
    public List<List<Li>> li = null;

}
