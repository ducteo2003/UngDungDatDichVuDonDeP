package com.example.happyhomes.Customer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityPayAndConfirmBinding;

public class PayAndConfirmActivity extends AppCompatActivity {
    ActivityPayAndConfirmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPayAndConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}