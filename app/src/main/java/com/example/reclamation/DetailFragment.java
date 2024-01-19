package com.example.reclamation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;

import data.AppDatabase;
import data.Reclamation;
import data.User;
import data.data_dao;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static AppDatabase database;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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

        Bundle bundle = getArguments();

        assert bundle != null;
        String paramValue = bundle.getString("paramKey", "defaultValue");
            System.out.println("Received Parameter: " + paramValue);            // Use the paramValue as needed
        View view= inflater.inflate(R.layout.fragment_detail, container, false);

        // Inflate the layout for this fragment
        TextView userNameTextView = view.findViewById(R.id.textusername);
        TextView dateTextView = view.findViewById(R.id.textdate);
        TextView bodyTextView = view.findViewById(R.id.textbody);
        Button doneButton = view.findViewById(R.id.donebutton);
        Button deletebutton = view.findViewById(R.id.deletebutton);


        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(requireContext());
                        data_dao dao = database.userDao();
                        dao.deleteReclamationById(Integer.parseInt(paramValue));
                    }
                });

                Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_resultFragment);
            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(requireContext());
                        data_dao dao = database.userDao();
                        dao.updateReclamationStatus(Integer.parseInt(paramValue),"DONE");
                    }
                });

                Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_resultFragment);
            }
        });


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getDatabase(requireContext());
                data_dao dao = database.userDao();
                Reclamation x=dao.getreclamation(Integer.parseInt(paramValue));
                User y=dao.getuser(x.id_user);
                System.out.println(x.id_user);
                System.out.println(y.username);
                userNameTextView.setText(y.username);
                dateTextView.setText(x.date);
                bodyTextView.setText(x.body);



            }
        });

          // Use the paramValue as needed

        return  view;
    }
}