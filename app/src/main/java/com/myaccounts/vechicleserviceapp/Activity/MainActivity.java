package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Fragments.ChangePasswordFragment;
import com.myaccounts.vechicleserviceapp.Fragments.ContextMenuDialogFragment;
import com.myaccounts.vechicleserviceapp.Fragments.DatabaseHelper1;
import com.myaccounts.vechicleserviceapp.Fragments.HomeFragment;
import com.myaccounts.vechicleserviceapp.Fragments.JobCardReportsFragment;
import com.myaccounts.vechicleserviceapp.Fragments.MainJobCardFragment;
import com.myaccounts.vechicleserviceapp.Fragments.SparePartEstAgstJobCard;
import com.myaccounts.vechicleserviceapp.Fragments.SparePartIssueFragment;
import com.myaccounts.vechicleserviceapp.Fragments.UserWisePrevilegeFragment;
import com.myaccounts.vechicleserviceapp.LoginSetUp.LoginActivity;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Reports.JobCardHistoryReport;
import com.myaccounts.vechicleserviceapp.Reports.ReportsFrgament;
import com.myaccounts.vechicleserviceapp.Utils.AppUtil;
import com.myaccounts.vechicleserviceapp.Utils.DatePickerFragment;
import com.myaccounts.vechicleserviceapp.Utils.OnDateSetCompleted;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.yalantis.contextmenu.lib.MenuParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.myaccounts.vechicleserviceapp.Utils.DatePickerFragment.currentdate;
import static com.myaccounts.vechicleserviceapp.Utils.DatePickerFragment.menuCalenderSelectonDate;
import static com.myaccounts.vechicleserviceapp.Utils.SessionManager.KEY_SELECTED_DATE;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int month, day, year;
    SharedPreferences lpref;
    SharedPreferences.Editor  leditor;
    public static String comingfrom="MAINACTIVITY";
    private FragmentManager fragmentManager;
    private ProjectVariables gV1;
    private RecyclerView recyclerView;
    private TextView userName, emailId;
    private DrawerLayout mDrawerLayout;
    private String dealerAddress;
    private AlertDialog alertDialog;
    private JSONObject jsonObject;
    private ContextMenuDialogFragment mMenuDialogFragment;
    SharedPreferences sharedPreferences;
    private String loginusername = "";

    TextView newTv;
    Menu menu;
    public static boolean menuClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>JobCard</font>"));
        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(LoginActivity.mypreference, Context.MODE_PRIVATE);
        loginusername = sharedPreferences.getString(LoginActivity.Name, "");
        // initMenuFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // initMenuFragment();
        gV1 = new ProjectVariables();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        TextView nav_username = (TextView) hView.findViewById(R.id.nav_username);
        nav_username.setText(loginusername);
        Log.d("ANUSHA ", "" + "MAINACTIVTY");
        lpref = MainActivity.this.getSharedPreferences("goWheelsVehicleIdPref", 0);
        leditor = lpref.edit();
       /* SharedPreferences logPerf = getSharedPreferences(gV1.LOGIN_STATUS, Context.MODE_PRIVATE);

        dealerAddress = logPerf.getString("DealerAddress", "");
        userName = (TextView) hView.findViewById(R.id.txt_UserName);
        emailId = (TextView) hView.findViewById(R.id.txt_Mail);
        userName.setText(ProjectVariables.Dealer);
        emailId.setText(ProjectVariables.DealerAddress);*/

//        if (savedInstanceState == null) {
//            Fragment f = MainJobCardFragment.newInstance();
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.container, f);
//            ft.commit();
//        }
        if (savedInstanceState == null) {
            menuClick=false;
            Fragment f = JobCardReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }


        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = com.yalantis.contextmenu.lib.ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(MainActivity.this);
    }
*/

    private void Aleartdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("My Retail !");
        builder.setMessage("Database running out of space. Please Update  DataBase Space For Fixing the immediate issue.");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        //  alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide;
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AppUtil.displayExitDialog(MainActivity.this);
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu=menu;
//        newTv=(TextView) menu.findItem(R.id.datetv);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String selectedDate="";

        try {
            selectedDate = lpref.getString(KEY_SELECTED_DATE, null);
            Log.d("ANUSHA ","@@@@ selectedDate"+selectedDate);
            Log.d("ANUSHA ","@@@@ currentdate"+currentdate);
        }catch(Exception e){
            Log.d("ANUSHA ","@@@@@ EXCEPTION "+e.toString());
        }
        if(!menuCalenderSelectonDate)
            menu.findItem(R.id.datetv).setTitle(formattedDate);
        else
            menu.findItem(R.id.datetv).setTitle(currentdate);
        menu.findItem(R.id.datebtn).setIcon(R.drawable.calendarwhite24);
