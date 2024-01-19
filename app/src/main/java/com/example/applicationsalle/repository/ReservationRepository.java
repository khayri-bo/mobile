package com.example.applicationsalle.repository;

// ReservationRepository.java
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import androidx.lifecycle.LiveData;



import com.example.applicationsalle.AppDatabase;
import com.example.applicationsalle.dao.ReservationDao;
import com.example.applicationsalle.entity.ReservationEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ReservationRepository {

    private ReservationDao reservationDao;
    private LiveData<List<ReservationEntity>> allReservations;

    public ReservationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        reservationDao = database.reservationDao();
        allReservations = reservationDao.getAllReservations();
    }

    public CompletableFuture<Void> insert(ReservationEntity reservation) {
        Log.d("ReservationRepository", "Inserting data: " + reservation.toString());

        // Use CompletableFuture to perform the database insertion asynchronously
        return CompletableFuture.runAsync(() -> {
            reservationDao.insert(reservation);
            Log.d("ReservationRepository", "Data inserted successfully");
        }).exceptionally(throwable -> {
            // Handle exceptions that might occur during asynchronous execution
            Log.e("ReservationRepository", "Error inserting data: " + throwable.getMessage());
            return null;
        });
    }


    // Example AsyncTask for updating a reservation
    public void update(ReservationEntity reservation) {
        new UpdateReservationAsyncTask(reservationDao).execute(reservation);
    }

    public LiveData<List<ReservationEntity>> getAllReservations() {
        return allReservations;
    }
    public void delete(ReservationEntity reservation) {
        new DeleteReservationAsyncTask(reservationDao).execute(reservation);
    }



    // Example AsyncTask for inserting a reservation
    private static class InsertReservationAsyncTask extends AsyncTask<ReservationEntity, Void, Void> {
        private ReservationDao reservationDao;

        private InsertReservationAsyncTask(ReservationDao reservationDao) {
            this.reservationDao = reservationDao;
        }

        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            reservationDao.insert(reservations[0]);
            return null;
        }
    }



    private static class UpdateReservationAsyncTask extends AsyncTask<ReservationEntity, Void, Void> {
        private ReservationDao reservationDao;

        private UpdateReservationAsyncTask(ReservationDao reservationDao) {
            this.reservationDao = reservationDao;
        }

        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            reservationDao.update(reservations[0]);
            return null;
        }
    }

    // Example AsyncTask for deleting a reservation
    private static class DeleteReservationAsyncTask extends AsyncTask<ReservationEntity, Void, Void> {
        private ReservationDao reservationDao;

        private DeleteReservationAsyncTask(ReservationDao reservationDao) {
            this.reservationDao = reservationDao;
        }

        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            reservationDao.delete(reservations[0]);
            return null;
        }
    }

    // Define a method to get a reservation synchronously by ID
    public ReservationEntity getReservationByIdSync(int reservationId) {
        try {
            return new GetReservationByIdAsyncTask(reservationDao).execute(reservationId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // AsyncTask to get a reservation by ID
    private static class GetReservationByIdAsyncTask extends AsyncTask<Integer, Void, ReservationEntity> {
        private ReservationDao reservationDao;

        private GetReservationByIdAsyncTask(ReservationDao reservationDao) {
            this.reservationDao = reservationDao;
        }

        @Override
        protected ReservationEntity doInBackground(Integer... ids) {
            return reservationDao.getReservationById(ids[0]);
        }
    }
}
