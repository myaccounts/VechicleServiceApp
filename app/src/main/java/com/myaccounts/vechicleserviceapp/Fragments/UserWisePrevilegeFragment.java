package com.myaccounts.vechicleserviceapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.myaccounts.vechicleserviceapp.Activity.JobCardNoActivity;
import com.myaccounts.vechicleserviceapp.Adapter.SelectedServiceAdpater;
import com.myaccounts.vechicleserviceapp.MainActivity;
import com.myaccounts.vechicleserviceapp.Pojo.ServiceMaster;
import com.myaccounts.vechicleserviceapp.Pojo.UserList;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserWisePrevilegeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private LayoutInflater inflater;
    View view;
    @BindView(R.id.IdServicesList)
    RecyclerView mServicesListRecyclerview;
    @BindView(R.id.IdUserListSpn)
    Spinner mUserListSpn;
    private SelectedServiceAdpater selectedServiceAdpater;
    @BindView(R.id.IdSaveUserBtn)
    Button mSaveUserBtn;
    private String requestName;
    private ArrayList<ServiceMaster> serviceMasterArrayList;
    private ArrayList<UserList> usersList = new ArrayList<>();
    private ArrayList<ServiceMaster> sericeIdList = new ArrayList<>();
    private String selectUser, taskToId,ServiceListData="";
    private String finalselectedServiceList = "";
    CheckBox checkBox;
    private ProgressDialog pDialog;

    public static UserWisePrevilegeFragment newInstance() {
        Bundle args = new Bundle();
        UserWisePrevilegeFragment fragment = new UserWisePrevilegeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userwiseprevilesges_layout, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Services Assign To Users</font>"));
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        context = getActivity();
     /*   mswiperefreshlayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mswiperefreshlayout.setOnRefreshListener(this);*/
        serviceMasterArrayList = new ArrayList<>();
        GetUsersList();

        mUserListSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectUser = mUserListSpn.getSelectedItem().toString();
                if(!selectUser.equalsIgnoreCase("Select User")) {
                    SelectedUserServices(String.valueOf(usersList.get(position).getUserId()));
                }else{
                 //   Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                }
                if (usersList.size() != 0)
                    taskToId = usersList.get(position).getUserId();
                Log.v("test", taskToId);
                if(mUserListSpn.getId()==position){

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSaveUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Validate()) {
                        pDialog = new ProgressDialog(getActivity(),
                                R.style.AppTheme_Dark_Dialog);
                        pDialog.setIndeterminate(true);
                        pDialog.setMessage("Please Wait..");
                        pDialog.show();
                        if (AppUtil.isNetworkAvailable(getActivity())) {
                            try {
                                final String ServiceIdValues = finalselectedServiceList.replaceAll(",$", "");
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.accumulate("UserId", taskToId);
                                jsonObject.accumulate("Services", ServiceIdValues);
                                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                                requestName = "SaveUserServices";
                                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveUserServices, jsonObject, Request.Priority.HIGH);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void SelectedUserServices(String userId) {
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("UserId",userId);
                BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                requestName = "GetUserwiseService";
                serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetUserwiseService, jsonObject, Request.Priority.HIGH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            pDialog.dismiss();
            Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean Validate() {
        boolean Valid=true;
       if(selectUser.equalsIgnoreCase("Select User")){
           Valid=false;
           Toast toast = Toast.makeText(context,"Please Select User Before Assign Services", Toast.LENGTH_SHORT);
           View view = toast.getView();
           view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
           TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
           v.setTextColor(Color.WHITE);
           toast.setGravity(Gravity.CENTER, 0, 0);
           toast.show();
       }else if(finalselectedServiceList==""){
            Valid=false;
            Toast toast = Toast.makeText(context,"Please Select Atleast One Service.", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        return Valid;

    }

    private void GetUsersList() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                   // mswiperefreshlayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "GetUserDetails";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.GetUserDetails, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetServicesList() {
        try {
            pDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Please Wait..");
            pDialog.show();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    //mswiperefreshlayout.setRefreshing(true);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("Flag", "60");
                    BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                    requestName = "ServiceMaster";
                    serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerUserImpl());
                    serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.ServiceMaster, jsonObject, Request.Priority.HIGH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
               pDialog.dismiss();
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh()
    {
        //mswiperefreshlayout.setRefreshing(false);
    }

    private class OnServiceCallCompleteListenerUserImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray) {
            if (requestName.equalsIgnoreCase("ServiceMaster")) {
                try {
                    pDialog.dismiss();
                    handeServiceMasterDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetUserDetails")) {
                try {
                    pDialog.dismiss();
                    handeGetUserDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("SaveUserServices")) {
                try {
                    pDialog.dismiss();
                    handeSaveUserDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetUserwiseService")) {
                try {
                    pDialog.dismiss();
                    handleUserwiseService(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error)
        {
            pDialog.dismiss();
           // mswiperefreshlayout.setRefreshing(false);
        }
    }

    private void handleUserwiseService(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            try {
                sericeIdList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                String Result = object.getString("Result");
                if (Result.equalsIgnoreCase("No Data Found")) {
                    Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();

                } else {

                    ServiceMaster sm = new ServiceMaster();
                    sm.setServiceId(object.getString("ServiceId"));
                    sm.setServiceName(object.getString("ServiceName"));
                    sm.setActiveStatus(object.getString("ActiveStatus"));
                    sericeIdList.add(sm);
                }
            }

                selectedServiceAdpater = new SelectedServiceAdpater(getActivity(), R.layout.selectedservice_row_item, sericeIdList);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
                mServicesListRecyclerview.setLayoutManager(gridLayoutManager1);
                mServicesListRecyclerview.setItemAnimator(new DefaultItemAnimator());
                mServicesListRecyclerview.setAdapter(selectedServiceAdpater);
                selectedServiceAdpater.notifyDataSetChanged();
              /*  try {
                    finalselectedServiceList="";
                    for (int i = 0; i < sericeIdList.size(); i++) {
                        if (sericeIdList.get(i).getActiveStatus().equalsIgnoreCase("1")) {
                            finalselectedServiceList +=  sericeIdList.get(i).getServiceId().toString() + ",";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, String itemName) {
                        switch (view.getId()) {

                            case R.id.ServiceNameCheckBox:
                                checkBox = (CheckBox) view.findViewById(R.id.ServiceNameCheckBox);
                                boolean checked = ((CheckBox) view).isChecked();
                                String ServiceId = selectedServiceAdpater.serviceMasterArrayList.get(position).getServiceId();
                                String ServiceIdKey = selectedServiceAdpater.serviceMasterArrayList.get(position).getServiceName();

                                if (checked) {
                                    if (selectedServiceAdpater.serviceMasterArrayList.get(position).getSelected()) {
                                        selectedServiceAdpater.serviceMasterArrayList.get(position).setSelected(false);
                                    } else {
                                        selectedServiceAdpater.serviceMasterArrayList.get(position).setSelected(true);
                                        selectedServiceAdpater.serviceMasterArrayList.get(position).setActiveStatus("1");
                                    }

                                }else{
                                    selectedServiceAdpater.serviceMasterArrayList.get(position).setSelected(false);
                                    selectedServiceAdpater.serviceMasterArrayList.get(position).setActiveStatus("0");
                                }
                        }

                        try {
                            finalselectedServiceList="";
                            for (int i = 0; i < selectedServiceAdpater.serviceMasterArrayList.size(); i++) {
                                if (selectedServiceAdpater.serviceMasterArrayList.get(i).getActiveStatus().equalsIgnoreCase("1")) {
                                    finalselectedServiceList += selectedServiceAdpater.serviceMasterArrayList.get(i).getServiceId().toString() + ",";
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
               /* List<String> ListData = Arrays.asList(ServiceListData.split(","));
                if(ListData.size()>0){
                    for(int m=0;m<ListData.size();m++){
                        for (int i = 0; i < selectedServiceAdpater.serviceMasterArrayList.size(); i++) {
                            String ServiceId = serviceMasterArrayList.get(i).getServiceId();
                            if(ServiceId.equalsIgnoreCase(ListData.get(m).toString())){
                                selectedServiceAdpater.serviceMasterArrayList.get(i).setSelected(true);
                                selectedServiceAdpater.notifyDataSetChanged();
                            }
                        }

                    }
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handeSaveUserDetails(JSONArray jsonArray) {

        if (jsonArray.length() > 0) {

            try {
                JSONObject object = jsonArray.getJSONObject(0);
                String Result = object.getString("Result");
                if (Result != ""||Result!=null) {
                    Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                   // mswiperefreshlayout.setRefreshing(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void handeGetUserDetails(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0) {
                usersList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                            //mswiperefreshlayout.setRefreshing(false);
                        } else {
                            UserList userList = new UserList();
                            userList.setUserId(object.getString("UserId"));
                            userList.setUserName(object.getString("UserName"));
                            usersList.add(userList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {


                    String[] us = new String[usersList.size()];
                    for (int i = 0; i < usersList.size(); i++) {
                        us[i] = usersList.get(i).getUserName();

                    }
                   // List<String> arrayList= Arrays.asList(Collections.sort(us));


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,us );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mUserListSpn.setAdapter(spinnerArrayAdapter);
                    GetServicesList();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeServiceMasterDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                serviceMasterArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Result = object.getString("Result");
                        if (Result.equalsIgnoreCase("No Data Found")) {
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_SHORT).show();
                            //mswiperefreshlayout.setRefreshing(false);
                        } else {
                            ServiceMaster serviceMaster = new ServiceMaster();
                            serviceMaster.setServiceId(object.getString("ServiceId"));
                            serviceMaster.setServiceName(object.getString("ServiceName"));
                            serviceMaster.setActiveStatus(object.getString("ActiveStatus"));
                            serviceMasterArrayList.add(serviceMaster);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            selectedServiceAdpater = new SelectedServiceAdpater(getActivity(), R.layout.selectedservice_row_item, serviceMasterArrayList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
            mServicesListRecyclerview.setLayoutManager(gridLayoutManager1);
            mServicesListRecyclerview.setItemAnimator(new DefaultItemAnimator());
            mServicesListRecyclerview.setAdapter(selectedServiceAdpater);


            selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                                                             @Override
                                                             public void onItemClick(View view, int position, String itemName) {

                                                             }
                                                         });
          /*  selectedServiceAdpater.setOnItemClickListner(new SelectedServiceAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String itemName) {
                    switch(view.getId()) {
                        case R.id.ServiceNameCheckBox:
                            checkBox = (CheckBox) view.findViewById(R.id.ServiceNameCheckBox);
                            boolean checked = ((CheckBox) view).isChecked();
                            if (checked) {
                                // SelectedSubServiceLayout1.setVisibility(View.VISIBLE);
                                String ServiceId = serviceMasterArrayList.get(position).getServiceId();
                                String ServiceIdKey = serviceMasterArrayList.get(position).getServiceName();
                                if (serviceMasterArrayList.get(position).getSelected()) {
                                    serviceMasterArrayList.get(position).setSelected(true);
                                    serviceMasterArrayList.get(position).setActiveStatus("1");
                                } else {
                                    serviceMasterArrayList.get(position).setSelected(false);

                                }


                            }

                    }

                }

            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
