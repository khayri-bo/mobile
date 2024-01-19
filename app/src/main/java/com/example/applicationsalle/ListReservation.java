package com.example.applicationsalle;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.applicationsalle.adapter.ReservationAdapter;
import com.example.applicationsalle.entity.ReservationEntity;
import com.example.applicationsalle.viewModel.ReservationViewModel;
import java.util.List;

public class ListReservation extends AppCompatActivity {

    private ReservationViewModel reservationViewModel;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ReservationAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        reservationViewModel.getAllReservations().observe(this, reservations -> {
            adapter.setReservations(reservations);
        });

        // Set click listener for items in the RecyclerView
        adapter.setOnItemClickListener(new ReservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReservationEntity reservation) {
                // Handle item click (e.g., show details, update, etc.)
                // For simplicity, let's open the UpdateReservationActivity
                Intent intent = new Intent(ListReservation.this, UpdateReservationActivity.class);
                intent.putExtra(UpdateReservationActivity.EXTRA_RESERVATION_ID, reservation.getId());
                startActivity(intent);
            }
        });

        // Set delete click listener for items in the RecyclerView
        adapter.setOnDeleteClickListener(new ReservationAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(ReservationEntity reservation) {
                // Handle delete click, you can call a method in ViewModel to delete the reservation
                reservationViewModel.delete(reservation);
            }
        });

        // Set update click listener for items in the RecyclerView
        adapter.setOnUpdateClickListener(new ReservationAdapter.OnUpdateClickListener() {
            @Override
            public void onUpdateClick(ReservationEntity reservation) {
                // Handle update click, open the UpdateReservationActivity
                Intent intent = new Intent(ListReservation.this, UpdateReservationActivity.class);
                intent.putExtra(UpdateReservationActivity.EXTRA_RESERVATION_ID, reservation.getId());
                startActivity(intent);
            }
        });

        // Set click listener for "Add" button
        Button btnAddOne = findViewById(R.id.btn_addOne);
        btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ReservationActivity
                Intent intent = new Intent(ListReservation.this, ReservationActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for "Map" button
        Button btnMap = findViewById(R.id.Localisation);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MapActivity
                Intent intent = new Intent(ListReservation.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for "Back" button
        Button btnBack = findViewById(R.id.BackF);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity
                Intent intent = new Intent(ListReservation.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
