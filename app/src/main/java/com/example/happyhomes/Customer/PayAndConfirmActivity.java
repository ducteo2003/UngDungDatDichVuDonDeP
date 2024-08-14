package com.example.happyhomes.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.Model.ServiceSchedule;
import com.example.happyhomes.databinding.ActivityPayAndConfirmBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Load data from the intent and display on the UI
        loadData();

        // Handle the event when the Post Job button is pressed
        binding.btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJobToDatabase();
            }
        });
    }

    private void loadData() {
        // Get data from the intent and display on the TextViews
        Intent intent = getIntent();
        if (intent != null) {
            String selectedDate = intent.getStringExtra("selectedDate");
            String selectedHour = intent.getStringExtra("selectedHour");
            String additionalRequest = intent.getStringExtra("additionalRequest");
            String adress = intent.getStringExtra("adress");
            binding.txtDate.setText(selectedDate);
            binding.txtTime.setText(selectedHour);
            binding.txtNote.setText(additionalRequest);
            binding.locationtext.setText(adress);
        } else {
            Toast.makeText(this, "Error: No data received.", Toast.LENGTH_LONG).show();
        }
    }

    private void saveJobToDatabase() {
        Intent intent = getIntent();
        int serviceId = intent.getIntExtra("selectedServiceId", -1);
        int cusId = 3;
        String note = binding.txtNote.getText().toString();
        String selectedDate = binding.txtDate.getText().toString(); // Expecting format "yyyy-MM-dd"
        String selectedHour = binding.txtTime.getText().toString(); // Expecting format "HH:mm:ss"

        if (serviceId == -1) {
            Toast.makeText(this, "Error: Service not selected.", Toast.LENGTH_LONG).show();
            return;
        }

        // Convert String to Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()); // Ensure this format matches the date string
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault()); // Updated format to match "04:48"

        try {
            Date date = dateFormat.parse(selectedDate);
            // Handle the case where the time might not include seconds
            Date startTime = timeFormat.parse(selectedHour);

            Schedule schedule = new Schedule(
                    null,
                    (long) cusId,
                    date,
                    startTime,
                    binding.locationtext.getText().toString(),
                    "Đang chờ"
            );

            // Save the schedule to the database
            long scheduleId = databaseHelper.addSchedule(schedule);
            if (scheduleId != -1) {
                ServiceSchedule serviceSchedule = new ServiceSchedule(
                        null,
                        (long) serviceId,
                        scheduleId
                );
                databaseHelper.addServiceSchedule(serviceSchedule);

                Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to save job.", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            Log.e("PayAndConfirmActivity", "Error parsing date or hour", e);
            Toast.makeText(this, "Failed to post job", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("PayAndConfirmActivity", "Error saving job to database", e);
            Toast.makeText(this, "Failed to post job", Toast.LENGTH_LONG).show();
        }

    }
}
