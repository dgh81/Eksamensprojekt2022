package com.example.eksamensprojekt2022.Enteties;

import com.example.eksamensprojekt2022.DBController.MySQL;

import java.util.ArrayList;

public class ProjectInformation {

    public static ProjectInformation instance;


    public static ProjectInformation getInstance() {
        if (instance == null) {
            instance = new ProjectInformation();
        }
        return instance;
    }

    public static void setInstance(ProjectInformation projectInformation) {
        instance = projectInformation;
    }

    int projectInformationID = 0;
    String customerName = "";
    String customerAddress = "";
    String customerPostalCode = "";
    String customerCity = "";
    String caseNumber = "";
    String installationName = "";
    int fk_userID = 0;
    int fk_questionGroup = 0;

    ArrayList<InspectionInformation> inspectionInformations = new ArrayList<>();

    public ArrayList<InspectionInformation> getInspectionInformations() {
        return inspectionInformations;
    }

    public void setInspectionInformation(ArrayList<InspectionInformation> inspectionInformations) {
        this.inspectionInformations = inspectionInformations;
    }

    public ProjectInformation(int projectInformationID, String customerName, String customerAddress, String customerPostalCode, String customerCity, String installationIdentification, String installationName, int fk_userID, int fk_questionGroup) {
        this.projectInformationID = projectInformationID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCity = customerCity;
        this.caseNumber = installationIdentification;
        this.installationName = installationName;
        this.fk_userID = fk_userID;
        this.fk_questionGroup = fk_questionGroup;
    }

    public ProjectInformation(int projectInformationID, String customerName, String customerAddress, String customerPostalCode, String customerCity, String installationIdentification, String installationName) {
        this.projectInformationID = projectInformationID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCity = customerCity;
        this.caseNumber = installationIdentification;
        this.installationName = installationName;
    }

    public ProjectInformation(int projectInformationID, String customerName, String customerAddress, String customerPostalCode, String customerCity) {
        this.projectInformationID = projectInformationID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCity = customerCity;
    }

    public ProjectInformation(String customerName, String customerAddress, String customerPostalCode, String customerCity, String installationIdentification, String installationName) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCity = customerCity;
        this.caseNumber = installationIdentification;
        this.installationName = installationName;

    }

    public ProjectInformation() {

    }

    public int getProjectInformationID() {
        return projectInformationID;
    }

    public void setProjectInformationID(int projectInformationID) {
        this.projectInformationID = projectInformationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    public int getFk_userID() {
        return fk_userID;
    }

    public void setFk_userID(int fk_userID) {
        this.fk_userID = fk_userID;
    }



    public int getFk_questionGroup() {
        return fk_questionGroup;
    }

    public void setFk_questionGroup(int fk_questionGroup) {
        this.fk_questionGroup = fk_questionGroup;
    }


    @Override
    public String toString() {
        return "ProjectInformation{" +
                "projectInformationID=" + projectInformationID +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerPostalCode='" + customerPostalCode + '\'' +
                ", customerCity='" + customerCity + '\'' +
                ", installationIdentification='" + caseNumber + '\'' +
                ", installationName='" + installationName + '\'' +
                ", fk_userID=" + fk_userID +
                ", fk_questionGroup=" + fk_questionGroup +
                '}';
    }



    private static MySQL mySQL = new MySQL();

    public static ArrayList<Room> getRoomsFromProjectInformationID(int projectID) {

        return mySQL.getRoomsFromProjectID(projectID) ;

    }

}
