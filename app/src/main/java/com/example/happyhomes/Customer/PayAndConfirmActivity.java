package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.ServiceDetail;
import com.example.happyhomes.databinding.ActivityPayAndConfirmBinding;

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
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String selectedHour = getIntent().getStringExtra("selectedHour");
        String additionalRequest = getIntent().getStringExtra("additionalRequest");
        binding.txtDate.setText(selectedDate);
        binding.txtTime.setText(selectedHour);
        binding.txtNote.setText(additionalRequest);
    }

    private void saveJobToDatabase() {
        // Lấy dữ liệu từ intent và các view
        Intent intent = getIntent();
        int serviceId = intent.getIntExtra("selectedServiceId", -1); // -1 nếu không tìm thấy
        int cusId = 1; // Giả sử một customer ID (thay thế bằng logic thực tế)
        int payId = 1; // Giả sử một payment ID (thay thế bằng logic thực tế)
        String note = binding.txtNote.getText().toString();

        // Đảm bảo rằng serviceId hợp lệ
        if (serviceId == -1) {
            Toast.makeText(this, "Error: Service not selected.", Toast.LENGTH_LONG).show();
            return;
        }
        // Tạo đối tượng ServiceDetail
        ServiceDetail serviceDetail = new ServiceDetail(cusId, serviceId, payId, note);

        try {
            databaseHelper.addServiceDetail(serviceDetail);
            Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("PayAndConfirmActivity", "Error saving job to database", e);
            Toast.makeText(this, "Failed to post job", Toast.LENGTH_LONG).show();
        }
    }
}
