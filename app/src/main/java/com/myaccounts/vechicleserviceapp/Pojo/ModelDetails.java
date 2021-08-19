package com.myaccounts.vechicleserviceapp.Pojo;

public class ModelDetails {
    private String ModelName="";
    private String ModelId="";
    private String Make="";
    private String vehicletype="";

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getModelId() {
        return ModelId;
    }

    public void setModelId(String modelId) {
        ModelId = modelId;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }
}
