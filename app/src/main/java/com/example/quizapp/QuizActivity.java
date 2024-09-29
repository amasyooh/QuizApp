package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private Button submitButton;
    private RadioGroup optionsGroup;
    private TextView questionTextView;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 1 minute for the entire quiz

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private final int totalQuestions = 10;
    private boolean quizFinished = false; // To prevent showing results after submission
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        submitButton = findViewById(R.id.submit_button);
        optionsGroup = findViewById(R.id.options_group);
        questionTextView = findViewById(R.id.question_text);
        timerTextView = findViewById(R.id.timer_text);

        // Initialize shared preferences to store high score
        sharedPreferences = getSharedPreferences("QuizApp", MODE_PRIVATE);

        // Load and shuffle questions
        loadQuestions();
        Collections.shuffle(questionList);
        questionList = questionList.subList(0, totalQuestions);

        // Display the first question
        displayQuestion();

        // Start the countdown timer for the entire quiz session
        startTimer();

        // Submit button click listener
        submitButton.setOnClickListener(v -> {
            int selectedOptionId = optionsGroup.getCheckedRadioButtonId();

            if (selectedOptionId == -1) {
                // No option is selected
                Toast.makeText(QuizActivity.this, "Please select an answer before submitting", Toast.LENGTH_SHORT).show();
            } else {
                // Option selected, proceed to check the answer
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                checkAnswer(selectedAnswer);

                currentQuestionIndex++;
                if (currentQuestionIndex < totalQuestions) {
                    loadNextQuestion();
                } else {
                    endQuiz(); // End quiz if all questions are answered
                }
            }
        });
    }

    // Function to start the countdown timer for the entire quiz session
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                // If the quiz hasn't been finished manually (by answering all questions), end it
                if (!quizFinished) {
                    Toast.makeText(QuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    endQuiz(); // End quiz if time runs out
                }
            }
        }.start();
    }

    // Function to update the timer display
    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    // Function to check the answer
    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            score++; // Increment score if correct
        }
    }

    // Function to load the next question
    private void loadNextQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            displayQuestion(); // Display next question
        }
    }

    // Display the current question and options
    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestionText());

        RadioButton option1 = findViewById(R.id.option_1);
        RadioButton option2 = findViewById(R.id.option_2);
        RadioButton option3 = findViewById(R.id.option_3);
        RadioButton option4 = findViewById(R.id.option_4);

        option1.setText(currentQuestion.getOptions().get(0));
        option2.setText(currentQuestion.getOptions().get(1));
        option3.setText(currentQuestion.getOptions().get(2));
        option4.setText(currentQuestion.getOptions().get(3));

        optionsGroup.clearCheck(); // Clear previous selection
    }

    // Function to load questions
    private void loadQuestions() {
        questionList = new ArrayList<>();

        // Adding the first set of 10 questions
        questionList.add(new Question("What is the capital of Australia?", "Canberra", List.of("Sydney", "Melbourne", "Perth", "Canberra")));
        questionList.add(new Question("What is the capital of France?", "Paris", List.of("Paris", "London", "Rome", "Berlin")));
        questionList.add(new Question("What is the capital of USA?", "Washington, D.C.", List.of("New York", "Washington, D.C.", "Los Angeles", "Chicago")));
        questionList.add(new Question("What is the capital of Japan?", "Tokyo", List.of("Osaka", "Kyoto", "Tokyo", "Nagoya")));
        questionList.add(new Question("What is the capital of Canada?", "Ottawa", List.of("Toronto", "Ottawa", "Vancouver", "Montreal")));
        questionList.add(new Question("What is the capital of India?", "New Delhi", List.of("Mumbai", "Kolkata", "New Delhi", "Chennai")));
        questionList.add(new Question("What is the capital of Germany?", "Berlin", List.of("Munich", "Hamburg", "Berlin", "Frankfurt")));
        questionList.add(new Question("What is the capital of Brazil?", "Brasília", List.of("Rio de Janeiro", "São Paulo", "Brasília", "Salvador")));
        questionList.add(new Question("What is the capital of Italy?", "Rome", List.of("Milan", "Venice", "Rome", "Florence")));
        questionList.add(new Question("What is the capital of Russia?", "Moscow", List.of("Saint Petersburg", "Moscow", "Kazan", "Sochi")));

        // Adding 30 more questions
        questionList.add(new Question("What is the capital of Egypt?", "Cairo", List.of("Alexandria", "Cairo", "Giza", "Luxor")));
        questionList.add(new Question("What is the capital of South Africa?", "Pretoria", List.of("Cape Town", "Pretoria", "Johannesburg", "Durban")));
        questionList.add(new Question("What is the capital of Argentina?", "Buenos Aires", List.of("Rosario", "Córdoba", "Mendoza", "Buenos Aires")));
        questionList.add(new Question("What is the capital of Mexico?", "Mexico City", List.of("Cancún", "Guadalajara", "Mexico City", "Monterrey")));
        questionList.add(new Question("What is the capital of South Korea?", "Seoul", List.of("Busan", "Incheon", "Seoul", "Daegu")));
        questionList.add(new Question("What is the capital of Spain?", "Madrid", List.of("Barcelona", "Madrid", "Seville", "Valencia")));
        questionList.add(new Question("What is the capital of Portugal?", "Lisbon", List.of("Porto", "Lisbon", "Faro", "Braga")));
        questionList.add(new Question("What is the capital of New Zealand?", "Wellington", List.of("Auckland", "Wellington", "Christchurch", "Hamilton")));
        questionList.add(new Question("What is the capital of Greece?", "Athens", List.of("Thessaloniki", "Athens", "Heraklion", "Larissa")));
        questionList.add(new Question("What is the capital of Thailand?", "Bangkok", List.of("Chiang Mai", "Bangkok", "Phuket", "Pattaya")));
        questionList.add(new Question("What is the capital of Turkey?", "Ankara", List.of("Istanbul", "Ankara", "Izmir", "Antalya")));
        questionList.add(new Question("What is the capital of Nigeria?", "Abuja", List.of("Lagos", "Abuja", "Ibadan", "Kano")));
        questionList.add(new Question("What is the capital of Chile?", "Santiago", List.of("Valparaíso", "Concepción", "Santiago", "Arica")));
        questionList.add(new Question("What is the capital of Norway?", "Oslo", List.of("Bergen", "Stavanger", "Oslo", "Trondheim")));
        questionList.add(new Question("What is the capital of Sweden?", "Stockholm", List.of("Gothenburg", "Malmö", "Stockholm", "Uppsala")));
        questionList.add(new Question("What is the capital of Finland?", "Helsinki", List.of("Tampere", "Turku", "Helsinki", "Espoo")));
        questionList.add(new Question("What is the capital of Poland?", "Warsaw", List.of("Kraków", "Łódź", "Warsaw", "Gdańsk")));
        questionList.add(new Question("What is the capital of Switzerland?", "Bern", List.of("Zurich", "Geneva", "Bern", "Basel")));
        questionList.add(new Question("What is the capital of Austria?", "Vienna", List.of("Salzburg", "Innsbruck", "Vienna", "Graz")));

        // Example question
        questionList.add(new Question("What is the capital of Australia?", "Canberra", List.of("Sydney", "Melbourne", "Perth", "Canberra")));
        // Add the remaining 39 questions...
    }

    // End the quiz and update the high score
    private void endQuiz() {
        quizFinished = true; // Mark the quiz as finished
        countDownTimer.cancel(); // Stop the timer

        // Calculate wrong answers
        int wrongAnswers = totalQuestions - score;

        // Get the stored high score
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        // Update high score if current score is greater
        if (score > highScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply(); // Save new high score
            highScore = score; // Set new high score
        }

        // Create an Intent to start the ResultActivity and pass the results
        Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
        resultIntent.putExtra("SCORE", score);
        resultIntent.putExtra("CORRECT_ANSWERS", score); // Score represents correct answers
        resultIntent.putExtra("WRONG_ANSWERS", wrongAnswers);
        resultIntent.putExtra("HIGH_SCORE", highScore); // Pass the updated high score

        // Start the ResultActivity
        startActivity(resultIntent);
        finish(); // Close QuizActivity
    }
}
