package com.example.reclamation;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;

import data.AppDatabase;
import data.Reclamation;
import data.User;
import data.data_dao;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reclamerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reclamerFragment extends Fragment {
    private MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object Toast;

    public reclamerFragment() {
        // Required empty public constructor
    }
    private static AppDatabase database;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reclamerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static reclamerFragment newInstance(String param1, String param2) {
        reclamerFragment fragment = new reclamerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private static int MICROPHONE_PERMISSIONS_CODE = 200;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isMicrophonePresent()){
            getMicrophonePermission();
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize the database only if it's not already initialized
        if (database == null) {
            database = Room.databaseBuilder(requireContext().getApplicationContext(), AppDatabase.class, "room2_DB_db")
                    .fallbackToDestructiveMigration() // For simplicity, consider using migration strategies
                    .build();
        }

        // Use 'database' as needed in your activity
        data_dao dao = database.userDao();
        // ... perform database operations


    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reclamer, container, false);

        Button cancelButton = view.findViewById(R.id.Cancel);

        Button addButton = view.findViewById(R.id.btnSend);
        EditText EditTextFeedBack = view.findViewById(R.id.EditTextFeedBack);
       EditText EditTextUsername = view.findViewById(R.id.EditTextUsername);








        cancelButton.setOnClickListener(new View.OnClickListener() {
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

                Navigation.findNavController(view).navigate(R.id.action_reclamerFragment_to_choise_admin_client_Fragment);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Database operations should be performed on a separate thread
                        AppDatabase database = AppDatabase.getDatabase(requireContext());
                        data_dao dao = database.userDao();
                        User newUser = new User();
                        newUser.setbody(String.valueOf(EditTextUsername.getText()));

                        Reclamation newReclamation = new Reclamation();
                        LocalDateTime currentDateTime = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            currentDateTime = LocalDateTime.now();
                        }
                        dao.insert(newUser);
                        newReclamation.setData(String.valueOf(EditTextFeedBack.getText()), String.valueOf(currentDateTime),dao.getLastUser(),"waiting");
                        dao.insertcelamation(newReclamation);

                    }
                });

               Navigation.findNavController(view).navigate(R.id.action_reclamerFragment_to_choise_admin_client_Fragment);
            }
        });


        return  view;
    }

}