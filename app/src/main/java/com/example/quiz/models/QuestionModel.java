package com.example.quiz.models;

public class QuestionModel {
    private int imagePath;
    private String variantA;
    private String variantB;
    private String variantC;
    private String variantD;
    private String variantTrue;

    public QuestionModel(int imagePath, String variantA, String variantB, String variantC, String variantD, String variantTrue) {
        this.imagePath = imagePath;
        this.variantA = variantA;
        this.variantB = variantB;
        this.variantC = variantC;
        this.variantD = variantD;
        this.variantTrue = variantTrue;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getVariantA() {
        return variantA;
    }

    public void setVariantA(String variantA) {
        this.variantA = variantA;
    }

    public String getVariantB() {
        return variantB;
    }

    public void setVariantB(String variantB) {
        this.variantB = variantB;
    }

    public String getVariantC() {
        return variantC;
    }

    public void setVariantC(String variantC) {
        this.variantC = variantC;
    }

    public String getVariantD() {
        return variantD;
    }

    public void setVariantD(String variantD) {
        this.variantD = variantD;
    }

    public String getVariantTrue() {
        return variantTrue;
    }

    public void setVariantTrue(String variantTrue) {
        this.variantTrue = variantTrue;
    }
}
