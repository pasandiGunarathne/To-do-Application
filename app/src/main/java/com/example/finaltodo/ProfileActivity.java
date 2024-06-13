package com.example.finaltodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText textViewProfileEmail;
    private TextView textViewProfileName;
    private Database db;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewProfileName = findViewById(R.id.textView2);
        textViewProfileEmail = findViewById(R.id.textViewProfileEmail);
        Button logoutButton = findViewById(R.id.buttonLogout);
        Button editProfileButton = findViewById(R.id.buttonEditAccount);

        db = new Database(this, "finaltodo", null, 1);

        // Replace this with logic to get current logged-in user
        currentUsername = "currentLoggedInUser";

        // Retrieve and display user details
        loadUserDetails();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });
    }

    private void loadUserDetails() {
        String[] userDetails = db.getUserDetails(currentUsername);
        if (userDetails != null) {
            textViewProfileName.setText(userDetails[0]);
            textViewProfileEmail.setText(userDetails[1]);
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoglogout, null);
        builder.setView(dialogView);

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Log out ?");

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        dialogMessage.setText("Are you sure want to logout?");

        Button cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        Button logoutBtn = dialogView.findViewById(R.id.logoutBtn);

        AlertDialog dialog = builder.create();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });

        dialog.show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the profile activity
    }

    private void showEditProfileDialog() {
        EditProfileDialogActivity editProfileDialog = new EditProfileDialogActivity(this, currentUsername);
        editProfileDialog.setProfileUpdateListener(new EditProfileDialogActivity.ProfileUpdateListener() {
            @Override
            public void onProfileUpdated(String newUsername, String newEmail) {
                db.updateUserDetails(currentUsername, newUsername, newEmail);
                currentUsername = newUsername;
                loadUserDetails();
            }
        });
        editProfileDialog.show();
    }
}
