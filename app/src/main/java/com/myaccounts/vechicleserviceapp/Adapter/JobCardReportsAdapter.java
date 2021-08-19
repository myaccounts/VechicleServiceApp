package com.myaccounts.vechicleserviceapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.myaccounts.vechicleserviceapp.Activity.CancelJobCardDetails;
import com.myaccounts.vechicleserviceapp.Activity.NewJobCardDetailsMain;
import com.myaccounts.vechicleserviceapp.Activity.PopupActivity;
import com.myaccounts.vechicleserviceapp.Activity.ServiceStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Activity.SpareStatusUpdateActivity;
import com.myaccounts.vechicleserviceapp.Fragments.DatabaseHelper1;
import com.myaccounts.vechicleserviceapp.Fragments.LatestNewServiceSelectedFragment;
import com.myaccounts.vechicleserviceapp.Fragments.NewMainVehicleDetailsFragment;
import com.myaccounts.vechicleserviceapp.Fragments.ServiceStatusFragment;
import com.myaccounts.vechicleserviceapp.Pojo.JobCardDetails;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;
import com.myaccounts.vechicleserviceapp.Utils.ProjectVariables;
import com.myaccounts.vechicleserviceapp.Utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class JobCardReportsAdapter extends RecyclerView.Adapter<JobCardReportsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String itemName, String screenName);
    }


    Context context;
    ArrayList<JobCardDetails> jobCardDetailsArrayList;
    private int layout_rowitems;
    private OnItemClickListener mItemClickListener;
    SessionManager sessionManager;
    private PopupWindow mPopupWindow;
    private LinearLayout lr;
    TextView cancelby,canceldate,cancelfrom,canceltime,cancelreasons;

    public JobCardReportsAdapter(ArrayList<JobCardDetails> items,OnItemClickListener listener){
        this.jobCardDetailsArrayList = items;
        this.mItemClickListener = listener;
    }

    public JobCardReportsAdapter(Context con, ArrayList<JobCardDetails> jobcardLis, int layout_rowi) {
        context = con;
        jobCardDetailsArrayList = jobcardLis;
        layout_rowitems = layout_rowi;
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View views;
        @BindView(R.id.IdSnotxt)
        TextView mSno;
        @BindView(R.id.IdjobCardNotxt)
        TextView mJobCardNo;
        @BindView(R.id.IdVehicleNotxt)
        TextView mVehicleNo;
        @BindView(R.id.IdNametxt)
        TextView mCustomerName;
        @BindView(R.id.IdMobileNotxt)
        TextView mCustomerMobNo;
        @BindView(R.id.IdStatustxt)
        ImageView mCustomerStatus;


        //@BindView(R.id.IdBlocktxt)
//        TextView IdBlocktxt;
        @BindView(R.id.JobCardRowLayout)
        LinearLayout JobCardRowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.views = itemView;
            lr = (LinearLayout) itemView.findViewById(R.id.lr);
            ButterKnife.bind(this, itemView);

           /* mJobCardNo.setSelected(true);
            mJobCardNo.setOnClickListener(this);*/
            //  JobCardRowLayout.setOnClickListener(this);
            sessionManager = new SessionManager(context);
        }
            public void bind(final JobCardDetails jobCardDetails,final OnItemClickListener mItemClickListener) {
            Log.e("ANUSHA ***","__"+jobCardDetails);

                //  final JobCardDetails customerList = jobCardDetailsArrayList.get(position);
//                mSno.setText(jobCardDetails.getSLNO());
//                mJobCardNo.setText(jobCardDetails.getMake());
                mJobCardNo.setText(jobCardDetails.getModel());
                mVehicleNo.setText(jobCardDetails.getVehicleNo());
                mCustomerName.setText(jobCardDetails.getCustomerName());
                mCustomerMobNo.setText(jobCardDetails.getMobileNo());
                //holder.mCustomerStatus.setText(customerList.getStatus());
//        holder.IdBlocktxt.setText(customerList.getBlock());

                Log.e("remarks", "remarks: " + jobCardDetails.getJobRemarks() );

                Log.e("JobCardAdapter", "name " + jobCardDetails.getCustomerName() + " status " + jobCardDetails.getStatus()  );

                /*Log.d("ANUSHA ","JOBCARDSTATUS "+ jobCardDetails.getStatus());
                Log.d("ANUSHA ","JOBCARDSTATUS "+ jobCardDetails.getCustomerName());*/
                if (jobCardDetails.getStatus().equalsIgnoreCase("Pending")) {
//                    mCustomerStatus.setText("Pending");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.red_color));
//                    mCustomerStatus.setTextColor(views.getResources().R.color.divider_color);
                    mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.pending));

                } else if(jobCardDetails.getStatus().equalsIgnoreCase("Ready")){
//                    mCustomerStatus.setText("Ready");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.yellow_color));
                    mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.mipmap.app_icon));

                }else if(jobCardDetails.getStatus().equalsIgnoreCase("Completed")){
//                    mCustomerStatus.setText("Completed");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.green_color));
                    mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.completed));

                }
                else if(jobCardDetails.getStatus().equalsIgnoreCase("Canceled")){
//                    mCustomerStatus.setText("Completed");
//                    mCustomerStatus.setTextColor(views.getResources().getColor(R.color.green_color));
                    mCustomerStatus.setBackgroundDrawable(views.getResources().getDrawable(R.drawable.cancelled));

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());


                        if (jobCardDetails.getStatus().equalsIgnoreCase("Completed"))
                        {
                            Toast.makeText(context,"Service Completed For This Vehicle...",Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            showPopupForOptions();
                        }
                    }

                    private void showPopupForOptions() {

                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                        // Inflate the custom layout/view
                        final View customView = inflater.inflate(R.layout.activity_popup,null);

                        mPopupWindow = new PopupWindow(
                                customView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                true);

                        if(Build.VERSION.SDK_INT>=21){
                            mPopupWindow.setElevation(5.0f);
                        }

                        final LinearLayout layout_reasons = customView.findViewById(R.id.layout_reasons);
                        layout_reasons.setVisibility(View.GONE);
                        final LinearLayout layout_options = customView.findViewById(R.id.layout_options);
                        layout_options.setVisibility(View.VISIBLE);
                        // Get a reference for the custom view close button
                        final TextView titleText=(TextView)customView.findViewById(R.id.title);
                        final LinearLayout remarks_layout=(LinearLayout) customView.findViewById(R.id.remarks_layout);

                        final TextView remarks_in_dialog=(TextView)customView.findViewById(R.id.remarks_in_dialog);
                        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                        final Button btn_edit_jobcard = (Button)customView.findViewById(R.id.btn_edit_jobcard);
                        final Button btn_edit_service_status = (Button)customView.findViewById(R.id.btn_edit_service_status);
                        final Button btn_edit_spare_status = (Button)customView.findViewById(R.id.btn_edit_spare_status);
                        final Button btn_cancle_jobCards = (Button)customView.findViewById(R.id.btn_cancel_jobcard);
                        final Button btn_cancellation_reason = (Button)customView.findViewById(R.id.btn_cancellation_reason);

                        cancelby = customView.findViewById(R.id.cancel_by);
                        canceldate = customView.findViewById(R.id.cancel_date);
                        cancelfrom = customView.findViewById(R.id.cancel_from);
                        canceltime = customView.findViewById(R.id.cancel_time);
                        cancelreasons = customView.findViewById(R.id.cancel_reasons);

                        if (jobCardDetails.getJobRemarks().equals("") || jobCardDetails.getJobRemarks() == null)
                        {
                            remarks_in_dialog.setVisibility(View.GONE);
                            remarks_layout.setVisibility(View.GONE);
                        }
                        else
                        {
                            remarks_in_dialog.setVisibility(View.VISIBLE);
                            remarks_layout.setVisibility(View.VISIBLE);
                            remarks_in_dialog.setText(jobCardDetails.getJobRemarks());
                        }

                        if(jobCardDetails.getStatus().equalsIgnoreCase("Pending"))
                        {
                            btn_cancle_jobCards.setVisibility(View.VISIBLE);
                        }
                        else {
                            btn_cancle_jobCards.setVisibility(View.GONE);
                        }

                        if (jobCardDetails.getStatus().equalsIgnoreCase("Canceled"))
                        {
                            btn_cancellation_reason.setVisibility(View.VISIBLE);
                        }
                        else {
                            btn_cancellation_reason.setVisibility(View.GONE);
                        }

                        // Set a click listener for the popup window close button
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Dismiss the popup window
                                mPopupWindow.dismiss();
                            }
                        });
                        /*mPopupWindow.setFocusable(false);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(false);*/
                        //mPopupWindow.setOutsideTouchable(false);
