package com.myaccounts.vechicleserviceapp.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myaccounts.vechicleserviceapp.Activity.DrawingSignatureActivity;
import com.myaccounts.vechicleserviceapp.Activity.MainActivity;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.BackendServiceCall;
import com.myaccounts.vechicleserviceapp.Utils.Constants;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.DownloadImageServer;
import com.myaccounts.vechicleserviceapp.Utils.DownloadImageSignature;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.JSONVariables;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.OnServiceCallCompleteListener;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.myaccounts.vechicleserviceapp.Utils.UploadimageServer;
import com.myaccounts.vechicleserviceapp.Utils.UploadimageSignatureServer;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.dateAndTimeTV;
import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.tabLayout;
import static com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain.viewPager;
import static com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment.JOBCARD_STATUS;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.CustMobileNoEdt;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.CustNameEdt;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.PlaceEdt;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.VehicleNoEdt;
import static com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment.jobCardId;
import static com.myaccounts.vechicleserviceapp.Utils.SessionManager.KEY_SERVICES_QTY;
import static com.myaccounts.vechicleserviceapp.Utils.SessionManager.KEY_SPAREID;
import static com.myaccounts.vechicleserviceapp.Utils.SessionManager.KEY_SPAREPARTS_QTY;

public class NewCaptureImageAndSignFragment extends Fragment {

    ProgressBar progress_bar_jobcard_save;

    ProgressDialog dialog;
    SharedPreferences pref, lpref;
    public static boolean saveClick=false;
    private long mLastClickTime = 0;
    private Button IdImageCaptureBtn, IdSignatureBtn,IdClearBtn,IdViewSignature,IdViewCaptureImage;
    private File captureImagePath;
    private static final int TAKE_PHOTO = 115;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private LinearLayout ImgBackLinerLayout;
    private ImageView IdCaptureImgView, drawingImage;
    final int PICK_IMAGE_REQUEST = 2;
    private Uri picUri;
    final int PIC_CROP = 3;
    private String imageURl = "";
    private Boolean picTaken = false;
    String ImageName = "";
    String imageURI = "", SavedJobCardNo;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String requestName,signatureImageName;
    private SharedPreferences jobcardPref;
    public static Button IdSaveBtn;
    private View rootView;
    SessionManager sessionManager;
    String technicianName,vehicleId, vehicleNo = null, contactNo = null, name = null, place = null, block = null, odoReading = null, mileage = null, avgKms = null, model = null, make = null, vehicleType = null, serviceDetails = null, newsparePartsDetails = null, noOfServices = null;
    String jobcardId,jobcardno,SpareId=null,ServiceId=null,signatureFilePath=null;
    private String local_signature_image1=null,signature_image1=null,capture_image1=null,noOfSparesParts = null,local_capture_image1=null;
    TextView vehicleNoTv,vehicleNameTv, modelNoTv, noOfServicesTv, numberOfSparesTv, totalAmountTv;
    String currentDate, currentTime, mCustomerId, totalAmountServices, totalAmountSpares,qtyServices,qtySpareParts;
    Double totalAmount = 0.0;
    private ArrayList<VehicleDetails> vehicleDetailsArrayList;
    String selectedBlock;
    public static final String TAG = "Submit Parts";
    boolean called = false;
    EditText RemarksEdt;
    String remarks = null;
    private int requestCode = 100;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                sessionManager = new SessionManager(getActivity());
                HashMap<String, String> user = sessionManager.getVehicleDetails();
                totalAmountSpares = user.get(SessionManager.KEY_SPARES_DETAILS_TOTAL_AMOUNT);
                newsparePartsDetails = user.get(SessionManager.KEY_SPARE_PARTS_DETAILS_LIST);
                qtyServices = user.get(KEY_SERVICES_QTY);
                qtySpareParts = user.get(SessionManager.KEY_SPAREPARTS_QTY);

