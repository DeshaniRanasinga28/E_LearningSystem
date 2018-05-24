package com.elearning.elearning.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mcq implements Parcelable{
    private String id;
    private String moduleName;
    private String moduleCode;
    private String questionNum;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String correctAnswer;

    public Mcq(String id, String moduleName, String moduleCode, String questionNum, String question, String answerOne, String answerTwo, String answerThree, String correctAnswer) {
        this.id = id;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.questionNum = questionNum;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.correctAnswer = correctAnswer;
    }

    public Mcq(){

    }

    protected Mcq(Parcel in){
        id = in.readString();
        moduleName = in.readString();
        moduleCode = in.readString();
        questionNum = in.readString();
        question = in.readString();
        answerOne = in.readString();
        answerTwo = in.readString();
        answerThree = in.readString();
        correctAnswer = in.readString();
    }

    public static final Parcelable.Creator<Mcq> CREATOR = new Parcelable.Creator<Mcq>() {
        @Override
        public Mcq createFromParcel(Parcel in) {
            return new Mcq(in);
        }

        @Override
        public Mcq[] newArray(int size) {
            return new Mcq[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(moduleName);
        parcel.writeString(moduleCode);
        parcel.writeString(questionNum);
        parcel.writeString(question);
        parcel.writeString(answerOne);
        parcel.writeString(answerTwo);
        parcel.writeString(answerThree);
        parcel.writeString(correctAnswer);
    }
}
