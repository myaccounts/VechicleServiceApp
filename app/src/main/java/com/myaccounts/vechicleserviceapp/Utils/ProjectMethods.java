package com.myaccounts.vechicleserviceapp.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.EPSON;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.EPSON_M30;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.MAESTROS;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.SCANTECH;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.SUNMI_28_COLUMN;
import static com.myaccounts.vechicleserviceapp.Utils.Enums.Printers.SUNMI_42_COLUMN;

public class ProjectMethods {
    private static SharedPreferences configSharedPreferences;

    private static String KEY_CONFIG_PREFERENCES = "MyServerDetails";
    private static String KEY_PRINTERTYPE = "PrinterType";
    private static String KEY_PRINTERIP = "PrinterIp";
    public static String GetCurrentDate() {
        String fromdate = null;
        try {
            final Calendar c = Calendar.getInstance();
            SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            fromdate = ss.format(c.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fromdate;
    }

    public static String dateFormat(final Context context, EditText ReceiptDateEdt) {
        final String[] DateFormat = new String[1];
        try {
            final int month, day, year;

            final Calendar c = Calendar.getInstance();
            SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
            final Date date = new Date();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            String currentdate = ss.format(date);
            ReceiptDateEdt.setText(currentdate);
            ReceiptDateEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog picker = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                   // ReceiptDateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    DateFormat[0] =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                }
                            }, year, month, day);
                    picker.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(DateFormat);
    }


    public static int GetDateToInt(String DD_MM_YYYY) {
        int Result = 0;
        try {
            int day, month, year;
            day = Integer.parseInt(DD_MM_YYYY.substring(0, 2));
            month = Integer.parseInt(DD_MM_YYYY.substring(3, 5));
            year = Integer.parseInt(DD_MM_YYYY.substring(6, 10));

            Result = ((year - 1950) * 416) + (month * 32) + day;
        } catch (Exception Ex) {
        }
        return Result;
    }
    public static String GetCurrentTime() {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String GetCustomerCurrentTime() {

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");//date format has changes
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
    public static String BusinessDate= "";
    public static String UserId= "";
    public static String UserName= "";

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String CounterId= "";

    public static String getCounterId() {
        return CounterId;
    }

    public static void setCounterId(String counterId) {
        CounterId = counterId;
    }

    public static String getBusinessDate() {
        return BusinessDate;
    }

    public static void setBusinessDate(String businessDate) {
        BusinessDate = businessDate;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public static String ServiceList1= "";
    public static String ServiceList2= "";
    public static String ServiceList3= "";
    public static String ServiceList4= "";
    public static String ServiceList5= "";
    public static String ServiceList6= "";
    public static String ServiceList7= "";
    public static String ServiceList8= "";
    public static String ServiceList9= "";
    public static String ServiceList10= "";
    public static String ServiceList11= "";
    public static String ServiceList12= "";
    public static String ServiceList13= "";
    public static String ServiceList14= "";
    public static String ServiceList15= "";

    public static String getServiceList1() {
        return ServiceList1;
    }

    public static void setServiceList1(String serviceList1) {
        ServiceList1 = serviceList1;
    }

    public static String getServiceList2() {
        return ServiceList2;
    }

    public static void setServiceList2(String serviceList2) {
        ServiceList2 = serviceList2;
    }

    public static String getServiceList3() {
        return ServiceList3;
    }

    public static void setServiceList3(String serviceList3) {
        ServiceList3 = serviceList3;
    }

    public static String getServiceList4() {
        return ServiceList4;
    }

    public static void setServiceList4(String serviceList4) {
        ServiceList4 = serviceList4;
    }

    public static String getServiceList5() {
        return ServiceList5;
    }

    public static void setServiceList5(String serviceList5) {
        ServiceList5 = serviceList5;
    }

    public static String getServiceList6() {
        return ServiceList6;
    }

    public static void setServiceList6(String serviceList6) {
        ServiceList6 = serviceList6;
    }

    public static String getServiceList7() {
        return ServiceList7;
    }

    public static void setServiceList7(String serviceList7) {
        ServiceList7 = serviceList7;
    }

    public static String getServiceList8() {
        return ServiceList8;
    }

    public static void setServiceList8(String serviceList8) {
        ServiceList8 = serviceList8;
    }

    public static String getServiceList9() {
        return ServiceList9;
    }

    public static void setServiceList9(String serviceList9) {
        ServiceList9 = serviceList9;
    }

    public static String getServiceList10() {
        return ServiceList10;
    }

    public static void setServiceList10(String serviceList10) {
        ServiceList10 = serviceList10;
    }

    public static String getServiceList11() {
        return ServiceList11;
    }

    public static void setServiceList11(String serviceList11) {
        ServiceList11 = serviceList11;
    }

    public static String getServiceList12() {
        return ServiceList12;
    }

    public static void setServiceList12(String serviceList12) {
        ServiceList12 = serviceList12;
    }

    public static String getServiceList13() {
        return ServiceList13;
    }

    public static void setServiceList13(String serviceList13) {
        ServiceList13 = serviceList13;
    }

    public static String getServiceList14() {
        return ServiceList14;
    }

    public static void setServiceList14(String serviceList14) {
        ServiceList14 = serviceList14;
    }

    public static String getServiceList15() {
        return ServiceList15;
    }

    public static void setServiceList15(String serviceList15) {
        ServiceList15 = serviceList15;
    }



    private static Enums.Printers _BILL_PRINTER_TYPE = null;

    public static void setBillPrinterType(Enums.Printers bilPrinterType) {
        _BILL_PRINTER_TYPE = bilPrinterType;
    }

    public static void setBillPrinterType(String bilPrinterType) {
        if (bilPrinterType.equalsIgnoreCase(EPSON.toString())) {
            _BILL_PRINTER_TYPE = EPSON;
        } else if (bilPrinterType.equalsIgnoreCase(EPSON_M30.toString())) {
            _BILL_PRINTER_TYPE = EPSON_M30;
        } else if (bilPrinterType.equalsIgnoreCase(MAESTROS.toString())) {
            _BILL_PRINTER_TYPE = MAESTROS;
        } else if (bilPrinterType.equalsIgnoreCase(SCANTECH.toString())) {
            _BILL_PRINTER_TYPE = SCANTECH;
        } else if (bilPrinterType.equalsIgnoreCase(SUNMI_28_COLUMN.toString())) {
            _BILL_PRINTER_TYPE = SUNMI_28_COLUMN;
        } else if (bilPrinterType.equalsIgnoreCase(SUNMI_42_COLUMN.toString())) {
            _BILL_PRINTER_TYPE = SUNMI_42_COLUMN;
        }
    }

    public static Enums.Printers getBillPrinterType() {
        Enums.Printers Result = EPSON;
        try {
            if (_BILL_PRINTER_TYPE == EPSON) {
                Result = EPSON;
            } else if (_BILL_PRINTER_TYPE == EPSON_M30) {
                Result = EPSON_M30;
            } else if (_BILL_PRINTER_TYPE == MAESTROS) {
                Result = MAESTROS;
            } else if (_BILL_PRINTER_TYPE == SCANTECH) {
                Result = SCANTECH;
            } else if (_BILL_PRINTER_TYPE == SUNMI_28_COLUMN) {
                Result = SUNMI_28_COLUMN;
            } else if (_BILL_PRINTER_TYPE == SUNMI_42_COLUMN) {
                Result = SUNMI_42_COLUMN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
    private static String _BillPrinterIP = "";

    public static String get_BillPrinterIP() {
        return _BillPrinterIP;
    }

    public static void set_BillPrinterIP(String _BillPrinterIP) {
       ProjectMethods._BillPrinterIP = _BillPrinterIP;

    }
    public static void SetPrinterType(Context context, String printertype) {
        try {
            configSharedPreferences = context.getSharedPreferences(KEY_CONFIG_PREFERENCES, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = configSharedPreferences.edit();
            editor.putString(KEY_PRINTERTYPE, printertype);
            editor.commit();

          //  SetConnection(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetPrinterType(Context context) {
        String Result = "";
        try {
            configSharedPreferences = context.getSharedPreferences(KEY_CONFIG_PREFERENCES, context.MODE_PRIVATE);
            Result = configSharedPreferences.getString(KEY_PRINTERTYPE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
    public static void SetPrinterIP(Context context, String printerIp) {
        try {
            configSharedPreferences = context.getSharedPreferences(KEY_CONFIG_PREFERENCES, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = configSharedPreferences.edit();
            editor.putString(KEY_PRINTERIP, printerIp);
            editor.commit();

           // SetConnection(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String AddSpace(String strString, int TargetLength, Enums.AlignAt At) {
        StringBuffer strResult = new StringBuffer("");
        try {
            if (TargetLength == 0) {
                return strResult.toString();
            }
            if (strString.length() < TargetLength) {
                int Length = strString.length();
                boolean OnBothLeft = false;
                StringBuffer Spc1 = new StringBuffer("");
                StringBuffer Spc2 = new StringBuffer("");

                for (int i = 1; i <= TargetLength - Length; i++) {
                    if (OnBothLeft) {
                        Spc1.append(" ");
                        OnBothLeft = false;
                    } else {
                        Spc2.append(" ");
                        OnBothLeft = true;
                    }
                }

                if (At == Enums.AlignAt.Right) {
                    strResult.append(Spc1.toString() + Spc2.toString() + strString);
                } else if (At == Enums.AlignAt.Left) {
                    strResult.append(strString + Spc1.toString() + Spc2.toString());
                } else {
                    strResult.append(Spc1.toString() + strString + Spc2.toString());
                }
            } else {
                strResult.append(strString.substring(0, TargetLength));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult.toString();
    }

    public static String GetLine(int Length) {
        String Result = "";
        try {
            for (int i = 0; i < Length; i++) {
                Result = Result + "-";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

}
