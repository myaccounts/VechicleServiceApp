package com.myaccounts.vechicleserviceapp.Pojo;

public class SubServiceHeader {
    private String Key="";
    private String Value="";
    private int ItemPosition = -1;

    public int getItemPosition() {
        return ItemPosition;
    }

    public void setItemPosition(int itemPosition) {
        ItemPosition = itemPosition;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