//        menu.findItem(R.id.alltv).setTitle("All");
//        menu.findItem(R.id.alltv).setVisible(true);
        menu.findItem(R.id.datetv).setVisible(true);
        menu.findItem(R.id.datebtn).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        TextView tv=item.getItemId(R.id.datetv);

        //noinspection SimplifiableIfStatement
        if (id == R.id.datebtn) {
            menuClick=false;
                    DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(MainActivity.this,menu,year, month, day,menu.findItem(R.id.datetv));
                    toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                        @Override
                        public void onDateCompleted(int year, int month, int day) {
                            MainActivity.this.year = year;
                            MainActivity.this.month = month;
                            MainActivity.this.day = day;
                        }
                    });
                    toDatePickerFragment.show(MainActivity.this.getSupportFragmentManager(), "0");

            return true;
        }/*else if (id == R.id.alltv) {
            menuClick=true;
            Fragment f = JobCardR
            eportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
            return true;
        }*/
        else if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_changepwd) {
            Fragment f = ChangePasswordFragment.newInstance();
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
            return true;
        } else if (id == R.id.action_printer) {
            Intent intent = new Intent(MainActivity.this, PrinterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_serviceassign) {
            Fragment f = UserWisePrevilegeFragment.newInstance();
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
            return true;
        }
       /* else if (id == R.id.action_refresh) {
            try {
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment f = MainJobCardFragment.newInstance();
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }
        if (id == R.id.nav_jobcarddetails) {
            menuClick=false;
            Fragment f = JobCardReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }
        if (id == R.id.nav_usermaster) {
            Fragment f = UserWisePrevilegeFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }
        /*if (id == R.id.nav_sparepartmaster) {

        }*/
        else if (id == R.id.nav_sparepartissue) {
            Fragment f = SparePartIssueFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.nav_sparepartagstjobcard) {
            Fragment f = SparePartEstAgstJobCard.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.nav_reports) {
            Fragment f = ReportsFrgament.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.nav_chnagepwd) {
            Fragment f = ChangePasswordFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } /*else if (id == R.id.nav_mrf) {
            Fragment f = HomeFragment.newInstance();
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }*/
       /* else if (id == R.id.nav_sparepartagstjobcard) {
            Fragment f = CustomerReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
            Intent Subscriber = new Intent(MainActivityDrawing.this, CustomerReportsActivity.class);
            Subscriber.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(Subscriber);

        } else if (id == R.id.navigation_item_orderDetails) {

            Fragment f = OrderDetailsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.navigation_item_deliverylist) {

            Fragment f = DeliverylistFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.navigation_item_pendinglist) {

            Fragment f = PendinglistFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.navigation_item_paymentsdetails) {

            Fragment f = PaymentsDetailsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }  else if (id == R.id.navigation_item_instantorderlist) {
            Fragment f = InstantOrderDetailsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        }
        else if (id == R.id.navigation_item_RouteIndentReports) {

            Fragment f = VanDeliveryDetailInseartFragments.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.navigation_item_vanDelivery_Reports) {

            Fragment f = VanDeliveryREportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.navigation_item_BlockedCancellationReports) {

            Fragment f = BlockedCancellationReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();


        } else if (id == R.id.navigation_item_Customer_recepit) {

            Fragment f = CustomerWiseBalanceandDueFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

            Fragment f = CustomerWiseBalanceFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.navigation_item_latest_news) {
            Fragment f = LatestNewsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.navigation_item_Complaint) {
            Fragment f = ComplaintFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.navigation_item_Customer_ReceiptSSS) {
            Fragment f = CustomerReceiptFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

            Fragment f = OrderCancelListFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();


        } else if (id == R.id.navigation_item_Reports) {


        } else if (id == R.id.nav_share) {

            Intent setting = new Intent(MainActivity.this, UsersProfileActivity.class);
            startActivity(setting);

        } else if (id == R.id.nav_aboutus) {
            Fragment f = AboutUsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.nav_send) {
            handelLogout();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* private void handelLogout() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        SharedPreferences logPerfs = getSharedPreferences(gV1.LOGIN_STATUS, MODE_PRIVATE);
        SharedPreferences.Editor edit1 = logPerfs.edit();
        edit1.putString(gV1.LSTATUS, gV1.LOGGED_OUT).commit();
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ANUSHA "," ONRESUME "+comingfrom);
        //comingfrom="MAINACTIVITY"
        if(comingfrom.equalsIgnoreCase("1") || comingfrom.equalsIgnoreCase("2") || comingfrom.equalsIgnoreCase("3")) {
            menuClick=false;
            Fragment f = JobCardReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
            comingfrom="MAINACTIVITY";
        }
    }
}
