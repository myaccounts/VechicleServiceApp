package com.myaccounts.vechicleserviceapp.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.myaccounts.vechicleserviceapp.Fragments.JobCardReportsFragment;
import com.myaccounts.vechicleserviceapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NEWSYSTEM1 on 5/20/2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static int year, month, day;
//    private static TextView textView;
    private static Menu menu;
    private static MenuItem menuItem;
    static SessionManager sessionManager;
    public static boolean menuCalenderSelectonDate;
    private OnDateSetCompleted onDateSetCompleted;
    public static String currentdate;
    private static Context view;

    public static DatePickerFragment newInstance(Context view,Menu menu,int year, int month, int day, MenuItem textView) {
        DatePickerFragment pickerFragment = new DatePickerFragment();
        sessionManager= new SessionManager(view);
        // Supply num input as an argument.
        DatePickerFragment.view=view;
        DatePickerFragment.year = year;
        DatePickerFragment.month = month;
        DatePickerFragment.day = day;
        DatePickerFragment.menu=menu;
        DatePickerFragment.menuItem=textView;

        return pickerFragment;
    }
    public static DatePickerFragment newInstance(int year, int month, int day) {
        DatePickerFragment pickerFragment = new DatePickerFragment();

        // Supply num input as an argument.
        DatePickerFragment.year = year;
        DatePickerFragment.month = month;
        DatePickerFragment.day = day;
        DatePickerFragment.menuItem = menuItem;

        return pickerFragment;
    }
    public void setOnDateSetCompleted(OnDateSetCompleted onDateSetCompleted) {
        this.onDateSetCompleted = onDateSetCompleted;
//        JobCardHistoryReport
        /*Fragment f = JobCardReportsFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, f);
        ft.commit();*/
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
/*remove this part after*/
        /*final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);*/
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), this, year,month,day);

        //following line to restrict future date selection
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        /*open this part*/
        // DateUtil util = DateUtil.newInstance();
        //  String monthName = util.getMonthName(month);

        // String date = day + "-" + month + "-" + year;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        currentdate = ss.format(c.getTime());
        //currentdate = day + "-" + month + "-" + year;
        menuItem.setTitle(currentdate);

        sessionManager.storeSelectedDate(currentdate);
        onDateSetCompleted.onDateCompleted(year, month, day);
        menuCalenderSelectonDate=true;
        Fragment f = JobCardReportsFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, f);
        ft.commit();

    }


}