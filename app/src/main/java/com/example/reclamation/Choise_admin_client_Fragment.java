package com.example.reclamation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.util.concurrent.Executors;

import data.AppDatabase;
import data.User;
import data.data_dao;

public class Choise_admin_client_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private static AppDatabase database;

    public Choise_admin_client_Fragment() {
        // Required empty public constructor
    }

    public static Choise_admin_client_Fragment newInstance(String param1, String param2) {
        Choise_admin_client_Fragment fragment = new Choise_admin_client_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize the database only if it's not already initialized
        if (database == null) {
            database = Room.databaseBuilder(requireContext().getApplicationContext(), AppDatabase.class, "room2_DB_1")
                    .fallbackToDestructiveMigration() // For simplicity, consider using migration strategies
                    .build();
        }

        // Use 'database' as needed in your activity
        data_dao dao = database.userDao();
        // ... perform database operations
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choise_admin_client_, container, false);

        Button step1Button = view.findViewById(R.id.send);
        Button adminButton = view.findViewById(R.id.other);

        step1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Database operations should be performed on a separate thread
                        //AppDatabase database = AppDatabase.getDatabase(requireContext());
                      //  data_dao dao = database.userDao();

//to_step1
}
                });

                Navigation.findNavController(view).navigate(R.id.reclamer_action);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Database operations should be performed on a separate thread
                   /*     AppDatabase database = AppDatabase.getDatabase(requireContext());
                        data_dao dao = database.userDao();
                        User newUser = new User();
                        newUser.setUsername("newUsername");

// The 'id' field will be automatically generated
                        dao.insert(newUser);*/
                    }
                });

                 Navigation.findNavController(view).navigate(R.id.to_admin_action);
            }
        });

        return view;
    }





}
