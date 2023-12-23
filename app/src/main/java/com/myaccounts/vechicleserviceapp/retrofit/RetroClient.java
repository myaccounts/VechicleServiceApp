package com.myaccounts.vechicleserviceapp.retrofit;

import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

  //private static final String ROOT_URL = "http://Myaccountsonline.co.in/TestServices/WheelAlignment.svc/";

    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(ProjectVariables.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService()
    {
        return getRetrofitInstance().create(ApiService.class);
    }
}
