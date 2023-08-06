package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_ISCHEAT = "isCheat";
    private static final String KEY_CHEAT_NUMBER = "CheatNumber";
    private boolean isCheater;
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mCheatsNumberTextView;
    private TextView mShowAPITextView;
    private Button mShowAnswerButton;
    private static int cheatTimes = 0;//作弊次数
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            isCheater = savedInstanceState.getBoolean(KEY_ISCHEAT);
            setAnswerShownResult(true);
        }
        setContentView(R.layout.activity_cheat);
        /*
         * 从QuizActivity发过来的Intent获得答案
         */
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mShowAPITextView = (TextView)findViewById(R.id.show_api_text_view);
        mShowAPITextView.setText("API Level " + Build.VERSION.SDK_INT);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mCheatsNumberTextView = (TextView) findViewById(R.id.number_of_cheats_text_view);
        mCheatsNumberTextView.setText("Number of cheats: "+cheatTimes);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheater = true;
                cheatTimes += 1;
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else{
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        if(cheatTimes == 3){
            mShowAnswerButton.setEnabled(false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ISCHEAT, isCheater);
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}