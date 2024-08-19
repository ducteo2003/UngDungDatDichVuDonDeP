package com.example.happyhomes.NhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.R;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleActivity extends AppCompatActivity {

    private ListView lvCongViec;
    private ScheduleAdapter scheduleAdapter;
    private DatabaseHelper databaseHelper;
    private long employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lvCongViec = findViewById(R.id.lvCongViec);
        TextView caLam = findViewById(R.id.caLam);
        TextView lichDK = findViewById(R.id.lichDK);
        LinearLayout backToNV = findViewById(R.id.backToNV);
        databaseHelper = new DatabaseHelper(this);

        employeeId = getIntent().getLongExtra("EMPLOYEE_ID", -1);

        refreshData();  // Load "Đang chờ" schedules by default

        backToNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the NhanVienActivity
                Intent intent = new Intent(ScheduleActivity.this, NhanVienActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity
            }
        });

        caLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        lichDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegisteredSchedules();
            }
        });


        lvCongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule = (Schedule) parent.getItemAtPosition(position);
                long scheduleId = schedule.getScheduleId();
                Log.d("ScheduleActivity", "Schedule ID: " + scheduleId);

                Intent intent = new Intent(ScheduleActivity.this, ScheduleDetailActivity.class);
                intent.putExtra("SCHEDULE_ID", scheduleId);
                startActivity(intent);
            }
        });

    }

    public void refreshData() {
        List<Schedule> filteredSchedules = databaseHelper.getSchedulesByStatus("Đang chờ");
        scheduleAdapter = new ScheduleAdapter(this, filteredSchedules, employeeId,true);
        lvCongViec.setAdapter(scheduleAdapter);
    }

    private void filterSchedulesByStatus(List<String> statuses) {
        List<Schedule> filteredSchedules = databaseHelper.getSchedulesByEmployeeIdAndStatus(employeeId, statuses);
        scheduleAdapter = new ScheduleAdapter(this, filteredSchedules, employeeId,true);
        lvCongViec.setAdapter(scheduleAdapter);
    }

    private void loadRegisteredSchedules() {
        filterSchedulesByStatus(Arrays.asList("Đã xác nhận", "Đang làm việc"));
    }


}
