package com.example.eksamensprojekt2022;

import java.util.ArrayList;

public class Room {
    int roomID = 0;
    public Inspection inspection = null;
    //DGH tilføjet fk:
    int fk_InspectionID = 0;
    String roomName = "";

    //Ikke sikker på at vi skal bruge denne?
    ArrayList<QuestionGroup> questionGroups = null;

    //TODO Husk at tilføje tabellerne (measurement)

    public Room(int roomID, Inspection inspection, int fk_InspectionID, String roomName, ArrayList<QuestionGroup> questionGroups) {
        this.roomID = roomID;
        this.inspection = inspection;
        this.fk_InspectionID = fk_InspectionID;
        this.roomName = roomName;
        this.questionGroups = questionGroups;
    }

    public Room() {
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public int getFk_InspectionID() {
        return fk_InspectionID;
    }

    public void setFk_InspectionID(int fk_InspectionID) {
        this.fk_InspectionID = fk_InspectionID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<QuestionGroup> getQuestionGroups() {
        return questionGroups;
    }

    public void setQuestionGroups(ArrayList<QuestionGroup> questionGroups) {
        this.questionGroups = questionGroups;
    }
}
