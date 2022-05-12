package com.example.eksamensprojekt2022;

import java.time.LocalDate;
import java.util.Date;

public class InspectionInformation {

    int inspectorInformationID = 0;
    String inspectorName = "";
    Date inspectionDate = null;
    int fk_projectID = 0;

    public InspectionInformation(int inspectorInformationID, String inspectorName, Date inspectionDate, int fk_projectID) {
        this.inspectorInformationID = inspectorInformationID;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.fk_projectID = fk_projectID;
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
}
