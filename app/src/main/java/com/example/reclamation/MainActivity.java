package com.example.reclamation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import data.AppDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        AppDatabase.getDatabase(this);
        setContentView(R.layout.activity_main);
    }


}