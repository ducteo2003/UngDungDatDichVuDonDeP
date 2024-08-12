package com.example.happyhomes.Customer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivitySelectTimeCustomerBinding;

import java.util.Calendar;

public class SelectTimeCustomerActivity extends AppCompatActivity {

    ActivitySelectTimeCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectTimeCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up date and time pickers
        setupDatePicker();
        setupTimePicker();
        addEvents();
    }

    private void addEvents() {
        // When "Next" button is clicked
        binding.btnNext.setOnClickListener(view -> {
            TextView dateTextView = binding.dateTextView;
            TextView hourPicker = binding.hourPicker;
            EditText additionalRequestEditText = binding.edtNote; // Assuming the EditText ID is "edtNote"

            // Retrieve service ID from the incoming Intent
            Intent incomingIntent = getIntent();
            int selectedServiceId = incomingIntent.getIntExtra("selectedServiceId", -1);

            // Retrieve values from the views
            String selectedDate = dateTextView.getText().toString();
            String selectedHour = hourPicker.getText().toString();
            String additionalRequest = additionalRequestEditText.getText().toString();

            // Create an Intent to start PayAndConfirmActivity
            Intent payAndConfirmIntent = new Intent(SelectTimeCustomerActivity.this, PayAndConfirmActivity.class);

            // Add the retrieved values to the Intent as extras
            payAndConfirmIntent.putExtra("selectedServiceId", selectedServiceId);
            payAndConfirmIntent.putExtra("selectedDate", selectedDate);
            payAndConfirmIntent.putExtra("selectedHour", selectedHour);
            payAndConfirmIntent.putExtra("additionalRequest", additionalRequest);

            // Start the PayAndConfirmActivity
            startActivity(payAndConfirmIntent);
        });
    }

    private void setupDatePicker() {
        binding.dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        binding.dateTextView.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });
    }

    private void setupTimePicker() {
        binding.timePickerLayout.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, selectedHour, selectedMinute) -> {
                        String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        binding.hourPicker.setText(time);
                    },
                    hour, minute, true
            );

            timePickerDialog.show();
        });
    }
}
