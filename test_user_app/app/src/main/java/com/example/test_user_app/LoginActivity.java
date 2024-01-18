package com.example.test_user_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test_user_app.DAO.UserDao;
import com.example.test_user_app.database.UserDatabase;
import com.example.test_user_app.entities.User;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    private UserDatabase appDatabase;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appDatabase = UserDatabase.getInstance(this);
        userDao = appDatabase.userDao();

        EditText usernameEditText = findViewById(R.id.EditUsername);
        EditText passwordEditText = findViewById(R.id.EditPassword);
        Button loginButton = findViewById(R.id.LoginButton);
        TextView signUpTextView = findViewById(R.id.Register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSignUp();
            }
        });
    }

    private void loginUser() {
        String username = getUsernameInput();
        String password = getPasswordInput();

        if (validateInputs(username, password)) {
            User user = userDao.getUserByUsername(username);
            if (user != null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
                // Login successful
                showLoginSuccessMessage();
                navigateToHome();
            } else {
                // Login failed
                showLoginFailedMessage();
            }
        } else {
            showFieldCheckMessage();
        }
    }


    private String getUsernameInput() {
        return ((EditText) findViewById(R.id.EditUsername)).getText().toString().trim();
    }

    private String getPasswordInput() {
        return ((EditText) findViewById(R.id.EditPassword)).getText().toString().trim();
    }

    private boolean validateInputs(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void showFieldCheckMessage() {
        // Display a message indicating that fields should be checked
        // You may want to use a Toast or another UI element for this
    }

    private void showLoginSuccessMessage() {
        // Display a message indicating that login was successful
        // You may want to use a Toast or another UI element for this
    }

    private void showLoginFailedMessage() {
        // Display a message indicating that login failed
        // You may want to use a Toast or another UI element for this
    }

    private void navigateToSignUp() {
        // Navigate to the registration screen
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void navigateToHome() {
        // Navigate to the home screen or main activity
        // You can replace the placeholder with your main activity class
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Optional: finish the current activity to prevent going back to it from the home screen
    }
}