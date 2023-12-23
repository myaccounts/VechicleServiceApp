package com.myaccounts.vechicleserviceapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetailsResponseModel {

    @SerializedName("CustomerId")
    @Expose
    public String customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("EmailId")
    @Expose
    public String emailId;
    @SerializedName("MobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("VehicleNo")
    @Expose
    public String vehicleNo;
    @SerializedName("ModelId")
    @Expose
    public String modelId;
    @SerializedName("MakeCompany")
    @Expose
    public String makeCompany;
    @SerializedName("MakeYear")
    @Expose
    public String makeYear;
    @SerializedName("Result")
    @Expose
    public String result;
    @SerializedName("Model")
    @Expose
    public String model;
    @SerializedName("Place")
    @Expose
    public String place;
    @SerializedName("vehicletype")
    @Expose
    public String vehicletype;
    @SerializedName("Make")
    @Expose
    public String make;
    @SerializedName("Date")
    @Expose
    public String date;
    @SerializedName("JcId")
    @Expose
    public String jobCardId;
    @SerializedName("OMReading")
    @Expose
    public String omReading;
    @SerializedName("TechId")
    @Expose
    public String techId;
    @SerializedName("TechName")
    @Expose
    public String techName;


    public CustomerDetailsResponseModel(String customerId, String customerName, String emailId, String mobileNo, String vehicleNo, String modelId, String makeCompany, String makeYear,String result,String model,String place,String vehicletype,String make
            ,String date,String jobCardId,String omReading,String techId,String techName) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.vehicleNo = vehicleNo;
        this.modelId = modelId;
        this.makeCompany = makeCompany;
        this.makeYear = makeYear;
        this.result = result;
        this.model = model;
        this.place = place;
        this.vehicletype = vehicletype;
        this.make = make;
        this.date = date;
        this.jobCardId = jobCardId;
        this.omReading = omReading;
        this.techId = techId;
        this.techName = techName;
    }
}

