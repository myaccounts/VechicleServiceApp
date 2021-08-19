package com.myaccounts.vechicleserviceapp.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Fragments.LatestNewServiceSelectedFragment;
import com.myaccounts.vechicleserviceapp.Fragments.NewCaptureImageAndSignFragment;
import com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment;
import com.myaccounts.vechicleserviceapp.Fragments.NewSparePartsFragment;
import com.myaccounts.vechicleserviceapp.Pojo.NewServiceMasterDetails;
import com.myaccounts.vechicleserviceapp.Pojo.SparePartDetails;
import com.myaccounts.vechicleserviceapp.Pojo.VehicleTypes;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NewJobCardDetailsMain extends AppCompatActivity {

    Toolbar toolbar;
    public static ViewPager viewPager;
    public static TabLayout tabLayout;
    public static TextView dateAndTimeTV;
    SessionManager sessionManager;
    String modelId;
    String vehicleModelId;
    Fragment fragment;
    public static boolean validationCheck=true;
    NewMainVehicleDetailsFragment newVehicleFragment;
    LatestNewServiceSelectedFragment latestservicefragment;
    NewSparePartsFragment newsparefragment;
    NewCaptureImageAndSignFragment newcaptureimgsignfragment;
    String vehicleId, vehicleNo = null, contactNo = null, name = null, place = null, technicianName=null,block = null,
            odoReading = null, mileage = null, avgKms = null, model = null, make = null, vehicleType = null,
            serviceDetails = null, newsparePartsDetails = null, noOfServices = null, selectedBlock,TimeIn,DateIN,selectedTechnicianName;
    String jobcardNo;
    public static EditText VehicleNoEdt;
    private ArrayList<NewServiceMasterDetails> serviceMasterArrayList = new ArrayList<>();
    private ArrayList<SparePartDetails> sparepartArrayList = new ArrayList<>();
    private ViewPagerAdapter adapter;
    public String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_job_card_details_main);

        Intent intent = getIntent();
        type = intent.getStringExtra("new");
        Log.d("ANUSHA ","___3"+intent.getStringExtra("jobcardNo")+","+type);
        Log.d("ANUSHA ","___3"+intent.getStringExtra("technicianName")+","+type);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Job Card");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        CustomViewPager ViewPagerObj = (CustomViewPager) findViewById(R.id.viewpager);
        ViewPagerObj.setSwipeable(false);

        dateAndTimeTV = (TextView) findViewById(R.id.dateAndTimeTV);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getVehicleDetails();

        try {
            vehicleModelId = user.get(SessionManager.KEY_VEHICLE_ID);
            vehicleId = user.get(SessionManager.KEY_VEHICLE_ID);
            vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
            contactNo = user.get(SessionManager.KEY_MOBILE_NO);
            name = user.get(SessionManager.KEY_CUSTOMER_NAME);
            place = user.get(SessionManager.KEY_PLACE);
            block = user.get(SessionManager.KEY_BLOCK);
            technicianName=user.get(SessionManager.KEY_TECHINICIANNAME);
            odoReading = user.get(SessionManager.KEY_ODO_READING);
            mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
            avgKms = user.get(SessionManager.KEY_AVG_KMS);
            model = user.get(SessionManager.KEY_MODEL);
            make = user.get(SessionManager.KEY_MAKE);
            vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
            selectedBlock = user.get(SessionManager.KEY_BLOCK);
            selectedTechnicianName=user.get(SessionManager.KEY_TECHINICIANNAME);
            serviceDetails = user.get(SessionManager.KEY_SERVICE_DETAILS_LIST);
            newsparePartsDetails = user.get(SessionManager.KEY_SPARE_PARTS_DETAILS_LIST);
            noOfServices = user.get(SessionManager.KEY_NO_OF_SERVICES);
            Log.d("dddddddd", vehicleNo);

            Log.d("ANUSHA ", " "+contactNo);
            TimeIn=user.get(SessionManager.KEY_JOBCARD_JCTIME);
            DateIN=user.get(SessionManager.KEY_JOBCARD_JCDATE);
            dateAndTimeTV.setText(DateIN + ", " + TimeIn);

        } catch (NullPointerException e) {
        }
        if(type!=null) {
            if (type.equalsIgnoreCase("new"))
                dateAndTimeTV.setText(ProjectMethods.getBusinessDate() + ", " + ProjectMethods.GetCurrentTime());
            Log.e("ANUSHA "," "+ProjectMethods.getBusinessDate()+ ", " + ProjectMethods.GetCurrentTime());
        }

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                boolean clickable;
                Log.d("ANUSHA ", "tabselection called "+position);
                sessionManager = new SessionManager(getApplicationContext());
                HashMap<String, String> user = sessionManager.getVehicleDetails();
                /*if(position==2 || position ==3 || position == 4){
                    Toast.makeText(getApplicationContext(),"IF"+position,Toast.LENGTH_LONG).show();
                }*/
               /* if(jobCardId.equalsIgnoreCase("empty")){
                    tab.parent.getChildAt(position).setClickable(false);
                    Toast.makeText(getApplicationContext(),"IF"+"setting false",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),"ELSE"+"setting true",Toast.LENGTH_LONG).show();
                    tab.parent.getChildAt(position).setClickable(true);

                }*/
