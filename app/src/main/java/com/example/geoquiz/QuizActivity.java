package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAB = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ISCHEAT = "isCheat";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private double score = 0;
    private ImageButton mNextButton;
    private ImageButton mPervButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private final Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_australia, true, false),
        new Question(R.string.question_oceans, true, false),
        new Question(R.string.question_mideast, false, false),
        new Question(R.string.question_africa, false, false),
        new Question(R.string.question_americas, true, false),
        new Question(R.string.question_asia, true, false)
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
        mQuestionTextView.setOnClickListener(this);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(this);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(this);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);
        mPervButton = (ImageButton) findViewById(R.id.prev_button);
        mPervButton.setOnClickListener(this);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.question_text_view:
                Toast.makeText(QuizActivity.this, "This is a questiont.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
            case R.id.prev_button:
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length -1) % mQuestionBank.length;
                updateQuestion();
                break;
            case R.id.next_button:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                RestoreAnswerButton();
                if(score >= 100){
                    score = 0;
                }
                break;
            case R.id.cheat_button:
                Intent intent = newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                break;
            default:
                break;
        }
    }
    private static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
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
        String ret = "";
        if(mQuestionBank[mCurrentIndex].ismIsCheater()){
            messageResId = R.string.judgment_toast;
            ret += getString(R.string.judgment_toast);
        }
        if(userPressedTrue == answerIsTrue){
            score += (double)100/mQuestionBank.length;
            ret += (" " + getString(R.string.correct_toast));
        }else{
            ret += (" " + getString(R.string.incorrect_toast));
        }
        ProhibitAnswerButton();
        Toast.makeText(this,ret+" Score: "+score, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mQuestionBank[mCurrentIndex].setmIsCheater(CheatActivity.wasAnswerShown(data));
        }
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