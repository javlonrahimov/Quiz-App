package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public static final String CORRECT_ANSWERS = "951";
    public static final String TIME = "512";
    private TextView correctAnswers;
    private TextView timeSpent;
    private TextView finalQuestionResult;
    private LinearLayout results;
    private LinearLayout finalQuestion;
    CheckBox uae;
    CheckBox india;
    CheckBox china;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        correctAnswers = findViewById(R.id.correctAnswersResult);
        timeSpent = findViewById(R.id.timeResult);
        finalQuestionResult = findViewById(R.id.finalQuestionResult);
        results = findViewById(R.id.results);
        finalQuestion = findViewById(R.id.finalQuestion);
        uae = findViewById(R.id.uae);
        india = findViewById(R.id.india);
        china = findViewById(R.id.china);
    }

    public void onShowResultClicked(View view){
        finalQuestion.setVisibility(View.INVISIBLE);
        results.setVisibility(View.VISIBLE);
        if (getIntent()!=null){
            correctAnswers.setText(String.valueOf(getIntent().getIntExtra(CORRECT_ANSWERS,0)));
            timeSpent.setText(getIntent().getStringExtra(TIME));
            if (checkFinalQuestion()){
                finalQuestionResult.setText(getString(R.string.correct1));
            }else {
                finalQuestionResult.setText(getString(R.string.wrong1));
            }
        }
    }

    public boolean checkFinalQuestion(){
        return uae.isChecked() && china.isChecked() && india.isChecked();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
