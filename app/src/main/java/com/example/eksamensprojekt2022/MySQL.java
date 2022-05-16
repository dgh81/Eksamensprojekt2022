package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.Room;
import com.example.eksamensprojekt2022.Objeckts.User;

import java.sql.*;
import java.util.ArrayList;

public class MySQL implements Runnable {

    public MySQL() {}

    public static Connection connection;



    public void createProjectInformation(ProjectInformation projectInformation) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ProjectInformation (customerName, customerAddress, customerPostalCode, customerCity, installationIdentification, installationName) VALUES ('"
                    + projectInformation.getCustomerName() + "', '"
                    + projectInformation.getCustomerAddress() + "', '"
                    + projectInformation.getCustomerPostalCode() + "', '"
                    + projectInformation.getCustomerCity() + "', '"
                    + projectInformation.getInstallationIdentification() + "', '"
                    + projectInformation.getInstallationName() +"');");
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProjectInformation> getProjectInformation() {
        ArrayList<ProjectInformation> projectInformationList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ProjectInformation");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                ProjectInformation projectInformation = new ProjectInformation();

                //System.out.print(rs.getString("answerText" + " - "));
                projectInformation.setProjectInformationID(rs.getInt("ID"));
                projectInformation.setCustomerName(rs.getString("customerName"));
                projectInformation.setCustomerAddress(rs.getString("customerAddress"));
                projectInformation.setCustomerPostalCode(rs.getString("customerPostalCode"));
                projectInformation.setInstallationIdentification(rs.getString("installationIdentification"));
                projectInformation.setInstallationName(rs.getString("installationName"));
                projectInformation.setCustomerCity(rs.getString("customerCity"));
                projectInformationList.add(projectInformation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectInformationList;
    }



    @Override
    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db?useSSL=false";
            connection = DriverManager.getConnection(url, "danielguldberg_dk", "280781");
            System.out.println("connection created is: " + (connection != null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User logUserIn(String username, String password) {
        User loggedInUser = new User();
        boolean userCreated = false;
        try {

            System.out.println(connection + " From login page");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User " +
                    "WHERE username = '" + username + "'" +
                    " AND password = '" + password + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("1" + rs.getInt(1));
                System.out.println("2" + rs.getString(2));
                System.out.println("3" + rs.getString(3));

                loggedInUser.setUserID(rs.getInt(1));
                loggedInUser.setName(rs.getString(2));
                loggedInUser.setPassword(rs.getString(3));
                userCreated = true;
            }
            if (userCreated) {
                return loggedInUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getQuestionGroups() {
        ArrayList<String> questionGroups = new ArrayList<>();
        try {
            PreparedStatement userType = connection.prepareStatement("SELECT * FROM QuestionGroup");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                questionGroups.add(rs.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionGroups;
    }

    public ArrayList<Question> getQuestionsFromGroupTitle(String title) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            PreparedStatement userType = connection.prepareStatement("SELECT * FROM Question" +
                    " INNER JOIN QuestionGroup ON Question.fk_questionGroup = QuestionGroup.Id" +
                    " WHERE QuestionGroup.title='" + title + "';");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestion(rs.getString("question"));
                questions.add(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void getAnswerInfo() {
        try {
            PreparedStatement userType = connection.prepareStatement("SELECT ProjectInformation.installationIdentification, " +
                "ProjectInformation.installationName, " +
                "InspectionInformation.inspectorName, " +
                "InspectionInformation.inspectionDate, " +
                "Room.roomName, " +
                "QuestionGroup.title, " +
                "Question.question, " +
                "Answer.answerText " +
                "FROM (Answer INNER JOIN ((Question INNER JOIN (Room INNER JOIN Inspection ON Room.Id = Inspection.fk_roomID) ON Question.Id = Inspection.fk_questionID) INNER JOIN QuestionGroup ON Question.fk_questionGroup = QuestionGroup.Id) ON Answer.Id = Inspection.fk_answerID) INNER JOIN (InspectionInformation INNER JOIN ProjectInformation ON InspectionInformation.fk_projectID = ProjectInformation.Id) ON Inspection.fk_inspectionInformationID = InspectionInformation.Id;");
            ResultSet rs = userType.executeQuery();
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Alle resultatsæt: ");
            while (rs.next()) {
                System.out.print(rs.getString("installationIdentification") + " - ");
                System.out.print(rs.getString("installationName") + " - ");
                System.out.print(rs.getString("inspectorName") + " - ");
                System.out.print(rs.getString("inspectionDate") + " - ");
                System.out.print(rs.getString("roomName") + " - ");
                System.out.print(rs.getString("title") + " - ");
                System.out.print(rs.getString("question") + " - ");
                System.out.print(rs.getString("answerText"));
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void createRoom(String name , int projectID) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Room (roomName, fk_projectID) VALUES ('"
                    + name + "', '" +  projectID +"');");
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void deleteRoom(Room room) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Room WHERE roomName='" + room.getRoomName() + "'");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteRoom(int roomID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Room WHERE ID='" + roomID + "'");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Skift fra newRoomName til ID?
    public void editRoom(Room room, String newRoomName) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Room SET roomName ='" + newRoomName + "' WHERE roomName='" + room.getRoomName() + "'");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Room> getRoomsIDAndNameFromProjectID(int projectID) {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Room WHERE fk_projectID  ='" + projectID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Room r = new Room();
                r.setRoomID(rs.getInt("ID"));
                r.setRoomName(rs.getString("roomName"));

                rooms.add(r);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;





    }


}


