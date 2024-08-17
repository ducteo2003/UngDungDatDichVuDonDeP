package com.example.happyhomes.NhanVien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.R;

public class ScheduleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        long scheduleId = getIntent().getLongExtra("SCHEDULE_ID", -1);
        Log.d("ScheduleDetailActivity", "Received Schedule ID: " + scheduleId);

        if (scheduleId != -1) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT S.SCHEDULEID, C.CUSNAME, SE.SERVICETYPE, SE.SERVICECOST, S.DATE, S.STARTTIME, S.LOCATION " +
                    "FROM SCHEDULE S " +
                    "JOIN CUSTOMER C ON S.CUSID = C.CUSID " +
                    "JOIN SERVICE_SCHEDULE SS ON S.SCHEDULEID = SS.SCHEDULEID " +
                    "JOIN SERVICE SE ON SS.SERVICEID = SE.SERVICEID " +
                    "WHERE S.SCHEDULEID = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(scheduleId)});

            if (cursor != null && cursor.moveToFirst()) {
                String customerName = cursor.getString(cursor.getColumnIndexOrThrow("CUSNAME"));
                String serviceType = cursor.getString(cursor.getColumnIndexOrThrow("SERVICETYPE"));
                float serviceCost = cursor.getFloat(cursor.getColumnIndexOrThrow("SERVICECOST"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
                String startTime = cursor.getString(cursor.getColumnIndexOrThrow("STARTTIME"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));

                cursor.close();

                TextView tvScheduleId = findViewById(R.id.tvScheduleId);
                TextView tvCustomerName = findViewById(R.id.tvCustomerName);
                TextView tvServiceType = findViewById(R.id.tvServiceType);
                TextView tvServiceCost = findViewById(R.id.tvServiceCost);
                TextView tvDate = findViewById(R.id.tvDate);
                TextView tvStartTime = findViewById(R.id.tvStartTime);
                TextView tvLocation = findViewById(R.id.tvLocation);

                tvScheduleId.setText("Schedule ID: " + scheduleId);
                tvCustomerName.setText("Customer Name: " + customerName);
                tvServiceType.setText("Service Type: " + serviceType);
                tvServiceCost.setText("Service Cost: " + serviceCost);
                tvDate.setText("Date: " + date);
                tvStartTime.setText("Start Time: " + startTime);
                tvLocation.setText("Location: " + location);
            } else {
                Toast.makeText(this, "Không tìm thấy chi tiết lịch làm việc", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi khi tải chi tiết lịch làm việc", Toast.LENGTH_SHORT).show();
        }
    }
}
