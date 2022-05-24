package com.example.eksamensprojekt2022;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.Room;

import java.util.ArrayList;

public class GetAllInspectionInformations {


    public ArrayList<InspectionInformation> GetAllInspectionInformation (int projectID) {

        ArrayList<InspectionInformation> inspectionInformations = new ArrayList<>();

        ArrayList<Room> roomsIDs = UserCase.getRoomsFromProjectInformationID(projectID);





        for (int i = 0; i < roomsIDs.size(); i++) {

            UserCase.setInspectionInformationFromDB(roomsIDs.get(i).getRoomID() , projectID);

            UserCase.appendAllQuestionsWithAnswersToInspectionInformation();

            UserCase.appendAllMeasurements(InspectionInformation.getInstance().getInspectionInformationID());

            InspectionInformation.getInstance().removeAllUnansweredQuestions();

            inspectionInformations.add(InspectionInformation.getInstance());

        }

        return inspectionInformations;






    }









}
