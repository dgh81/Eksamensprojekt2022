package com.example.eksamensprojekt2022;

import java.sql.*;
import java.util.ArrayList;

public class MySQL implements Runnable {

    public MySQL() {
    }

    // public static?
    public Connection connection;

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void run() {

        try {
            //gammel driver: "com.mysql.jdbc.Driver"
            //ny     driver: "com.mysql.cj.jdbc.Driver"
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //Fjern ssl: ?useSSL=false
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db?useSSL=false";
            connection = DriverManager.getConnection(url, "danielguldberg_dk", "280781");

            System.out.println("connection created is: " + (connection != null));
            System.out.println(userType("a@a.dk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String userType(String email) {
        String result = "";
        try {
            PreparedStatement userType = connection.
                    prepareStatement("SELECT Type FROM PasswordTable WHERE Email = '" + email + "'");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                result = rs.getString("Type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getQuestionGroupTitles() {
        ArrayList<String> questionGroups = new ArrayList<>();

        try {
            PreparedStatement userType = connection.
                    prepareStatement("SELECT * FROM QuestionGroup");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                questionGroups.add(rs.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionGroups;
    }

/*    public ArrayList<String> getQuestionsFromGroupTitle(String title) {
        ArrayList<String> questions = new ArrayList<>();

        try {
            PreparedStatement userType = connection.
                    prepareStatement("SELECT * FROM" +
                            " (Question INNER JOIN QuestionGroup ON Question.fk_questionGroup = QuestionGroup.Id)" +
                            " INNER JOIN Answer ON Question.answer = Answer.Id WHERE (((QuestionGroup.title)='" + title + "'));");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                questions.add(rs.getString("question"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }*/

    public ArrayList<Question> getQuestionsFromGroupTitle(String title) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            PreparedStatement userType = connection.
                    prepareStatement("SELECT * FROM" +
                            " (Question INNER JOIN QuestionGroup ON Question.fk_questionGroup = QuestionGroup.Id)" +
                            " WHERE QuestionGroup.title='" + title + "';");
            ResultSet rs = userType.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                //q.setAnswerText(rs.getString("answerText"));
                q.setQuestion(rs.getString("question"));
                questions.add(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}