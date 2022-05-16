package com.example.eksamensprojekt2022.Objeckts;

public class Answer {

    //forklaring af int her:
    //1 = ikke svaret
    //2 = ja
    //3 = nej
    //4 = ikke relevant

    int answerID = 0;
    String answerText = "";

    public Answer(int answerID, String answerText) {
        this.answerID = answerID;
        this.answerText = answerText;
    }

    public Answer() {
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
