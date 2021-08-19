package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Adapter.DocumentListAdapter;
import com.myaccounts.vechicleserviceapp.Adapter.SelectedServiceAdpater;
import com.myaccounts.vechicleserviceapp.LoginSetUp.LoginActivity;
import com.myaccounts.vechicleserviceapp.Pojo.DocumentTypes;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AlertDialogManager;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class DocumentListActivity extends Activity implements View.OnClickListener {
    DocumentListAdapter documentListAdapter;
    private ArrayList<DocumentTypes> documentTypesArrayList;
    private EditText inputSearch;
    private ProgressDialog pDialog;
    private String vehicleNo, requestName;
    private Button IdOkBtn, IdClearBtn, IdCancelBtn;
    SparseBooleanArray sparseBooleanArray;
    private RecyclerView recyclerView;
    private Object ArrayList;
    private ProgressDialog progressDialog;
    private  ArrayList<DocumentTypes> myList=new ArrayList<>();
    private AlertDialogManager dialogManager = new AlertDialogManager();
    private String editTextValue,edittextStrings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_documents);
            /************************setting edittext defalut keyboard hidden*******************************/
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            inputSearch = (EditText) findViewById(R.id.inputSearch);
            IdOkBtn = (Button) findViewById(R.id.IdOkBtn);
            IdClearBtn = (Button) findViewById(R.id.IdClearBtn);
            IdCancelBtn = (Button) findViewById(R.id.IdCancelBtn);
            IdClearBtn.setOnClickListener(this);
            IdOkBtn.setOnClickListener(this);
            IdCancelBtn.setOnClickListener(this);
            recyclerView = (RecyclerView) findViewById(R.id.documents_listview);
            Intent intent=getIntent();
            editTextValue = intent.getStringExtra("ListId");
            edittextStrings = intent.getStringExtra("listStrings");
            //myList = (ArrayList<DocumentTypes>) getIntent().getSerializableExtra("mylist");
            documentTypesArrayList = new ArrayList<>();
           // documentTypesArrayList = getModel(false);
            try {

                handleDocumentList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            inputSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (documentTypesArrayList != null) {
                            filter(s.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            IdOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String EditValue = "";
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < DocumentListAdapter.salesmenlist.size(); i++) {
                            if (DocumentListAdapter.salesmenlist.get(i).getSelected()) {
                                String selectedRowLabel =DocumentListAdapter.salesmenlist.get(i).getName();
                                String edittextvalue = DocumentListAdapter.salesmenlist.get(i).getEdtValue();
                                if(edittextvalue==null){
                                    edittextvalue="";
                                }
                                if (edittextvalue.length() > 0) {
                                    EditValue = "(" + edittextvalue + ")";
                                }else{
                                    EditValue="";
                                }
                                stringBuilder.append(selectedRowLabel + EditValue + ",");
                            }
                            }
                              //  finalselectedServiceList += SelectedServiceAdpater.serviceMasterArrayList.get(i).getServiceId().toString() + ",";
                      //  SparseBooleanArray selectedRows = DocumentListAdapter.salesmenlist;
                      /*  if (selectedRows.size() > 0) {
                            String EditValue = "";
                            for (int i = 0; i < selectedRows.size(); i++) {
                                if (selectedRows.valueAt(i)) {
                                    String selectedRowLabel = documentTypesArrayList.get(i).getName();
                                    String edittextvalue = documentTypesArrayList.get(i).getEdtValue();
                                    if(edittextvalue==null){
                                        edittextvalue="";
                                    }
                                    if (edittextvalue.length() > 0) {
                                        EditValue = "(" + edittextvalue + ")";
                                    }else{
                                        EditValue="";
                                    }
                                    stringBuilder.append(selectedRowLabel + EditValue + "\n");
                                }
                            }

                        }*/
                        try {

                            Intent itemIntent = new Intent();
                            itemIntent.putExtra("Documents", stringBuilder.toString());
                            /*//itemIntent.putParcelableArrayListExtra("MyListData", documentTypesArrayList);*/
                            setResult(RESULT_OK, itemIntent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            });
            IdClearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    documentTypesArrayList.clear();
                  /*  DocumentListAdapter.ListId="";
                    DocumentListAdapter.EdtStrings="";*/
                    handleDocumentList();

                    for(int i=DocumentListAdapter.salesmenlist.size()-1; i >= 0; i--){
                        DocumentListAdapter.salesmenlist.get(i).setEdtValue("");
                        DocumentListAdapter.salesmenlist.get(i).setSelected(false);
                    }
                    documentListAdapter.notifyDataSetChanged();
                }



            });
            IdCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filter(String text) {
        ArrayList<DocumentTypes> countryLists = new ArrayList<>();
        for (DocumentTypes c : documentTypesArrayList) {
            if (c.getName().toLowerCase().contains(text))
            {
                countryLists.add(c);
            }

        }
        documentListAdapter.setFilter(countryLists);
    }

    private ArrayList<DocumentTypes> getModel(boolean isSelect){
        ArrayList<DocumentTypes> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){

            DocumentTypes documentTypes = new DocumentTypes();
            documentTypes.setSelected(isSelect);
            documentTypes.setId(String.valueOf(documentTypesArrayList.get(i)));
            documentTypes.setName(String.valueOf(documentTypesArrayList.get(i)));
            documentTypes.setShortName(String.valueOf(documentTypesArrayList.get(i)));
            list.add(documentTypes);
        }
        return list;
    }

    private void handleDocumentList() {
        try {
            progressDialog = new ProgressDialog(DocumentListActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            if (AppUtil.isNetworkAvailable(DocumentListActivity.this)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("TypeName", "DOCUMENTS");
                requestName = "GeneralMasterData";
                BackendServiceCall serviceCall = new BackendServiceCall(DocumentListActivity.this, false);
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerDocument());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GeneralMasterData, jsonObject, Request.Priority.HIGH);
            } else {
                progressDialog.dismiss();
                dialogManager.showAlertDialog(DocumentListActivity.this, "Internet Connection Error !", Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OnServiceCallCompleteListenerDocument implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            progressDialog.dismiss();
            if (requestName.equalsIgnoreCase("GeneralMasterData")) {
                try {
                    handeDocumentDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
        }
    }

    private void handeDocumentDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(DocumentListActivity.this, Result, Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentTypes documentTypes = new DocumentTypes();
                            documentTypes.setId(object.getString("Id"));
                            documentTypes.setName(object.getString("Name"));
                            documentTypes.setShortName(object.getString("ShortName"));
                            documentTypesArrayList.add(documentTypes);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(myList==null){
                    DocumentTypes documentTypes=new DocumentTypes();
                    documentTypes.setName("");
                    documentTypes.setEdtValue("");
                    documentTypes.setId("");
                    documentTypes.setShortName("");
                   myList.add(documentTypes);
                }
               documentListAdapter = new DocumentListAdapter(DocumentListActivity.this, R.layout.document_row_list, documentTypesArrayList,editTextValue,edittextStrings);
                documentListAdapter.SetOnItemClickListener(new DocumentListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, String type) {
                        switch (view.getId()) {
                            case R.id.IdClearBtn:
                               // documentTypesArrayList.remove(position);
                               /* EditText editText=(EditText)view.findViewById(R.id.entryFieldEdt);
                                editText.setText("");*/
                               /* if(DocumentListAdapter.salesmenlist.size()>0){
                                    for(int i=0;i<DocumentListAdapter.salesmenlist.size();i++) {
                                        if (DocumentListAdapter.salesmenlist.get(i).getSelected()) {
                                            DocumentListAdapter.salesmenlist.get(i).setSelected(false);
                                        }
                                        documentListAdapter.notifyDataSetChanged();
                                    }
                                }*/
                            /*    for(int i=0; i<DocumentListAdapter.salesmenlist.size();i++)
                                {

                                    cb.setChecked(false);
                                }
                                adapter.notifyDataSetChanged();*/
                                documentListAdapter.removeSelection();
                                documentListAdapter.notifyDataSetChanged();
                                break;
                            case R.id.IdOkBtn:
                                try {
                                    documentTypesArrayList = getModel(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;



                        }
                    }
                });
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(documentListAdapter);
               /* recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                        String id;
                        String Name;
                        String ShortName;
                        if (dynamicList.size() == 0) {
                            id = documentTypesArrayList.get(position).getId();
                            Name = documentTypesArrayList.get(position).getName();
                            ShortName = documentTypesArrayList.get(position).getShortName();
                        } else {
                            id = dynamicList.get(position).getId();
                            Name = dynamicList.get(position).getName();
                            ShortName = dynamicList.get(position).getShortName();
                        }
                        Intent itemIntent = new Intent();
                        itemIntent.putExtra("id", id);
                        itemIntent.putExtra("Name", Name);
                        itemIntent.putExtra("ShortName", ShortName);

                        setResult(RESULT_OK, itemIntent);
                        finish();
                    }
                });*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    public void getDetails(GlobalObject resultSet, Enums.RequestType reqType) {
        try {
            ArrayList<SalesMen> salesMens = resultSet.getSalesMens();
            SalesMenList = salesMens;
            salesMenNameAdapter = new SalesMenNameAdapter(SalesmenActivity.this, R.layout.salesmen_row_item, SalesMenList);
            ListView listView = (ListView) findViewById(R.id.suppliers_list);
            listView.setAdapter(salesMenNameAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                    String id;
                    String SalesMenName;
                    if (dynamicList.size() == 0) {
                        id = SalesMenList.get(position).getSalesmenid();
                        SalesMenName = SalesMenList.get(position).getSalesmenname();
                    } else {
                        id = dynamicList.get(position).getSalesmenid();
                        SalesMenName = dynamicList.get(position).getSalesmenname();
                    }
                    Intent itemIntent = new Intent();
                    itemIntent.putExtra("salesmenid", id);
                    itemIntent.putExtra("salesmanname", SalesMenName);

                    setResult(RESULT_OK, itemIntent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
