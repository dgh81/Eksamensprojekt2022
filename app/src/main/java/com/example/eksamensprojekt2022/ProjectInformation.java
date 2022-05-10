package com.example.eksamensprojekt2022;

public class ProjectInformation {

    int projectInformationID = 0;
    String customerName = "";
    String customerAddress = "";
    String customerPostalCode = "";
    String customerCity = "";
    String installationIdentification = "";
    String installationName = "";

    // ensret store / små bogstaver - husk at ændre i SQL queries hvis ja.
    int fk_UserID = 0;
    int fk_RoomID = 0;
    int fk_questionGroup = 0;

    public ProjectInformation(int projectInformationID, String customerName, String customerAddress, String customerPostalCode, String customerCity, String installationIdentification, String installationName, int fk_UserID, int fk_RoomID, int fk_questionGroup) {
        this.projectInformationID = projectInformationID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCity = customerCity;
        this.installationIdentification = installationIdentification;
        this.installationName = installationName;
        this.fk_UserID = fk_UserID;
        this.fk_RoomID = fk_RoomID;
        this.fk_questionGroup = fk_questionGroup;
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

    public String getInstallationIdentification() {
        return installationIdentification;
    }

    public void setInstallationIdentification(String installationIdentification) {
        this.installationIdentification = installationIdentification;
    }

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    public int getFk_UserID() {
        return fk_UserID;
    }

    public void setFk_UserID(int fk_UserID) {
        this.fk_UserID = fk_UserID;
    }

    public int getFk_RoomID() {
        return fk_RoomID;
    }

    public void setFk_RoomID(int fk_RoomID) {
        this.fk_RoomID = fk_RoomID;
    }

    public int getFk_questionGroup() {
        return fk_questionGroup;
    }

    public void setFk_questionGroup(int fk_questionGroup) {
        this.fk_questionGroup = fk_questionGroup;
    }
}
