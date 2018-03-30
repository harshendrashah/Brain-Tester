package com.shahharshendra.braintester;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView readyTextView, steadyTextView, goTextView;
    TextView timerTextView, questionTextView,  scoreTextView, resultTextView;
    TextView optionA, optionB, optionC, optionD;
    Button playAgainButton;
    TextView finalScoreTextView;
    ConstraintLayout quizScreen, gameOver;

    int[] options = new int[4];
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        quizScreen.setVisibility(View.VISIBLE);
        gameOver.setVisibility(View.INVISIBLE);
        optionA.setClickable(true);
        optionB.setClickable(true);
        optionC.setClickable(true);
        optionD.setClickable(true);

        generateQuestion();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");

            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mediaPlayer.start();

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                optionA.setClickable(false);
                optionB.setClickable(false);
                optionC.setClickable(false);
                optionD.setClickable(false);
                gameOver.setVisibility(View.VISIBLE);
                finalScoreTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

            }
        }.start();
    }

    public void generateQuestion(){

        Random rand = new Random();
        int r1 = rand.nextInt(100);
        int r2 = rand.nextInt(100);
        questionTextView.setText(Integer.toString(r1) + " + " + Integer.toString(r2));
        locationOfCorrectAnswer = rand.nextInt(4);

        //options.clear();
        int incorrectAnswer;

        for (int i = 0; i < 4; i++){
            if (i == locationOfCorrectAnswer) {
                options[i] = r1 + r2;
            } else {
                if (rand.nextInt(2) == 0){
                    incorrectAnswer = r1 + r2 + rand.nextInt(21);
                } else {
                    incorrectAnswer = r1 + r2 - rand.nextInt(21);
                }

                while ((incorrectAnswer == r1 + r2 || incorrectAnswer == options[0]
                        || incorrectAnswer == options[1] || incorrectAnswer == options[2] || incorrectAnswer == options[3])) {
                    if (rand.nextInt(2) == 0) {
                        incorrectAnswer = r1 + r2 + rand.nextInt(21);
                    } else {
                        incorrectAnswer = r1 + r2 - rand.nextInt(6);
                    }
                }
                options[i] = incorrectAnswer;
            }
        }
        optionA.setText(Integer.toString(options[0]));
        optionB.setText(Integer.toString(options[1]));
        optionC.setText(Integer.toString(options[2]));
        optionD.setText(Integer.toString(options[3]));

    }

    public void checkAnswer(View view){
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
            resultTextView.setVisibility(View.VISIBLE);
        } else {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        resultTextView.setVisibility(View.VISIBLE);
        generateQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readyTextView = findViewById(R.id.readyTextView);
        steadyTextView = findViewById(R.id.steadyTextView);
        goTextView = findViewById(R.id.goTextView);

        timerTextView = findViewById(R.id.timerTextView);
        questionTextView =findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        finalScoreTextView = findViewById(R.id.final_score_text_view);

        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);

        quizScreen = findViewById(R.id.quizScreen);
        gameOver = findViewById(R.id.gameOver);

        readyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyTextView.setVisibility(View.INVISIBLE);
                steadyTextView.setVisibility(View.VISIBLE);
            }
        });

        steadyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steadyTextView.setVisibility(View.INVISIBLE);
                goTextView.setVisibility(View.VISIBLE);
            }
        });

        goTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTextView.setVisibility(View.INVISIBLE);
                quizScreen.setVisibility(View.VISIBLE);

                playAgain(findViewById(R.id.playAgainButton));

            }
        });


    }
}
