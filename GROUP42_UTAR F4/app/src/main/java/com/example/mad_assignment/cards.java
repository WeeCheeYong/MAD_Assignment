package com.example.mad_assignment;

public class cards {
    private int id;
    private int setId;
    private String question;
    private String answer;

    public cards() {
    }

    public cards(int id, int setId, String question, String answer) {
        this.id = id;
        this.setId = setId;
        this.question = question;
        this.answer = answer;
    }

    public cards(int setId, String question, String answer) {
        this.setId = setId;
        this.question = question;
        this.answer = answer;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
