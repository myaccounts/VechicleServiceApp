package com.myaccounts.vechicleserviceapp.Printernew;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.Log;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.myaccounts.vechicleserviceapp.R;

import java.util.ArrayList;

public class InitializePrinter {
    public static final String TARGET = "Target";
    private static Activity mContext = null;
    private static ReceiveListener mReceiveListener;
    private Printer mPrinter = null;
    private static String printerText;
    private static SharedPreferences printerSharedpreferences;
    public static final String PRINTER_PREFERENCES = "Printer Preferences";
    private ArrayList<String> printerList;
    private String header;
    private String ginvNo_A;

    public InitializePrinter(Activity context, ReceiveListener receiveListener) {
        mReceiveListener = receiveListener;
        mContext = context;
        printerSharedpreferences = mContext.getSharedPreferences(PRINTER_PREFERENCES, Context.MODE_PRIVATE);
        try {
            Log.setLogSettings(context, Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
        } catch (Exception e) {
            ShowMsg.showException(e, "setLogSettings", context);
        }
    }


    public boolean runPrintReceiptSequence(String header, ArrayList<String> printerList, String ginvNo_A) {
        this.printerList = printerList;
        this.header = header;
        this.ginvNo_A = ginvNo_A;
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
        Bitmap logoData = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.myaccounts_logo);
        logoData = Bitmap.createScaledBitmap(logoData, 400, 80, false);
        if (mPrinter == null) {
            return false;
        }
        try {
            for (int i = 0; i < printerList.size(); i++) {
                String strPrintText = printerList.get(i);
                if (strPrintText.equalsIgnoreCase("CompName")) {
                    strPrintText = printerList.get(i + 1);
                    mPrinter.addTextSize(2, 2);
                    i++;
                } else {
                    mPrinter.addTextSize(1, 1);
                }
                mPrinter.addText(strPrintText + "\n");
            }
            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Exception e) {
            //show the error message

            ShowMsg.showException(e, method, mContext);
            return false;
        }
        return true;
    }

    public boolean printData() {
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
            mPrinter = new Printer(Printer.TM_P20, Printer.MODEL_ANK, mContext);
        } catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);
            return false;
        }
        mPrinter.setReceiveEventListener(mReceiveListener);
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

    public boolean connectPrinter() {
        boolean isBeginTransaction = false;
        if (mPrinter == null) {
            return false;
        }
        try {
            mPrinter.connect(printerSharedpreferences.getString(TARGET, ""), Printer.PARAM_DEFAULT);
        } catch (Exception e) {
           /* if(ProjectVariables.get_AppModeIsOnline()){
                //ShowMsg.showException(e, "Please Connect the Printer", mContext);
            }else {*/
               // ShowMsg.showException(e, "Connect Error While connecting", mContext);//Initially bletooth is not paired from discovered devices.
                ShowMsg.showException(e, "Please Connect the Printer", mContext);//Initially bletooth is not paired from discovered devices.
           // }
            //paired with wrong device
            //mobile Bluetooth is disabled.
            //  Blue is not enabled in printer.
            return false;
        }
        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "Connect error while begin the transaction", mContext);
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

    public void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }
        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }
        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }
        finalizeObject();
    }

    public boolean isPrintable(PrinterStatusInfo status) {
        try {
            if (status == null) {
                return false;
            }
            if (status.getConnection() == Printer.FALSE) {
                return false;
            } else if (status.getOnline() == Printer.FALSE) {
                return false;
            } else {
                ;//print available
            }
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";
        if (status.getOnline() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += mContext.getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += mContext.getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += mContext.getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_autocutter);
            msg += mContext.getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += mContext.getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += mContext.getString(R.string.handlingmsg_err_battery_real_end);
        }
        return msg;
    }

    public void dispPrinterWarnings(PrinterStatusInfo status) {
        // EditText edtWarnings = (EditText)findViewById(R.id.edtWarnings);
        String warningsMsg = "";
        if (status == null) {
            return;
        }
        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += mContext.getString(R.string.handlingmsg_warn_receipt_near_end);
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += mContext.getString(R.string.handlingmsg_warn_battery_near_end);
        }
        System.out.println("WarningsMsg :" + warningsMsg);
    }

    public boolean ReceiptSequence(String testPrintValue) {
        if (!initializeObject()) {
            return false;
        }
        if (!createTestReceiptData(testPrintValue)) {
            finalizeObject();
            return false;
        }
        if (!printData()) {
            finalizeObject();
            return false;
        }
        return true;
    }

    private boolean createTestReceiptData(String testPrintValue) {
        try{
            mPrinter.addText(testPrintValue + "\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
  /*  private void updateButtonState(boolean state) {
        generateReceipt.setEnabled(state);
        if(state)
            generateReceipt.getBackground().setAlpha(255);
        else
            generateReceipt.getBackground().setAlpha(0);
    }*/





}
