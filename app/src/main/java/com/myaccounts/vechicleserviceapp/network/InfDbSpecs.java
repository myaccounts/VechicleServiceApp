package com.myaccounts.vechicleserviceapp.network;


public interface InfDbSpecs {
    static String ColumnType_NULL = " text null COLLATE NOCASE, ";
    static String ColumnType_NOTNULL = " text not null COLLATE NOCASE, ";
    static String ColounmType_Integer = "integer,";
    static String Boolen = "INTEGER DEFAULT 0,";

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
//    public static final String TABLE_TECHNICIAN_DATA = "TechnicianTable";

    public static final String STR_CREATE_TABLE_SERVICES_DATA = "create table "
            + TABLE_SERVICES_DATA + " ("
            + KEY_ROWID + " integer primary key autoincrement, "
            + SERVICENAME + ColumnType_NULL
            + SERVICEID + ColumnType_NULL
           +");";


}