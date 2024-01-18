package com.example.test_user_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test_user_app.DAO.UserDao;
import com.example.test_user_app.database.UserDatabase;
import com.example.test_user_app.entities.User;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    private UserDatabase appDatabase;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        appDatabase = UserDatabase.getInstance(this);
        userDao = appDatabase.userDao();

        EditText emailEditText = findViewById(R.id.EditEmail);
        EditText telephoneEditText = findViewById(R.id.EditPhone);
        EditText passwordEditText = findViewById(R.id.EditPassword);
        Button registerButton = findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = getUsernameInput();
        String email = getEmailInput();
        String password = getPasswordInput();
        String telephone = getTelephoneInput();

        if (validateInputs(username, email, password, telephone)) {
            String hashedPassword = hashPassword(password);

            User existingUser = userDao.getUserByEmail(email);
            if (existingUser != null) {
                showEmailTakenMessage();
            } else {
                User newUser = new User(username, hashedPassword, email, telephone);
                userDao.addUser(newUser);
                showRegistrationSuccessMessage();
                navigateToLogin();
                List<User> users = userDao.getAll();
                for (User user : users) {
                    Log.d("Database", "User: " + user.getUsername() + ", Email: " + user.getEmail());
                }
            }
        } else {
            showFieldCheckMessage();
        }
    }


    private String getEmailInput() {
        return ((EditText) findViewById(R.id.EditUsername)).getText().toString().trim();
    }
    private String getUsernameInput() {
        return ((EditText) findViewById(R.id.EditEmail)).getText().toString().trim();
    }

    private String getPasswordInput() {
        return ((EditText) findViewById(R.id.EditPassword)).getText().toString().trim();
    }

    private String getTelephoneInput() {
        return ((EditText) findViewById(R.id.EditPhone)).getText().toString().trim();
    }

    private boolean validateInputs(String email, String password, String telephone, String s) {
        return !email.isEmpty() && !password.isEmpty() && !telephone.isEmpty();
    }

    private String hashPassword(String password) {
        int logRounds = 10;
        return BCrypt.withDefaults().hashToString(logRounds, password.toCharArray());
    }

    private void showFieldCheckMessage() {
        Toast.makeText(this, "Check Your fields!!!", Toast.LENGTH_SHORT).show();
    }

    private void showEmailTakenMessage() {
        Toast.makeText(this, "Email already taken, please choose another one", Toast.LENGTH_SHORT).show();
    }

    private void showRegistrationSuccessMessage() {
        Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Optional: finish the current activity to prevent going back to it from the login screen
    }
}
