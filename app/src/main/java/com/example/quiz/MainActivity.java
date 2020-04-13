package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz.managers.QuizManager;
import com.example.quiz.models.QuestionModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mCurrentPosition;
    private TextView mTimer;
    private ImageView mImageFLag;
    private EditText editText;
    private QuizManager mManager;
    private int mTimerCount = 0;
    private RadioGroup mRadioGroup;
    private boolean mHasCheckedTheLastOne = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        loadDataToView(mManager.getQuestionAt(mManager.getPosition()));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimerCount++;
                runOnUiThread(() -> mTimer.setText(getFormattedTime(mTimerCount)));
            }
        }, 1000, 1000);
    }

    private String getFormattedTime(int timerCount) {
        String timeFormat;
        String minute = String.valueOf(timerCount / 60);
        String second = String.valueOf(timerCount % 60);
        if (minute.length() < 2)
            minute = "0" + minute;
        if (second.length() < 2)
            second = "0" + second;
        timeFormat = minute + " : " + second;
        return timeFormat;
    }

    private void loadViews() {
        mManager = new QuizManager(getQuestions());
        mCurrentPosition = findViewById(R.id.currentPosition);
        TextView mQuestionAmount = findViewById(R.id.questionsAmount);
        mTimer = findViewById(R.id.timer);
        editText = findViewById(R.id.editText);
        mImageFLag = findViewById(R.id.imageFlag);
        mRadioGroup = findViewById(R.id.radioGroup);
        mQuestionAmount.setText(String.valueOf(mManager.getAllQuestionCount()));
    }

    private void loadDataToView(QuestionModel questionModel) {
        mCurrentPosition.setText(String.valueOf(mManager.getPosition() + 1));
        mImageFLag.setImageResource(questionModel.getImagePath());
        mRadioGroup.clearCheck();
        if (mManager.getPosition() < 8) {
            ((RadioButton) mRadioGroup.getChildAt(0)).setText(questionModel.getVariantA());
            ((RadioButton) mRadioGroup.getChildAt(1)).setText(questionModel.getVariantB());
            ((RadioButton) mRadioGroup.getChildAt(2)).setText(questionModel.getVariantC());
            ((RadioButton) mRadioGroup.getChildAt(3)).setText(questionModel.getVariantD());
        } else {
            mRadioGroup.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setText("");
        }
    }

    public void onSubmitButtonClicked(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.CORRECT_ANSWERS,mManager.getCorrects());
        intent.putExtra(ResultActivity.TIME,mTimer.getText().toString());
        startActivity(intent);
        finish();
    }

    public void onNextButtonClicked(View view) {
        if (mManager.hasNext()) {
            if (mManager.getPosition() > 7) {
                if (!editText.getText().toString().equals("")){
                    mManager.checkEditText(editText.getText().toString());
                    mManager.setPosition(mManager.getPosition() + 1);
                    loadDataToView(mManager.getQuestionAt(mManager.getPosition()));
                }else {
                    Toast.makeText(this, "Please write an answer", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (getSelected() != -1) {
                    mManager.checkRadioButton(getSelected());
                    mManager.setPosition(mManager.getPosition() + 1);
                    loadDataToView(mManager.getQuestionAt(mManager.getPosition()));
                } else {
                    Toast.makeText(this, getString(R.string.select_option), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (mHasCheckedTheLastOne) {
                Toast.makeText(this, getString(R.string.correct_answers) + mManager.getCorrects(), Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                if (!editText.getText().toString().equals("")){
                    mManager.checkEditText(editText.getText().toString());
                    findViewById(R.id.nexButton).setVisibility(View.INVISIBLE);
                    mHasCheckedTheLastOne = true;
                }else {
                    Toast.makeText(this, "Please write an answer", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getSelected() {
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            if (((RadioButton) mRadioGroup.getChildAt(i)).isChecked()) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<QuestionModel> getQuestions() {
        ArrayList<QuestionModel> list = new ArrayList<>();
        list.add(new QuestionModel(R.drawable.japan, "China", "Uzbekistan", "Russia", "Japan", "Japan"));
        list.add(new QuestionModel(R.drawable.uzbekistan, "Nigeria", "Uzbekistan", "Australia", "Japan", "Uzbekistan"));
        list.add(new QuestionModel(R.drawable.kazakhstan, "Kazakhstan", "Uzbekistan", "Russia", "Japan", "Kazakhstan"));
        list.add(new QuestionModel(R.drawable.united_kingdom, "United Kingdom", "Uzbekistan", "Russia", "Japan", "United Kingdom"));
        list.add(new QuestionModel(R.drawable.italy, "China", "Uzbekistan", "Russia", "Italy", "Italy"));
        list.add(new QuestionModel(R.drawable.germany, "Brasil", "Uzbekistan", "Russia", "Germany", "Germany"));
        list.add(new QuestionModel(R.drawable.south_korea, "Chile", "South Korea", "Russia", "Japan", "South Korea"));
        list.add(new QuestionModel(R.drawable.uruguay, "Argentina", "Uzbekistan", "Uruguay", "Japan", "Uruguay"));
        list.add(new QuestionModel(R.drawable.new_zealand, "India", "New Zealand", "Russia", "Japan", "New Zealand"));
        list.add(new QuestionModel(R.drawable.china, "China", "Uzbekistan", "Russia", "Japan", "China"));
        Collections.shuffle(list);
        return list;
    }
}
