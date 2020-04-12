package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    TextView currentPosition, questionAmount, timer;
    ImageView imageFLag;
    QuizManager manager;
    int timerCount = 0;
    RadioGroup radioGroup;
    boolean hasCheckedTheLastOne = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        loadDataToView(manager.getQuestionAt(manager.getPosition()));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timerCount++;
                runOnUiThread(() -> timer.setText(getFormattedTime(timerCount)));
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
        manager = new QuizManager(getQuestions());
        currentPosition = findViewById(R.id.currentPosition);
        questionAmount = findViewById(R.id.questionsAmount);
        timer = findViewById(R.id.timer);
        imageFLag = findViewById(R.id.imageFlag);
        radioGroup = findViewById(R.id.radioGroup);
        questionAmount.setText(String.valueOf(manager.getAllQuestionCount()));
    }

    private void loadDataToView(QuestionModel questionModel) {
        currentPosition.setText(String.valueOf(manager.getPosition() + 1));
        imageFLag.setImageResource(questionModel.getImagePath());
        radioGroup.clearCheck();
        ((RadioButton) radioGroup.getChildAt(0)).setText(questionModel.getVariantA());
        ((RadioButton) radioGroup.getChildAt(1)).setText(questionModel.getVariantB());
        ((RadioButton) radioGroup.getChildAt(2)).setText(questionModel.getVariantC());
        ((RadioButton) radioGroup.getChildAt(3)).setText(questionModel.getVariantD());
    }

    public void onSubmitButtonClicked(View view) {
        Toast.makeText(this, "Correct answers: " + manager.getCorrects(), Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onNextButtonClicked(View view) {
        if (manager.hasNext()) {
            if (getSelected() != -1) {
                manager.check(getSelected());
                manager.setPosition(manager.getPosition() + 1);
                loadDataToView(manager.getQuestionAt(manager.getPosition()));
            } else {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (hasCheckedTheLastOne) {
                Toast.makeText(this, "Correct answers: " + manager.getCorrects(), Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                if (getSelected() != -1) {
                    manager.check(getSelected());
                    findViewById(R.id.nexButton).setVisibility(View.INVISIBLE);
                    hasCheckedTheLastOne = true;
                } else {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getSelected() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (((RadioButton) radioGroup.getChildAt(i)).isChecked()) {
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
