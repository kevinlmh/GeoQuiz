package com.minghui_liu.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kevin on 9/28/15.
 */
public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.minghui_liu.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.minghui_liu.com.geoquiz.answer_shown";
    private static final String KEY_ANSWER_SHOWN = "answer_shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerShown = false;

    private TextView mAnswerTextView;
    private TextView mApiLevelTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER_SHOWN, mAnswerShown);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mApiLevelTextView = (TextView)findViewById(R.id.api_level_view);
        mApiLevelTextView.setText("API level " + Build.VERSION.SDK_INT);

        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
                mAnswerShown = true;
                setAnswerShownResult();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }

            }
        });

        if (savedInstanceState != null) {
            mAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
            if (mAnswerShown)
                mAnswerTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
        }

        setAnswerShownResult();

    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mAnswerShown);
        setResult(RESULT_OK, data);
    }
}
