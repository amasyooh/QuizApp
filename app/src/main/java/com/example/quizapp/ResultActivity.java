package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView correctAnswersTextView;
    private TextView wrongAnswersTextView;
    private TextView highScoreTextView;
    private Button playAgainButton;
    private Button returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        scoreTextView = findViewById(R.id.score_text);
        correctAnswersTextView = findViewById(R.id.correct_answers_text);
        wrongAnswersTextView = findViewById(R.id.wrong_answers_text);
        highScoreTextView = findViewById(R.id.highest_score_text);
        playAgainButton = findViewById(R.id.play_again_button);
        returnHomeButton = findViewById(R.id.return_home_button);

        // Get the quiz results from Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0);
        int wrongAnswers = intent.getIntExtra("WRONG_ANSWERS", 0);
        int highScore = intent.getIntExtra("HIGH_SCORE", 0);

        // Set the result values in TextViews
        scoreTextView.setText("Final Score: " + score);
        correctAnswersTextView.setText("Correct Answers: " + correctAnswers);
        wrongAnswersTextView.setText("Wrong Answers: " + wrongAnswers);
        highScoreTextView.setText("Highest Score: " + highScore);

        // Play Again button listener - Restart the quiz
        playAgainButton.setOnClickListener(v -> {
            Intent playAgainIntent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(playAgainIntent);
            finish(); // Close ResultActivity
        });

        // Return Home button listener - Go to home page (MainActivity)
        returnHomeButton.setOnClickListener(v -> {
            Intent returnHomeIntent = new Intent(ResultActivity.this, MainActivity.class);
            returnHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(returnHomeIntent);
            finish(); // Close ResultActivity
        });
    }
}
