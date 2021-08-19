package com.myaccounts.vechicleserviceapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.myaccounts.vechicleserviceapp.MainActivity;
import com.myaccounts.vechicleserviceapp.R;

public class GeneralMasterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IdRightArrow,IdLeftArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.generalmaster_layout);
        IdRightArrow=(ImageView)findViewById(R.id.IdRightArrow);
        IdLeftArrow=(ImageView)findViewById(R.id.IdLeftArrow);
        IdRightArrow.setOnClickListener(this);
        IdLeftArrow.setOnClickListener(this);
    }

/*
    @Override
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
            Intent intent=new Intent(GeneralMasterActivity.this, ServiceMasterActivity.class);
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
                    Intent intent=new Intent(GeneralMasterActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.IdRightArrow:
                    Intent intent1=new Intent(GeneralMasterActivity.this, ServiceMasterActivity.class);
                    startActivity(intent1);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
