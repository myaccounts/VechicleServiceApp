package com.myaccounts.vechicleserviceapp.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class SubServiceList implements Parcelable {
    private String Serviceheader="";
    private String ServiceheaderId="";
    private String PaidOrFreeService="";
    private String SpinWheelType="";
    private boolean isSelected;
    private boolean PaidSelected;

    public SubServiceList(Parcel in) {
        ServiceheaderId = in.readString();
        Serviceheader = in.readString();
        PaidOrFreeService = in.readString();
        isSelected = in.readByte() != 0;
        PaidSelected = in.readByte() != 0;
    }

    public static final Creator<SubServiceList> CREATOR = new Creator<SubServiceList>() {
        @Override
        public SubServiceList createFromParcel(Parcel parcel) {
            return new SubServiceList(parcel);
        }

        @Override
        public SubServiceList[] newArray(int size) {
            return new SubServiceList[size];
        }
    };

    public SubServiceList() {

    }

    public String getSpinWheelType() {
        return SpinWheelType;
    }

    public void setSpinWheelType(String spinWheelType) {
        SpinWheelType = spinWheelType;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getPaidSelected() {
        return PaidSelected;
    }

    public void setPaidSelected(boolean paidSelected) {
        PaidSelected = paidSelected;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ServiceheaderId);
        dest.writeString(Serviceheader);
        dest.writeString(PaidOrFreeService);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (PaidSelected ? 1 : 0));
    }


    private void readFromParcel(Parcel in) {
        ServiceheaderId = in.readString();
        Serviceheader = in.readString();
        PaidOrFreeService = in.readString();


    }


    public String getServiceheader() {
        return Serviceheader;
    }

    public void setServiceheader(String serviceheader) {
        Serviceheader = serviceheader;
    }

    public String getPaidOrFreeService() {
        return PaidOrFreeService;
    }

    public String getServiceheaderId() {
        return ServiceheaderId;
    }

    public void setServiceheaderId(String serviceheaderId) {
        ServiceheaderId = serviceheaderId;
    }

    public void setPaidOrFreeService(String paidOrFreeService) {
        PaidOrFreeService = paidOrFreeService;
    }
}
