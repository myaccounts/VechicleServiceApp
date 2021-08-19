package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.SparePartsAdapter;
import com.myaccounts.vechicleserviceapp.Pojo.SpareParts;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SparePartsActivity extends Activity {
    private SparePartsAdapter sparePartsAdapter;
    private ArrayList<SpareParts> sparePartsArrayList;
    private ArrayList<SpareParts> dynamicList;
    private JSONArray albums;
    private EditText inputSearchfield;
    private ProgressDialog pDialog;
    String requestName;
    private AlertDialogManager dialogManager = new AlertDialogManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.spareparts_search);

            inputSearchfield = (EditText) findViewById(R.id.inputSearchfield);
            dynamicList = new ArrayList<>();
            sparePartsArrayList = new ArrayList<>();
            GetSparePartsData();
            inputSearchfield.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = inputSearchfield.getText().toString().toLowerCase(Locale.getDefault());
                    dynamicList = sparePartsAdapter.filter(text);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetSparePartsData() {
        try{
            pDialog = new ProgressDialog(SparePartsActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(this)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", 10);
                    Log.d("ANUSHA "," "+jsonObject.toString());
                    BackendServiceCall serviceCall = new BackendServiceCall(SparePartsActivity.this, false);
                    requestName = "SparepartMaster";
                    Log.d("ANUSHA "," "+ProjectVariables.BASE_URL + ProjectVariables.GetSparepartMaster);
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerSpareImp());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetSparepartMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                dialogManager.showAlertDialog(SparePartsActivity.this, Constants.INTERNET_CONNECTION_ERROR, Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }





    private class OnServiceCallCompleteListenerSpareImp implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("SparepartMaster")) {
                try {
                    pDialog.dismiss();
                    handeGetSparePartsDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();
        }
    }

    private void handeGetSparePartsDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Result = object.getString("Result");
                    if (Result.equalsIgnoreCase("No Data Found")) {
                        Toast.makeText(SparePartsActivity.this, Result, Toast.LENGTH_SHORT).show();

                    } else {
                        SpareParts documentTypes = new SpareParts();
                        documentTypes.setId(object.getString("Id"));
                        documentTypes.setName(object.getString("Name"));
                        documentTypes.setBalQty(object.getString("BalQty"));
                        documentTypes.setUOMId(object.getString("UOMId"));
                        documentTypes.setUOMName(object.getString("UOMName"));
                        documentTypes.setRate(object.getString("Rate"));
                      //  documentTypes.setFreeMRP(object.getString("MRP"));
                        sparePartsArrayList.add(documentTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {


            sparePartsAdapter = new SparePartsAdapter(SparePartsActivity.this, R.layout.spareparts_row_item, sparePartsArrayList);
            ListView listView = (ListView) findViewById(R.id.Id_SparePartsListView);
            listView.setAdapter(sparePartsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                    String SparePartId,SparePartName,SparePartShortName,SparePartBalQty,SparePartUOMId,SparePartUOMName,SparePartMRP,SparePartFreeRate;

                    if (dynamicList.size() == 0) {

                        SparePartId = sparePartsArrayList.get(position).getId();
                        SparePartName = sparePartsArrayList.get(position).getName();
                        SparePartBalQty = sparePartsArrayList.get(position).getBalQty();
                        SparePartUOMId = sparePartsArrayList.get(position).getUOMId();
                        SparePartUOMName = sparePartsArrayList.get(position).getUOMName();
                        SparePartMRP = sparePartsArrayList.get(position).getRate();
                        SparePartFreeRate = sparePartsArrayList.get(position).getFreeMRP();

                        Log.d("ANUSHA","++"+SparePartBalQty);
                    } else {

                        SparePartId = dynamicList.get(position).getId();
                        SparePartName = dynamicList.get(position).getName();
                        SparePartBalQty = dynamicList.get(position).getBalQty();
                        SparePartUOMId = dynamicList.get(position).getUOMId();
                        SparePartUOMName = dynamicList.get(position).getUOMName();
                        SparePartMRP = dynamicList.get(position).getRate();
                        SparePartFreeRate = sparePartsArrayList.get(position).getFreeMRP();

                    }
                    Intent itemIntent = new Intent();

                    itemIntent.putExtra("SprptId", SparePartId);
                    itemIntent.putExtra("SprptName", SparePartName);
                    itemIntent.putExtra("ShortBalQty", SparePartBalQty);
                    itemIntent.putExtra("ShortUomId", SparePartUOMId);
                    itemIntent.putExtra("ShortUomName", SparePartUOMName);
                    itemIntent.putExtra("SparePartMRP", SparePartMRP);
                    itemIntent.putExtra("ServiceFreeRate", SparePartFreeRate);

                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
