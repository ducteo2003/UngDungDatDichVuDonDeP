package com.example.happyhomes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import com.example.happyhomes.Model.Check;
import com.example.happyhomes.Model.Employee;
import com.example.happyhomes.Model.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "data_app_cleaning.db";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH;
    private final Context context;

    public static final String TABLE_SERVICE = "SERVICE";
    public static final String COLUMN_SERVICE_ID = "SERVICEID";
    public static final String COLUMN_SERVICE_TYPE = "SERVICETYPE";

    public static final String TABLE_CHECK = "'CHECK'";
    public static final String COLUMN_CHECK_ID = "CHECKID";
    public static final String COLUMN_EMPLOYEE_ID = "EMID";
    public static final String COLUMN_CHECK_PIC = "CHECKPIC";
    public static final String COLUMN_CHECK_TYPE = "CHECKTYPE";
    public static final String COLUMN_CHECK_TIME = "TIME";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Not needed if you're copying the database from assets
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if necessary
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(DATABASE_PATH);
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(DATABASE_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public SQLiteDatabase openDatabase() throws SQLException {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        SQLiteDatabase db = this.openDatabase();

        Cursor cursor = db.query(TABLE_SERVICE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int serviceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID));
                String serviceType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_TYPE));

                services.add(new Service(serviceId, serviceType));
            }
            cursor.close();
        }
        return services;
    }

    public void addService(int serviceId, String serviceType) {
        SQLiteDatabase db = this.openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_ID, serviceId);
        values.put(COLUMN_SERVICE_TYPE, serviceType);
        db.insert(TABLE_SERVICE, null, values);
        db.close();


    }

    // Method to add a check record
    public void addCheck(Check check) {
        SQLiteDatabase db = this.openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_ID, check.getEmId());
        values.put(COLUMN_CHECK_PIC, check.getCheckPic());
        values.put(COLUMN_CHECK_TYPE, check.getCheckType());
        values.put(COLUMN_CHECK_TIME, check.getCheckTime());

        // Thêm log để kiểm tra nội dung của ContentValues
        Log.d("DatabaseHelper", "Inserting Check - Values: " + values.toString());

        long result = db.insert(TABLE_CHECK, null, values);
        // Kiểm tra kết quả chèn dữ liệu
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert Check.");
        } else {
            Log.d("DatabaseHelper", "Check inserted successfully with ID: " + result);
        }
        db.close();
    }

    //get duu lieu dua tren EMID
    public Employee getEmployeeById(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("EMPLOYEE",
                new String[]{"EMID", "EMNAME", "EMEMAIL"},  // Đúng tên cột
                "EMID = ?",
                new String[]{String.valueOf(employeeId)},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Employee employee = new Employee(
                    cursor.getInt(cursor.getColumnIndexOrThrow("EMID")),
                    cursor.getString(cursor.getColumnIndexOrThrow("EMNAME")),
                    cursor.getString(cursor.getColumnIndexOrThrow("EMEMAIL")),
                    null
            );
            cursor.close();
            return employee;
        } else {
            return null;
        }

    }


    public List<Check> getAllChecks() {
        List<Check> checks = new ArrayList<>();
        SQLiteDatabase db = this.openDatabase();

        Cursor cursor = db.query(TABLE_CHECK, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int checkId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHECK_ID));
                int emId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_ID));
                byte[] checkPic = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_CHECK_PIC));
                int checkType = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHECK_TYPE));
                String checkTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECK_TIME));

                checks.add(new Check(checkId, emId, checkPic, checkType, checkTime));
            }
            cursor.close();
        }

        db.close();
        return checks;
    }
}
