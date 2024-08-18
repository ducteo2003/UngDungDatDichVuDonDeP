package com.example.happyhomes.NhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.R;

import java.util.Arrays;
import java.util.List;

public class LichSuCongViecActivity extends AppCompatActivity {

    private ListView lvLSCongViec;
    private ScheduleAdapter scheduleAdapter;
    private DatabaseHelper databaseHelper;
    private long employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_cong_viec);

        lvLSCongViec = findViewById(R.id.lvLSCongViec);

        databaseHelper = new DatabaseHelper(this);

        LinearLayout backToNV = findViewById(R.id.backToNV);

        employeeId = getIntent().getLongExtra("EMPLOYEE_ID", -1);

        loadCompletedSchedules();

        backToNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the NhanVienActivity
                Intent intent = new Intent(LichSuCongViecActivity.this, NhanVienActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity
            }
        });


        lvLSCongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected Schedule
                Schedule schedule = (Schedule) parent.getItemAtPosition(position);

                // Create an Intent to navigate to ScheduleDetailActivity
                Intent intent = new Intent(LichSuCongViecActivity.this, ScheduleDetailActivity.class);

                // Pass the scheduleId to the ScheduleDetailActivity
                intent.putExtra("SCHEDULE_ID", schedule.getScheduleId());

                // Start the ScheduleDetailActivity
                startActivity(intent);
            }
        });
    }


    private void loadCompletedSchedules() {
        List<Schedule> completedSchedules = databaseHelper.getSchedulesByEmployeeIdAndStatus(employeeId, Arrays.asList("Hoàn tất"));
        scheduleAdapter = new ScheduleAdapter(this, completedSchedules, employeeId, true);
        lvLSCongViec.setAdapter(scheduleAdapter);
    }

}
