package com.example.applicationsalle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationsalle.viewModel.ReservationViewModel;

import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private ReservationViewModel reservationViewModel;
    private DatePicker datePickerDebut;
    private DatePicker datePickerFin;
    private EditText edtDescription;
    private Button btnSaveCourse;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        datePickerDebut = findViewById(R.id.datePickerDebut);
        datePickerFin = findViewById(R.id.datePickerFin);
        edtDescription = findViewById(R.id.idEdtDescription);
        btnSaveCourse = findViewById(R.id.idBtnSaveCourse);
        btnCancel = findViewById(R.id.Cancel);
        if (btnSaveCourse != null) {
            btnSaveCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int yearDebut = datePickerDebut.getYear();
                    int monthDebut = datePickerDebut.getMonth();
                    int dayDebut = datePickerDebut.getDayOfMonth();
                    Calendar calendarDebut = Calendar.getInstance();
                    calendarDebut.set(yearDebut, monthDebut, dayDebut);

                    int yearFin = datePickerFin.getYear();
                    int monthFin = datePickerFin.getMonth();
                    int dayFin = datePickerFin.getDayOfMonth();
                    Calendar calendarFin = Calendar.getInstance();
                    calendarFin.set(yearFin, monthFin, dayFin);

                    Date dateDebut = calendarDebut.getTime();
                    Date dateFin = calendarFin.getTime();

                    String description = edtDescription.getText().toString();

                    // Validate DateDebut and DateFin
                    if (dateDebut.after(dateFin)) {
                        showToast("DateDebut should be before DateFin");
                    } else if (description.isEmpty()) {
                        showToast("Description cannot be empty");
                    } else {

                        reservationViewModel.insert(dateDebut, dateFin, description);

                        // Display a message
                        Toast.makeText(ReservationActivity.this, "Reservation saved", Toast.LENGTH_SHORT).show();
                        // Navigate to ListReservationActivity
                        Intent intent = new Intent(ReservationActivity.this, ListReservation.class);
                        startActivity(intent);
                        // Close the current activity
                        finish();
                    }
                }
                private void showToast(String message) {
                    Toast.makeText(ReservationActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (btnCancel != null) {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to MainActivity when Cancel button is clicked
                    Intent intent = new Intent(ReservationActivity.this, MainActivity.class);
                    startActivity(intent);
                    // Close the current activity
                    finish();
                }
            });
        }


    }
}

