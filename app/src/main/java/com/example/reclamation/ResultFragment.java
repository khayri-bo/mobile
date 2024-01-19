package com.example.reclamation;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import data.AppDatabase;
import data.Reclamation;
import data.User;
import data.data_dao;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static AppDatabase database;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        LinearLayout verticalLayout = view.findViewById(R.id.body_reclamation);
        Button backButton = view.findViewById(R.id.back_id);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Navigation.findNavController(view).navigate(R.id.navigation_frag);
            }});

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Database operations should be performed on a separate thread
                AppDatabase database = AppDatabase.getDatabase(requireContext());
                data_dao dao = database.userDao();
                List<Reclamation> x = dao.getAll();
              List<User> c=  dao.getalluser();
                System.out.println(c);

                // UI updates should be done on the main (UI) thread
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Iterate through the list and append rows to the LinearLayout
                        for (Reclamation reclamation : x) {
                            View row = inflater.inflate(R.layout.row_list_reclamation, container, false);
                            TextView userNameTextView = row.findViewById(R.id.fb_contenue);
                            TextView dataTextView = row.findViewById(R.id.fb_date);
                            TextView statusTextView = row.findViewById(R.id.status);

                            System.out.println(reclamation.id_user);
                            for (User user:c){
                                if (user.id == reclamation.id_user){
                                    userNameTextView.setText(user.username);
                                }
                            }
                            if (Objects.equals(reclamation.Status, "waiting")){
                                statusTextView.setText(reclamation.Status);

                            }else{
                                statusTextView.setText(reclamation.Status);
                                 statusTextView.setTextColor(Color.parseColor("#5afd0c"));
                            }


                            // Update the row with user-specific data
                            // For example, assuming there's a TextView in your row layout with id 'userName'


                            // Append the row to the LinearLayout
                            verticalLayout.addView(row);
                            ImageView itemUpdateImageView = row.findViewById(R.id.item_update);
                            itemUpdateImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Handle the click event here
                                    // For example, you can open a new activity or perform some action
                                    // when the ImageView is clicked.
                                    // You can replace the following line with your desired action.
                                    Bundle bundle = new Bundle();
                                    bundle.putString("paramKey", String.valueOf(reclamation.id_user));
                                    Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_detailFragment, bundle);


                                }
                            });
                        }
                    }
                });
            }
        });




        return view;
    }
}