package com.myaccounts.vechicleserviceapp.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myaccounts.vechicleserviceapp.network.InfDbSpecs;

import javax.security.auth.Subject;

import static com.myaccounts.vechicleserviceapp.network.InfDbSpecs.TABLE_SERVICES_DATA;

public class DatabaseHelper1 extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "COUNTRIES";

    // Table columns
    public static final String _ID = "_id";
    public static final String SUBJECT = "subject";
    public static final String DESC = "description";

    // Database Information
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT UNIQUE, " + DESC + " TEXT UNIQUE);";

    public DatabaseHelper1(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DropAllTables(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    private void DropAllTables(SQLiteDatabase db) {
        try {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICIAN_DATA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Cursor getServiceDetails(String serviceId) {
//            String Qry = "SELECT " + DESC + " from " + TABLE_NAME + " where " + SUBJECT + " = " + serviceId + " ";
        SQLiteDatabase db = this.getReadableDatabase();
//        String Qry = "select * from "+TABLE_NAME+" WHERE name = "+serviceId, null

//        Log.e("Executing block", "JESUS I am here" + Qry);
//            Cursor mCursor = db.rawQuery(Qry, null);'"+rid+"'"
//        Cursor res = db.rawQuery( "SELECT * from "+TABLE_NAME+" where subject='"+serviceId+"'", null );
       Cursor res= db.rawQuery("SELECT description FROM  COUNTRIES   where subject=?" ,
                new String [] {serviceId});
            return res;
//        }
    }
    public String jgetId(String subject){
        String jValue = "";
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String Qry = "SELECT * FROM " + TABLE_NAME + " Where " + DESC + " = \"" + subject + "\"";
            Cursor mCursor = db.rawQuery(Qry, null);
            if (mCursor.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper1._ID));
                    Log.d("JESUS ", " " + jValue);

                }
                while (mCursor.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," EXCEPTION "+e.toString());
        }
        /*try {
            SQLiteDatabase db = this.getReadableDatabase();

//Cursor cursorr = db.rawQuery(Query, null);

            Cursor cursorr = db.rawQuery("select * from " + DatabaseHelper1.TABLE_NAME + " where " + DatabaseHelper1.DESC + "=" + subject, null);

            if (cursorr.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = cursorr.getString(cursorr.getColumnIndex(DatabaseHelper1.DESC));
                    Log.d("JESUS ", " " + jValue);

                }
                while (cursorr.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," "+e.toString());
        }*/
        return jValue;
    }
        public String jgetValues(String subject){
        String jValue = "";
        try{
            SQLiteDatabase db = this.getReadableDatabase();
                String Qry = "SELECT * FROM " + TABLE_NAME + " Where " + SUBJECT + " = \"" + subject + "\"";
                Cursor mCursor = db.rawQuery(Qry, null);
            if (mCursor.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper1.DESC));
                    Log.d("JESUS ", " " + jValue);

                }
                while (mCursor.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," EXCEPTION "+e.toString());
        }
        /*try {
            SQLiteDatabase db = this.getReadableDatabase();

//Cursor cursorr = db.rawQuery(Query, null);

            Cursor cursorr = db.rawQuery("select * from " + DatabaseHelper1.TABLE_NAME + " where " + DatabaseHelper1.DESC + "=" + subject, null);

            if (cursorr.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = cursorr.getString(cursorr.getColumnIndex(DatabaseHelper1.DESC));
                    Log.d("JESUS ", " " + jValue);

                }
                while (cursorr.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," "+e.toString());
        }*/
        return jValue;
    }

    public String jgetValueId(String desc){
        String jValue = "";
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String Qry = "SELECT * FROM " + TABLE_NAME + " Where " + DESC + " = \"" + desc + "\"";
            Cursor mCursor = db.rawQuery(Qry, null);
            if (mCursor.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper1.SUBJECT));
                    Log.d("JESUS ", " " + jValue);

                }
                while (mCursor.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," EXCEPTION "+e.toString());
        }
        /*try {
            SQLiteDatabase db = this.getReadableDatabase();

//Cursor cursorr = db.rawQuery(Query, null);

            Cursor cursorr = db.rawQuery("select * from " + DatabaseHelper1.TABLE_NAME + " where " + DatabaseHelper1.DESC + "=" + subject, null);

            if (cursorr.moveToFirst()) {

                do {

                    // your code like get columns
                    jValue = cursorr.getString(cursorr.getColumnIndex(DatabaseHelper1.DESC));
                    Log.d("JESUS ", " " + jValue);

                }
                while (cursorr.moveToNext());
            }
        }catch(Exception e){
            Log.d("JESUS "," "+e.toString());
        }*/
        return jValue;
    }
    public Cursor getAllItemDetails() {
        Cursor Result = null;
        try {
            String countQuery = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Result = db.rawQuery(countQuery, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
}
