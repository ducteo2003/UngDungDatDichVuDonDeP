package com.example.happyhomes.Customer;

import android.os.Bundle;
import android.util.Log; // Import for logging
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Service;
import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityServiceBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private ActivityServiceBinding binding;
    private DatabaseHelper databaseHelper;
    private List<Service> serviceList;
    private ArrayAdapter<String> adapter;
    private static final String TAG = "ServiceActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        serviceList = new ArrayList<>();

        // Use Data Binding to find ListView
        ListView serviceListView = binding.serviceListView;

        try {
            // Initialize and copy the database if necessary
            databaseHelper.createDatabase();
            Log.d(TAG, "Database created successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Error initializing database", e);
            Toast.makeText(this, "Error initializing database", Toast.LENGTH_LONG).show();
            return;
        }

        // Load services from the database and display them
        loadServices(serviceListView);
    }

    private void loadServices(ListView listView) {
        // Open the database
        try {
            databaseHelper.openDatabase();
            Log.d(TAG, "Database opened successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error opening database", e);
            Toast.makeText(this, "Error opening database", Toast.LENGTH_LONG).show();
            return;
        }

        // Get services from database
        serviceList = databaseHelper.getAllServices();

        // Check if serviceList is empty
        if (serviceList == null || serviceList.isEmpty()) {
            Log.d(TAG, "No services found in the database.");
            Toast.makeText(this, "No services available", Toast.LENGTH_LONG).show();
            return;
        }

        // Convert to String list for adapter
        ArrayList<String> serviceNames = new ArrayList<>();
        for (Service service : serviceList) {
            serviceNames.add(service.getServiceType());
        }

        // Check if serviceNames list is populated
        if (serviceNames.isEmpty()) {
            Log.d(TAG, "Service names list is empty.");
        } else {
            Log.d(TAG, "Service names list populated with " + serviceNames.size() + " items.");
        }

        // Setup adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, serviceNames);
        listView.setAdapter(adapter);
        Log.d(TAG, "Adapter set with " + serviceNames.size() + " items.");
    }
}
