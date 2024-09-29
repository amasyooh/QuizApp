package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText;
    private ImageView profileImage1, profileImage2;
    private SharedPreferences sharedPreferences;
    private String selectedProfileImage = "ic_male"; // Default profile image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        profileImage1 = findViewById(R.id.profile_image_1); // Male image
        profileImage2 = findViewById(R.id.profile_image_2); // Female image
        Button saveButton = findViewById(R.id.save_button); // Save button only

        // Load shared preferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // Load previously saved profile data
        firstNameEditText.setText(sharedPreferences.getString("FIRST_NAME", ""));
        lastNameEditText.setText(sharedPreferences.getString("LAST_NAME", ""));
        selectedProfileImage = sharedPreferences.getString("PROFILE_IMAGE", "ic_male");

        // Set the currently selected profile image based on saved data
        updateProfileImageSelection(selectedProfileImage);

        // Set profile image 1 (Male) click listener
        profileImage1.setOnClickListener(v -> {
            selectedProfileImage = "ic_male"; // Set to male image
            updateProfileImageSelection(selectedProfileImage);
        });

        // Set profile image 2 (Female) click listener
        profileImage2.setOnClickListener(v -> {
            selectedProfileImage = "ic_female"; // Set to female image
            updateProfileImageSelection(selectedProfileImage);
        });

        // Save button click listener
        saveButton.setOnClickListener(v -> {
            // Save profile data to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FIRST_NAME", firstNameEditText.getText().toString());
            editor.putString("LAST_NAME", lastNameEditText.getText().toString());
            editor.putString("PROFILE_IMAGE", selectedProfileImage);
            editor.apply();

            // Show success message
            Toast.makeText(ProfileActivity.this, "Profile saved successfully!", Toast.LENGTH_LONG).show();

            // Pass updated data back to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("FIRST_NAME", firstNameEditText.getText().toString());
            resultIntent.putExtra("LAST_NAME", lastNameEditText.getText().toString());
            resultIntent.putExtra("PROFILE_IMAGE", selectedProfileImage);

            // Set the result and finish the activity (automatically returns to MainActivity)
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // Method to update the selected profile image (highlight the selected image)
    private void updateProfileImageSelection(String selectedProfileImage) {
        if ("ic_male".equals(selectedProfileImage)) {
            profileImage1.setAlpha(1f);  // Highlight the male image
            profileImage2.setAlpha(0.5f); // Dim the female image
        } else {
            profileImage1.setAlpha(0.5f); // Dim the male image
            profileImage2.setAlpha(1f);  // Highlight the female image
        }
    }
}
