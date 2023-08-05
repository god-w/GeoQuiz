package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int testResId, boolean answerTrue){
        mTextResId = testResId;
        mAnswerTrue = answerTrue;
    }
}
