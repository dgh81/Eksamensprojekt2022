package com.example.eksamensprojekt2022.Objeckts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class InspectionInformation {

    int inspectorInformationID = 0;
    String inspectorName = "";
    Date inspectionDate = null;
    int fk_projectID = 0;
    int fk_roomID;
    String roomName;
    ArrayList<QuestionGroup> questionGroups;

    public static InspectionInformation instance;

    public static InspectionInformation getInstance() {

        if (instance == null) {
            instance = new InspectionInformation();
        }
        return instance;
    }

    public static void setInstance(InspectionInformation inspectionInformation) {
        instance = inspectionInformation;
    }


    
    public int getFk_roomID() {
        return fk_roomID;
    }

    public void setFk_roomID(int fk_roomID) {
        this.fk_roomID = fk_roomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<QuestionGroup> getQuestionGroups() {
        return questionGroups;
    }

    public void setQuestionGroups(ArrayList<QuestionGroup> questionGroups) {
        this.questionGroups = questionGroups;
    }

    public InspectionInformation(int inspectorInformationID, String inspectorName, Date inspectionDate , int fk_projectID) {
        this.inspectorInformationID = inspectorInformationID;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.fk_projectID = fk_projectID;
    }

    public InspectionInformation(String inspectorName, Date inspectionDate, int fk_projectID, int fk_roomID) {
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.fk_projectID = fk_projectID;
        this.fk_roomID = fk_roomID;
    }

    public InspectionInformation(int inspectorInformationID, String inspectorName, Date inspectionDate, int fk_projectID, int fk_roomID, String roomName) {
        this.inspectorInformationID = inspectorInformationID;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.fk_projectID = fk_projectID;
        this.fk_roomID = fk_roomID;
        this.roomName = roomName;
    }



    public String getRoomName() {
        return roomName;
    }

    public int getRoomID() {
        return fk_roomID;
    }




    public InspectionInformation() {
    }

    public int getInspectorInformationID() {
        return inspectorInformationID;
    }

    public void setInspectorInformationID(int inspectorInformationID) {
        this.inspectorInformationID = inspectorInformationID;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public int getFk_projectID() {
        return fk_projectID;
    }

    public void setFk_projectID(int fk_projectID) {
        this.fk_projectID = fk_projectID;
    }

    @Override
    public String toString() {
        return "InspectionInformation{" +
                "inspectorInformationID=" + inspectorInformationID +
                ", inspectorName='" + inspectorName + '\'' +
                ", inspectionDate=" + inspectionDate +
                ", fk_projectID=" + fk_projectID +
                ", fk_roomID=" + fk_roomID +
                ", roomName='" + roomName + '\'' +
                ", questionGroups=" + questionGroups +
                '}';
    }

    public int getQuestionGroupIndexByQuestionID(int i) {


        for (int j = 0; j < questionGroups.size(); j++) {
            if (i - questionGroups.get(j) .getQuestions().size() + 1  > 0 ) {
                i -= questionGroups.get(j).getQuestions().size();
            } else {
                return j;
            }
        }
        return 0;
    }


    public int getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(int i) {

        for (int j = 0; j < questionGroups.size(); j++) {
            if (i - questionGroups.get(j) .getQuestions().size() + 1 > 0 ) {
                i -= questionGroups.get(j).getQuestions().size();
            } else {
                return i ;
            }
        }
        return 0;
    }

    public int getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(int group , int question) {
        int index = 0;

        for (int i = 0; i < group; i++) {

            index += questionGroups.get(i).getQuestions().size();

        }

        index += question;

        return index;
    }

    public boolean isTotalIndexInsideQuestionGroup(int index, int group) {

        return  (getQuestionGroupIndexByQuestionID(index) == group);

    }







}
