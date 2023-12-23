package com.myaccounts.vechicleserviceapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.myaccounts.vechicleserviceapp.R;

public class ServiceMasterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IdRightArrow,IdLeftArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.vechiclemaster_layout);
        IdRightArrow=(ImageView)findViewById(R.id.IdRightArrow);
        IdLeftArrow=(ImageView)findViewById(R.id.IdLeftArrow);
        IdRightArrow.setOnClickListener(this);
        IdLeftArrow.setOnClickListener(this);
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
            Intent intent=new Intent(ServiceMasterActivity.this, VechicleMasterActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId())
            {
                case R.id.IdLeftArrow:
                    Intent intent=new Intent(ServiceMasterActivity.this, GeneralMasterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.IdRightArrow:
                    Intent intent1=new Intent(ServiceMasterActivity.this, VechicleMasterActivity.class);
                    startActivity(intent1);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
