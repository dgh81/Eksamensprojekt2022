package com.example.eksamensprojekt2022;

import java.util.ArrayList;

public class Room {

    int roomID = 0;
    String roomName = "";

    //TODO Husk at tilføje tabellerne (measurement)

    public Room(int roomID, String roomName, ArrayList<QuestionGroup> questionGroups) {
        this.roomID = roomID;
        this.roomName = roomName;
    }

    public Room() {
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

}
