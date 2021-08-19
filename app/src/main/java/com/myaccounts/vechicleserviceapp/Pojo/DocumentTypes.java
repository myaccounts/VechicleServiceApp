package com.myaccounts.vechicleserviceapp.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DocumentTypes implements Parcelable {
    private String Id="";
    private String Name="";
    private String ShortName="";
    private String EdtValue="";
    private boolean isSelected;

    public DocumentTypes(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        ShortName = in.readString();
        EdtValue = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<DocumentTypes> CREATOR = new Creator<DocumentTypes>() {
        @Override
        public DocumentTypes createFromParcel(Parcel parcel) {
            return new DocumentTypes(parcel);
        }

        @Override
        public DocumentTypes[] newArray(int size) {
            return new DocumentTypes[size];
        }
    };

    public DocumentTypes() {

    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEdtValue() {
        return EdtValue;
    }

    public void setEdtValue(String edtValue) {
        EdtValue = edtValue;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(ShortName);
        dest.writeString(EdtValue);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }


    private void readFromParcel(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        ShortName = in.readString();
        EdtValue = in.readString();
    }
}
