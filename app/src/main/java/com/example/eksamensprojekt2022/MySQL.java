package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.Objeckts.Answer;
import com.example.eksamensprojekt2022.Objeckts.Inspection;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.QuestionGroup;
import com.example.eksamensprojekt2022.Objeckts.Room;
import com.example.eksamensprojekt2022.Objeckts.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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

    public ArrayList<QuestionGroup> getQuestionGroupTitles( int inspectionInformationID ) {
        ArrayList<QuestionGroup> questionGroups = new ArrayList<>();
        try {
            PreparedStatement userType = connection.prepareStatement("SELECT * FROM QuestionGroup WHERE fk_inspectionInformationID = 0 OR fk_inspectionInformationID = " +
                    "' " + inspectionInformationID + "'" ) ;
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


    public QuestionGroup getQuestionGroupFromTitle(String title ) {

        try {
            PreparedStatement userType = connection.prepareStatement("SELECT * FROM QuestionGroup WHERE title = '" + title + "'");
            ResultSet rs = userType.executeQuery();
            if (rs.next()) {
                QuestionGroup group = new QuestionGroup(
                        rs.getInt("ID"),
                        rs.getString("title"));

                return group;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public ArrayList<Question> getQuestionsFromQuestionGroup(QuestionGroup questionGroup , int inspectionInformationID) {
        ArrayList<Question> questions = new ArrayList<>();

        System.out.println(questionGroup);


        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Question WHERE fk_questionGroup = '" + questionGroup.getQuestionGroupID() + " '   AND " +
                    "  fk_inspectionInformationID = '" +  inspectionInformationID + "' OR fk_questionGroup = '" + questionGroup.getQuestionGroupID() + "' AND " +
                    " fk_inspectionInformationID = 0;"  );
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Question q = new  Question(
                        questionGroup.getQuestionGroupID(),
                        rs.getInt("ID"),
                        rs.getString("question"),
                        ""
                );
                questions.add(q);
            }
            return questions;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("questions not added");
        }
        return questions;

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

    public ArrayList<Room> getRoomsFromProjectID(int projectID) {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Room WHERE fk_projectID = '"+ projectID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Room r = new Room(
                        rs.getInt("ID"),
                        rs.getString("roomName"),
                        rs.getInt("inspected"),
                        projectID
                );
                rooms.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;





    }



    public void createInspectionInformation( InspectionInformation inspectionInformation  ) {


        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO InspectionInformation (inspectorName, inspectionDate , fk_projectID , fk_roomID) VALUES ('"
                                + inspectionInformation.getInspectorName() +"','"
                                + LocalDate.now() + "','"
                                + inspectionInformation.getFk_projectID()  + "','"
                                + inspectionInformation.getRoomID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public InspectionInformation getInspectionInformationDB(int roomID) {

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Room INNER JOIN InspectionInformation ON Room.Id = InspectionInformation.fk_roomID WHERE Room.Id = '" + roomID + "'");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new InspectionInformation(

                        rs.getInt("InspectionInformation.ID"),
                        User.getInstance().getName(),
                        new Date(),
                        rs.getInt("fk_projectID"),
                        roomID,
                        rs.getString("roomName")
                );
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return  null;

    }




    public ArrayList<Answer> getAllAnswersFromInspectionInformationID(int inspectionInformationID) {
        ArrayList<Answer> answers  = new ArrayList<>();
    try {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM QuestionGroup INNER JOIN (Question INNER" +
                " JOIN Inspection ON Question.Id = Inspection.fk_questionID) ON QuestionGroup.Id " +
                "= Question.fk_questionGroup WHERE Inspection.fk_inspectionInformationID = '" + inspectionInformationID + "'");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {

            Answer a = new Answer(
                    rs.getInt("fk_answerID"),
                    rs.getInt("fk_questionGroup"),
                    rs.getInt("question.id"),
                    rs.getString("comment")
            );
            answers.add(a);
        }

    }catch (Exception e) {
        e.printStackTrace();
    }
        return answers;
    }




    public void clearInspectionWithQuestionInspectionInformationID() {

        System.out.println(InspectionInformation.getInstance().getInspectorInformationID());

                //TODO call insert into sql: fk_questionID, fk_answerID, fk_inspectionInformationID
                try {
                    PreparedStatement statement = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        statement = connection.prepareStatement( "DELETE FROM Inspection WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectorInformationID()
                                + "'");
                    }
                    statement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
         }


    public void createInspection() {


        for (int i = 0; i < InspectionInformation.getInstance().getQuestionGroups().size(); i++) {

            for (int j = 0; j < InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size(); j++) {

                Question q = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(j);
                System.out.println(q.getAnswerID());

                //TODO call insert into sql: fk_questionID, fk_answerID, fk_inspectionInformationID


                try {
                    PreparedStatement statement = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        statement = connection.prepareStatement("INSERT INTO Inspection (fk_questionID, fk_answerID, fk_inspectionInformationID , comment) VALUES ('"
                                + q.getQuestionID() +"','"
                                + q.getAnswerID() + "','"
                                + InspectionInformation.getInstance().getInspectorInformationID() + "','"
                                + q.getComment() + "')" );
                    }
                    statement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }




    public void createQuestionGroup(String questionGroupTitle, int fk_inspectionInformationID) {

        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO QuestionGroup (title, fk_inspectionInformationID) VALUES ('"
                        + questionGroupTitle + "','"
                        + fk_inspectionInformationID
                        + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createQuestion(int questionGroupID, String questionText , int inspectionInformationID) {

        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Question (fk_questionGroup, question, fk_inspectionInformationID) VALUES ('"
                        + questionGroupID + "','"
                        + questionText + "','"
                        + InspectionInformation.getInstance().getInspectorInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }













}


