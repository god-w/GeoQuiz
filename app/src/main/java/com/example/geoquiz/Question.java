package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsCheater;
    public Question(int testResId, boolean answerTrue, boolean isCheater){
        mTextResId = testResId;
        mAnswerTrue = answerTrue;
        mIsCheater = isCheater;
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

    public boolean ismIsCheater() {
        return mIsCheater;
    }

    public void setmIsCheater(boolean mIsCheater) {
        this.mIsCheater = mIsCheater;
    }
}
