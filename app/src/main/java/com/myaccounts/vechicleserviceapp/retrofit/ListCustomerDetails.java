package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCustomerDetails {
    @SerializedName("d")
    @Expose
    public List<List<List<CustomerDetailsResponseModel>>> d = null;

    public ListCustomerDetails(List<List<List<CustomerDetailsResponseModel>>> d) {
        super();
        this.d = d;
    }
}
