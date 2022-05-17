package com.example.eksamensprojekt2022.Objeckts;

public class Question {

    //forklaring af int her:
    //1 = ikke svaret
    //2 = ja
    //3 = nej
    //4 = ikke relevant

    int questionID = 0;
    int fk_questionGroup = 0;
    String question = "";
    int answerID = 4;

    public int getAnswerID() {
        return answerID;
    }

    public boolean isAnswered() {
        return answerID != 1;
    }


    public Question(int fk_questionGroup, int questionID, String question) {
        this.fk_questionGroup = fk_questionGroup;
        this.questionID = questionID;
        this.question = question;
    }

    public Question(int questionID, int fk_questionGroup, String question, int answer) {
        this.questionID = questionID;
        this.fk_questionGroup = fk_questionGroup;
        this.question = question;
        this.answerID = answer;
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

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }


    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", fk_questionGroup=" + fk_questionGroup +
                ", question='" + question + '\'' +
                ", answerID=" + answerID +
                '}';
    }
}
