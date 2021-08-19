package com.myaccounts.vechicleserviceapp.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class VehicleTypes {
    private String Id="";
    private String Name="";
    private String ShortName="";


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }
}
