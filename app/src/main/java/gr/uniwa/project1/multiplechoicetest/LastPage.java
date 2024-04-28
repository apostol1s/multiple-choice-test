package gr.uniwa.project1.multiplechoicetest;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

public class LastPage extends AppCompatActivity implements View.OnClickListener {

    TextView firstname;
    TextView lastname;
    TextView finalScore;
    TextView startTime;
    TextView endTime;
    Button tryAgainButton;
    Button exitButton;
    Questionnaire allQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_page);
        firstname = findViewById(R.id.firstnameTextView);
        lastname = findViewById(R.id.lastnameTextView);
        finalScore = findViewById(R.id.finalScoreTextView);
        startTime = findViewById(R.id.startTimeTextView);
        endTime = findViewById(R.id.endTimeTextView);
        startTime.setText(getFormattedTime(Test.startTime));
        endTime.setText(getFormattedTime(Test.endTime));
        tryAgainButton = findViewById (R.id.tryAgainButton);
        exitButton = findViewById(R.id.exitButton);
        displayLastScore();
        tryAgainButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        User user = User.getInstance();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        firstname.setText(firstName);
        lastname.setText(lastName);
    }

    @Override
    public void onClick(View view) {
        if (view == tryAgainButton) {
            Intent intent = new Intent(getApplicationContext(), FirstPage.class);
            startActivity(intent);
        }
        else if (view == exitButton) {
            finishAffinity();
        }
    }

    /**
     * Displays the last score obtained by the user.
     */
    private void displayLastScore() {
        DBHelper dbHelper = new DBHelper(this);
        List<Score> scores = dbHelper.getScoresFromDatabase();
        allQuestions = new Questionnaire(dbHelper.getQuestionsFromDatabase(), 0);
        if (!scores.isEmpty()) {
            Score latestScore = scores.get(0);
            int scoreValue = latestScore.getScore();
            List<Question> totalQuestions = allQuestions.getRandomQuestions(5);
            finalScore.setText(scoreValue + "/" + totalQuestions.size());
        } else {
            finalScore.setText("No score available");
        }
    }

    /**
     * Formats the given timestamp into HH:MM:SS format.
     *
     * @param timestamp The timestamp to be formatted.
     * @return The formatted time string.
     */
    private String getFormattedTime(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }
}
