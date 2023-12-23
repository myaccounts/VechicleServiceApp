package com.myaccounts.vechicleserviceapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.MEDIA_TYPE;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.TAG_ACTIVE_STATUS;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.TAG_RESULT;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.TAG_SERVICE_ID;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.TAG_SERVICE_NAME;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.TAG_SUB_SERVICE;

public class NewServiceSelectedFragment extends Fragment {

    View view;
    List<String> servicesList, servicesIdList;
    Spinner selectServiceSpinner;
    ArrayAdapter<String> servicesAdapter;

    RecyclerView SelectedServiceRecyclerviewid1;

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<NewServiceSelectedData> myList = new ArrayList<>();
    List<String> selectedList;
    List<String> selectedIDList;

    Button serviceSelectedFragmentBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Log.d(TAG, "onCreateView: called");
        view = inflater.inflate(R.layout.new_service_selected_fragment, container, false);
        selectServiceSpinner = (Spinner) view.findViewById(R.id.selectServiceSpinner);
        SelectedServiceRecyclerviewid1 = (RecyclerView) view.findViewById(R.id.SelectedServiceRecyclerviewid1);
        servicesList = new ArrayList<>();
        servicesList.add("Select Service");
        servicesIdList = new ArrayList<>();
        servicesIdList.add("Select Service");

        selectedList = new ArrayList<>();
        selectedIDList = new ArrayList<>();
        selectedList.clear();

        new newGetServicesDetails().execute();

        mRecyclerAdapter = new RecyclerAdapter(myList, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SelectedServiceRecyclerviewid1.setLayoutManager(layoutManager);
        SelectedServiceRecyclerviewid1.setAdapter(mRecyclerAdapter);

        return view;
    }

