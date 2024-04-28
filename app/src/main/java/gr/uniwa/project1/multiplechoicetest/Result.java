package gr.uniwa.project1.multiplechoicetest;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Result extends AppCompatActivity {
    ProgressBar scoreProgressBar;
    TextView score;
    Button nextButton;
    Questionnaire allQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        score = findViewById(R.id.scoreNumberTextView);
        DBHelper dbHelper = new DBHelper(this);
        allQuestions = new Questionnaire(dbHelper.getQuestionsFromDatabase(), 0);
        List<Question> randomQuestions = allQuestions.getRandomQuestions(5);
        int scoreValue = getIntent().getIntExtra("SCORE", 0);
        score.setText(scoreValue + "/" + randomQuestions.size());
        scoreProgressBar = findViewById(R.id.scoreProgressBar);
        int progress = scoreValue * 20;
        animateProgressBar(progress);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LastPage.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Animates the progress of the score progress bar.
     *
     * @param progress The target progress value.
     */
    private void animateProgressBar(int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(scoreProgressBar.getProgress(), progress);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                scoreProgressBar.setProgress(progress);
            }
        });
        animator.start();
    }
}
