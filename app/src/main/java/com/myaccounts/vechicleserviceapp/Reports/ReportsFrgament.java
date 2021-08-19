package com.myaccounts.vechicleserviceapp.Reports;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReportsFrgament extends Fragment {
    private View view;
    private Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static ReportsFrgament newInstance() {
        Bundle args = new Bundle();
        ReportsFrgament fragment = new ReportsFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentsreports_layout, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Reports</font>"));
        context = getActivity();
        intialVariables();
        return view;
    }

    private void intialVariables() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new JobCardHistoryReport(), "Job Card History");
        adapter.addFragment(new VehicleHistoryReport(), "Vehicle History");
        /*adapter.addFragment(new SparePartConsumptionReport(), "Spare Part Legder");
        adapter.addFragment(new EstimationReport(), "Estimation Report");
        adapter.addFragment(new SparePartIssueReport(), "Spare Part Issue");
        adapter.addFragment(new TATReport(), "TAT Report");*/
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    JobCardHistoryReport Summary = new JobCardHistoryReport();
                    return Summary;
                case 1:
                    VehicleHistoryReport Details = new VehicleHistoryReport();
                    return Details;
                case 2:
                    SparePartConsumptionReport consumptionReport = new SparePartConsumptionReport();
                    return consumptionReport;
                case 3:
                    EstimationReport estimationReport = new EstimationReport();
                    return estimationReport;
                case 4:
                    SparePartIssueReport issueReport = new SparePartIssueReport();
                    return issueReport;
                case 5:
                    TATReport tatReport = new TATReport();
                    return tatReport;

            }
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
}
