package com.example.applicationsalle.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.applicationsalle.entity.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insert(ReservationEntity reservation);

    @Update
    void update(ReservationEntity reservation);

    @Delete
    void delete(ReservationEntity reservation);

    @Query("SELECT * FROM reservation_table")
    LiveData<List<ReservationEntity>> getAllReservations();

    @Query("SELECT * FROM reservation_table WHERE id = :reservationId")
    ReservationEntity getReservationById(int reservationId);
}
