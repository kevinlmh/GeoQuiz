package com.minghui_liu.geoquiz;

/**
 * Created by Minghui Liu on 9/22/15.
 */
public class TrueFalse {
    private int mQuestion;
    private boolean mAnswerTrue;

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public TrueFalse(int question, boolean answerTrue) {
        mQuestion = question;
        mAnswerTrue = answerTrue;
    }
}
