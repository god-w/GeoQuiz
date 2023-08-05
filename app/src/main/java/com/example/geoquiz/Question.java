package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    public Question(int testResId, boolean answerTrue){
        mTextResId = testResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }
}
