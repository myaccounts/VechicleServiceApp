package com.myaccounts.vechicleserviceapp.network;


public interface InfDbSpecs {
    static String ColumnType_NULL = " text null COLLATE NOCASE, ";
    static String ColumnType_NOTNULL = " text not null COLLATE NOCASE, ";
    static String ColounmType_Integer = "integer,";
    static String Boolen = "INTEGER DEFAULT 0,";

    public static final String PAYMENTID="PaymentId";
    public static final String CUSTOMER_NAME="CustomerEmailId";
    public static final String CUSTOMER_MOBILENUMBER="CustomerMobileNumber";

    public static final String VehicleNo="VehicleNo";
    public static final String Filepath="Filepath";

    //printer data info
    public static final String PRINTERIP="PrinterIp";
    public static final String PRINTER_NAME="PrinterName";
    public static final String KEY_ROWID = "_id";
    public static final String SERVICENAME = "ServiceName";
    public static final String SERVICEID = "ServiceId";
    public static final String TECHNICIANNAME = "TechnicianName";
    public static final String TECHNICIANID = "TechnicianId";
    public static final String RATE = "Rate";
    public static final String SUBSERVICENAME = "SubServiceName";
    public static final String SUBSERVICEID = "SubServiceId";
    public static final String QTY = "Qty";
    public static final String TOTALAMOUNT="totalAmount";
    public static final String NOTAPPLICABLE="notapplicable";
    public static final String STATUS="status";
    public static final String TABLE_SERVICES_DATA = "JServiceTable";
    public static final String TABLE_PRINTER_DATA="JPrintertable";
    public static final String TABLE_PAYMENT_DETAILS="JPaymentdetails";
    public static final String TABLE_EMAILID_DATA="JEmailidDetails";
    public static final String JobcardNo = "jobcardId";
    public static final String SpareList = "sparesList";
    public static final String TABLE_SPARES_DATA = "JsparesTable";
//    public static final String TABLE_TECHNICIAN_DATA = "TechnicianTable";

    public static final String STR_CREATE_TABLE_SERVICES_DATA = "create table "
            + TABLE_SERVICES_DATA + " ("
            + KEY_ROWID + " integer primary key autoincrement, "
            + SERVICENAME + ColumnType_NULL
            + SERVICEID + " text null COLLATE NOCASE"
           +");";

    public static final String STR_CREATE_TABLE_PRINTER_IP = "create table "
            + TABLE_PRINTER_DATA + "("
            + KEY_ROWID + " integer primary key autoincrement, "
            + PRINTER_NAME+ ColumnType_NOTNULL
            + PRINTERIP + " text null COLLATE NOCASE"
          //  + SERVICEID + ColumnType_NULL
            +");";
    public static final String STR_CREATE_TABLE_PAYMENT_DETAILS = "create table "
            + TABLE_PAYMENT_DETAILS + "("
            + KEY_ROWID + " integer primary key autoincrement, "
            + CUSTOMER_NAME+ ColumnType_NOTNULL
            + CUSTOMER_MOBILENUMBER+ ColumnType_NOTNULL
            + PAYMENTID + " text null COLLATE NOCASE"
            //  + SERVICEID + ColumnType_NULL
            +");";
    public static final String STR_CREATE_TABLE_EMAIL_ID = "create table "
            + TABLE_EMAILID_DATA + "("
            + KEY_ROWID + " integer primary key autoincrement, "
            + VehicleNo+ ColumnType_NOTNULL
            + Filepath + " text null COLLATE NOCASE"
            //  + SERVICEID + ColumnType_NULL
            +");";
    public static final String STR_CREATE_TABLE_SPARES_DATA="create table "
            + TABLE_SPARES_DATA + "("
            + KEY_ROWID + " integer primary key autoincrement, "
            + JobcardNo+ ColumnType_NOTNULL
            + SpareList + " text null COLLATE NOCASE"
            //  + SERVICEID + ColumnType_NULL
            +");";


}