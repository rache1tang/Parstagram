package com.example.parstagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";

    EditText etAccUser;
    EditText etAccPass;
    Button btnMakeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO);
        getSupportActionBar().setIcon(R.drawable.nav_logo_whiteout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etAccUser = findViewById(R.id.etAccUser);
        etAccPass = findViewById(R.id.etAccPass);
        btnMakeAccount = findViewById(R.id.btnMakeAcc);

        btnMakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etAccUser.getText().toString();
                String password = etAccPass.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Username is a required field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password is a required field", Toast.LENGTH_SHORT).show();
                    return;
                }

                makeAccount(username, password);
            }
        });
    }

    private void makeAccount(String username, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "Sign in to use the app!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    Log.e(TAG, "Error with sign up");
                }
            }
        });
    }
}