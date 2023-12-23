package com.myaccounts.vechicleserviceapp.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Fragments.CancelFragment;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CancelJobCardDetails extends AppCompatActivity {

    Toolbar toolbar;
    public static ViewPager viewPager;
    public static TabLayout tabLayout;

    public static TextView dateAndTimeTV;
//    public static TextClock tClock;

    SessionManager sessionManager;
    String modelId;
    String vehicleModelId;
    Fragment fragment;
    public static boolean validationCheck=true;

    CancelFragment newVehicleFragment;


    String vehicleId, vehicleNo = null, contactNo = null, name = null, place = null, block = null,
            odoReading = null, mileage = null, avgKms = null, model = null, make = null, vehicleType = null,
            serviceDetails = null, newsparePartsDetails = null, noOfServices = null, selectedBlock,TimeIn,DateIN;

    String jobcardNo;
    public static EditText VehicleNoEdt;

    private ViewPagerAdapter adapter;

    public String type;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_job_card_details_main);
        Intent intent = getIntent();
        type = intent.getStringExtra("new");
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

        /*NewCaptureImageAndSignFragment frag_name = new NewCaptureImageAndSignFragment();
        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.viewpager, frag_name, frag_name.getTag()).commit();*/

        dateAndTimeTV = (TextView) findViewById(R.id.dateAndTimeTV);
//        tClock=(TextClock)findViewById(R.id.textClock1);
//        dateAndTimeTV.setText(ProjectMethods.getBusinessDate() + ", " + ProjectMethods.GetCurrentTime());
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
            odoReading = user.get(SessionManager.KEY_ODO_READING);
            mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
            avgKms = user.get(SessionManager.KEY_AVG_KMS);
            model = user.get(SessionManager.KEY_MODEL);
            make = user.get(SessionManager.KEY_MAKE);
            vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
            selectedBlock = user.get(SessionManager.KEY_BLOCK);

            serviceDetails = user.get(SessionManager.KEY_SERVICE_DETAILS_LIST);
            newsparePartsDetails = user.get(SessionManager.KEY_SPARE_PARTS_DETAILS_LIST);
            noOfServices = user.get(SessionManager.KEY_NO_OF_SERVICES);
            TimeIn=user.get(SessionManager.KEY_JOBCARD_JCTIME);
            DateIN=user.get(SessionManager.KEY_JOBCARD_JCDATE);
            dateAndTimeTV.setText(DateIN + ", " + TimeIn);

        } catch (NullPointerException e) {
        }
        if(type!=null) {
            if (type.equalsIgnoreCase("new"))
                dateAndTimeTV.setText(ProjectMethods.getBusinessDate() + ", " + ProjectMethods.GetCurrentTime());
        }
//        nm = new NewMainVehicleDetailsFragment();
//        vehicleNo = nm.vehicleNo;
//        CustMobileNo = nm.CustMobileNo;
//        CustName = nm.CustName;
//        Place = nm.Place;
//        selectBlock = nm.selectBlock;
//        CustOdometerReading = nm.CustOdometerReading;
//        Mileage = nm.Mileage;
//        Avgkmsperday = nm.Avgkmsperday;
//        CustVehicleModel = nm.CustVehicleModel;
//        CustVehiclemakemodel = nm.CustVehiclemakemodel;
//        CustVehicleType = nm.CustVehicleType;
//        VehicleNoEdt = nm.VehicleNoEdt;

        //  Log.d("aaaaaaaa", vehicleNo + selectBlock + CustOdometerReading + Mileage + Avgkmsperday + CustVehicleModel);


