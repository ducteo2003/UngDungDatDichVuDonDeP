package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityMainCustomerBinding;

public class Main_CustomerActivity extends AppCompatActivity {

    private String cusID;

    ActivityMainCustomerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_customer);
        binding = ActivityMainCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadActivity();
    }
    public void loadActivity()
    {
        Intent intent = getIntent();
        if (intent != null) {
            String cusName = intent.getStringExtra("Cusname");
            String cusId = intent.getStringExtra("CusId");

            if (cusName != null && cusId != null) {
                binding.txtUserName.setText(String.format("Xin chào, "+ cusName));
                cusID = cusId;
            }
        }

    }
}