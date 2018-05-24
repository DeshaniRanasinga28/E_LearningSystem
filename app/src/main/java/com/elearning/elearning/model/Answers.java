package com.elearning.elearning.model;

public class Answers {
    private  String id;
    private String username;
    private String mcqNum;
    private String userAnswer;
    private String correctAnswer;
    private String maek;

    public Answers(String id, String username, String mcqNum, String userAnswer, String correctAnswer, String mark) {
        this.id = id;
        this.username = username;
        this.mcqNum = mcqNum;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.maek = mark;
    }

    public Answers(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMcqNum() {
        return mcqNum;
    }

    public void setMcqNum(String mcqNum) {
        this.mcqNum = mcqNum;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getMaek() {
        return maek;
    }

    public void setMaek(String maek) {
        this.maek = maek;
    }
}
