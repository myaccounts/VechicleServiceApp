package com.myaccounts.vechicleserviceapp.Pojo;

public class SpareParts {

    private String Rate="";

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    private String Id="";
    private String Name="";
    private String ShortName="";
    private String BalQty="";
    private String UOMId="";
    private String UOMName="";
    private String MRP="";
    private String FreeMRP="";

    public String getFreeMRP() {
        return FreeMRP;
    }

    public void setFreeMRP(String freeMRP) {
        FreeMRP = freeMRP;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getBalQty() {
        return BalQty;
    }

    public void setBalQty(String balQty) {
        BalQty = balQty;
    }

    public String getUOMId() {
        return UOMId;
    }

    public void setUOMId(String UOMId) {
        this.UOMId = UOMId;
    }

    public String getUOMName() {
        return UOMName;
    }

    public void setUOMName(String UOMName) {
        this.UOMName = UOMName;
    }

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