                totalAmountServices = user.get(SessionManager.KEY_SERVICE_DETAILS_TOTAL_AMOUNT);
                try {
                    Log.d("ewrwerwer", totalAmountSpares + "," + totalAmountServices + "," + qtySpareParts);
                    double amountSpare,amountServices;
                    if(qtyServices==(null))
                        qtyServices="0";
                    if(qtySpareParts==(null))
                        qtySpareParts="0";
                    noOfServicesTv.setText(" : " + qtyServices);
                    numberOfSparesTv.setText(" : " + qtySpareParts);
                    if(totalAmountSpares==null)
                        amountSpare=0;
                    else
                        amountSpare=Double.parseDouble(totalAmountSpares);
                    if(totalAmountServices==null)
                        amountServices=0;
                    else
                        amountServices=Double.parseDouble(totalAmountServices);

                    Double total = amountServices+amountSpare;
                    //  totalAmountTv.setText(" : Rs." + totalAmountSpares);
                    /*if(totalAmountServices == null)//added null check
                    totalAmount = Double.parseDouble(totalAmountServices) + Double.parseDouble(totalAmountSpares);
                    else
                        totalAmount =Double.parseDouble(totalAmountSpares);*/

                    totalAmountTv.setText(" : " + total);

                } catch (NullPointerException e) {
                    qtySpareParts = "0";
                    numberOfSparesTv.setText(" : " + qtySpareParts);
                }
                numberOfSparesTv.setText(" : " + qtySpareParts);
                setRemarksText(qtyServices,qtySpareParts);
            }
            catch (NullPointerException e)
            {
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        rootView = lf.inflate(R.layout.new_capture_image_sign_fragment, container, false);

        getActivity();
        lpref = getActivity().getSharedPreferences("goWheelsVehicleIdPref", 0);
        progress_bar_jobcard_save = rootView.findViewById(R.id.progress_bar_jobcard_save);
        progress_bar_jobcard_save.setVisibility(View.GONE);
        numberOfSparesTv = (TextView) rootView.findViewById(R.id.numberOfSparesTv);
        IdImageCaptureBtn = (Button) rootView.findViewById(R.id.IdImageCaptureBtn);
        IdSignatureBtn = (Button) rootView.findViewById(R.id.IdSignatureBtn);
        IdViewCaptureImage=(Button) rootView.findViewById(R.id.IdImageViewCaptureBtn);
        IdViewSignature=(Button) rootView.findViewById(R.id.IdImageViewSignatureBtn);
        IdSaveBtn = (Button) rootView.findViewById(R.id.IdSaveJobCardBtn);
        ImgBackLinerLayout = (LinearLayout) rootView.findViewById(R.id.ImgBackLinerLayout);
        IdCaptureImgView = (ImageView) rootView.findViewById(R.id.IdCaptureImgView);
        drawingImage= (ImageView) rootView.findViewById(R.id.drawingImage);

        IdClearBtn=(Button)rootView.findViewById(R.id.IdClearBtn);

        RemarksEdt = (EditText) rootView.findViewById(R.id.RemarksEdt);

        vehicleNameTv = (TextView) rootView.findViewById(R.id.vehicleNameTv);
        vehicleNoTv = (TextView) rootView.findViewById(R.id.vehicleNoTv);
        modelNoTv = (TextView) rootView.findViewById(R.id.modelNoTv);
        noOfServicesTv = (TextView) rootView.findViewById(R.id.noOfServicesTv);

        totalAmountTv = (TextView) rootView.findViewById(R.id.totalAmountTv);

        jobcardPref = getActivity().getSharedPreferences(JOBCARD_STATUS, Context.MODE_PRIVATE);
        SavedJobCardNo = jobcardPref.getString("JobCardNumber", null);
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getVehicleDetails();

        try {
            jobcardId = user.get(SessionManager.KEY_JOBCARD_ID);//entry debug point
            vehicleId=user.get(SessionManager.KEY_VEHICLE_ID);
            vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
            contactNo = user.get(SessionManager.KEY_CONTACT_NO);
            name = user.get(SessionManager.KEY_CUSTOMER_NAME);
            place = user.get(SessionManager.KEY_PLACE);
            block = user.get(SessionManager.KEY_BLOCK);
            odoReading = user.get(SessionManager.KEY_ODO_READING);
            mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
            avgKms = user.get(SessionManager.KEY_AVG_KMS);
            model = user.get(SessionManager.KEY_MODEL);
            make = user.get(SessionManager.KEY_VEHICLE_MAKE);
            vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
            selectedBlock = user.get(SessionManager.KEY_BLOCK);
            technicianName = user.get(SessionManager.KEY_TECHINICIANNAME);
            totalAmountServices = user.get(SessionManager.KEY_SERVICE_DETAILS_TOTAL_AMOUNT);
            totalAmountSpares = user.get(SessionManager.KEY_SPARES_DETAILS_TOTAL_AMOUNT);
            remarks = user.get(SessionManager.KEY_REMARKS);

            ServiceId = user.get(SessionManager.KEY_SERVICEID);
            SpareId=lpref.getString(KEY_SPAREID, null);
            serviceDetails = user.get(SessionManager.KEY_SERVICE_DETAILS_LIST);
            newsparePartsDetails = user.get(SessionManager.KEY_SPARE_PARTS_DETAILS_LIST);
           } catch (NullPointerException e) {
            }
        if (vehicleNo == null) {
            vehicleNo = "";
        }
        if (name == null) {
            name = "";
        }

        if (qtyServices == null) {
            qtyServices = "";
        }

        if (contactNo == null) {
            contactNo = "";
        }

        if (model == null) {
            model = "";
        }

        if (totalAmountSpares == null) {
            totalAmountSpares = "0";
        }

        if (totalAmountServices == null) {
            totalAmountServices = "0";
        }

        if (qtySpareParts == null) {
            qtySpareParts = "0";
        }



        totalAmount = Double.parseDouble(totalAmountServices) + Double.parseDouble(totalAmountSpares);
        totalAmountTv.setText(" : " + totalAmount);

        vehicleNoTv.setText(" : " + vehicleNo);
        vehicleNameTv.setText(" : " + name);
        modelNoTv.setText(" : " + contactNo);
        noOfServicesTv.setText(" : " + qtyServices);
        numberOfSparesTv.setText(" : " + qtySpareParts);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);


        if ( jobCardId != null && !jobCardId.isEmpty() && !jobCardId.equals("null") && !jobCardId.equals("empty"))
        {
            Log.e("update data","update");
            IdSaveBtn.setText("UPDATE");
            HashMap<String, String> userr = sessionManager.getCaptureImage();
            capture_image1 = userr.get(SessionManager.KEY_CAPTURE_IMAGE1);
            if(capture_image1.length() != 0){
                    IdViewCaptureImage.setVisibility(View.VISIBLE);
//                IdImageCaptureBtn.setVisibility(View.VISIBLE);

            }else {
                IdViewCaptureImage.setVisibility(View.GONE);
//                IdImageCaptureBtn.setVisibility(View.GONE);
            }
            HashMap<String, String> userSignature = sessionManager.getSignatureImage();
            signature_image1 = userSignature.get(SessionManager.KEY_SIGNATURE_IMAGE1);
            if(signature_image1.length() != 0){
                IdViewSignature.setVisibility(View.VISIBLE);
//                IdSignatureBtn.setVisibility(View.VISIBLE);//IdViewSignature,IdViewCaptureImage
            }else {
                IdViewSignature.setVisibility(View.GONE);
//                IdSignatureBtn.setVisibility(View.GONE);
            }
            if(signature_image1!=null){
                try {// image download from server
                    HashMap<String, String> userrS = sessionManager.getSignatureImage1LocalPath();
                    local_signature_image1=userrS.get(SessionManager.KEY_SIGNATURE_IMAGE1_LOCAL);
                    if(local_signature_image1!=null){
                        try {
                            imageURI=local_signature_image1;
                            ImgBackLinerLayout.setVisibility(View.VISIBLE);
                            drawingImage.setVisibility(View.VISIBLE);
//                            IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                            drawingImage.setImageDrawable(Drawable.createFromPath(local_signature_image1));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            IdImageCaptureBtn.setText("VIEW");
            setRemarksText(qtyServices,qtySpareParts);

//            if(remarks.length()>0)
//            RemarksEdt.setSelection(RemarksEdt.getText().length());

        }
        else
        {
            Log.e("save data","save");
            IdSaveBtn.setText("SAVE");
            IdSignatureBtn.setText("SIGNATURE");
            RemarksEdt.setText("");
        }


            IdImageCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //  FileUriExposedException
                    Intent photocapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoURI = null;
                    captureImagePath = new File(getActivity().getExternalCacheDir(), "temp.jpg");
                    if (Build.VERSION.SDK_INT >= 24) {

                        photoURI = FileProvider.getUriForFile(getActivity(), com.myaccounts.vechicleserviceapp.BuildConfig.APPLICATION_ID + ".provider", captureImagePath);

//                        UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
//                        server.execute();
                        Log.d("zzzzzz", "" + captureImagePath);
                    } else {
                        photoURI = Uri.fromFile(captureImagePath);

                        Log.d("rrrrrrr", "" + captureImagePath);
                    }
                    photocapture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(photocapture, TAKE_PHOTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        IdSignatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     try {
                        Intent sig = new Intent(getActivity(), DrawingSignatureActivity.class);
                        startActivityForResult(sig, 11);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                saveClick=false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        IdViewSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DownloadImageSignature server1 = new DownloadImageSignature(signature_image1, getActivity());
                    server1.execute();
                } catch (Exception e) {
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something here
                        HashMap<String, String> userrS = sessionManager.getSignatureImage1LocalPath();
                        local_signature_image1 = userrS.get(SessionManager.KEY_SIGNATURE_IMAGE1_LOCAL);
                        if (local_signature_image1 != null) {
                            try {
//                                    imageURI = local_signature_image1;
                                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                                drawingImage.setVisibility(View.VISIBLE);
//                            IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                                drawingImage.setImageDrawable(Drawable.createFromPath(local_signature_image1));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 5000);
            }

        });
        IdViewCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {// image download from server
                    DownloadImageServer server = new DownloadImageServer(capture_image1,getActivity());
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> userrL = sessionManager.getCaptureImage1LocalPath();
                        local_capture_image1=userrL.get(SessionManager.KEY_CAPTURE_IMAGE1_LOCAL);
                        if(local_capture_image1!=null) {
                            try {
                                imageURI = local_capture_image1;
                                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                                IdCaptureImgView.setVisibility(View.VISIBLE);
//                            IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));
                                IdCaptureImgView.setImageDrawable(Drawable.createFromPath(local_capture_image1));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 5000);
            }
        });

//        IdSaveBtn.clic
        IdSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// preventing double, using threshold of 1000 ms
                saveClick=true;
                IdSaveBtn.setEnabled(false);
//                        if(saveClick) {
                            if (IdSaveBtn.getText().equals("SAVE")) {
                                if (validationVehicleDetails(signatureImageName))
                                {
                                    CustomDailog("Job Card", "Do You Want to Save JobCard Details?", 38, "save");
                                }
                                else
                                {
                                    saveClick = false;
                                    IdSaveBtn.setEnabled(true);
                                }
                            }
                            else {
                                if (validationVehicleDetails(signature_image1))
                                    UpdateData();
                                else {
                                    saveClick = false;
                                    IdSaveBtn.setEnabled(true);
                                }
//                        Toast.makeText(getActivity(), "Updated sucessfully", Toast.LENGTH_SHORT).show();
                            }
            }
        });

        IdClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CustomDailog("Job Card", "Do You Want to Clear Data?", 33, "clear");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        currentDate = ProjectMethods.getBusinessDate();
        currentTime = ProjectMethods.GetCustomerCurrentTime();

        return rootView;
    }

    private void setRemarksText(String tempServicesQty,String tempSparesQty) {
        if(remarks.isEmpty() || remarks == null || remarks.startsWith(" | ")) {// | No Of S : 0 | No Of Sp : 0
            remarks = "";
            RemarksEdt.setText("");
        }else{
            String tempRemarks = remarks.split(" | ")[0];
            if(tempServicesQty != null) {
                tempRemarks = tempRemarks + " | No Of S : "+tempServicesQty;
            }
            if(tempSparesQty != null) {
                tempRemarks = tempRemarks + " | No Of Sp : "+tempSparesQty;
            }
            remarks = tempRemarks;
            RemarksEdt.setText(remarks);
        }
    }


    private boolean validationVehicleDetails(String signatureImageName) {
        boolean resultValue = true;
        if(signatureImageName == null || signatureImageName.isEmpty())
            signatureImageName = null;
        if(capture_image1 == null || capture_image1.isEmpty() )
            capture_image1 = null;
        if (vehicleNo.isEmpty()) {
            VehicleNoEdt.setError("Please Enter VehicleNo");
            VehicleNoEdt.requestFocus();
            resultValue = false;
        } else if (contactNo.length() == 0) {
            CustMobileNoEdt.setError("Please Enter MobileNo");
            CustMobileNoEdt.requestFocus();
            resultValue = false;
        } else if (contactNo.length() < 10) {
            CustMobileNoEdt.setError("Please Enter Valid MobileNo");
            CustMobileNoEdt.requestFocus();
            resultValue = false;
        } else if (name.isEmpty()) {
            CustNameEdt.setError("Enter Customer Name");
            CustNameEdt.requestFocus();
            resultValue = false;
        } else if (place.isEmpty()) {
            PlaceEdt.setError("Enter Place");
            PlaceEdt.requestFocus();
            resultValue = false;
        } else if (vehicleId == null) {
            Toast.makeText(getActivity().getBaseContext(), "Please Select VehicleModel", Toast.LENGTH_SHORT).show();
            resultValue = false;
        } /*else if(capture_image1 == null) {
            resultValue = false;
        }*/ else if(signatureImageName == null){//signature_image1

            resultValue=false;
//            resultSignature=false;
        }
        if (!resultValue) {
//                NewJobCardDetailsMain.validationCheck=true; && capture_image1 != null
            if(signatureImageName != null) {
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "  Please Enter Mandatory Fields  ", Toast.LENGTH_SHORT);
//                View view = toast.getView();
//                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//                v.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                tabhost.getTabAt(0).select();
                resultValue = false;
            } /*else if(capture_image1 == null){
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "  Please Capture Image ", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                try {
                    //  FileUriExposedException
                    Intent photocapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoURI = null;
                    captureImagePath = new File(getActivity().getExternalCacheDir(), "temp.jpg");
                    if (Build.VERSION.SDK_INT >= 24) {

                        photoURI = FileProvider.getUriForFile(getActivity(), com.myaccounts.vechicleserviceapp.BuildConfig.APPLICATION_ID + ".provider", captureImagePath);

//                        UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
//                        server.execute();
                        Log.d("zzzzzz", "" + captureImagePath);
                    } else {
                        photoURI = Uri.fromFile(captureImagePath);

                        Log.d("rrrrrrr", "" + captureImagePath);
                    }
                    photocapture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(photocapture, TAKE_PHOTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resultValue=false;
            } */else if(signatureImageName == null){
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "  Please Add Signature  ", Toast.LENGTH_SHORT);
//                View view = toast.getView();
//                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//                v.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                try {
                    Intent sig = new Intent(getActivity(), DrawingSignatureActivity.class);
                    startActivityForResult(sig, 11);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                resultValue=false;
            }
        }
        Log.e("Executing block","I am here"+vehicleNo);
        Log.e("Executing block","I am here"+contactNo);
        Log.e("Executing block","I am here"+name);
        Log.e("Executing block","I am here"+place);
        Log.e("Executing block","I am here"+vehicleId);
        return resultValue;
    }

    private void UpdateData()
    {
            try {
                remarks = RemarksEdt.getText().toString();

                String list = "";
                String DocumentIdValues = list;
                Gson userGson = new GsonBuilder().create();
                dialog = new ProgressDialog(viewPager.getContext(), R.style.AppTheme_Dark_Dialog);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.show();

                if (AppUtil.isNetworkAvailable(getActivity())) {
                    try {
                        if(newsparePartsDetails==null){
                            newsparePartsDetails="";
                        }
                        if(serviceDetails==null){
                            serviceDetails="";
                        }
                        String[] separated = dateAndTimeTV.getText().toString().split(" ");

                            JSONObject jsonObject = new JSONObject();
//                          jsonObject.accumulate(JSONVariables.JMODIFIEDDATE, currentDate);
//                          jsonObject.accumulate(JSONVariables.JMODIFIEDTIME,jobCardId );
                            jsonObject.accumulate(JSONVariables.JCDate, separated[0].replace(",",""));
                            jsonObject.accumulate(JSONVariables.JCId,jobCardId );
                            jsonObject.accumulate(JSONVariables.JCNo, jobCardId);
                            jsonObject.accumulate(JSONVariables.JC_InTime,dateAndTimeTV.getText().toString());
                            jsonObject.accumulate(JSONVariables.JC_OutTime, "");
                            jsonObject.accumulate(JSONVariables.CustomerId, mCustomerId);
                            jsonObject.accumulate(JSONVariables.Vehicle_Id, vehicleId);
                            jsonObject.accumulate(JSONVariables.OMReading, odoReading);
                            jsonObject.accumulate(JSONVariables.AvgKMS_RPD, avgKms);
                            jsonObject.accumulate(JSONVariables.ServiceId, ServiceId);
                            jsonObject.accumulate(JSONVariables.SUserId, ProjectMethods.getUserId());
                            jsonObject.accumulate(JSONVariables.CounterId, ProjectMethods.getCounterId());
                            jsonObject.accumulate(JSONVariables.ServiceDetails, serviceDetails);
                            jsonObject.accumulate(JSONVariables.JCImage, imageURl);
                            jsonObject.accumulate(JSONVariables.JCSignature,signature_image1);
                            jsonObject.accumulate(JSONVariables.Documents, "");
                            jsonObject.accumulate(JSONVariables.CustName, name);
                            jsonObject.accumulate(JSONVariables.CustEmailId, "");
                            jsonObject.accumulate(JSONVariables.CustMobileNo, contactNo);
                            jsonObject.accumulate(JSONVariables.JCRemarks, remarks);
                            jsonObject.accumulate(JSONVariables.JCVehicleNo, vehicleNo);
                            jsonObject.accumulate(JSONVariables.JCMake, make);
                            jsonObject.accumulate(JSONVariables.JCModel, vehicleId);
                            jsonObject.accumulate(JSONVariables.JCModelId, vehicleId);
                            jsonObject.accumulate(JSONVariables.JCRegno, "");
                            jsonObject.accumulate(JSONVariables.ScreenName, "JobCard");
                            jsonObject.accumulate(JSONVariables.VehicleType, vehicleType);
                            jsonObject.accumulate(JSONVariables.Mileage, mileage);
                            jsonObject.accumulate(JSONVariables.Place, place);
                            jsonObject.accumulate(JSONVariables.SpareId, SpareId);
                            jsonObject.accumulate(JSONVariables.ItemDetails, newsparePartsDetails);
                            jsonObject.accumulate(JSONVariables.Block, selectedBlock);
                        String dbValue="";
                        if(!technicianName.isEmpty()) {
                            DatabaseHelper1 db = new DatabaseHelper1(rootView.getContext());
                            dbValue = db.jgetValueId(technicianName);
                        }
                        jsonObject.accumulate(JSONVariables.TechnicianName, dbValue);
                        jsonObject.accumulate(JSONVariables.Type, "Update");

                        Log.e("ImageAndSignFragment", "UpdateJSONObj " + jsonObject );
                      //  writeToFile(jsonObject.toString(),getContext(),vehicleNo,"update");
                        BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                        requestName = "UpdateJobCardDetails_New";
                        serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                        serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.UpdateJobCardDetails, jsonObject, Request.Priority.HIGH);
                        /*Log.d("updatedetail", "" + jsonObject);
                        Log.d("updated ID", "" + newsparePartsDetails);
                        */


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View rooView = getView();
        numberOfSparesTv = (TextView) rooView.findViewById(R.id.numberOfSparesTv);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getVehicleDetails();
        qtySpareParts = user.get(SessionManager.KEY_SPAREPARTS_QTY);
        totalAmountSpares = user.get(SessionManager.KEY_SPARES_DETAILS_TOTAL_AMOUNT);
        totalAmountServices = user.get(SessionManager.KEY_SERVICE_DETAILS_TOTAL_AMOUNT);


        try {
            Log.d("kjghfk", qtySpareParts);
            numberOfSparesTv.setText(" : " + qtySpareParts);
            Double total = Double.parseDouble(totalAmountSpares) + Double.parseDouble(totalAmountServices);
            noOfServicesTv.setText(" : " + qtyServices);
            totalAmountTv.setText(" : Rs." + total);
        } catch (NullPointerException e) {
            qtySpareParts = "0";
            numberOfSparesTv.setText(" : " + qtySpareParts);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
              /*  picUri = data.getData();
                performCrop();*/
                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                IdCaptureImgView.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Log.e("request_Capture_image", "" + imageBitmap);
                IdCaptureImgView.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            picUri = data.getData();
            performCrop();

        } else if (requestCode == TAKE_PHOTO) {
            try {

                startImageCrop(Uri.fromFile(captureImagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                IdCaptureImgView.setVisibility(View.VISIBLE);
                Uri resultUri = result.getUri();
                imageURl = resultUri.getPath();
                captureImagePath = new File(imageURl);
                imageURl = "Image_" + getRandomNumberInRange(1, 10000) + ".jpg";

                /*try {
                    UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    Log.e(TAG, "crop_image_request_code: " + bitmap );
                    IdCaptureImgView.setImageBitmap(bitmap);

                    Log.d("CaptureimmM", captureImagePath + "," + imageURl + "," + resultUri);

                    UploadimageServer server = new UploadimageServer(captureImagePath, imageURl, resultUri, getActivity());
                    server.execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        } else if (resultCode == RESULT_OK && requestCode == 003) {
            imageURI = ImageName;
            try {
              /*  UploadTasks task = new UploadTasks(new File(Environment.getExternalStorageDirectory(), imageURI), imageURI, null);
                task.execute();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e(TAG, "result_ok_003: " + imageURI );
            ImgBackLinerLayout.setVisibility(View.VISIBLE);
            IdCaptureImgView.setVisibility(View.VISIBLE);
            IdCaptureImgView.setImageBitmap(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), imageURI).getAbsolutePath()));

        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK) {
                try {
                    ImgBackLinerLayout.setVisibility(View.VISIBLE);
                    IdCaptureImgView.setVisibility(View.VISIBLE);

                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    Log.e(TAG, "cam_capture_img_req_res_ok: " + imageBitmap );
                    IdCaptureImgView.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//ImageView to set the picture taken from camera.

                picTaken = true; //to ensure picture is taken

            }
        }else if(resultCode == 200 && requestCode == 11){
//            Toast.makeText(getContext(), "ONACTIVITYRESULT ", Toast.LENGTH_SHORT).show();
            sessionManager = new SessionManager(getActivity());
            HashMap<String, String> user = sessionManager.getstoreSignatureDetails();
            String fileSignature = user.get(SessionManager.KEY_SIGNATURE_FILE);
            String imageSignature = user.get(SessionManager.KEY_IMAGE); 
            String uriSignature = user.get(SessionManager.KEY_URI);
            signatureImageName=imageSignature;
            signature_image1 = imageSignature;
            /*Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(uriSignature));
                ImgBackLinerLayout.setVisibility(View.VISIBLE);
                drawingImage.setVisibility(View.VISIBLE);
                drawingImage.setImageDrawable(Drawable.createFromPath(fileSignature));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "crop_image_request_code: " + bitmap );*/

//            drawingImage.setImageDrawable(Drawable.createFromPath(local_signature_image1));
            try {
                Uri myUri = Uri.parse(uriSignature);
                File file = new File(fileSignature);
                UploadimageSignatureServer server = new UploadimageSignatureServer(file, imageSignature, myUri, getActivity());
                server.execute();
            }catch(Exception e){
            }

        }
        //user is returning from cropping the image
        else if (requestCode == PIC_CROP)
        {
            //get the returned data
            Bundle extras = data.getExtras();
            //get the cropped bitmap
            Bitmap thePic = (Bitmap) extras.get("data");
            Log.e("piccc", "" + thePic);
            Log.e(TAG, "pic_crop: " + thePic );
            //display the returned cropped image
            IdCaptureImgView.setImageBitmap(thePic);
        }
        else if (requestCode == 101 && data != null && resultCode == RESULT_OK) {

        }
        else if (requestCode == 33 && data != null && resultCode == RESULT_OK)
        {
            ClearData();
        }
        else if (requestCode == 38 && data != null && resultCode == RESULT_OK)
        {

            SaveData();
            saveClick=false;
        }
    }

    private void ClearData() {
        RemarksEdt.setText("");
    }

    private void startImageCrop(Uri imageUri)
    {
        CropImage.activity(imageUri).start(getContext(), this);
    }

    private void performCrop() {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch (ActivityNotFoundException anfe)
        {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void SaveData()
    {

        saveDataToServer();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            else
            {
               // writeToFile(jsonObject.toString(),getApplicationContext());
               // saveDataToServer();
            }
        }*/


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveDataToServer()
    {

        progress_bar_jobcard_save.setVisibility(View.VISIBLE);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        try {
            remarks = RemarksEdt.getText().toString();
            Log.e("remarks", "remarks: " + remarks);
            if(!remarks.contains("|")) {
                if (qtyServices != null) {
                    remarks = remarks + " | No Of S : " + qtyServices;
                }
                if (qtySpareParts != null) {
                    remarks = remarks + " | No Of Sp : " + qtySpareParts;
                }
            }
            String list = "";
            String DocumentIdValues = list;
            Gson userGson = new GsonBuilder().create();
            dialog = new ProgressDialog(viewPager.getContext(), R.style.AppTheme_Dark_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage("Please Wait..");
            dialog.show();
            if (AppUtil.isNetworkAvailable(getActivity())) {
                try {
                    if(newsparePartsDetails==null)
                    {
                        newsparePartsDetails="";
                    }
                    if(serviceDetails==null){
                        serviceDetails="";
                    }
                    HashMap<String, String> user = sessionManager.getSignatureFilepath();

                    try {
                        signatureFilePath = user.get(SessionManager.KEY_SIGNATURE_FILE_PATH);
                    } catch (NullPointerException e) {
                    }
                    Log.e("ImageAndSignFragment "," newsparePartsDetails "+newsparePartsDetails);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(JSONVariables.JCDate, currentDate);
                    jsonObject.accumulate(JSONVariables.JCId,jobcardId );
                    jsonObject.accumulate(JSONVariables.JCNo, jobcardId);
                    jsonObject.accumulate(JSONVariables.JC_InTime, currentTime);
                    jsonObject.accumulate(JSONVariables.JC_OutTime, "");
                    jsonObject.accumulate(JSONVariables.CustomerId, mCustomerId);
                    jsonObject.accumulate(JSONVariables.Vehicle_Id, vehicleId);
                    jsonObject.accumulate(JSONVariables.OMReading, odoReading);
                    jsonObject.accumulate(JSONVariables.AvgKMS_RPD, avgKms);
                    jsonObject.accumulate(JSONVariables.ServiceId, ServiceId);
                    jsonObject.accumulate(JSONVariables.SUserId, ProjectMethods.getUserId());
                    jsonObject.accumulate(JSONVariables.CounterId, ProjectMethods.getCounterId());
                    jsonObject.accumulate(JSONVariables.ServiceDetails, serviceDetails);
                    jsonObject.accumulate(JSONVariables.JCImage, imageURl);
                    jsonObject.accumulate(JSONVariables.JCSignature,signatureImageName);
                    jsonObject.accumulate(JSONVariables.Documents, "");
                    jsonObject.accumulate(JSONVariables.CustName, name);
                    jsonObject.accumulate(JSONVariables.CustEmailId, "");
                    jsonObject.accumulate(JSONVariables.CustMobileNo, contactNo);
                    jsonObject.accumulate(JSONVariables.JCRemarks, remarks);
                    jsonObject.accumulate(JSONVariables.JCVehicleNo, vehicleNo);
                    jsonObject.accumulate(JSONVariables.JCMake, make);
                    jsonObject.accumulate(JSONVariables.JCModel, vehicleId);
                    jsonObject.accumulate(JSONVariables.JCModelId, vehicleId);
                    jsonObject.accumulate(JSONVariables.JCRegno, "");
                    jsonObject.accumulate(JSONVariables.ScreenName, "JobCard");
                    jsonObject.accumulate(JSONVariables.VehicleType, vehicleType);
                    jsonObject.accumulate(JSONVariables.Mileage, mileage);
                    jsonObject.accumulate(JSONVariables.Place, place);
                    jsonObject.accumulate(JSONVariables.ItemDetails, newsparePartsDetails);
                    jsonObject.accumulate(JSONVariables.Block, selectedBlock);

                    String dbValue="";
                    if(!technicianName.isEmpty()) {
                        DatabaseHelper1 db = new DatabaseHelper1(rootView.getContext());
                        dbValue = db.jgetValueId(technicianName);
                    }else{
                    }
                    jsonObject.accumulate(JSONVariables.TechnicianName, dbValue);
                    jsonObject.accumulate(JSONVariables.Type, "Save");

                    if(validationVehicleDetails(signatureImageName)) {
                        if(!serviceDetails.equalsIgnoreCase("")) {
                            String[] result = serviceDetails.split("~");
                            for (String s : result) {
                                System.out.println(">" + s + "<");
                                String[] singleResult=s.split("!");

                            }
                        }

                        Log.e("ImageAndSignFragment "," SaveJSONObj "+jsonObject);
                       // writeToFile(jsonObject.toString(),getContext(),vehicleNo,"save");
                        BackendServiceCall serviceCall = new BackendServiceCall(getActivity(), false);
                        requestName = "SaveJobCardDetails_New";
                        serviceCall.setOnServiceCallCompleteListener(new OnServiceCallCompleteListenerImpl());
                        serviceCall.makeJSONOArryPostRequest(ProjectVariables.BASE_URL + ProjectVariables.SaveJobCardDetails_New, jsonObject, Request.Priority.HIGH);
                        /*DatabaseHelper db = new DatabaseHelper(getActivity());
                        db.insert_EmailidDetails(jobCardId, newsparePartsDetails);*/
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(String data, Context context,String vNumber,String status) {

        try {

            long tsLong = System.currentTimeMillis()/1000;
            String ts = Long.toString(tsLong) +"_"+vNumber+ "_JSONSave.txt";
            String name=  status+"_"+vNumber+ "_JSON.txt";

            FileWriter out = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
            {
                out = new FileWriter(new File(Objects.requireNonNull(getContext()).getExternalFilesDir(null), name));
                out.write(data);
                out.close();
            }

            Toast.makeText(context,"Saved..",Toast.LENGTH_LONG).show();

            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }


    private class OnServiceCallCompleteListenerImpl implements OnServiceCallCompleteListener {
        @Override
        public void onJSONObjectResponse(JSONObject jsonObject) {

        }

        @Override
        public void onJSONArrayResponse(JSONArray jsonArray)
        {
            if (requestName.equalsIgnoreCase("SaveJobCardDetails_New")) {
                try {
                    handeSaveJobCardDetails(jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("UpdateJobCardDetails_New")) {
                try {
                    handeSaveJobCardDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestName.equalsIgnoreCase("GetCustomerDataOpenData")) {
                try {
                    handeUserDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestName.equalsIgnoreCase("GetDetailsByVehicleNo")) {
                try {
                    handeVehicleDetails(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handeSaveJobCardDetails(JSONArray jsonArray) {
        try {

            swipeRefreshLayout.setRefreshing(false);
            if (jsonArray.length() > 0)
            {
                String result = jsonArray.getJSONObject(0).getString("Result");
                String JobCardNumber = jsonArray.getJSONObject(0).getString("JobCardNumber");

                sessionManager.clearSession();
                MainActivity.comingfrom="1";

                if(dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    // progress_bar_jobcard_save.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                IdSaveBtn.setEnabled(true);
                Toast.makeText(getActivity(), result + "" + JobCardNumber, Toast.LENGTH_SHORT).show();
                long millis=System.currentTimeMillis();
                java.util.Date date=new java.util.Date(millis);
                String jobc=JobCardNumber+IdSaveBtn.getText().toString()+date;
                DatabaseHelper db = new DatabaseHelper(getActivity());
                db.insert_SpareDetails(jobc, newsparePartsDetails);
                SharedPreferences.Editor edit = jobcardPref.edit();
                edit.putString("JobCardNumber", JobCardNumber);
                edit.commit();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_bar_jobcard_save.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        progress_bar_jobcard_save.setVisibility(View.GONE);
                    }
                }, 2000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                {
                    Objects.requireNonNull(getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeUserDetails(JSONArray jsonArray) {
        try {

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String customerId = jsonArray.getJSONObject(i).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(i).getString("CustomerName");
                    String Result = jsonArray.getJSONObject(i).getString("Result");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handeVehicleDetails(JSONArray jsonArray) {
        try {
            VehicleDetails vehicleDetails = new VehicleDetails();
            if (jsonArray.length() > 0) {
                String result = jsonArray.getJSONObject(0).getString("Result");
                if (result.equalsIgnoreCase("No Details Found")) {
                    Toast toast = Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.WHITE);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLACK);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    mCustomerId = jsonArray.getJSONObject(0).getString("CustomerId");
                    String CustomerName = jsonArray.getJSONObject(0).getString("CustomerName");
                    String EmailId = jsonArray.getJSONObject(0).getString("EmailId");
                    String MakeCompany = jsonArray.getJSONObject(0).getString("MakeCompany");
                    String MakeYear = jsonArray.getJSONObject(0).getString("MakeYear");
                    String MobileNo = jsonArray.getJSONObject(0).getString("MobileNo");
                    String ModelNo = jsonArray.getJSONObject(0).getString("ModelNo");
                    vehicleNo = jsonArray.getJSONObject(0).getString("VehicleNo");

                }
            }
            vehicleDetailsArrayList.add(vehicleDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

private void CustomDailog(String Title, String msg, int value, String btntxt) {
    try {
        MyMessageObject.setMyTitle(Title);
        MyMessageObject.setMyMessage(msg);
        MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
        Intent intent = new Intent(getActivity(), CustomDialogClass.class);
        intent.putExtra("msgbtn", btntxt);
        startActivityForResult(intent, value);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
           //  writeToFile(jsonObject.toString(),getApplicationContext());
            saveDataToServer();
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            }
        }
    }
}
