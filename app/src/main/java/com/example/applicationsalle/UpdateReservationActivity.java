// UpdateReservationActivity.java
package com.example.applicationsalle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.applicationsalle.entity.ReservationEntity;
import com.example.applicationsalle.viewModel.ReservationViewModel;
import java.util.Calendar;
import java.util.Date;

public class UpdateReservationActivity extends AppCompatActivity {

    public static final String EXTRA_RESERVATION_ID = "extra_reservation_id";
    private ReservationViewModel reservationViewModel;
    private DatePicker datePickerDebut;
    private DatePicker datePickerFin;
    private EditText editTextUpdateDescription; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        int reservationId = getIntent().getIntExtra(EXTRA_RESERVATION_ID, -1);
        datePickerDebut = findViewById(R.id.datePickerDebutUpdate);
        datePickerFin = findViewById(R.id.datePickerFinUpdate);
        editTextUpdateDescription = findViewById(R.id.idEdtDescriptionUpdate);

        // Retrieve existing reservation details
        ReservationEntity existingReservation = reservationViewModel.getReservationByIdSync(reservationId);

        // Set the DatePicker values
        Calendar calDebut = Calendar.getInstance();
        calDebut.setTime(existingReservation.getDateDebut());
        datePickerDebut.updateDate(
                calDebut.get(Calendar.YEAR),
                calDebut.get(Calendar.MONTH),
                calDebut.get(Calendar.DAY_OF_MONTH)
        );

        Calendar calFin = Calendar.getInstance();
        calFin.setTime(existingReservation.getDateFin());
        datePickerFin.updateDate(
                calFin.get(Calendar.YEAR),
                calFin.get(Calendar.MONTH),
                calFin.get(Calendar.DAY_OF_MONTH)
        );

        // Set the EditText value
        editTextUpdateDescription.setText(existingReservation.getDescription());

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            // Update existingReservation with new dates and description
            int yearDebut = datePickerDebut.getYear();
            int monthDebut = datePickerDebut.getMonth();
            int dayDebut = datePickerDebut.getDayOfMonth();
            Calendar calendarDebut = Calendar.getInstance();
            calendarDebut.set(yearDebut, monthDebut, dayDebut);
            Date newDateDebut = calendarDebut.getTime();

            int yearFin = datePickerFin.getYear();
            int monthFin = datePickerFin.getMonth();
            int dayFin = datePickerFin.getDayOfMonth();
            Calendar calendarFin = Calendar.getInstance();
            calendarFin.set(yearFin, monthFin, dayFin);
            Date newDateFin = calendarFin.getTime();

            String newDescription = editTextUpdateDescription.getText().toString();

            // Validate DateDebut and DateFin
            if (newDateDebut.after(newDateFin)) {
                showToast("DateDebut should be before DateFin");
            } else if (newDescription.isEmpty()) {
                showToast("Description cannot be empty");
            } else {

                existingReservation.setDateDebut(newDateDebut);
                existingReservation.setDateFin(newDateFin);
                existingReservation.setDescription(newDescription);

                reservationViewModel.update(existingReservation);

                // Show a message or navigate to ListReservationActivity
                showMessageInListReservationActivity();
            }
        });

        Button btnCancel = findViewById(R.id.Cancel);
        btnCancel.setOnClickListener(v -> {
            // Navigate to ListReservationActivity when Cancel button is clicked
            Intent intent = new Intent(UpdateReservationActivity.this, ListReservation.class);
            startActivity(intent);
            // Close the current activity
            finish();
        });
    }

    private void showMessageInListReservationActivity() {
        Intent intent = new Intent(UpdateReservationActivity.this, ListReservation.class);
        intent.putExtra("message", "Reservation updated");
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(UpdateReservationActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
