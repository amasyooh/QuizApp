package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView highScoreValueTextView;
    private TextView nameTextView;
    private ImageView profileIcon;
    private SharedPreferences sharedPreferences;

    private static final int PROFILE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this is the correct layout file

        // Initialize views
        highScoreValueTextView = findViewById(R.id.high_score_value); // High score value
        profileIcon = findViewById(R.id.profile_icon); // Profile image
        nameTextView = findViewById(R.id.name_text); // Name of the user
        Button startQuizButton = findViewById(R.id.start_quiz_button); // Start quiz button

        // Load shared preferences for user data and high score
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // Retrieve and display high score
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);
        highScoreValueTextView.setText(String.valueOf(highScore));

        // Update profile details (name and profile icon)
        updateProfileDetails();

        // Start quiz when the button is clicked
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        // Clicking the profile icon opens ProfileActivity to edit profile
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivityForResult(intent, PROFILE_REQUEST_CODE); // Expect a result from ProfileActivity
        });
    }

    // Update profile details on the home screen
    private void updateProfileDetails() {
        // Retrieve the first name, last name, and profile image from SharedPreferences
        String firstName = sharedPreferences.getString("FIRST_NAME", "First Name");
        String lastName = sharedPreferences.getString("LAST_NAME", "Last Name");
        String profileImage = sharedPreferences.getString("PROFILE_IMAGE", "ic_male");

        // Update name display (full name)
        nameTextView.setText(firstName + " " + lastName);

        // Update profile image based on the saved profile image in SharedPreferences
        int profileResId = getResources().getIdentifier(profileImage, "drawable", getPackageName());
        if (profileResId != 0) {
            profileIcon.setImageResource(profileResId); // Use saved profile image
        } else {
            profileIcon.setImageResource(R.drawable.ic_profile); // Fallback to default image
        }
    }

    // Handle result from ProfileActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            // If profile was updated, refresh the profile details
            updateProfileDetails();
        }

    }

}

