package com.example.applicationsalle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button in the layout for adding a reservation
        Button reservationButton = findViewById(R.id.reservationButton);

        // Set click listener for the button to navigate to ReservationActivity
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ReservationActivity
                Intent intent = new Intent(MainActivity.this, ReservationActivity.class);

                // Start the ReservationActivity
                startActivity(intent);
            }
        });

        // Find the button in the layout for viewing reservations
        Button viewReservationButton = findViewById(R.id.viewReservation);

        // Set click listener for the button to navigate to ListReservation
        viewReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ListReservation
                Intent intent = new Intent(MainActivity.this, ListReservation.class);

                // Start the ListReservation
                startActivity(intent);
            }
        });
    }
}