//                tab.parent.getChildAt(position).isClickable();
                    vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);
                    vehicleModelId = user.get(SessionManager.KEY_VEHICLE_ID);
                    vehicleId = user.get(SessionManager.KEY_VEHICLE_ID);
                    contactNo = user.get(SessionManager.KEY_CONTACT_NO);
                    name = user.get(SessionManager.KEY_CUSTOMER_NAME);
                    place = user.get(SessionManager.KEY_PLACE);
                    block = user.get(SessionManager.KEY_BLOCK);
                    technicianName=user.get(SessionManager.KEY_TECHINICIANNAME);
                    odoReading = user.get(SessionManager.KEY_ODO_READING);
                    mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
                    avgKms = user.get(SessionManager.KEY_AVG_KMS);
                    model = user.get(SessionManager.KEY_MODEL);
                    make = user.get(SessionManager.KEY_MAKE);
                    vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
                    selectedBlock = user.get(SessionManager.KEY_BLOCK);
                selectedTechnicianName=user.get(SessionManager.KEY_TECHINICIANNAME);
                    Log.d("fffffff", odoReading + vehicleModelId);
                    Log.d("ANUSHA ", " "+block);
                    /*if(type !=null && type.equalsIgnoreCase("new") &&
                            NewMainVehicleDetailsFragment.vehicleNo.isEmpty()){
                        disable(tabLayout,false);

                    }else{
                        disable(tabLayout,true);
                    }*/
                /*if(position == 0 || position == 2 || position == 3 &&
                        (vehicleNo.isEmpty() || name.isEmpty() || place.isEmpty() || contactNo.isEmpty() || vehicleModelId.isEmpty())) {
                        Toast.makeText(getApplicationContext(), "Enter All Fileds", Toast.LENGTH_SHORT).show();
                        disable(tabLayout, false);
                    } else {
                        disable(tabLayout, true);
                    }*/
                /*else if (tab.getPosition() == 1 && (vehicleNo == null || name == null || place == null || contactNo == null || vehicleModelId == null)) {
                    if(latestservicefragment == null){
                        latestservicefragment = new LatestNewServiceSelectedFragment();
                        latestservicefragment.setEdiServiceDetailsArrayList(serviceMasterArrayList);
                    }
                    fragment = latestservicefragment;
                } else if (tab.getPosition() == 2) {
                    if (newsparefragment == null) {
                        newsparefragment = new NewSparePartsFragment();
                    }
                    fragment = newsparefragment;
                }
                else if (tab.getPosition() == 3) {
                    if (newcaptureimgsignfragment == null) {
                        newcaptureimgsignfragment = new NewCaptureImageAndSignFragment();
                    }
                    fragment = newcaptureimgsignfragment;
                }*/

                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.submitId, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commitAllowingStateLoss();*/

//                    LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
//                    tabStrip.setEnabled(false);
//                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
//                        tabStrip.getChildAt(position).setClickable(false);
//                    }
//                }

//                    LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
//                    tabStrip.setEnabled(false);
//                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
//                        tabStrip.getChildAt(i).setClickable(false);
//                    }
//                    nm.Validation();

//                receiveData();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    Log.d("enterting", "enetering");
//                    if (newVehicleFragment == null){
//                        newVehicleFragment = new NewMainVehicleDetailsFragment();
//                    }
//                    fragment = newVehicleFragment;
//
//                } else if (tab.getPosition() == 1) {
//                    if(latestservicefragment == null){
//                        latestservicefragment = new LatestNewServiceSelectedFragment();
//                        latestservicefragment.setEdiServiceDetailsArrayList(serviceMasterArrayList);
//                    }
//                    fragment = latestservicefragment;
//                } else if (tab.getPosition() == 2) {
//                    if (newsparefragment == null) {
//                        newsparefragment = new NewSparePartsFragment();
//                    }
//                    fragment = newsparefragment;
//                }
//                else if (tab.getPosition() == 3) {
//                    if (newcaptureimgsignfragment == null) {
//                        newcaptureimgsignfragment = new NewCaptureImageAndSignFragment();
//                    }
//                    fragment = newcaptureimgsignfragment;
//                }
//
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.submitId, fragment, "findThisFragment")
//                        .addToBackStack(null)
//                        .commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void disable(TabLayout tabLayout, boolean b) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            if (j == 1 || j == 2 || j == 3) {
                vgTab.setEnabled(b);
                Toast.makeText(getApplicationContext(),"Selection disable",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void receiveData() {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        String name = i.getStringExtra("SENDER_KEY");
//        Log.d("SENDER_KEY", name);
    }

    private void setupViewPager(ViewPager viewPager) {

        if (newVehicleFragment == null){

            newVehicleFragment = new NewMainVehicleDetailsFragment();

        }

        if(latestservicefragment == null){
                latestservicefragment = new LatestNewServiceSelectedFragment();
                latestservicefragment.setEdiServiceDetailsArrayList(serviceMasterArrayList);
            Log.d("ANUSHA ", "position "+" "+serviceMasterArrayList.size());

        }

        if (newsparefragment == null) {
            newsparefragment = new NewSparePartsFragment();
            newsparefragment.setEdiSpareDetailsArrayList(sparepartArrayList);
            Log.d("ANUSHA ", "position "+" "+sparepartArrayList.size());

        }

        if (newcaptureimgsignfragment == null) {
            newcaptureimgsignfragment = new NewCaptureImageAndSignFragment();
        }

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newVehicleFragment, "Vehicle Details");
        adapter.addFragment(latestservicefragment, "Services");
        adapter.addFragment(newsparefragment, "Spare Parts");
        adapter.addFragment(newcaptureimgsignfragment, "Submit");
        //}
//        adapter.addFragment(new DeliveryFragment(), "Delivery");

        viewPager.setAdapter(adapter);
        //nextTabSelectionValidation();
        viewPager.setOffscreenPageLimit(0);
    }


     class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("ANUSHA ", "position "+" "+mFragmentList.get(position));

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Discard?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.clearSession();
                        //if user pressed "yes", then he is allowed to exit from application
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
