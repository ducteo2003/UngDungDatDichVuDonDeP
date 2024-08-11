package com.example.happyhomes.Customer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivitySelectTimeCustomerBinding;

import java.util.Calendar;

public class SelectTimeCustomerActivity extends AppCompatActivity {

    ActivitySelectTimeCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectTimeCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up date and time pickers
        setupDatePicker();
        setupTimePicker();
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
                        binding.hourPicker.setText(time.split(":")[0]);
                        binding.minutePicker.setText(time.split(":")[1]);
                    },
                    hour, minute, true
            );

            timePickerDialog.show();
        });
    }
}
