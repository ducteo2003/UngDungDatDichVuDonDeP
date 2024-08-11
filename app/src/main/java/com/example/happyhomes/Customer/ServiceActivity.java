package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Service;
import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityServiceBinding;
import java.io.IOException;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private ActivityServiceBinding binding;
    private DatabaseHelper databaseHelper;
    private List<Service> serviceList;
    private static final String TAG = "ServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        try {
            // Initialize and copy database if needed
            databaseHelper.createDatabase();
            Log.d(TAG, "Database created successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Error initializing database", e);
            Toast.makeText(this, "Error initializing database", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        if (address != null) {
            binding.address.setText(address);
        } else {
            binding.address.setText("No address selected");
        }

        // Load services from database and display them in RadioButtons
        loadServicesIntoRadioButtons();
    }

    private void loadServicesIntoRadioButtons() {
        // Open the database
        try {
            databaseHelper.openDatabase();
            Log.d(TAG, "Database opened successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error opening database", e);
            Toast.makeText(this, "Error opening database", Toast.LENGTH_LONG).show();
            return;
        }

        // Fetch services from the database
        serviceList = databaseHelper.getAllServices();

        // Check if service list is empty
        if (serviceList == null || serviceList.isEmpty()) {
            Log.d(TAG, "No services available in the database.");
            Toast.makeText(this, "No services available", Toast.LENGTH_LONG).show();
            return;
        }

        // Set service types to RadioButtons
        if (serviceList.size() > 0) {
            binding.radioTwoHours.setText(serviceList.get(0).getServiceType());
            Log.d(TAG, "Setting text for radioTwoHours: " + serviceList.get(0).getServiceType());
        }
        if (serviceList.size() > 1) {
            binding.radioThreeHours.setText(serviceList.get(1).getServiceType());
            Log.d(TAG, "Setting text for radioThreeHours: " + serviceList.get(1).getServiceType());
        }
        if (serviceList.size() > 2) {
            binding.radioFourHours.setText(serviceList.get(2).getServiceType());
            Log.d(TAG, "Setting text for radioFourHours: " + serviceList.get(2).getServiceType());
        }

        // Optional: Set default checked RadioButton
        binding.radioFourHours.setChecked(true);
        Log.d(TAG, "RadioFourHours set to checked.");
    }

}
