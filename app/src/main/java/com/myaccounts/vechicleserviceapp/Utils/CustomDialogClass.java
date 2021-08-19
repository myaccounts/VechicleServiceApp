package com.myaccounts.vechicleserviceapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.Fragments.NewCaptureImageAndSignFragment;
import com.myaccounts.vechicleserviceapp.R;


public class CustomDialogClass extends Activity {
    private String MyTitle = MyMessageObject.getMyTitle();
    private  String MyMessage = MyMessageObject.getMyMessage();
    private Enums.MyMesageType IsYesOrNo = MyMessageObject.getMessageType();
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_dialog_layout);
            this.setFinishOnTouchOutside(false);
            String msgbtn = getIntent().getStringExtra("msgbtn");
            TextView header_tv = (TextView) findViewById(R.id.header_tv);
            TextView body_tv = (TextView) findViewById(R.id.body_tv);
            Button saveButton = (Button) findViewById(R.id.btn_yes);
            Button cancelButton = (Button) findViewById(R.id.btn_no);
            saveButton.setText(msgbtn);
            if (IsYesOrNo == Enums.MyMesageType.Ok) {
                saveButton.setText("Ok");
                cancelButton.setVisibility(View.GONE);
            }
            header_tv.setText(MyTitle);
            body_tv.setText(MyMessage);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_CANCELED, intent);
                    NewCaptureImageAndSignFragment.saveClick=false;
                    if(NewCaptureImageAndSignFragment.IdSaveBtn!=null)
                    NewCaptureImageAndSignFragment.IdSaveBtn.setEnabled(true);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
