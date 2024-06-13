package com.example.finaltodo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class EditProfileDialogActivity extends Dialog {

    private EditText editTextUsername, editTextEmail;
    private String currentUsername;
    private ProfileUpdateListener listener;

    public EditProfileDialogActivity(@NonNull Context context, String currentUsername) {
        super(context);
        this.currentUsername = currentUsername;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonSave = findViewById(R.id.buttonSave);

        Database db = new Database(getContext(), "finaltodo", null, 1);
        String[] userDetails = db.getUserDetails(currentUsername);
        if (userDetails != null) {
            editTextUsername.setText(userDetails[0]);
            editTextEmail.setText(userDetails[1]);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editTextUsername.getText().toString();
                String newEmail = editTextEmail.getText().toString();
                if (listener != null) {
                    listener.onProfileUpdated(newUsername, newEmail);
                }
                dismiss();
            }
        });
    }

    public void setProfileUpdateListener(ProfileUpdateListener listener) {
        this.listener = listener;
    }

    public interface ProfileUpdateListener {
        void onProfileUpdated(String newUsername, String newEmail);
    }
}
