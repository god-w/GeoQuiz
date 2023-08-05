package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAB = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;

    private double score = 0;

    private ImageButton mNextButton;
    private ImageButton mPervButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAB, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, "This is a questiont.", Toast.LENGTH_SHORT).show();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                RestoreAnswerButton();
                if(score >= 100){
                    score = 0;
                }
            }
        });

        mPervButton = (ImageButton) findViewById(R.id.prev_button);
        mPervButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length -1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAB, "onStart(Bundle) called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAB, "onResume(Bundle) called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAB, "onPause(Bundle) called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAB, "onStop(Bundle) called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAB, "onDestroy(Bundle) called");
        super.onDestroy();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAB, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            score += (double)100/mQuestionBank.length;
            Toast.makeText(this,"correct! Score: "+score, Toast.LENGTH_SHORT).show();
        } else {
            messageResId = R.string.incorrect_toast;
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                    .show();
        }
        ProhibitAnswerButton();
    }

    public void ProhibitAnswerButton() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    public void RestoreAnswerButton() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }
}