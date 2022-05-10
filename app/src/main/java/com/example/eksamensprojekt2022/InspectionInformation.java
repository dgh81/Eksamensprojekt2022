package com.example.eksamensprojekt2022;

import java.time.LocalDate;

public class InspectionInformation {

    String installationName = "";
    String inspectorName = "";
    LocalDate date = null;

    public InspectionInformation(String installationName, String inspectorName, LocalDate date) {
        this.installationName = installationName;
        this.inspectorName = inspectorName;
        this.date = date;
    }

    public InspectionInformation() {
    }

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