    class newGetServicesDetails extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

//                    pDialog = new ProgressDialog(BrandsActivity.this);
//                    pDialog.setMessage("Loading...");
//                    pDialog.setIndeterminate(false);
//                    pDialog.setCancelable(true);
//                    pDialog.setCanceledOnTouchOutside(false);
//                    pDialog.show();

                }
            });

        }

        /**
         * Creating Application
         */
        @SuppressWarnings("deprecation")
        protected String doInBackground(String... args) {

            final OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBuilder = new FormBody.Builder();
            JSONObject postData = new JSONObject();

            try {
                postData.put("Flag", "60");
            } catch (JSONException e) {

            }
            RequestBody formBody = RequestBody.create(MEDIA_TYPE, postData.toString());

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://Myaccountsonline.co.in/TaskManager/WheelAlignment.svc/ServiceMaster")
                    .post(formBody)
                    .build();
            Log.d("endmoviiv", request.toString());
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    //call.cancel();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response)
                        throws IOException {

                    String mMessage = response.body().string();
                    if (response.isSuccessful()) {
                        try {

                            final JSONArray json = new JSONArray(mMessage);

                            servicesList.clear();
                            servicesList.add("Select Service");

                            servicesIdList.clear();
                            servicesIdList.add("Select Service");

                            selectedList.clear();

                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);

                                // Constants.galleryList.clear();
                                String activeStatus = c.getString(TAG_ACTIVE_STATUS);
                                String result = c.getString(TAG_RESULT);
                                String serviceId = c.getString(TAG_SERVICE_ID);
                                String serviceName = c.getString(TAG_SERVICE_NAME);
                                String subService = c.getString(TAG_SUB_SERVICE);

                                servicesIdList.add(serviceId);
                                servicesList.add(serviceName);

                                Log.d("serviceslis", "" + servicesList);

                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    servicesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, servicesList);
                                    servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    selectServiceSpinner.setAdapter(servicesAdapter);

                                    selectServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                                   int arg2, long arg3) {
                                            // TODO Auto-generated method stub
                                            String selectedService = selectServiceSpinner.getSelectedItem().toString();

                                            Log.d("fdfdfdf", servicesIdList.get(arg2));

                                            Toast.makeText(getActivity(), selectedService, Toast.LENGTH_SHORT).show();

                                            if (selectedService.equalsIgnoreCase("Select Service")) {

                                            } else {

                                                if (selectedList.contains(servicesIdList.get(arg2))) {
                                                    Toast.makeText(getActivity(), "Already Exits", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    NewServiceSelectedData mLog = new NewServiceSelectedData();
                                                    Log.d("mLogggg", "" + mLog);
                                                    mLog.setServiceName(selectedService);
                                                    mLog.setQty("1");
                                                    myList.add(mLog);
                                                    selectedList.add(servicesIdList.get(arg2));

                                                    Log.d("myyyyyy", myList.toString());

                                                    JSONArray jsonArray = new JSONArray(selectedList);
                                                    for (int i = 0; i < myList.size(); i++) {

                                                        JSONArray jsonArray1 = new JSONArray(myList);
                                                        Log.d("aaaaaa", "" + jsonArray1);

                                                    }

                                                    Log.d("dsdsds", "" + jsonArray);

                                                    StringBuilder sb = new StringBuilder();
                                                    for (int i = 0; i < selectedList.size(); i++) {
                                                        if (selectedList.size() - 1 == i) {
                                                            sb.append(selectedList.get(i));
                                                        } else {
                                                            sb.append(selectedList.get(i));
                                                            sb.append(",");
                                                        }
                                                    }
                                                    String selectedNewID = sb.toString();

                                                    Log.d("selectedNewID", selectedNewID);


                                                }


                                                mRecyclerAdapter.notifyData(myList);


                                            }


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> arg0) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            return null;
        }

        private void showToast() {
            // TODO Auto-generated method stub
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    //Toast.makeText(NewServiceSelectedFragment.this, message, Toast.LENGTH_LONG).show();
                }
            });
        }


        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String status) {
            // dismiss the dialog once done


        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerItemViewHolder> {
        private ArrayList<NewServiceSelectedData> myList;
        int mLastPosition = 0;

        public RecyclerAdapter(ArrayList<NewServiceSelectedData> myList, NewServiceSelectedFragment recyclerViewMain) {

        }


        public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_selected_service_item, parent, false);
            RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerItemViewHolder holder, final int position) {
            Log.d("onBindViewHoler ", myList.size() + "");
            holder.selectedServiceTv.setText(myList.get(position).getServiceName());
            holder.selectServiceQtyEt.setText(myList.get(position).getQty());
            //  holder.crossImage.setImageResource(R.drawable.cross);
            mLastPosition = position;
        }

        @Override
        public int getItemCount() {
            return (null != myList ? myList.size() : 0);
        }

        public void notifyData(ArrayList<NewServiceSelectedData> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.myList = myList;
            notifyDataSetChanged();
        }

        public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView selectedServiceTv;
            EditText selectServiceQtyEt;
            private RelativeLayout mainLayout;
            public ImageView crossImage;

            public RecyclerItemViewHolder(final View parent) {
                super(parent);
                selectedServiceTv = (TextView) parent.findViewById(R.id.selectedServiceTv);
                selectServiceQtyEt = (EditText) parent.findViewById(R.id.selectServiceQtyEt);
//                crossImage = (ImageView) parent.findViewById(R.id.crossImage);
                mainLayout = (RelativeLayout) parent.findViewById(R.id.mainLayout);

                selectServiceQtyEt.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {

//                        NewServiceSelectedData mLog = new NewServiceSelectedData();
//                        Log.d("mLogggg", "" + mLog);
//                        mLog.setQty(s.toString());
//                        myList.add(mLog);
//                        selectedList.add(selectedService);

                    }
                });
                mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    }
                });
//                crossImage.setOnClickListener(new AdapterView.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //    mListner.OnRemoveClick(getAdapterPosition());
//                    }
//                });
            }
        }
    }
}
