package com.example.eksamensprojekt2022;

public class Question {

    int questionID = 0;
    int fk_questionGroup = 0;
    String question = "";

    public Question(int fk_questionGroup, int questionID, String question) {
        this.fk_questionGroup = fk_questionGroup;
        this.questionID = questionID;
        this.question = question;
    }

    public Question() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getFk_questionGroup() {
        return fk_questionGroup;
    }

    public void setFk_questionGroup(int fk_questionGroup) {
        this.fk_questionGroup = fk_questionGroup;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
