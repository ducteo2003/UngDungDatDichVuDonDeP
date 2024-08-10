package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityServiceBinding;

public class ServiceActivity extends AppCompatActivity {

    ActivityServiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getdata();
    }

    private void getdata() {
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        // Hiển thị thông tin vị trí lên giao diện
        TextView txtLocation = findViewById(R.id.txtLocation);
        txtLocation.setText("Địa chỉ: " + address);
    }


}