//                        mPopupWindow.setFocusable(true);
                        mPopupWindow.showAtLocation(lr, Gravity.CENTER,0,0);

                        btn_cancellation_reason.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                layout_options.setVisibility(View.GONE);
                                layout_reasons.setVisibility(View.VISIBLE);
                                requestCancellationReasonsApi(jobCardDetails.getJobCardNo());
                                Toast.makeText(context,"All Reasons are displayed here..", Toast.LENGTH_LONG).show();
                            }
                        });

                        btn_edit_jobcard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                Log.d("ANUSHA ","___3"+jobCardDetails.getJCDate()+" "+jobCardDetails.getJCTime());
                                Log.d("ANUSHA ","___3"+jobCardDetails.getVehicleNo()+" "+jobCardDetails.getJobCardNo());
                                Intent intent = new Intent(context, NewJobCardDetailsMain.class);
                                Log.d("ANUSHA ","___3"+jobCardDetails.getTechnicianName());
                                Log.d("ANUSHA ","___3"+jobCardDetails.getStatus());
                                /*String dbValue;
                                DatabaseHelper1 db=new DatabaseHelper1(customView.getContext());
                                dbValue=db.jgetValues(Technician);
                                Log.e("Executing block","JESUS"+dbValue);*/
                                sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                                intent.putExtra("jobcardNo", jobCardDetails.getJobCardNo());
                                intent.putExtra("technicianName", jobCardDetails.getTechnicianName());
                                context.startActivity(intent);
                            }
                        });

                        btn_edit_service_status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                Log.e("ANUSHA ***","__"+jobCardDetails);
                                Intent intent = new Intent (v.getContext(), ServiceStatusUpdateActivity.class);
                                sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                                v.getContext().startActivity(intent);

                            }
                        });

                        btn_edit_spare_status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                Intent intent = new Intent (v.getContext(), SpareStatusUpdateActivity.class);
                                sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(),jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                                v.getContext().startActivity(intent);

                            }
                        });//btn_cancel_jobcard

                        btn_cancle_jobCards.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                try {
                                    Intent intent = new Intent(context, CancelJobCardDetails.class);
                                    sessionManager.storeJobCardId(jobCardDetails.getJobCardNo(),jobCardDetails.getJCDate(),jobCardDetails.getJCTime(),jobCardDetails.getVehicleNo(), jobCardDetails.getMobileNo(),jobCardDetails.getCustomerName(),jobCardDetails.getTechnicianName());
                                    intent.putExtra("jobcardNo", jobCardDetails.getJobCardNo());
                                    context.startActivity(intent);
//                                    CustomDailog("Service Issue", "Do You Want to Cancle Jobcard?", 134, "Delete", position);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }

                    private void requestCancellationReasonsApi(String jobcardNo) {

                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("JobCardId",jobcardNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("jobcard_reports_adapter", "requestCancellationReasonsApi: " + jsonObject );

                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ProjectVariables.BASE_URL+ProjectVariables.GetCancelJobCardDetails, jsonObject, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray jsonArray) {

                                for (int i = 0 ; i < jsonArray.length() ; i++)
                                {
                                    try {

                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        Log.e("jobcard_reports_adapter", "obj: " + jsonObject1 );

                                        String reasons = jsonObject1.getString("ReasonforCancel");

                                        List<String> items = Arrays.asList(reasons.split("\\s*,\\s*"));

                                        Log.e("jobcard_reports_adapter", "obj: " + items.toString() );

                                        cancelby.setText(jsonObject1.getString("CancelBy"));
                                        canceldate.setText(jsonObject1.getString("CancelDate"));
                                        cancelfrom.setText(jsonObject1.getString("CancelFrom"));
                                        canceltime.setText(jsonObject1.getString("CancelTime"));

                                        String stringnewline = jsonObject1.getString("ReasonforCancel");
                                        stringnewline=stringnewline.replaceAll(", ", "\n\n");
                                        cancelreasons.setText("-> "+stringnewline);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        });

                        RequestQueue requestQueue= Volley.newRequestQueue(context);
                        requestQueue.add(jsonArrayRequest);

                    }
                });

            }


        @Override
        public void onClick(View v) {
            try {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition(), jobCardDetailsArrayList.get(getPosition()).getJobCardNo(), jobCardDetailsArrayList.get(getPosition()).getScreenName());

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }



    }

    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
        Log.d("clikedd", "clciked");
    }

    /*Action Bar Using SearchView Functionly*/
    public void setFilter(List<JobCardDetails> taskList) {
        jobCardDetailsArrayList = new ArrayList<>();
        jobCardDetailsArrayList.addAll(taskList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(layout_rowitems, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mSno.setText(String.valueOf(position+1));

        holder.bind(jobCardDetailsArrayList.get(position),mItemClickListener );

       /* holder.mJobCardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });
*/

      /*  holder.mVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });


        holder.mCustomerMobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewJobCardDetailsMain.class);

                sessionManager.storeJobCardId(customerList.getJobCardNo(),customerList.getVehicleNo(), customerList.getMobileNo(),customerList.getCustomerName());
                intent.putExtra("jobcardNo", customerList.getJobCardNo());
                context.startActivity(intent);
            }
        });


        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("2clikedd", "2clciked");

            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return jobCardDetailsArrayList == null ? 0 : jobCardDetailsArrayList.size();
    }
    /*private void CustomDailog(String Title, String msg, int value, String btntxt, int position) {
        Log.d("ANUSHA ","POSITION"+position);
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(context, CustomDialogClass.class);
            intent.putExtra("msgbtn", btntxt);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



}
