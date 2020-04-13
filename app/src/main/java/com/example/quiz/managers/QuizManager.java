package com.example.quiz.managers;

import com.example.quiz.models.QuestionModel;

import java.util.ArrayList;

public class QuizManager {
    private ArrayList<QuestionModel> list;
    private int position = 0;
    private int corrects = 0;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuizManager(ArrayList<QuestionModel> list) {
        this.list = list;
    }

    public QuestionModel getQuestionAt(int position) {
        return list.get(position);
    }

    private String getStringByIndex(int index) {
        String string = "";

        switch (index) {
            case 0: {
                string = list.get(position).getVariantA();
                break;
            }
            case 1: {
                string = list.get(position).getVariantB();
                break;
            }
            case 2: {
                string = list.get(position).getVariantC();
                break;
            }
            case 3: {
                string = list.get(position).getVariantD();
                break;
            }
        }
        return string;
    }

    public int getAllQuestionCount() {
        return list.size();
    }

    public int getCorrects() {
        return corrects;
    }

    public void checkRadioButton(int index) {
        if (getStringByIndex(index).equalsIgnoreCase(list.get(position).getVariantTrue())) {
            corrects++;
        }
    }

    public void checkEditText(String string){
        if (string.trim().equalsIgnoreCase(list.get(position).getVariantTrue())){
            corrects++;
        }
    }

    public boolean hasNext() {
        return list.size() != position + 1;
    }
}
