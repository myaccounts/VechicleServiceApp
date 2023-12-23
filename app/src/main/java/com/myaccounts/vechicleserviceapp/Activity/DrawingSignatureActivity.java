package com.myaccounts.vechicleserviceapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.DrawPadView;

import static com.myaccounts.vechicleserviceapp.Utils.SaveViewUtil.saveScreen;

public class DrawingSignatureActivity extends Activity implements View.OnClickListener {

    private DrawPadView hbView;
    private AlertDialog dialog;
    private View dialogView;
    private TextView shouWidth;
    private SeekBar widthSb;
    private int paintWidth;
    private Button savebtn, clear, cancelbtn;
    private int STORAGE_PERMISSION_CODE = 23;
    private String Value = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
      /*  if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Intent intent = getIntent();
        // intent.getExtra("yourData")

        Value = intent.getStringExtra("JobCardDetailsSignature");
        initView();
        requestStoragePermission();
    }

    private void initView() {
        try {
            dialogView = getLayoutInflater().inflate(R.layout.dialog_width_set, null);
            shouWidth = (TextView) dialogView.findViewById(R.id.textView1);
            widthSb = (SeekBar) dialogView.findViewById(R.id.seekBar1);
            savebtn = (Button) findViewById(R.id.savebtn);
            clear = (Button) findViewById(R.id.clearbtn);
            cancelbtn = (Button) findViewById(R.id.cancelbtn);
            savebtn.setOnClickListener(DrawingSignatureActivity.this);
            clear.setOnClickListener(DrawingSignatureActivity.this);
            cancelbtn.setOnClickListener(DrawingSignatureActivity.this);
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isReadStorageAllowed()) {
                        if (saveScreen(hbView, DrawingSignatureActivity.this)) {
                            Toast.makeText(DrawingSignatureActivity.this, "Save drawing succeed!", Toast.LENGTH_SHORT).show();
//                            finish();
                            Intent itemIntent = new Intent();
                            setResult(200, itemIntent);
                            finish();
                        } else {
                            Toast.makeText(DrawingSignatureActivity.this, "Save drawing fail. Please check your SD card", Toast.LENGTH_SHORT).show();
                        }
                    }
                    requestStoragePermission();
                }
            });
            widthSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    shouWidth.setText("Current Width：" + (progress + 1));
                    paintWidth = progress + 1;
                }
            });
            hbView = (DrawPadView) findViewById(R.id.drawPadView1);
            dialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Set the size of your Pen").
                    setView(dialogView).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (hbView != null) {
                        hbView.setPaintWidth(paintWidth);

                    } else {
                        Toast.makeText(DrawingSignatureActivity.this, "Plz draw the image", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("Cancel", null).create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Select Pen Color*/
        //getMenuInflater().inflate(R.menu.main, menu);
        SubMenu colorSm = menu.addSubMenu(1, 1, 1, "Select Pen color");

        colorSm.add(2, 200, 200, "red");
        colorSm.add(2, 210, 210, "green");
        colorSm.add(2, 220, 220, "blue");
        colorSm.add(2, 230, 230, "purple");
        colorSm.add(2, 240, 240, "yellow");
        colorSm.add(2, 250, 250, "black");
        /* Set pen Size*/
        menu.add(1, 2, 2, "Set pen size");
        /*Set pen Style*/
        SubMenu widthSm = menu.addSubMenu(1, 3, 3, "Set Pen style");
        widthSm.add(3, 300, 300, "Stoke");
        widthSm.add(3, 301, 301, "Fill ");
        /*Clear Drawing*/
        menu.add(1, 4, 4, "Clear Drawing");
        /*Exit*/
        menu.add(1, 5, 5, "Exit");
        //menu.add(1, 5, 5, "Save drawing");
        // menu.add(1, 6, 6, "Exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = item.getItemId();
        if (index == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
     /*   if (index == R.id.action_upload) {
            if (hbView != null) {
                if (isReadStorageAllowed()) {
                    if (saveScreen(hbView, DrawingSignatureActivity.this)) {
                        Toast.makeText(this, "Save drawing succeed!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Save drawing fail. Please check your SD card", Toast.LENGTH_SHORT).show();
                    }
                }
                requestStoragePermission();
            } else {

            }
            hbView.clearScreen();
            return true;
        }*/
        switch (index) {
            case 200:
                hbView.setColor(Color.RED);
                break;
            case 210:
                hbView.setColor(Color.GREEN);
                break;
            case 220:
                hbView.setColor(Color.BLUE);
                break;
            case 230:
                hbView.setColor(Color.MAGENTA);
                break;
            case 240:
                hbView.setColor(Color.YELLOW);
                break;
            case 250:
                hbView.setColor(Color.BLACK);
                break;
            case 2:
                dialog.show();
                break;
            case 300:
                hbView.setStyle(DrawPadView.PEN);
                break;
            case 301:
                hbView.setStyle(DrawPadView.PAIL);
                break;
            case 4:
                hbView.clearScreen();
                break;
            case 5:
                finish();
                break;
//            case 6:
//
//                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.savebtn) {
            if (hbView != null) {
                if (isReadStorageAllowed()) {
                    if (Value == null) {
                        Value = "";
                    }

                    if (saveScreen(hbView, DrawingSignatureActivity.this)) {
                        Toast.makeText(this, "Save drawing succeed!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Save drawing fail. Please check your SD card", Toast.LENGTH_SHORT).show();
                    }
                }
                requestStoragePermission();
            }


        } else if (v.getId() == R.id.clearbtn) {
            hbView.clearScreen();


        } else if (v.getId() == R.id.cancelbtn) {
            finish();

        }
        hbView.clearScreen();
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }

}
