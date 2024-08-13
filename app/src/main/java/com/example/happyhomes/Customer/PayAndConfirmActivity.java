package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.Model.ServiceDetail;
import com.example.happyhomes.databinding.ActivityPayAndConfirmBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PayAndConfirmActivity extends AppCompatActivity {
    ActivityPayAndConfirmBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayAndConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo databaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Tải dữ liệu từ intent và hiển thị trên giao diện
        loadData();

        // Xử lý sự kiện khi nhấn nút Post Job
        binding.btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJobToDatabase();
            }
        });
    }

    private void loadData() {
        // Lấy dữ liệu từ intent và hiển thị trên các TextView
        Intent intent = getIntent();
        if (intent != null) {
            String selectedDate = intent.getStringExtra("selectedDate");
            String selectedHour = intent.getStringExtra("selectedHour");
            String additionalRequest = intent.getStringExtra("additionalRequest");
            binding.txtDate.setText(selectedDate);
            binding.txtTime.setText(selectedHour);
            binding.txtNote.setText(additionalRequest);
        } else {
            Toast.makeText(this, "Error: No data received.", Toast.LENGTH_LONG).show();
        }

    }

    private void saveJobToDatabase() {
        // Lấy dữ liệu từ intent và các view
        Intent intent = getIntent();
        int serviceId = intent.getIntExtra("selectedServiceId", -1); // -1 nếu không tìm thấy
        int cusId = 3; // Giả sử một customer ID (thay thế bằng logic thực tế)
        int payId = 2; // Giả sử một payment ID (thay thế bằng logic thực tế)
        String note = binding.txtNote.getText().toString();
        String selectedDate = binding.txtDate.getText().toString();
        String selectedHour = binding.txtTime.getText().toString();
        // Đảm bảo rằng serviceId hợp lệ
        if (serviceId == -1) {
            Toast.makeText(this, "Error: Service not selected.", Toast.LENGTH_LONG).show();
            return;
        }
            ServiceDetail serviceDetail = new ServiceDetail(cusId, serviceId, payId, note);
        // Tạo đối tượng Schedule
        Schedule schedule = new Schedule();
        schedule.setDate(selectedDate);
        schedule.setStartTime(selectedHour);
        // Xử lý thời gian bắt đầu và kết thúc
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar startCalendar = Calendar.getInstance();
        try {
            startCalendar.setTime(sdf.parse(selectedDate + " " + selectedHour));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Xác định số giờ để thêm vào
        int hoursToAdd;
        switch (serviceId) {
            case 1:
                hoursToAdd = 2;
                break;
            case 2:
                hoursToAdd = 3;
                break;
            case 3:
                hoursToAdd = 4;
                break;
            default:
                hoursToAdd = 0;
                break;
        }
        // Tính toán endTime
        startCalendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        String endTime = sdf.format(startCalendar.getTime());
        schedule.setEndTime(endTime);
        schedule.setLocation("Location"); // Thay thế bằng thông tin vị trí thực tế
        schedule.setStatus("Pending"); // Hoặc trạng thái thực tế
        // Lấy ngày giờ hiện tại
        String currentDateTime = sdf.format(new Date());
        schedule.setCreatedAt(currentDateTime);
        schedule.setUpdatedAt(currentDateTime);
        try {
            databaseHelper.addServiceDetail(serviceDetail);
            databaseHelper.addSchedule(schedule);
            Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("PayAndConfirmActivity", "Error saving job to database", e);
            Toast.makeText(this, "Failed to post job", Toast.LENGTH_LONG).show();
        }
    }
}
