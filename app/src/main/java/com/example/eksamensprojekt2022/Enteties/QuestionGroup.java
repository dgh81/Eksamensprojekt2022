package com.example.eksamensprojekt2022.Enteties;

import java.util.ArrayList;

public class QuestionGroup {

    int questionGroupID = 0;
    String title = "";

    //måske vi ikke får brug for denne:
    ArrayList<Question> questions = new ArrayList<>();

    public QuestionGroup(int questionGroupID, String title) {
        this.questionGroupID = questionGroupID;
        this.title = title;
    }

    public int getTotalAnsweredQuestions() {

        int totalAnsweredQuestions = 0;

        for (Question q : questions ) {
            if (q.getAnswerID() != 1)
                totalAnsweredQuestions++;
        }
        return totalAnsweredQuestions;
    }

    public QuestionGroup() {
    }

    public int getQuestionGroupID() {
        return questionGroupID;
    }

    public void setQuestionGroupID(int questionGroupID) {
        this.questionGroupID = questionGroupID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    @Override
    public String toString() {
        return "QuestionGroup{" +
                "questionGroupID=" + questionGroupID +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }
}
