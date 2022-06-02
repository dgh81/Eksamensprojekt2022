package com.example.eksamensprojekt2022.DBController;

import android.graphics.Bitmap;

import com.example.eksamensprojekt2022.Enteties.AfproevningAfRCD;
import com.example.eksamensprojekt2022.Enteties.Answer;
import com.example.eksamensprojekt2022.Enteties.FileHandler;
import com.example.eksamensprojekt2022.Enteties.Inspection;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Kortslutningsstrom;
import com.example.eksamensprojekt2022.Enteties.Kredsdetaljer;
import com.example.eksamensprojekt2022.Enteties.Picture;
import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Enteties.Question;
import com.example.eksamensprojekt2022.Enteties.QuestionGroup;
import com.example.eksamensprojekt2022.Enteties.Room;
import com.example.eksamensprojekt2022.Enteties.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MySQL implements Runnable {

    public MySQL() {}
    public static Connection connection;

    //TODO Lav rigtige prep statements igennem hele klassen!
    // eller lav det som eksempel for et par af funktionerne...

    public void createProjectInformation(ProjectInformation projectInformation) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ProjectInformation (customerName, customerAddress, customerPostalCode, customerCity, installationIdentification, installationName) VALUES ('"
                    + projectInformation.getCustomerName() + "', '"
                    + projectInformation.getCustomerAddress() + "', '"
                    + projectInformation.getCustomerPostalCode() + "', '"
                    + projectInformation.getCustomerCity() + "', '"
                    + projectInformation.getCaseNumber() + "', '"
                    + projectInformation.getInstallationName() +"');");
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProjectInformation> getAllProjectInformations() {
        ArrayList<ProjectInformation> projectInformationList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ProjectInformation");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                //TODO Rettet dette til at bruge constructor:
                // test og slet kommentarer hvis det virker.
                //ProjectInformation projectInformation = new ProjectInformation();
                ProjectInformation projectInformation = new ProjectInformation(rs.getInt("ID"),
                        rs.getString("customerName"),
                        rs.getString("customerAddress"),
                        rs.getString("customerPostalCode"),
                        rs.getString("customerCity"),
                        rs.getString("installationIdentification"),
                        rs.getString("installationName"));
/*
                projectInformation.setProjectInformationID(rs.getInt("ID"));
                projectInformation.setCustomerName(rs.getString("customerName"));
                projectInformation.setCustomerAddress(rs.getString("customerAddress"));
                projectInformation.setCustomerPostalCode(rs.getString("customerPostalCode"));
                projectInformation.setCustomerCity(rs.getString("customerCity"));
                projectInformation.setInstallationIdentification(rs.getString("installationIdentification"));
                projectInformation.setInstallationName(rs.getString("installationName"));*/
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
            //TODO: Hvilken medtode ville vi anvende for at gøre understående info hemmelig og sikret?
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
                //TODO Ret til at bruge kolonnenavne i stedet for indeksnumre:
                // Ret til at bruge constructor?
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

    public ArrayList<QuestionGroup> getAllQuestionGroups(int inspectionInformationID ) {
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

    //TODO Denne kan vist slettes, når vi er færdige...
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
                result.add(rs.getString("answerText"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ProjectInformation getProjectInformation(int ID) {
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
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getQuestionsFromGroup(int group) {
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Question WHERE fk_questionGroup = '" + group + "';");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("question"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Question> getQuestionsFromQuestionGroup(QuestionGroup questionGroup , int inspectionInformationID) {
        ArrayList<Question> questions = new ArrayList<>();
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

    public void createQuestion(int questionGroupID, String questionText) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Question (fk_questionGroup, question, fk_projectID) VALUES ('"
                        + questionGroupID + "','"
                        + questionText + "','"
                        + InspectionInformation.getInstance().getInspectionInformationID() + "','"
                        + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void createInspection(InspectionInformation inspectionInformation) {
        inspectionInformation = InspectionInformation.getInstance();
        for (int i = 0; i < inspectionInformation.getQuestionGroups().size(); i++) {
            for (int j = 0; j < inspectionInformation.getQuestionGroups().get(i).getQuestions().size(); j++) {
                Question q = inspectionInformation.getQuestionGroups().get(i).getQuestions().get(j);
                try {
                    PreparedStatement statement = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        statement = connection.prepareStatement("INSERT INTO Inspection (fk_questionID, fk_answerID, fk_inspectionInformationID) VALUES ('"
                                + q.getQuestionID() +"','"
                                + q.getAnswerID() + "','"
                                + inspectionInformation.getInspectionInformationID() + "' )" );
                    }
                    statement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

    public InspectionInformation getInspectionInformation(int roomID) {
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

/*
    public String getOvergangsmodstand(int inspectionInformationID) {
        String overgangsmodstand;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Overgangsmodstand WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                overgangsmodstand = rs.getString("overgangsmodstand");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return overgangsmodstand;
    }

    public void createOvergangsmodstand(String overgangsmodstand, int inspectionInformationID) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Overgangsmodstand (overgangsmodstand, fk_inspectionInformationID) VALUES ('"
                        + overgangsmodstand + "', '"
                        + inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AfproevningAfRCD> getAfproevningAfRCDer(int inspectionInformationID) {
        ArrayList<AfproevningAfRCD> AfproevningAfRCDer  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM AfproevningAfRCD WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                AfproevningAfRCD a = new AfproevningAfRCD(
                        rs.getInt("ID"),
                        rs.getString("RCD"),
                        rs.getString("field1"),
                        rs.getString("field2"),
                        rs.getString("field3"),
                        rs.getString("field4"),
                        rs.getString("field5"),
                        rs.getString("field6"),
                        rs.getString("OK"),
                        rs.getInt("fk_inspectionInformationID")
                );
                AfproevningAfRCDer.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return AfproevningAfRCDer;
    }

    public ArrayList<Kortslutningsstrom> getKortslutningsstromme(int inspectionInformationID) {
        ArrayList<Kortslutningsstrom> Kortslutningsstromme  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Kortslutningsstrom WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kortslutningsstrom a = new Kortslutningsstrom(
                        rs.getInt("ID"),
                        rs.getString("k_gruppe"),
                        rs.getString("k_KiK"),
                        rs.getString("k_maaltIPunkt"),
                        rs.getString("s_gruppe"),
                        rs.getString("s_U"),
                        rs.getString("s_maaltIPunkt"),
                        rs.getInt("fk_inspectionInformationID")
                );
                Kortslutningsstromme.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Kortslutningsstromme;
    }

    public ArrayList<Kredsdetaljer> getKredsdetaljer(int inspectionInformationID) {
        ArrayList<Kredsdetaljer> kredsdetaljer  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Kredsdetaljer WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kredsdetaljer a = new Kredsdetaljer(
                        rs.getInt("ID"),
                        rs.getString("gruppe"),
                        rs.getString("oB"),
                        rs.getString("karakteristik"),
                        rs.getString("tvaersnit"),
                        rs.getString("MaksOB"),
                        rs.getString("RZBoolean"),
                        rs.getString("zsRa"),
                        rs.getString("isolation"),
                        rs.getInt("fk_inspectionInformationID")
                );
                kredsdetaljer.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return kredsdetaljer;
    }

    public void createAfproevningAfRCD(AfproevningAfRCD afproevningAfRCD) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO AfproevningAfRCD (RCD, field1 , field2, field3, field4, field5, field6, OK, fk_inspectionInformationID) VALUES ('"
                        + afproevningAfRCD.getRCD() +"','"
                        + afproevningAfRCD.getField1()  + "','"
                        + afproevningAfRCD.getField2()  + "','"
                        + afproevningAfRCD.getField3()  + "','"
                        + afproevningAfRCD.getField4()  + "','"
                        + afproevningAfRCD.getField5()  + "','"
                        + afproevningAfRCD.getField6()  + "','"
                        + afproevningAfRCD.getOK()  + "','"
                        + afproevningAfRCD.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createKortslutningsstrom(Kortslutningsstrom kortslutningsstrom) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO AfproevningAfRCD (RCD, field1 , field2, field3, field4, field5, field6, OK, fk_inspectionInformationID) VALUES ('"
                        + kortslutningsstrom.getK_gruppe() +"','"
                        + kortslutningsstrom.getK_KiK()  + "','"
                        + kortslutningsstrom.getK_maaltIPunkt()  + "','"
                        + kortslutningsstrom.getS_gruppe()  + "','"
                        + kortslutningsstrom.getS_U()  + "','"
                        + kortslutningsstrom.getS_maaltIPunkt()  + "','"
                        + kortslutningsstrom.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createKredsdetaljer(Kredsdetaljer kredsdetaljer) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Kredsdetaljer (gruppe, oB , karakteristik, tvaersnit, maksOB, RZBoolean, zsRa, isolation, fk_inspectionInformationID) VALUES ('"
                        + kredsdetaljer.getGruppe() +"','"
                        + kredsdetaljer.getOB()  + "','"
                        + kredsdetaljer.getKarakteristik()  + "','"
                        + kredsdetaljer.getTvaersnit()  + "','"
                        + kredsdetaljer.getMaksOB()  + "','"
                        + kredsdetaljer.getRZBoolean()  + "','"
                        + kredsdetaljer.getZsRa()  + "','"
                        + kredsdetaljer.getIsolation()  + "','"
                        + kredsdetaljer.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void deleteAllPicturesByInspectionInformationID(int inspectionInformationID) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("DELETE FROM Picture WHERE fk_inspectionInformationID ='" + inspectionInformationID + "'");
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearInspectionWithQuestionInspectionInformationID() {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement( "DELETE FROM Inspection WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectionInformationID()
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
                try {
                    PreparedStatement statement = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        statement = connection.prepareStatement("INSERT INTO Inspection (fk_questionID, fk_answerID, fk_inspectionInformationID , comment) VALUES ('"
                                + q.getQuestionID() +"','"
                                + q.getAnswerID() + "','"
                                + InspectionInformation.getInstance().getInspectionInformationID() + "','"
                                + q.getComment() + "')" );
                    }
                    statement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Integer> getAllInspectionIDs(int projectID) {
        ArrayList<Integer> list = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM InspectionInformation WHERE fk_projectID = '" + projectID + "'");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    list.add(rs.getInt("ID"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }

    public ArrayList<Inspection> getAllInspections(int projectID) {
        ArrayList<Integer> list = getAllInspectionIDs(projectID);
        ArrayList<Inspection> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Inspection WHERE fk_inspectionInformationID = '" + list.get(i) + "'");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Inspection inspection = new Inspection(
                            projectID,
                            rs.getInt("fk_questionID"),
                            rs.getInt("fk_answerID"),
                            rs.getInt("fk_inspectionInformationID"),
                            rs.getString("comment")
                    );
                    result.add(inspection);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void createPicture(String bitmapString, int inspectionInformationID, String comment) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Picture (base64, fk_inspectionInformationID, comment) VALUES ('"
                        + bitmapString +"','"
                        + inspectionInformationID +"','"
                        + comment + "')" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getBitmapString(int inspectionInformationID) {
        ArrayList<String> base64Strings = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Picture WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                base64Strings.add(rs.getString("base64"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return base64Strings;
    }

    //TODO Fjern parameter inspectionInformationID?:
    public void createQuestion(int questionGroupID, String questionText , int inspectionInformationID) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Question (fk_questionGroup, question, fk_inspectionInformationID) VALUES ('"
                        + questionGroupID + "','"
                        + questionText + "','"
                        + InspectionInformation.getInstance().getInspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AfproevningAfRCD> getAfproevningAfRCDer(int inspectionInformationID) {
        ArrayList<AfproevningAfRCD> AfproevningAfRCDer  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM AfproevningAfRCD WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                AfproevningAfRCD a = new AfproevningAfRCD(
                        rs.getInt("ID"),
                        rs.getString("RCD"),
                        rs.getString("field1"),
                        rs.getString("field2"),
                        rs.getString("field3"),
                        rs.getString("field4"),
                        rs.getString("field5"),
                        rs.getString("field6"),
                        rs.getBoolean("OK"),
                        rs.getInt("fk_inspectionInformationID")
                );
                AfproevningAfRCDer.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return AfproevningAfRCDer;
    }

    public ArrayList<Kortslutningsstrom> getKortslutningsstromme(int inspectionInformationID) {
        ArrayList<Kortslutningsstrom> Kortslutningsstromme  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Kortslutningsstrom WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kortslutningsstrom a = new Kortslutningsstrom(
                        rs.getInt("ID"),
                        rs.getString("k_gruppe"),
                        rs.getString("k_KiK"),
                        rs.getString("k_maaltIPunkt"),
                        rs.getString("s_gruppe"),
                        rs.getString("s_U"),
                        rs.getString("s_maaltIPunkt"),
                        rs.getInt("fk_inspectionInformationID")
                );
                Kortslutningsstromme.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Kortslutningsstromme;
    }

    public ArrayList<Kredsdetaljer> getKredsdetaljer(int inspectionInformationID) {
        ArrayList<Kredsdetaljer> kredsdetaljer  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Kredsdetaljer WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kredsdetaljer a = new Kredsdetaljer(
                        rs.getInt("ID"),
                        rs.getString("gruppe"),
                        rs.getString("oB"),
                        rs.getString("karakteristik"),
                        rs.getString("tvaersnit"),
                        rs.getString("MaksOB"),
                        rs.getBoolean("RZboolean"),
                        rs.getString("zsRa"),
                        rs.getString("isolation"),
                        rs.getInt("fk_inspectionInformationID")
                );
                kredsdetaljer.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return kredsdetaljer;
    }

    public String getOvergangsmodstand(int inspectionInformationID) {
        String overgangsmodstand = "";
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Overgangsmodstand WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                overgangsmodstand = rs.getString("overgangsmodstand");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return overgangsmodstand;
    }

    public ArrayList<String> getPDFComments(int inspectionInformationID) {
        ArrayList<String> pdfComments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PDFComment WHERE fk_inspectionInformationID = '" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                pdfComments.add(rs.getString("pdfComment"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return pdfComments;
    }

    public ProjectInformation getProjectInformation() {
        ProjectInformation project = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ProjectInformation WHERE ID = '" + InspectionInformation.getInstance().getFk_projectID() + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                project = new ProjectInformation(rs.getString("customerName"),
                        rs.getString("customerAddress"),
                        rs.getString("customerPostalCode"),
                        rs.getString("customerCity"),
                        rs.getString("installationIdentification"),
                        rs.getString("installationName"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    public String getRoomNameFromInspectionInformationID(int inspectionInformationID) {
        String roomname = "";
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Room.RoomName, InspectionInformation.Id\n" +
                    "FROM Room INNER JOIN (InspectionInformation INNER JOIN ProjectInformation ON InspectionInformation.fk_projectID = ProjectInformation.Id) ON Room.Id = InspectionInformation.fk_roomID\n" +
                    "WHERE InspectionInformation.Id='" + inspectionInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                roomname = rs.getString("roomName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomname;
    }

    public ArrayList<Picture> getPicturesFromProjectInformationID(int projectInformationID) {
        ArrayList<Picture> pics = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT ProjectInformation.Id, Picture.*" +
                    " FROM (Picture INNER JOIN InspectionInformation ON Picture.fk_inspectionInformationID = InspectionInformation.Id)" +
                    " INNER JOIN ProjectInformation ON InspectionInformation.fk_projectID = ProjectInformation.Id" +
                    " WHERE ProjectInformation.Id='" + projectInformationID + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FileHandler fileHandler = new FileHandler();
                Bitmap bitmap = fileHandler.bitmapDecodeFromBaseString(rs.getString("base64"));
                Picture pic = new Picture(bitmap,rs.getInt("Picture.fk_inspectionInformationID"),rs.getString("comment"));
                pics.add(pic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pics;
    }

    public void createAfproevningAfRCD(AfproevningAfRCD afproevningAfRCD) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO AfproevningAfRCD (RCD, field1 , field2, field3, field4, field5, field6, OK, fk_inspectionInformationID) VALUES ('"
                        + afproevningAfRCD.getRCD() +"','"
                        + afproevningAfRCD.getField1()  + "','"
                        + afproevningAfRCD.getField2()  + "','"
                        + afproevningAfRCD.getField3()  + "','"
                        + afproevningAfRCD.getField4()  + "','"
                        + afproevningAfRCD.getField5()  + "','"
                        + afproevningAfRCD.getField6()  + "','"
                        + (afproevningAfRCD.getOK() ? 1 : 0 )  + "','"
                        + afproevningAfRCD.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createKortslutningsstrom(Kortslutningsstrom kortslutningsstrom) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Kortslutningsstrom (k_gruppe , k_KiK, k_maaltIPunkt, s_gruppe, s_U, s_maaltIPunkt, fk_inspectionInformationID) VALUES ('"
                        + kortslutningsstrom.getK_gruppe() +"','"
                        + kortslutningsstrom.getK_KiK()  + "','"
                        + kortslutningsstrom.getK_maaltIPunkt()  + "','"
                        + kortslutningsstrom.getS_gruppe()  + "','"
                        + kortslutningsstrom.getS_U()  + "','"
                        + kortslutningsstrom.getS_maaltIPunkt()  + "','"
                        + kortslutningsstrom.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createKredsdetaljer(Kredsdetaljer kredsdetaljer) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Kredsdetaljer (gruppe, oB , karakteristik, tvaersnit, maksOB, zsRa, isolation, RZboolean, fk_inspectionInformationID) VALUES ('"
                        + kredsdetaljer.getGroup() +"','"
                        + kredsdetaljer.getoB()  + "','"
                        + kredsdetaljer.getKarakteristik()  + "','"
                        + kredsdetaljer.getTvaersnit()  + "','"
                        + kredsdetaljer.getMaksOB()  + "','"
                        + kredsdetaljer.getZsRaValue()  + "','"
                        + kredsdetaljer.getIsolation()  + "','"
                        + ((kredsdetaljer.iszSRa()) ? 1 : 0 )   + "','"
                        + kredsdetaljer.getFk_inspectionInformationID() + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void createOvergangsmodstand(String overgangsmodstand, int inspectionInformationID) {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement("INSERT INTO Overgangsmodstand (overgangsmodstand, fk_inspectionInformationID) VALUES ('"
                        + overgangsmodstand + "', '"
                        + inspectionInformationID + "' )" );
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMeasurementTablesWithSelectedInspectionID() {
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement( "DELETE FROM Kortslutningsstrom WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectionInformationID()
                        + "'");

                System.out.println(InspectionInformation.getInstance().getInspectionInformationID() + " this is the ID");

            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement( "DELETE FROM Kredsdetaljer WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectionInformationID()
                        + "'");
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement( "DELETE FROM AfproevningAfRCD WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectionInformationID()
                        + "'");
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                statement = connection.prepareStatement( "DELETE FROM Overgangsmodstand WHERE fk_inspectionInformationID = '" + InspectionInformation.getInstance().getInspectionInformationID()
                        + "'");
            }
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


