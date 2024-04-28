package gr.uniwa.project1.multiplechoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Test extends AppCompatActivity implements View.OnClickListener {
    static long startTime;
    static long endTime;
    TextView questionCounter;
    TextView timer;
    ImageView questionImage;
    TextView questionView;
    Button [] answerButton;
    Button nextButton;
    Question currentQuestion;
    int currentQuestionNumber;
    Questionnaire allQuestions;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        startTime = System.currentTimeMillis();
        timer = findViewById(R.id.timerTextView);
        countDownTimer();
        questionCounter = findViewById(R.id.questionCounterTextView);
        questionImage = findViewById(R.id.questionImageView);
        questionView = findViewById(R.id.questionTextView);
        answerButton = new Button[4];
        answerButton[0] = findViewById(R.id.answer1Button);
        answerButton[1] = findViewById(R.id.answer2Button);
        answerButton[2] = findViewById(R.id.answer3Button);
        answerButton[3] = findViewById(R.id.answer4Button);
        DBHelper dbHelper = new DBHelper(this);
        allQuestions = new Questionnaire(dbHelper.getQuestionsFromDatabase(), 0);
        List<Question> randomQuestions = allQuestions.getRandomQuestions(5);
        allQuestions.setQuestions(randomQuestions);
        for (int i = 0; i < 4; i++) {
            answerButton[i].setOnClickListener(this);
        }
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        displayQuestion();
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 4; i++) {
            if (view == answerButton[i]) {
                String userAnswer = answerButton[i].getText().toString();
                allQuestions.getQuestionByNumber(currentQuestionNumber).setUserAnswer(userAnswer);
                doNext();
                return;
            }
        }
        if (view == nextButton) {
            doNext();
        }
    }

    /**
     * Moves to the next question or finishes the test if all questions have been answered.
     */
    private void doNext() {
        currentQuestionNumber = allQuestions.getNextUnansweredQuestion();
        if (currentQuestionNumber != -1) {
            currentQuestion = allQuestions.getQuestionByNumber(currentQuestionNumber);
            displayQuestion();
        } else {
            int score = calculateScore();
            Intent intent = new Intent(getApplicationContext(), Result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }

    }

    /**
     * Displays the current question on the screen.
     */
    private void displayQuestion() {
        currentQuestion = allQuestions.getQuestionByNumber(currentQuestionNumber);
        questionCounter.setText("Question " + (currentQuestionNumber + 1) + "/" + allQuestions.getTotalQuestions());
        questionView.setText(currentQuestion.getQuestionText());
        Picasso.get().load(currentQuestion.getImageName()).fit().into(questionImage);
        answerButton[0].setText(currentQuestion.getOptionA());
        answerButton[1].setText(currentQuestion.getOptionB());
        answerButton[2].setText(currentQuestion.getOptionC());
        answerButton[3].setText(currentQuestion.getOptionD());
    }

    /**
     * Calculates the score based on the user's answers and inserts it into the database.
     *
     * @return The calculated score.
     */
    public int calculateScore() {
        int score = 0;
        boolean answeredAtLeastOneQuestion = false;
        int userId = getIntent().getIntExtra("userId", User.getInstance().getId());
        for (Question question : allQuestions.getQuestions()) {
            if (question.isAnswered()) {
                answeredAtLeastOneQuestion = true;
                if (question.isAnswered() == question.isCorrect()) {
                    score++;
                }
            }
        }
        if (!answeredAtLeastOneQuestion) {
            score = 0;
        }
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.insertScore(score, userId);
        return score;
    }

    @Override
    protected void onStop() {
        super.onStop();
        endTime = System.currentTimeMillis();
    }

    /**
     * Starts a countdown timer for the test.
     */
    private void countDownTimer() {
        countDownTimer = new CountDownTimer((long) 300 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                long minutes = secondsRemaining / 60;
                long seconds = secondsRemaining % 60;
                timer.setText(String.format("%02d:%02d", minutes, seconds));
            }
            public void onFinish() {
                timer.setText("00:00");
                int score = calculateScore();
                Intent intent = new Intent(getApplicationContext(), Result.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
