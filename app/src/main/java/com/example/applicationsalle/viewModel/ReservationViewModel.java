package com.example.applicationsalle.viewModel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.applicationsalle.entity.ReservationEntity;
import com.example.applicationsalle.repository.ReservationRepository;

import java.util.Date;
import java.util.List;


public class ReservationViewModel extends AndroidViewModel {

    private ReservationRepository repository;
    private LiveData<List<ReservationEntity>> allReservations;

    public ReservationViewModel(Application application) {
        super(application);
        repository = new ReservationRepository(application);
        allReservations = repository.getAllReservations();
    }

    public void insert(Date dateDebut, Date dateFin, String description) {
        if (isValidInput(dateDebut, dateFin, description)) {
            // Additional check for dateDebut being before dateFin
            if (dateDebut.before(dateFin)) {
                ReservationEntity reservation = new ReservationEntity(dateDebut, dateFin, description);
                repository.insert(reservation);
            } else {
                // Show a message for invalid date range
                Toast.makeText(getApplication(), "Invalid date range: DateDebut should be before DateFin", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a message for invalid input
            Toast.makeText(getApplication(), "Please verify all values", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(Date dateDebut, Date dateFin, String description) {
        // Add any additional validation logic as needed
        return dateDebut != null && dateFin != null && !TextUtils.isEmpty(description);
    }

    public LiveData<List<ReservationEntity>> getAllReservations() {
        return allReservations;
    }

    public void update(ReservationEntity reservation) {
        if (isValidInput(reservation.getDateDebut(), reservation.getDateFin(), reservation.getDescription())) {
            // Additional check for dateDebut being before dateFin
            if (reservation.getDateDebut().before(reservation.getDateFin())) {
                repository.update(reservation);
            } else {
                // Show a message for invalid date range
                Toast.makeText(getApplication(), "Invalid date range: DateDebut should be before DateFin", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a message for invalid input
            Toast.makeText(getApplication(), "Please verify all values", Toast.LENGTH_SHORT).show();
        }
    }

    // Define a method to get a reservation synchronously by ID
    public ReservationEntity getReservationByIdSync(int reservationId) {
        return repository.getReservationByIdSync(reservationId);
    }

    public void delete(ReservationEntity reservation) {
        repository.delete(reservation);
    }

}


