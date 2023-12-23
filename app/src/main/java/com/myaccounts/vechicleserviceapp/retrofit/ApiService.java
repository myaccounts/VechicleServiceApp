package com.myaccounts.vechicleserviceapp.retrofit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Get_TAT_Report")
    Call<List<TatResponseModel>> getList(@Body RequestModel requestModel);

    // http://Myaccountsonline.co.in/TaskManager/WheelAlignment.svc/GetDetailsByVehicleNo
    @POST("GetDetailsByVehicleNo")
    Call<List<CustomerDetailsResponseModel>> getCustomerDetails(@Body  CustomerDetailsRequestModel customerDetailsRequestModel);
}
