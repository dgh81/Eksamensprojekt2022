package com.example.eksamensprojekt2022;

import java.time.LocalDate;

public class InspectionInformation {

    int inspectorInformation = 0;
    String inspectorName = "";
    LocalDate inspectionDate = null;

    public InspectionInformation(int inspectorInformation, String inspectorName, LocalDate inspectionDate) {
        this.inspectorInformation = inspectorInformation;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
    }

    public InspectionInformation() {
    }

    public int getInspectorInformation() {
        return inspectorInformation;
    }

    public void setInspectorInformation(int inspectorInformation) {
        this.inspectorInformation = inspectorInformation;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
