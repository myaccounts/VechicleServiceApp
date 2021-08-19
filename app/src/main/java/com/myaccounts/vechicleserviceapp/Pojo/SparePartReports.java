package com.myaccounts.vechicleserviceapp.Pojo;

public class SparePartReports {
    private String TransDate="";
    private String TransType="";
    private String ItemDesc="";
    private String UOM="";
    private String OpenQty="";
    private String ReceivedQty="";
    private String IssuedQty="";
    private String BalanceQty="";
    private int Rows=0;

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getOpenQty() {
        return OpenQty;
    }

    public void setOpenQty(String openQty) {
        OpenQty = openQty;
    }

    public String getReceivedQty() {
        return ReceivedQty;
    }

    public void setReceivedQty(String receivedQty) {
        ReceivedQty = receivedQty;
    }

    public String getIssuedQty() {
        return IssuedQty;
    }

    public void setIssuedQty(String issuedQty) {
        IssuedQty = issuedQty;
    }

    public String getBalanceQty() {
        return BalanceQty;
    }

    public void setBalanceQty(String balanceQty) {
        BalanceQty = balanceQty;
    }

    public int getRows() {
        return Rows;
    }

    public void setRows(int rows) {
        Rows = rows;
    }
}
