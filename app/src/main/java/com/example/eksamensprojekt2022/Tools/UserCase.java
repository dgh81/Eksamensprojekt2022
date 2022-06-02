package com.example.eksamensprojekt2022.Tools;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.Enteties.AfproevningAfRCD;
import com.example.eksamensprojekt2022.Enteties.FileHandler;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Kortslutningsstrom;
import com.example.eksamensprojekt2022.Enteties.Kredsdetaljer;
import com.example.eksamensprojekt2022.Enteties.Picture;
import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Enteties.QuestionGroup;
import com.example.eksamensprojekt2022.Enteties.User;

import java.util.ArrayList;

public class UserCase {

    private static MySQL mySQL = new MySQL();

    public static ArrayList<ProjectInformation> getAllProjectInformation() {

        return mySQL.getAllProjectInformations();

    }


    public static void savePictureButtonClicked(Picture pic) {

        String encodedImageString = new FileHandler().bitmapEncodeToBaseString(pic.getBitmap());

        System.out.println(pic.getInspectionInformationID());

        mySQL.createPicture(encodedImageString, pic.getInspectionInformationID() , "Sagnr. "
        + pic.getName() + ". Rumnavn: "
        + pic.getName()  + ". Username: "
        + User.getInstance().getName() + ". Comment: " + pic.getComment() );

    }


    public static void userSelectedRoom(int roomID , int projectID) {

        InspectionInformation.setInspectionInformationFromDB( roomID , projectID);

        InspectionInformation.appendAllQuestionsWithAnswersToInspectionInformation();

        InspectionInformation.appendAllMeasurements(InspectionInformation.getInstance().getInspectionInformationID());

    }



    public static void userCreatedNewProject(ProjectInformation projectInformation) {

        mySQL.createProjectInformation(projectInformation);
    }



    public static void userCreatedNewRoom(String name , int ID ) {
        mySQL.createRoom(name, ID );
    }


    public static void userPressedSaveButton() {

        InspectionInformation.getInstance().removeAllUnansweredQuestions();

        mySQL.clearInspectionWithQuestionInspectionInformationID();

        mySQL.createInspection();

        InspectionInformation.getInstance().getQuestionGroups().clear();

        InspectionInformation.createMeasurementsInDataBase();

    }


    public static void userCreatedNewQuestionInsideQuestionGroup(int questionGroupID , String question , int InspectionInformationID) {

        mySQL.createQuestion(questionGroupID , question , InspectionInformationID);

        InspectionInformation.appendAllQuestionsWithAnswersToInspectionInformation();

    }

    public static void userCreatedNewQuestionGroupWithQuestions(String questionGroup, int fk_InspectionInformationID , ArrayList<String> questions ) {

        mySQL.createQuestionGroup(questionGroup , fk_InspectionInformationID);

        QuestionGroup questionG =  mySQL.getQuestionGroupFromTitle(questionGroup);

        for (int i = 0; i < questions.size(); i++) {

            userCreatedNewQuestionInsideQuestionGroup(questionG.getQuestionGroupID() , questions.get(i), fk_InspectionInformationID);

        }

    }


}
