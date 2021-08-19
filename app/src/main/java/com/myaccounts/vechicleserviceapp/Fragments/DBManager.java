package com.myaccounts.vechicleserviceapp.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper1 dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper1(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper1.SUBJECT, name);
        contentValue.put(DatabaseHelper1.DESC, desc);
        database.insert(DatabaseHelper1.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper1._ID, DatabaseHelper1.SUBJECT, DatabaseHelper1.DESC };
        Cursor cursor = database.query(DatabaseHelper1.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    /*public Cursor getServiceDetails(String serviceId) {
            String Qry = "SELECT " + DatabaseHelper1.DESC + " from " + DatabaseHelper1.TABLE_NAME + " where " + DatabaseHelper1.SUBJECT + " = \"" + serviceId + "\"  COLLATE NOCASE ";
            Cursor mCursor = database.getReadableDatabase().rawQuery(Qry, null);
            return mCursor;

    }*/
    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper1.SUBJECT, name);
        contentValues.put(DatabaseHelper1.DESC, desc);
        int i = database.update(DatabaseHelper1.TABLE_NAME, contentValues, DatabaseHelper1._ID + " = " + _id, null);
        return i;
    }

    public void delete() {
        database.delete(DatabaseHelper1.TABLE_NAME, null, null);
    }
}
