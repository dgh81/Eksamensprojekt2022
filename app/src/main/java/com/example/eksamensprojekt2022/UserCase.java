package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.Room;

import java.util.ArrayList;

public class UserCase {

    private static MySQL mySQL = new MySQL();

    public static ArrayList<ProjectInformation> getAllProjectInformation() {

        return mySQL.getProjectInformation();

    }


    public static void createProjectInformationInDataBase(ProjectInformation projectInformation) {
        mySQL.createProjectInformation(projectInformation);
    }


    public static void createRoomEmptyInProject(int projectId) {

    }

    public static ProjectInformation getRoomsForAProjectInformation(ProjectInformation projectInformation) {

        ArrayList<Room> rooms =  mySQL.getRoomsIDAndNameFromProjectID(projectInformation.getProjectInformationID());

        for (Room r: rooms ) {
            projectInformation.getRooms().add(r);
            System.out.println(r);
        }
        return projectInformation;


    }

    public static void createRoomFromName(String name , int ID ) {
        mySQL.createRoom(name, ID );
    }






}
