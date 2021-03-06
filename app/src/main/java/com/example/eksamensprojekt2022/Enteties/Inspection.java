package com.example.eksamensprojekt2022.Enteties;

import java.util.ArrayList;

public class Inspection {

   private int inspectionID = 0;
   private int fk_questionID = 0;
   private int fk_answerID = 0;
   private int fk_roomID = 0;
   private int fk_inspectionInformationID = 0;
   private String comment = "";
   private ArrayList<QuestionGroup> questionGroups;


    public Inspection(int inspectionID, ArrayList<QuestionGroup> questionGroups) {
        this.inspectionID = inspectionID;
        this.questionGroups = questionGroups;
    }

    public void setQuestionGroups(ArrayList<QuestionGroup> questionGroups) {
        this.questionGroups = questionGroups;
    }

    public ArrayList<QuestionGroup> getQuestionGroups() {
        return questionGroups;
    }


    public Inspection(int inspectionID, int fk_questionID, int fk_answerID, int fk_inspectionInformationID, String comment) {
        this.inspectionID = inspectionID;
        this.fk_questionID = fk_questionID;
        this.fk_answerID = fk_answerID;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
        this.comment = comment;
    }

    public Inspection() {
    }

    public int getInspectionID() {
        return inspectionID;
    }

    public void setInspectionID(int inspectionID) {
        this.inspectionID = inspectionID;
    }

    public int getFk_questionID() {
        return fk_questionID;
    }

    public void setFk_questionID(int fk_questionID) {
        this.fk_questionID = fk_questionID;
    }

    public int getFk_answerID() {
        return fk_answerID;
    }

    public void setFk_answerID(int fk_answerID) {
        this.fk_answerID = fk_answerID;
    }

    public int getFk_roomID() {
        return fk_roomID;
    }

    public void setFk_roomID(int fk_roomID) {
        this.fk_roomID = fk_roomID;
    }

    public int getFk_inspectionInformationID() {
        return fk_inspectionInformationID;
    }

    public void setFk_inspectionInformationID(int fk_inspectionInformationID) {
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }
}
