package com.example.eksamensprojekt2022.Enteties;


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



    ArrayList<Kredsdetaljer> kredsdetaljer;

    String overgangsmodstandR = "";

    ArrayList<AfproevningAfRCD> afprøvningAfRCD;

    ArrayList<Kortslutningsstrom> kortslutningsstroms;

    ArrayList<String> PDFComment;


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


    public ArrayList<Kredsdetaljer> getKredsdetaljer() {
        return kredsdetaljer;
    }

    public String getOvergangsmodstandR() {
        return overgangsmodstandR;
    }


    public ArrayList<AfproevningAfRCD> getAfprøvningAfRCD() {
        return afprøvningAfRCD;
    }

    public ArrayList<Kortslutningsstrom> getKortslutningsstroms() {
        return kortslutningsstroms;
    }

    public ArrayList<String> getPDFComment() {
        return PDFComment;
    }

    public InspectionInformation() {
    }

    public int getInspectionInformationID() {
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

    public int getTotalNumberOfQuestions() {

        int number = 0;

        for (int i = 0; i < questionGroups.size(); i++) {

            number += questionGroups.get(i).getQuestions().size();

        }
        return number;
    }




    public int getQuestionGroupIndexByQuestionID(int i) {


        for (int j = 0; j < questionGroups.size(); j++) {
            if (i - questionGroups.get(j) .getQuestions().size() + 1  > 0 ) {
                i -= questionGroups.get(j).getQuestions().size();
            } else {
                return j;
            }
        }

        if (i - kredsdetaljer.size() + 1   > 0 ) {
            i -= kredsdetaljer.size();
        } else {
            return questionGroups.size()  ;
        }

        if (i  - 1  >= 0 ) {
            i --;
        } else {
            return questionGroups.size() + 1;
        }

        if (i - afprøvningAfRCD.size() + 1  > 0 ) {

        } else {
            return questionGroups.size() + 2;
        }

        return questionGroups.size() + 3;
    }


    public int getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(int i) {

        for (int j = 0; j < questionGroups.size(); j++) {
            if (i - questionGroups.get(j) .getQuestions().size() + 1 > 0 ) {
                i -= questionGroups.get(j).getQuestions().size();
            } else {
                return i ;
            }
        }

        if (i - kredsdetaljer.size() + 1 > 0 ) {
            i -= kredsdetaljer.size();
        } else {
            return i;
        }

        if (i - 1 >= 0 ) {
            i --;
        } else {
            return i;
        }

        if (i - afprøvningAfRCD.size() + 1 > 0 ) {
            i -= afprøvningAfRCD.size();
        } else {
            return i;
        }

        if (i - kortslutningsstroms.size() + 1 > 0) {
            i -= kortslutningsstroms.size();
        } else {
            return i;
        }



        return i;




    }

    public int getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(int group , int question) {

        int index = 0;

        int size = questionGroups.size();

        for (int i = 0; i < group && i < questionGroups.size()   ; i++) {

            index += questionGroups.get(i).getQuestions().size();

        }

        for (int i = questionGroups.size(); i < (questionGroups.size() + 4) && i <= group   ; i++) {

            if (i == questionGroups.size() + 1)  {index += kredsdetaljer.size();  }
            if (i == questionGroups.size() + 2 ) {index ++ ; }
            if (i == questionGroups.size() + 3 ) {index += afprøvningAfRCD.size(); }
            if (i == questionGroups.size() + 4 ) {index += kortslutningsstroms.size(); }

        }

        index += question;


        System.out.println(index + "this is last index");

        return index;
    }




    public boolean isTotalIndexInsideQuestionGroup(int index, int group) {

        System.out.println(index + " this is index inside InspectionInformation " + group);

        return  (getQuestionGroupIndexByQuestionID(index) == group);

    }


    public void removeAllUnansweredQuestions() {


        for (int i = 0; i < questionGroups.size(); i++) {

                for (int j = 0; j < questionGroups.get(i).getQuestions().size(); j++) {

                    if (questionGroups.get(i).getQuestions().get(j).getAnswerID() != 4) {
                        System.out.println(questionGroups.get(i).getQuestions().get(j).getQuestion() + "it is inside instance");
                    }


                    if (  questionGroups.get(i).getQuestions().get(j).getAnswerID() == 4 && questionGroups.get(i).getQuestions().get(j).getComment().equals("")  ) {

                        questionGroups.get(i).getQuestions().remove(j);

                        j --;

                }
            }
        }
    }


    public void setKredsdetaljer(ArrayList<Kredsdetaljer> kredsdetaljer) {
        this.kredsdetaljer = kredsdetaljer;
    }

    public void setOvergangsmodstandR(String overgangsmodstandR) {
        this.overgangsmodstandR = overgangsmodstandR;
    }

    public void setAfprøvningAfRCD(ArrayList<AfproevningAfRCD> afprøvningAfRCD) {
        this.afprøvningAfRCD = afprøvningAfRCD;
    }

    public void setKortslutningsstroms(ArrayList<Kortslutningsstrom> kortslutningsstroms) {
        this.kortslutningsstroms = kortslutningsstroms;
    }

    public void setPDFComment(ArrayList<String> PDFComment) {
        this.PDFComment = PDFComment;
    }
}
