package com.example.eksamensprojekt2022;

import java.sql.*;
import java.util.ArrayList;

public class MySQL implements Runnable {

    public MySQL() {}

    public static Connection connection;

/*    public Connection getConnection() {
        return connection;
    }*/

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

    public ArrayList<QuestionGroup> getQuestionGroupTitle() {
        ArrayList<QuestionGroup> questionGroups = new ArrayList<>();
        try {
            PreparedStatement userType = connection.prepareStatement("SELECT * FROM QuestionGroup");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                QuestionGroup group = new QuestionGroup(
                        rs.getInt("ID"),
                        rs.getString("title"));
                questionGroups.add(group);
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

    public ArrayList<String> getAnswerText() {
        ArrayList<String> result = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Answer");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                //System.out.print(rs.getString("answerText" + " - "));
                result.add(rs.getString("answerText"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ProjectInformation projectInfo(int ID) {

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ProjectInformation WHERE ID = '" + ID + "';");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ProjectInformation info = new ProjectInformation(
                        rs.getInt("ID"),
                        rs.getString("customerName"),
                        rs.getString("customerAddress"),
                        rs.getString("customerPostalCode"),
                        rs.getString("customerCity"),
                        rs.getString("installationIdentification"),
                        rs.getString("installationName")
                );
                System.out.println("oprettet");
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("kunne ikke oprette");
        return null;
    }

    public InspectionInformation inspectionInfo(int ID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM InspectionInformation WHERE ID = '" + ID + "';");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                InspectionInformation info = new InspectionInformation(
                        rs.getInt("ID"),
                        rs.getString("inspectorName"),
                        rs.getDate("inspectionDate"),
                        rs.getInt("fk_projectID")

                );
                System.out.println("oprettet");
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("kunne ikke oprette");
        return null;
    }

    public ArrayList<String> getQuestionsFromGroup(int group) {
        ArrayList<String> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Question WHERE fk_questionGroup = '" + group + "';");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("question"));
                System.out.println("added question");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("questions not added");
        }
        return list;
    }
}


