package com.example.eksamensprojekt2022;

public class Question {
    int fk_questionGroup = 0;
    int questionID = 0;
    String question = "";
    int answer = 0;
    public boolean isAnswered = !(answer == 0);

    //forklaring af int her:
    //0 = ikke svaret
    //1 = ja
    //2 = nej
    //3 = ikke relevant


    public Question(int fk_questionGroup, int questionID, String question, int answer, boolean isAnswered) {
        this.fk_questionGroup = fk_questionGroup;
        this.questionID = questionID;
        this.question = question;
        this.answer = answer;
        this.isAnswered = isAnswered;
    }

    public Question() {
    }

    public int getFk_questionGroup() {
        return fk_questionGroup;
    }

    public void setFk_questionGroup(int fk_questionGroup) {
        this.fk_questionGroup = fk_questionGroup;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
