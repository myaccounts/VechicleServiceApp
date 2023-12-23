package com.myaccounts.vechicleserviceapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.Log;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.myaccounts.vechicleserviceapp.Printernew.DeviceListActivity;
import com.myaccounts.vechicleserviceapp.Printernew.DiscoveryActivity;
import com.myaccounts.vechicleserviceapp.Printernew.InitializePrinter;
import com.myaccounts.vechicleserviceapp.Printernew.ShowMsg;
import com.myaccounts.vechicleserviceapp.Printernew.SpnModelsItem;
import com.myaccounts.vechicleserviceapp.R;
import com.myaccounts.vechicleserviceapp.Utils.Enums;
import com.myaccounts.vechicleserviceapp.Utils.FileUtils;
import com.myaccounts.vechicleserviceapp.Utils.Global;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;
import com.myaccounts.vechicleserviceapp.network.DatabaseHelper;
import com.myaccounts.vechicleserviceapp.scantechPrinter.WorkService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.EPSON;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.EPSON_M30;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.MAESTROS;

public class PrinterActivity extends Activity implements View.OnClickListener, ReceiveListener {
    private ArrayList<String> printerList;
    private Button btnEpsonM30 = null;
    private Button buttonMaestros = null;
    private Button btnScantech = null;
    private Button btnSunmi29 = null;
    private Button btnSunmi43 = null;
    private Context mContext = null;
    private EditText mEditTarget = null;
    private Spinner mSpnSeries = null;
    private Spinner mSpnLang = null;
    private Printer mPrinter = null;
    private static SharedPreferences printerSharedpreferences;
    public static final String PRINTER_PREFERENCES = "Printer Preferences";
    private BluetoothAdapter BA;
    private static Handler mHandler = null;
    WorkService service = new WorkService();
    private EditText edtSampletxt;
    private Button printbtn;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.printerlayout);
            mContext = this;
            checkLocationPermission();
            ScanTechPrinterConfig();
            btnEpsonM30 = (Button) findViewById(R.id.btnEpsonM30);
            buttonMaestros = (Button) findViewById(R.id.btnMaestros);
            btnScantech = (Button) findViewById(R.id.btnScantech);
            btnSunmi29 = (Button) findViewById(R.id.btnSunmi29);
            btnSunmi43 = (Button) findViewById(R.id.btnSunmi43);
            // printbtn = (Button) findViewById(R.id.printbtn);
            // edtSampletxt = (EditText) findViewById(R.id.edtSampletxt);
            btnEpsonM30.setOnClickListener(this);
            buttonMaestros.setOnClickListener(this);
            btnScantech.setOnClickListener(this);
            btnSunmi29.setOnClickListener(this);
            btnSunmi43.setOnClickListener(this);

           /* if (MyServerDetails.getPrinterType(PrinterActivity.this).equalsIgnoreCase(String.valueOf(Enums.PrinterMode.LAN))) {
                ProjectVariables.set_PrinterMode("0");
            } else {
                ProjectVariables.set_PrinterMode("1");

            }*/
            BA = BluetoothAdapter.getDefaultAdapter();
            if (!BA.isEnabled()) {
                Intent turnon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnon, 0);
            }
            printerSharedpreferences = mContext.getSharedPreferences(PRINTER_PREFERENCES, Context.MODE_PRIVATE);
            int[] target = {
                    R.id.btnDiscovery,
                    R.id.btnSampleReceipt,
                    R.id.btnSampleCoupon
            };

            for (int i = 0; i < target.length; i++) {
                Button button = (Button) findViewById(target[i]);
                button.setOnClickListener(this);
            }


            mSpnSeries = (Spinner) findViewById(R.id.spnModel);
            ArrayAdapter<SpnModelsItem> seriesAdapter = new ArrayAdapter<SpnModelsItem>(this, android.R.layout.simple_spinner_item);
            seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m10), Printer.TM_M10));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m30), Printer.TM_M30));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p20), Printer.TM_P20));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60), Printer.TM_P60));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p80), Printer.TM_P80));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t20), Printer.TM_T20));
            // seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t60), Printer.TM_T60));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t70), Printer.TM_T70));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t81), Printer.TM_T81));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t82), Printer.TM_T82));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t83), Printer.TM_T83));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t88), Printer.TM_T88));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90), Printer.TM_T90));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u220), Printer.TM_U220));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u330), Printer.TM_U330));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_l90), Printer.TM_L90));
            seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_h6000), Printer.TM_H6000));
            mSpnSeries.setAdapter(seriesAdapter);
            mSpnSeries.setSelection(0);

            mSpnLang = (Spinner) findViewById(R.id.spnLang);
            ArrayAdapter<SpnModelsItem> langAdapter = new ArrayAdapter<SpnModelsItem>(this, android.R.layout.simple_spinner_item);
            langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_ank), Printer.MODEL_ANK));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_thai), Printer.MODEL_THAI));
            langAdapter.add(new SpnModelsItem(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
            mSpnLang.setAdapter(langAdapter);
            mSpnLang.setSelection(0);

            try {
                Log.setLogSettings(mContext, Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
            } catch (Exception e) {
                ShowMsg.showException(e, "setLogSettings", mContext);
            }
            mEditTarget = (EditText) findViewById(R.id.edtTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        try {
            if (data != null) {
                ProjectMethods.set_BillPrinterIP(data.getStringExtra(getString(R.string.title_target)));
                ProjectMethods.SetPrinterIP(mContext, data.getStringExtra(getString(R.string.title_target)));
                SharedPreferences.Editor edit1 = printerSharedpreferences.edit();
                edit1.putString(InitializePrinter.PRINT_IP, ProjectMethods.get_BillPrinterIP());
                edit1.commit();
                String printerName=printerSharedpreferences.getString(InitializePrinter.PRINT_NAME,"");
                DatabaseHelper db = new DatabaseHelper(this);
                db.insert_PrinterDetails(ProjectMethods.get_BillPrinterIP(),printerName);

                /*Cursor cursor = db.getPrinterDetails("");
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        String Permission = cursor.getString(cursor.getColumnIndex(InfDbSpecs.PRINTERIP));
                    }
                }*/
//                db.getPrinterDetails("");
//                String TransactionNo="";
//                Print_BillFormat(TransactionNo);
                if (requestCode == 5) {
                    String target = data.getStringExtra(getString(R.string.title_target));
                    if (target != null) {

                        SharedPreferences.Editor edit = printerSharedpreferences.edit();
                        edit.putString(InitializePrinter.TARGET, target);
                        edit.commit();

                        ProjectMethods.SetPrinterIP(mContext, target);
                        EditText mEdtTarget = (EditText) findViewById(R.id.edtTarget);
                        mEdtTarget.setText(target);
                        finish();
                    }
                } else if (requestCode == 10) {
                    ProjectMethods.set_BillPrinterIP(data.getStringExtra("Device"));
                    ProjectMethods.SetPrinterIP(mContext, data.getStringExtra("Device"));
                    finish();
                } else if (requestCode == 15) {
                    if (data != null && requestCode == 15 && resultCode == RESULT_OK) {
                        if (data.getStringExtra("Sucess").equalsIgnoreCase(Global.toast_success)) {
                            finish();
                        } else {
                            finish();
                        }

                    }
                } else {
                    if (data != null && requestCode == 16 && resultCode == RESULT_OK) {
                        if (data.getStringExtra("Sucess").equalsIgnoreCase(Global.toast_success)) {
                            finish();
                        } else {
                            finish();
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Print_BillFormat(String transactionNo) {
            try {
                PreparePrintList();
                PrintEpson_fM30_Printer();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void PrintEpson_fM30_Printer() {
        try {
            android.util.Log.v("Result3", "Now Trying to connect");
//            if (!Connect_Epson_M_30_Printer()) {
//                finalizeObject();
//                return;
//            }
            mPrinter = new Printer(Printer.TM_M30, 0, this);
//            mPrinter.connect("BT:00:01:90:84:FD:9", Printer.PARAM_DEFAULT);
//            mPrinter.connect("BT:00:01:90:77:25:00", Printer.PARAM_DEFAULT);
            mPrinter.connect(ProjectMethods.get_BillPrinterIP(), Printer.PARAM_DEFAULT);
            mPrinter.clearCommandBuffer();
            mPrinter.setReceiveEventListener(null);

            for (int PrintLine = 0; PrintLine < printerList.size(); PrintLine++) {
                String strPrintLine = printerList.get(PrintLine);
                if (strPrintLine.equalsIgnoreCase("CompName")) {
                    Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.myaccounts600x135);
                    mPrinter.addImage(logoData, 0, 0,
                            logoData.getWidth(),
                            logoData.getHeight(),
                            Printer.COLOR_1,
                            Printer.MODE_MONO,
                            Printer.HALFTONE_DITHER,
                            Printer.PARAM_DEFAULT,
                            Printer.COMPRESS_AUTO);
                    PrintLine++;
                    PrintLine++;
                }
                mPrinter.addText(strPrintLine);
                mPrinter.addFeedLine(1);
            }
            mPrinter.addCut(Printer.CUT_FEED);
            mPrinter.sendData(Printer.PARAM_DEFAULT);
            android.util.Log.v("Result3", "msg" + Printer.PARAM_DEFAULT);

            After_Epson_M_30_Print();
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.v("PrintEpson_fM30_Printer", "QuickBilling");
        }
    }

    private boolean After_Epson_M_30_Print() {
        boolean Result = false;
        try {
            android.util.Log.v("Result3", "end connection");
            boolean isBeginTransaction = false;
            try {
                mPrinter.beginTransaction();
                isBeginTransaction = true;
            } catch (Exception e) {
                e.printStackTrace();
                android.util.Log.v("After_Epson_M_30_Print", "PrinterTransaction");
            }
            if (!isBeginTransaction) {
                try {
                    mPrinter.disconnect();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mPrinter.endTransaction();
            mPrinter.disconnect();

            PrinterStatusInfo status = mPrinter.getStatus();
            /*if (!isPrintable(status)) {
                ShowMsg.showMsg(makeErrorMessage(status), getContext());
                try {
                    mPrinter.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }*/

            try {
                mPrinter.sendData(Printer.PARAM_DEFAULT);
            } catch (Exception e) {
                ShowMsg.showException(e, "sendData", this);
                try {
                    mPrinter.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.v("After_Epson_M_30_Print", "");
            return false;
        }
        return true;
    }

    private void PreparePrintList() {
        printerList = new ArrayList<String>();
        int NoOfCols = 48;
        String BillPrintType = ProjectMethods.getBillPrinterType().toString();
        printerList.add(" PRINTNAME ");
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnDiscovery:
                try {
                    ProjectMethods.setBillPrinterType(EPSON);
                    ProjectMethods.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    SharedPreferences.Editor edit1 = printerSharedpreferences.edit();
                    edit1.putString(InitializePrinter.PRINT_NAME,Enums.Printers.EPSON.toString());
                    edit1.commit();
                    intent = new Intent(this, DiscoveryActivity.class);
                    startActivityForResult(intent, 5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnEpsonM30:
                try {
                    ProjectMethods.setBillPrinterType(EPSON_M30);
                    ProjectMethods.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    SharedPreferences.Editor edit1 = printerSharedpreferences.edit();
                    edit1.putString(InitializePrinter.PRINT_NAME, EPSON_M30.toString());
                    edit1.commit();
                    intent = new Intent(this, DiscoveryActivity.class);
                    startActivityForResult(intent, 5);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnMaestros:
                try {
                    ProjectMethods.setBillPrinterType(MAESTROS);
                    ProjectMethods.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    intent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(intent, 10);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

          /*  case R.id.btnScantech:
                try {
                    ProjectMethods.setBillPrinterType(SCANTECH);
                    MyServerDetails.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    intent = new Intent(this, ConnectBTPairedActivity.class);
                    startActivityForResult(intent, 15);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnSunmi29:
                try {
                    ProjectMethods.setBillPrinterType(SUNMI_28_COLUMN);
                    MyServerDetails.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    intent = new Intent(this, ConnectBTPairedActivity.class);
                    startActivityForResult(intent, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnSunmi43:
                try {
                    ProjectMethods.setBillPrinterType(SUNMI_42_COLUMN);
                    MyServerDetails.SetPrinterType(mContext, ProjectMethods.getBillPrinterType().toString());
                    intent = new Intent(this, ConnectBTPairedActivity.class);
                    startActivityForResult(intent, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;*/

            case R.id.btnSampleReceipt:
                updateButtonState(false);
                if (!runPrintReceiptSequence()) {
                    updateButtonState(true);
                }
                break;

            case R.id.btnSampleCoupon:
                updateButtonState(false);
                if (!runPrintCouponSequence()) {
                    updateButtonState(true);
                }
                break;

            default:
                // Do nothing
                break;
        }
    }

    private boolean runPrintReceiptSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createReceiptData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createReceiptData() {
        String method = "";
        Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.visiting);
        StringBuilder textData = new StringBuilder();
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

            method = "addImage";
            mPrinter.addImage(logoData, 0, 0,
                    logoData.getWidth(),
                    logoData.getHeight(),
                    Printer.COLOR_1,
                    Printer.MODE_MONO,
                    Printer.HALFTONE_DITHER,
                    Printer.PARAM_DEFAULT,
                    Printer.COMPRESS_AUTO);

            method = "addFeedLine";
            mPrinter.addFeedLine(1);
            textData.append("THE STORE 123 (555) 555 – 5555\n");
            textData.append("STORE DIRECTOR – John Smith\n");
            textData.append("\n");
            textData.append("7/01/07 16:58 6153 05 0191 134\n");
            textData.append("ST# 21 OP# 001 TE# 01 TR# 747\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("400 OHEIDA 3PK SPRINGF  777779.99 R\n");
            textData.append("410 3 CUP BLK TEAPOT    999.99 R\n");
            textData.append("445 EMERIL GRIDDLE/PAN 88817.99 R\n");
            textData.append("438 CANDYMAKER ASSORT   14.99 R\n");
            textData.append("474 TRIPOD              18.99 R\n");
            textData.append("433 BLK LOGO PRNTED ZO  111111117.99 R\n");
            textData.append("458 AQUA MICROTERRY SC  6.99 R\n");
            textData.append("493 30L BLK FF DRESS      16.99 R\n");
            textData.append("407 LEVITATING DESKTOP  55557.99 R\n");
            textData.append("441 **Blue Overprint P  2.99 R\n");
            textData.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
            textData.append("461 WESTGATE BLACK 25  59.99 R\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("SUBTOTAL                160.38\n");
            textData.append("TAX                      14.43\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addTextSize";
            mPrinter.addTextSize(2, 2);
            method = "addText";
            mPrinter.addText("TOTAL    174.81\n");
            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            textData.append("CASH                    200.00\n");
            textData.append("CHANGE                   25.19\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("Purchased item total number\n");
            textData.append("Sign Up and Save !\n");
            textData.append("With Preferred Saving Card\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            method = "addFeedLine";
            mPrinter.addFeedLine(2);

            method = "addBarcode";
            mPrinter.addBarcode("01209457",
                    Printer.BARCODE_CODE39,
                    Printer.HRI_BELOW,
                    Printer.FONT_A,
                    barcodeWidth,
                    barcodeHeight);

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        textData = null;

        return true;
    }

    private boolean runPrintCouponSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createCouponData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createCouponData() {
        String method = "";
        Bitmap coffeeData = BitmapFactory.decodeResource(getResources(), R.drawable.coffee);
        Bitmap wmarkData = BitmapFactory.decodeResource(getResources(), R.drawable.wmark);

        final int barcodeWidth = 2;
        final int barcodeHeight = 64;
        final int pageAreaHeight = 500;
        final int pageAreaWidth = 500;
        final int fontAHeight = 24;
        final int fontAWidth = 12;
        final int barcodeWidthPos = 110;
        final int barcodeHeightPos = 70;

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addPageBegin";
            mPrinter.addPageBegin();

            method = "addPageArea";
            mPrinter.addPageArea(0, 0, pageAreaWidth, pageAreaHeight);

            method = "addPageDirection";
            mPrinter.addPageDirection(Printer.DIRECTION_TOP_TO_BOTTOM);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, coffeeData.getHeight());

            method = "addImage";
            mPrinter.addImage(coffeeData, 0, 0, coffeeData.getWidth(), coffeeData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, 3, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, wmarkData.getHeight());

            method = "addImage";
            mPrinter.addImage(wmarkData, 0, 0, wmarkData.getWidth(), wmarkData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(fontAWidth * 4, (pageAreaHeight / 2) - (fontAHeight * 2));

            method = "addTextSize";
            mPrinter.addTextSize(3, 3);

            method = "addTextStyle";
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.TRUE, Printer.PARAM_DEFAULT);

            method = "addTextSmooth";
            mPrinter.addTextSmooth(Printer.TRUE);

            method = "addText";
            mPrinter.addText("FREE Coffee\n");

            method = "addPagePosition";
            mPrinter.addPagePosition((pageAreaWidth / barcodeWidth) - barcodeWidthPos, coffeeData.getHeight() + barcodeHeightPos);

            method = "addBarcode";
            mPrinter.addBarcode("01234567890", Printer.BARCODE_UPC_A, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, barcodeWidth, barcodeHeight);

            method = "addPageEnd";
            mPrinter.addPageEnd();

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        return true;
    }

    private boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        dispPrinterWarnings(status);

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "sendData", mContext);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean initializeObject() {
        try {
            mPrinter = new Printer(((SpnModelsItem) mSpnSeries.getSelectedItem()).getModelConstant(),
                    ((SpnModelsItem) mSpnLang.getSelectedItem()).getModelConstant(),
                    mContext);
        } catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(mEditTarget.getText().toString(), Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        boolean Result = false;
        if (status == null) {
            Result = false;
        }

        if (status.getConnection() == Printer.FALSE) {
            Result = false;
        } else if (status.getOnline() == Printer.FALSE) {
            Result = false;
        } else {
            Result = true;
        }
        return Result;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void dispPrinterWarnings(PrinterStatusInfo status) {
        EditText edtWarnings = (EditText) findViewById(R.id.edtWarnings);
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        edtWarnings.setText(warningsMsg);
    }

    private void updateButtonState(boolean state) {
        Button btnReceipt = (Button) findViewById(R.id.btnSampleReceipt);
        Button btnCoupon = (Button) findViewById(R.id.btnSampleCoupon);
        btnReceipt.setEnabled(state);
        btnCoupon.setEnabled(state);
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }

    private void ScanTechPrinterConfig() {
        try {

            mHandler = new MHandler(PrinterActivity.this);
            InitGlobalString();
            service.addHandler(mHandler);
            if (null == WorkService.workThread) {
                Intent intent = new Intent(this, WorkService.class);
                startService(intent);
            }
            //handleIntent(getIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MHandler extends Handler {
        WeakReference<PrinterActivity> mActivity;

        MHandler(PrinterActivity activity) {
            mActivity = new WeakReference<PrinterActivity>(activity);
        }
    }

    private void InitGlobalString() {
        try {
            Global.toast_success = getString(R.string.toast_success);
            Global.toast_fail = getString(R.string.toast_fail);
            Global.toast_notconnect = getString(R.string.toast_notconnect);
            Global.toast_usbpermit = getString(R.string.toast_usbpermit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                //  handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                // handleSendImage(intent); // Handle single image being sent
            }
        }
    }

    private void handleSendText(Intent intent) {
        Uri textUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (textUri != null) {
            // Update UI to reflect text being shared
            if (WorkService.workThread.isConnected()) {
                byte[] buffer = {0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39, 0x01};
                Bundle data = new Bundle();
                data.putByteArray(Global.BYTESPARA1, buffer);
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, buffer.length);
                WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
            }
            if (WorkService.workThread.isConnected()) {
                String path = textUri.getPath();
                String strText = FileUtils.ReadToString(path);
                byte buffer[] = strText.getBytes();

                Bundle data = new Bundle();
                data.putByteArray(Global.BYTESPARA1, buffer);
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, buffer.length);
                data.putInt(Global.INTPARA3, 256);
                WorkService.workThread.handleCmd(Global.CMD_POS_WRITE_BT_FLOWCONTROL, data);

            } else {
                Toast.makeText(this, Global.toast_notconnect, Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }
    }


}
