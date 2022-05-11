package com.example.eksamensprojekt2022;

public class Question {
    int fk_questionGroup = 0;
    int questionID = 0;
    String question = "";
    int answer = 1;
    public boolean isAnswered = !(answer == 1);
    String answerText = "";

    //forklaring af int her:
    //1 = ikke svaret
    //2 = ja
    //3 = nej
    //4 = ikke relevant


    public Question(int fk_questionGroup, int questionID, String question, int answer, boolean isAnswered, String answerText) {
        this.fk_questionGroup = fk_questionGroup;
        this.questionID = questionID;
        this.question = question;
        this.answer = answer;
        this.isAnswered = isAnswered;
        this.answerText = answerText;
    }

    public Question() {
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
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
