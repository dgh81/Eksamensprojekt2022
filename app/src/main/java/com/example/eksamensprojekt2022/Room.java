package com.example.eksamensprojekt2022;

import java.util.ArrayList;

public class Room {
    int roomID = 0;
    public Inspection inspection = null;
    String roomName = "";
    ArrayList<QuestionGroup> questionGroups = null;
    //TODO Husk at tilføje tabellerne (measurement)


    public Room(int roomID, Inspection inspection, String roomName, ArrayList<QuestionGroup> questionGroups) {
        this.roomID = roomID;
        this.inspection = inspection;
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
