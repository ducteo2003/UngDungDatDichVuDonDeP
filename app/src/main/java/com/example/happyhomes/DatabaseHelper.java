package com.example.happyhomes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.happyhomes.Model.Check_Work;
import com.example.happyhomes.Model.Employee;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.Model.Service;
import com.example.happyhomes.Model.ServiceSchedule;
import com.example.happyhomes.Model.Workdate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "data_app_cleaning.db";
    private static final int DATABASE_VERSION = 1;
    private static  String DATABASE_PATH;
    private final Context context;
    // Table names
    private static final String TABLE_SERVICE = "SERVICE";
    private static final String TABLE_SCHEDULE = "SCHEDULE";
    private static final String TABLE_SERVICE_SCHEDULE = "SERVICE_SCHEDULE";

    // SERVICE Table columns
    private static final String COLUMN_SERVICE_ID = "SERVICEID";
    private static final String COLUMN_SERVICE_TYPE = "SERVICETYPE";
    private static final String COLUMN_SERVICE_COST = "SERVICECOST";


    // SCHEDULE Table columns
    private static final String COLUMN_SCHEDULE_ID = "SCHEDULEID";
    private static final String COLUMN_CUS_ID = "CUSID";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_START_TIME = "STARTTIME";
    private static final String COLUMN_LOCATION = "LOCATION";
    private static final String COLUMN_STATUS = "STATUS";

    // SERVICE_SCHEDULE Table columns
    private static final String COLUMN_SER_SCHE_ID = "SER_SCHE_ID";
    private static final String COLUMN_SERVICE_ID_FK = "SERVICEID";
    private static final String COLUMN_SCHEDULE_ID_FK = "SCHEDULEID";

    // Tên bảng và cột cho bảng Check_Work
    private static final String TABLE_CHECK_WORK = "CHECK_WORK";
    private static final String COLUMN_WORKDATE_ID = "WORKDATEID";
    private static final String COLUMN_CHECKPIC = "CHECKPIC";
    private static final String COLUMN_CHECKTYPE = "CHECKTYPE";
    private static final String COLUMN_TIME = "TIME";

    // Tên bảng và cột cho bảng Workdate
    private static final String TABLE_WORKDATE = "'WORKDATE'";
    private static final String COLUMN_EMID = "EMID";
    //private static final String COLUMN_SER_SCHE_ID = "SER_SCHE_ID";

    // Bảng Employee
    private static final String TABLE_EMPLOYEE = "EMPLOYEE";
    private static final String COLUMN_EM_ID = "EMID";
    private static final String COLUMN_EM_NAME = "EMNAME";
    private static final String COLUMN_EM_EMAIL = "EMEMAIL";
    private static final String COLUMN_EM_PASSWORD = "PASSWORD";




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

    // Add new Service
    public void addService(Service service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_ID, service.getServiceId());
        values.put(COLUMN_SERVICE_TYPE, service.getServiceType());
        values.put(COLUMN_SERVICE_COST, service.getServiceCost());
        db.insert(TABLE_SERVICE, null, values);
        db.close();
    }
    // Get all Services
    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SERVICE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Service service = new Service(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_COST))

                );
                serviceList.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return serviceList;
    }

    // Add new Schedule
    public long addSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUS_ID, schedule.getCusId());
        values.put(COLUMN_DATE, schedule.getDateString()); // Convert Date to String
        values.put(COLUMN_START_TIME, schedule.getStartTimeString()); // Convert Time to String
        values.put(COLUMN_LOCATION, schedule.getLocation());
        values.put(COLUMN_STATUS, schedule.getStatus());

        long result = db.insert(TABLE_SCHEDULE, null, values);
        db.close();
        return result;
    }

    // Get all Schedules
    public List<Schedule> getAllSchedules() {
        List<Schedule> scheduleList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CUS_ID)),
                        new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE))),
                        new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_START_TIME))),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                );
                scheduleList.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return scheduleList;
    }

    // Add new ServiceSchedule
    public void addServiceSchedule(ServiceSchedule serviceSchedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_ID_FK, serviceSchedule.getServiceId());
        values.put(COLUMN_SCHEDULE_ID_FK, serviceSchedule.getScheduleId());
        db.insert(TABLE_SERVICE_SCHEDULE, null, values);
        db.close();
    }
    public List<ServiceSchedule> getAllServiceSchedules() {
        List<ServiceSchedule> serviceScheduleList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SERVICE_SCHEDULE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ServiceSchedule serviceSchedule = new ServiceSchedule(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SER_SCHE_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID_FK)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_ID_FK))
                );
                serviceScheduleList.add(serviceSchedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return serviceScheduleList;
    }

    // Phương thức lấy thông tin nhân viên từ ID
    public Employee getEmployeeById(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COLUMN_EM_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(employeeId)});

        Employee employee = null;
        if (cursor.moveToFirst()) {
            employee = new Employee(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_EM_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EM_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EM_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EM_PASSWORD))  // Lấy thêm trường password
            );
        }
        cursor.close();
        db.close();
        return employee;
    }



    // Get Workdates by Employee ID
    public List<Workdate> getWorkdatesByEmployeeId(Long employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_WORKDATE + " WHERE " + COLUMN_EMID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(employeeId)});

        List<Workdate> workdates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workdate workdate = new Workdate(
                        cursor.getLong(cursor.getColumnIndexOrThrow("WORKDATE_ID")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("EMID")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("SER_SCHE_ID"))
                );
                workdates.add(workdate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return workdates;
    }

    // Get Schedule by Ser_Sche_ID
    public Schedule getScheduleBySerScheId(Long serScheId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " + COLUMN_SCHEDULE_ID + " = (SELECT " + COLUMN_SCHEDULE_ID_FK + " FROM " + TABLE_SERVICE_SCHEDULE + " WHERE " + COLUMN_SER_SCHE_ID + " = ?)";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serScheId)});

        Schedule schedule = null;
        if (cursor.moveToFirst()) {
            schedule = new Schedule(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_ID)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CUS_ID)),
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE))),
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_START_TIME))),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            );
        }
        cursor.close();
        db.close();
        return schedule;
    }

    // Insert Check_Work
    public void addCheckWork(Check_Work checkWork) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKDATE_ID, checkWork.getWorkdateId());
        values.put(COLUMN_CHECKPIC, checkWork.getCheckPic());
        values.put(COLUMN_CHECKTYPE, checkWork.getCheckType());
        values.put(COLUMN_TIME, checkWork.getTime());
        db.insert(TABLE_CHECK_WORK, null, values);
        db.close();
    }




}