//        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
//        tabStrip.setEnabled(false);
//        for (int i = 0; i < tabStrip.getChildCount(); i++) {
//            tabStrip.getChildAt(i).setClickable(false);
//        }
        /*tabStrip.getChildAt(1).setClickable(false);
        tabStrip.getChildAt(2).setClickable(false);
        tabStrip.getChildAt(3).setClickable(false);*/
        /*dateAndTimeTV = (TextView) findViewById(R.id.dateAndTimeTV);
        dateAndTimeTV.setText(ProjectMethods.getBusinessDate() + ", " + ProjectMethods.GetCurrentTime());*/

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#ffffff"));

        /*if(type !=null && type.equalsIgnoreCase("new")){
            Toast.makeText(getApplicationContext(),"Selection disable",Toast.LENGTH_SHORT).show();
            disable(tabLayout,false);

        }else{
            disable(tabLayout,true);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        /*try {
            Bundle b = getIntent().getExtras();
            String orderType = b.getString("orderType");
            int position = Integer.parseInt(orderType);
            if (position == 1) {
                position = 2;
            } else if (position == 2) {
                position = 1;
            }
            viewPager.setCurrentItem(position);
        } catch (NullPointerException e) {
            viewPager.setCurrentItem(0);
        } catch (NumberFormatException e1) {
        }*/

        /*try {
            Bundle b = getIntent().getExtras();
            String orderType = b.getString("orderType");
            int position = Integer.parseInt(orderType);
            if (position == 1) {
                if(!validationCheck)
                position = 2;
                else
                    Toast.makeText(getApplicationContext(),"Please Enter Vehicle Details",Toast.LENGTH_LONG).show();
            } else if (position == 2) {
                position = 1;
            }
            SharedPreferences sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            viewPager.setCurrentItem(position);
        } catch (NullPointerException e) {
            viewPager.setCurrentItem(0);
        } catch (NumberFormatException e1) {
        }*/


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                boolean clickable;
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
                odoReading = user.get(SessionManager.KEY_ODO_READING);
                mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
                avgKms = user.get(SessionManager.KEY_AVG_KMS);
                model = user.get(SessionManager.KEY_MODEL);
                make = user.get(SessionManager.KEY_MAKE);
                vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
                selectedBlock = user.get(SessionManager.KEY_BLOCK);
                Log.d("fffffff", odoReading + vehicleModelId);
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


    private void receiveData() {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        String name = i.getStringExtra("SENDER_KEY");
//        Log.d("SENDER_KEY", name);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (newVehicleFragment == null){

            newVehicleFragment = new CancelFragment();

        }


        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newVehicleFragment, "Vehicle Details");
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
                // app icon in action bar clicked; goto parent activity.
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

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
//                sessionManager.clearSession();
//                this.finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public void onBackPressed() {
//        sessionManager.clearSession();
//        super.onBackPressed();
////        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
//    }

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
    /*public boolean nextTabSelectionValidation() {
        boolean validationCheck=true;
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getVehicleDetails();
        vehicleNo = user.get(SessionManager.KEY_VEHICLE_NO);//KEY_VEHICLE_NO
        vehicleModelId = user.get(SessionManager.KEY_VEHICLE_ID);
        vehicleId = user.get(SessionManager.KEY_VEHICLE_ID);
        contactNo = user.get(SessionManager.KEY_MOBILE_NO);
        name = user.get(SessionManager.KEY_CUSTOMER_NAME);
        place = user.get(SessionManager.KEY_PLACE);
        block = user.get(SessionManager.KEY_BLOCK);
        odoReading = user.get(SessionManager.KEY_ODO_READING);
        mileage = user.get(SessionManager.KEY_NEXT_MILEAGE);
        avgKms = user.get(SessionManager.KEY_AVG_KMS);
        model = user.get(SessionManager.KEY_MODEL);
        make = user.get(SessionManager.KEY_MAKE);
        vehicleType = user.get(SessionManager.KEY_VEHICLE_TYPE);
        selectedBlock = user.get(SessionManager.KEY_BLOCK);

        if (vehicleNo == null || name == null || contactNo == null) {
            validationCheck=false;
            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//get number of tab
            int tabsCount = vg.getChildCount();
//loop the tab
            for (int j = 0; j < tabsCount; j++) {
                //get view of selected tab
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

                if (j == 1 || j == 2 || j == 3) {
                    //disable the selected tab
                    vgTab.setEnabled(false);
                }
            }
        }else {
            //get tab view
            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//get number of tab
            int tabsCount = vg.getChildCount();
//loop the tab
            for (int j = 0; j < tabsCount; j++) {
                //get view of selected tab
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

                if (j == 1 || j == 2 || j == 3) {
                    //disable the selected tab
                    vgTab.setEnabled(true);
                }
            }
        }
        return validationCheck;
    }*/

}