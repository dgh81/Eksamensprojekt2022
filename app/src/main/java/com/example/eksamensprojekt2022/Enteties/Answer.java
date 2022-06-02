package com.example.eksamensprojekt2022.Enteties;

public class Answer {

    //forklaring af int her:
    //1 = ikke svaret
    //2 = ja
    //3 = nej
    //4 = ikke relevant

    int answerID = 0;
    String answerText = "";
    int questionGroupID = 0;
    int questionID = 0;
    String comment;

    public Answer(int answerID, String answerText) {
        this.answerID = answerID;
        this.answerText = answerText;
    }

    public Answer(int answerID, int questionGroupID, int questionID, String comment) {
        this.answerID = answerID;
        this.questionGroupID = questionGroupID;
        this.questionID = questionID;
        this.comment = comment;
    }


    public String getComment() {
        return comment;
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

    public int getQuestionGroupID() {
        return questionGroupID;
    }

    public int getQuestionID() {
        return questionID;
    }
}
