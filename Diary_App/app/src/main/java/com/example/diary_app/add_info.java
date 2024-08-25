package com.example.diary_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_info extends Fragment {

    private Button dateButton;
    private Button timeButton;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_info, container, false);

        dateButton = view.findViewById(R.id.dateButton);
        timeButton = view.findViewById(R.id.timeButton);

        // Initialize Calendar instance
        calendar = Calendar.getInstance();

        // Initialize date format for displaying
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Initialize time format for displaying in 12-hour format
        timeFormat12 = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        // Set initial values to button text
        updateDateButtonText();
        updateTimeButtonText();

        // Date button click listener
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Time button click listener
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        return view;
    }

    // Method to show date picker dialog
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateButtonText();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // Method to show time picker dialog
    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateTimeButtonText();
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // 12-hour format
        );
        timePickerDialog.show();
    }

    // Method to update date button text
    private void updateDateButtonText() {
        dateButton.setText(dateFormat.format(calendar.getTime()));
    }

    // Method to update time button text
    private void updateTimeButtonText() {
        timeButton.setText(timeFormat12.format(calendar.getTime()));
    }
}
