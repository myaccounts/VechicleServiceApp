package com.myaccounts.vechicleserviceapp.Printernew;

public class SpnModelsItem {
    private String mModelName = "";
    private int mModelConstant = 0;

    public SpnModelsItem(String modelName, int modelConstant) {
        mModelName = modelName;
        mModelConstant = modelConstant;
    }

    public int getModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }
}
