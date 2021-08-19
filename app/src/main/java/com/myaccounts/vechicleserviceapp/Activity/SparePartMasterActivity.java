package com.myaccounts.vechicleserviceapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.CustomDialogClass;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.MyMessageObject;

public class SparePartMasterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IdRightArrow, IdLeftArrow;
    private EditText sparePartNameEdt, sparePartCodeEdt, sparePartBrand, sparePartUomEdt, sparePartNoEdt, sparePartRateEdt, sparePartWspEdt, sparePartmrpEdt, sparePartDescEdt, sparePartRemarksEdt;
    private Button mAddBtn, mClearBtn;
    private String mSparePartName,mSparePartCode,mSparePartBrand,mSparePartUom,mSparePartNo,mSparePartRate,mSparePartWSP,mSparePartMRP,mSparePartDesc,mSparePartRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sparepartmaster_layout);
  /*      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        initializeVariables();
        IdRightArrow = (ImageView) findViewById(R.id.IdRightArrow);
        IdLeftArrow = (ImageView) findViewById(R.id.IdLeftArrow);
        IdRightArrow.setOnClickListener(this);
        IdLeftArrow.setOnClickListener(this);
    }

    private void initializeVariables() {
        try {
            sparePartNameEdt = (EditText) findViewById(R.id.sparePartNameEdt);
            sparePartCodeEdt = (EditText) findViewById(R.id.sparePartCodeEdt);
            sparePartBrand = (EditText) findViewById(R.id.sparePartBrand);
            sparePartUomEdt = (EditText) findViewById(R.id.sparePartUomEdt);
            sparePartNoEdt = (EditText) findViewById(R.id.sparePartNoEdt);
            sparePartRateEdt = (EditText) findViewById(R.id.sparePartRateEdt);
            sparePartWspEdt = (EditText) findViewById(R.id.sparePartWspEdt);
            sparePartmrpEdt = (EditText) findViewById(R.id.sparePartmrpEdt);
            sparePartDescEdt = (EditText) findViewById(R.id.sparePartDescEdt);
            sparePartRemarksEdt = (EditText) findViewById(R.id.sparePartRemarksEdt);
            mAddBtn = (Button) findViewById(R.id.mAddBtn);
            mClearBtn = (Button) findViewById(R.id.mClearBtn);
            mAddBtn.setOnClickListener(this);
            mClearBtn.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent=new Intent(SparePartMasterActivity.this, VechicleMasterActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.IdLeftArrow:
                    Intent intent = new Intent(SparePartMasterActivity.this, VechicleMasterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.IdRightArrow:
                    Intent intent1 = new Intent(SparePartMasterActivity.this, SparePartReceiptActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.mClearBtn:
                    try {
                        CustomDailog("Spare Parts", "Do You Want to Clear Data?", 33, "Clear");


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                case R.id.mAddBtn:
                    try {
                        try {
                            if (Validation()) {
                                CustomDailog("Spare Parts", "Do You Want to Add the Data?", 35, "Add");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validation() {
        boolean result = true;
        mSparePartName = sparePartNameEdt.getText().toString().trim();
        mSparePartCode = sparePartCodeEdt.getText().toString().trim();
        mSparePartBrand = sparePartBrand.getText().toString().trim();
        mSparePartUom = sparePartUomEdt.getText().toString().trim();
        mSparePartNo = sparePartNoEdt.getText().toString().trim();
        mSparePartRate = sparePartRateEdt.getText().toString().trim();
        mSparePartWSP = sparePartWspEdt.getText().toString().trim();
        mSparePartMRP = sparePartmrpEdt.getText().toString().trim();
        mSparePartDesc = sparePartDescEdt.getText().toString().trim();

        if (mSparePartName.length() == 0) {
            sparePartNameEdt.requestFocus();
            sparePartNameEdt.setError("Please Enter SparePartName");
            result = false;
        } else if (mSparePartCode.length() == 0) {
            sparePartCodeEdt.requestFocus();
            sparePartCodeEdt.setError("Please Enter Code");
            result = false;
        } else if (mSparePartBrand.length() == 0) {
            sparePartBrand.requestFocus();
            sparePartBrand.setError("Please Enter Brand");
            result = false;

        } else if (mSparePartUom.length() == 0) {
            sparePartUomEdt.requestFocus();
            sparePartUomEdt.setError("Please Enter UOM");
            result = false;

        } else if (mSparePartNo.length() == 0 || mSparePartNo.toString().equalsIgnoreCase("")) {
            sparePartNoEdt.requestFocus();
            sparePartNoEdt.setError("Please Enter PartNo");
            result = false;

        } else if (mSparePartRate.length() == 0) {
            sparePartRateEdt.requestFocus();
            sparePartRateEdt.setError("Please Enter RATE");
            result = false;

        } else if (mSparePartWSP.length() == 0) {
            sparePartWspEdt.requestFocus();
            sparePartWspEdt.setError("Please Enter WSP");
            result = false;

        } else if (mSparePartMRP.toString().length() == 0 || mSparePartMRP.toString().equalsIgnoreCase("")) {

            sparePartmrpEdt.requestFocus();
            sparePartmrpEdt.setError("Please Enter MRP");
            result = false;
        } else if (mSparePartDesc.toString().length() == 0 || mSparePartDesc.toString().equalsIgnoreCase("")) {

            sparePartDescEdt.requestFocus();
            sparePartDescEdt.setError("Please Enter Description");
            result = false;
        } else if (mSparePartRemarks.toString().length() == 0 || mSparePartRemarks.toString().equalsIgnoreCase("")) {

            sparePartRemarksEdt.requestFocus();
            sparePartRemarksEdt.setError("Please Enter Remarks");
            result = false;
        }
        return result;
    }

    private void CustomDailog(String Title, String msg, int value, String btnValue) {
        try {
            MyMessageObject.setMyTitle(Title);
            MyMessageObject.setMyMessage(msg);
            MyMessageObject.setMessageType(Enums.MyMesageType.YesNo);
            Intent intent = new Intent(SparePartMasterActivity.this, CustomDialogClass.class);
            intent.putExtra("msgbtn", btnValue);
            startActivityForResult(intent, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 33 && data != null && resultCode == RESULT_OK) {
                ClearData();
            } else if (requestCode == 35 && data != null && resultCode == RESULT_OK) {
                SaveDataToServerDb();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveDataToServerDb() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ClearData() {
        try {
            sparePartNameEdt.setText("");
            sparePartCodeEdt.setText("");
            sparePartBrand.setText("");
            sparePartUomEdt.setText("");
            sparePartNoEdt.setText("");
            sparePartRateEdt.setText("");
            sparePartWspEdt.setText("");
            sparePartmrpEdt.setText("");
            sparePartDescEdt.setText("");
            sparePartRemarksEdt.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
