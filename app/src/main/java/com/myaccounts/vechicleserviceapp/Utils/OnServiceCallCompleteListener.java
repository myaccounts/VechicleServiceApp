package com.myaccounts.vechicleserviceapp.Utils;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface OnServiceCallCompleteListener {
    void onJSONObjectResponse(JSONObject jsonObject);

    void onJSONArrayResponse(JSONArray jsonArray);

    void onErrorResponse(VolleyError error);
}
