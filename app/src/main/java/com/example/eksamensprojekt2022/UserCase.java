package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.Objeckts.Answer;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.QuestionGroup;
import com.example.eksamensprojekt2022.Objeckts.Room;
import com.example.eksamensprojekt2022.Objeckts.User;

import java.util.ArrayList;
import java.util.Date;

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

    public static ArrayList<Room> getRoomsFromProjectInformationID(int projectID) {

        return mySQL.getRoomsFromProjectID(projectID) ;

    }

    public static void createRoomFromName(String name , int ID ) {
        mySQL.createRoom(name, ID );
    }

    public static InspectionInformation getInspectionInformationFromDB(int roomID , int projectID) {

        InspectionInformation i = mySQL.getInspectionInformationDB(roomID);

        if ( i == null) {

            InspectionInformation inspectionInformation = new InspectionInformation(
                    User.getInstance().getName(),
                    new Date(),
                    projectID,
                    roomID
            );

            mySQL.createInspectionInformation(inspectionInformation);
            return mySQL.getInspectionInformationDB(roomID);

        } else {
            return i;
        }

    }

    public static ArrayList<QuestionGroup> getAllQuestionGroupsWithAnswers(InspectionInformation inspectionInformation) {

        ArrayList<QuestionGroup> questionGroups = mySQL.getQuestionGroupTitle();

        ArrayList<Answer> answers = mySQL.getAllAnswersFromInspectionInformationID(inspectionInformation.getInspectorInformationID());

        for (QuestionGroup group : questionGroups ) {
            ArrayList<Question> questions =  mySQL.getQuestionsFromQuestionGroup(group);

            for (Question question: questions ) {
                group.getQuestions().add(question);

                for (Answer a: answers) {

                    if (question.getFk_questionGroup() == a.getQuestionGroupID()) {

                        if (question.getQuestionID() == a.getQuestionID()) {
                            question.setAnswerID(a.getAnswerID());
                            question.setComment(a.getComment());
                        }
                    }
                }
            }
        }

        return questionGroups;

    }

    public static void createInspectionInDataBase() {

        InspectionInformation.getInstance().removeAllUnansweredQuestions();

        mySQL.clearInspectionWithQuestionInspectionInformationID();

        mySQL.createInspection();

        InspectionInformation.getInstance().getQuestionGroups().clear();

    }

    public static void createNewQuestionInsideQuestionGroup(int questionGroupID , String question ) {



    }

    public static void addQuestionGroupWithQuestions( String questionGroup, ArrayList<String> questions ) {

        System.out.println(questionGroup);

        for (String q : questions ) {
            System.out.println(q);
        }




    }



}
