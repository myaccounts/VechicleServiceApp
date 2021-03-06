package com.myaccounts.vechicleserviceapp.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import static com.myaccounts.vechicleserviceapp.network.InfDbSpecs.TABLE_SERVICES_DATA;
//import static com.myaccounts.vechicleserviceapp.network.InfDbSpecs.TABLE_TECHNICIAN_DATA;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context myContext;
    public static final int DATABASE_VERSION = 43;
    public static SQLiteDatabase db;
    private static DatabaseHelper mInstance;
    public static final String DATABASE_NAME = "JTechnicianDB11";
    static String Lock = "dblock";


    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.myContext = context;
    }

    public DatabaseHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            db = this.getMyWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public SQLiteDatabase getMyWritableDatabase() throws SQLException {
        if ((db == null) || (!db.isOpen())) {
            db = this.getWritableDatabase();
        }
        return db;
    }

    @Override
    public void close() {
        super.close();
        if (db != null) {
            db.close();
            db = null;
            mInstance = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
   @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(InfDbSpecs.STR_CREATE_TABLE_SERVICES_DATA);
//            db.execSQL(InfDbSpecs.STR_CREATE_TABLE_TECHNICIAN_DATA);

        } catch (SQLiteException e) {
            e.printStackTrace();
            if (e.getMessage().contains("no such table")) {
                DropAllTables(db);
                onCreate(db);
            }
        }
    }


    /*    public void resetTables(){

            // Delete All Rows
            db.delete(DATABASE_NAME, null, null);
            db.close();
        }*/
 /*   @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldversion, int newVersion) {
        try {
            // arg0=getWritableDatabase();
            DropAllTables(arg0);
            CreateAllTables(arg0);
      /*      if (newVersion > oldversion){
                DropAllTables(arg0);
                onCreate(arg0);
               // CreateAllTables(arg0);
        }*/
          /*  if (arg2 > arg1) {
                db.execSQL("ALTER TABLE " + InfDbSpecs.TABLE_BILLS_Local + " ADD COLUMN " + InfDbSpecs.BILL_Customer_MobileNumber + " INTEGER DEFAULT 0");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CreateAllTables(SQLiteDatabase db) {
        try {
            db.execSQL(InfDbSpecs.STR_CREATE_TABLE_SERVICES_DATA);
//            db.execSQL(InfDbSpecs.STR_CREATE_TABLE_TECHNICIAN_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void DropAllTables(SQLiteDatabase db) {
        try {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES_DATA);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICIAN_DATA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*ServiceDetails Saving*/
    //insert_ServiceDetails(serviceId,subServiceId,qtyValue,rate,totalAmount,notApplicable,status);
    public boolean insert_ServiceDetails(String serviceId, String serviceName) {
        synchronized (Lock) {
            boolean Result = false;
            try {
                ContentValues initialValues = new ContentValues();
                initialValues.put(InfDbSpecs.SERVICEID, serviceId);
                initialValues.put(InfDbSpecs.SERVICENAME, serviceName);
                int update = 0;
                try {
//                    update = db.update(TABLE_SERVICES_DATA, initialValues, InfDbSpecs.BillPrintBodyRno + " = ? AND " + InfDbSpecs.BillPrintBodyPrinterName + " = ?", new String[]{String.valueOf(body.getBodyRno()), String.valueOf(body.getBodyPrinterName())});
                    Result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (update == 0) {
                    db.insert(TABLE_SERVICES_DATA, null, initialValues);
                    Result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Result;
        }
    }
    public boolean insert_TechnicianDetails(String technicianId, String technicianName) {
        synchronized (Lock) {
            boolean Result = false;
            try {
                ContentValues initialValues = new ContentValues();
                initialValues.put(InfDbSpecs.TECHNICIANID, technicianId);
                initialValues.put(InfDbSpecs.TECHNICIANNAME, technicianName);
                int update = 0;
                try {
//                    update = db.update(TABLE_SERVICES_DATA, initialValues, InfDbSpecs.BillPrintBodyRno + " = ? AND " + InfDbSpecs.BillPrintBodyPrinterName + " = ?", new String[]{String.valueOf(body.getBodyRno()), String.valueOf(body.getBodyPrinterName())});
                    Result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (update == 0) {
//                    db.insert(TABLE_TECHNICIAN_DATA, null, initialValues);
                    Result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Result;
        }
    }
    public Cursor getServiceDetails(String serviceId) {
        synchronized (Lock) {
            String Qry = "SELECT " + InfDbSpecs.SERVICENAME + " from " + TABLE_SERVICES_DATA + " where " + InfDbSpecs.SERVICEID + " = \"" + serviceId + "\"  COLLATE NOCASE ";
            Cursor mCursor = getReadableDatabase().rawQuery(Qry, null);
            return mCursor;
        }
    }
    /*public Cursor getTechnicianDetails(String technicianId) {
        synchronized (Lock) {
//            String Qry = "SELECT * FROM " + TABLE_TECHNICIAN_DATA + " Order By " + InfDbSpecs.TECHNICIANID + "," + InfDbSpecs.KEY_ROWID;
//            Cursor mCursor = getReadableDatabase().rawQuery(Qry, null);
//            return mCursor;
//            String Qry = "SELECT " + InfDbSpecs.TECHNICIANNAME + " from " + TABLE_TECHNICIAN_DATA + " where " + InfDbSpecs.TECHNICIANID + " = \"" + technicianId + "\"  COLLATE NOCASE ";
//            Cursor mCursor = getReadableDatabase().rawQuery(Qry, null);
            return mCursor;
        }
    }*/
    /*public void DeleteQTYRows(String qty) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_SERVICES_DATA + " Where " + InfDbSpecs.QTY + " = \"" + "0" + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}