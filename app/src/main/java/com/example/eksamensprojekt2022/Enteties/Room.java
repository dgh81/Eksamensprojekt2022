package com.example.eksamensprojekt2022.Enteties;

import java.util.ArrayList;

public class Room {

    int fk_projectID = 0;
    int inspected = 0;
    int roomID = 0;
    String roomName = "";


    public void setFk_projectID(int fk_projectID) {
        this.fk_projectID = fk_projectID;
    }

    public void setInspected(int inspected) {
        this.inspected = inspected;
    }



    public int getFk_projectID() {
        return fk_projectID;
    }

    public int getInspected() {
        return inspected;
    }




//TODO Husk at tilføje tabellerne (measurement)

    public Room(int roomID,String roomName, int inspected, int fk_projectID) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.inspected = inspected;
        this.fk_projectID = fk_projectID;
    }

    public Room(String roomName, int inspected, int fk_projectID) {
        this.roomName = roomName;
        this.inspected = inspected;
        this.fk_projectID = fk_projectID;
    }

    public Room(int roomID, String roomName , ArrayList<QuestionGroup> questionGroups) {
        this.roomID = roomID;
        this.roomName = roomName;
    }

    public Room() {
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    @Override
    public String toString() {
        return "Room{" +
                "fk_projectID=" + fk_projectID +
                ", inspected=" + inspected +
                ", roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
