package edu.gatech.rattracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final String logTag = "RegistrationActivity";

        final EditText usernameButton = (EditText) findViewById(R.id.usernameInput);
        final EditText passwordButton = (EditText) findViewById(R.id.passwordInput);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.isAdminCheckBox);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button backButton = (Button) findViewById(R.id.backButton);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.clearUser();
                Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcome);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseManager firebaseManager = FirebaseManager.getInstance();

                boolean validData = BackendManager.validateUserPassword(usernameButton.getText().toString(), passwordButton.getText().toString(), getApplicationContext());
                final String username = usernameButton.getText().toString();
                final String password = passwordButton.getText().toString();
                final boolean isAdmin = checkbox.isChecked();

                if (!validData) {
                    return;
                }

                DatabaseReference authenticator = firebaseManager.authenticateListener(username);

                authenticator.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            // no user with this name exists; make them an acct
                            User.setUser(firebaseManager.writeNewUser(username, password, isAdmin));

                            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                            Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(profile);
                        } else {
                            Toast.makeText(getApplicationContext(), "A user with this username already exists", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
