package com.example.eksamensprojekt2022.Objeckts;

public class Kortslutningsstrom {

    int ID;
    String k_gruppe;
    String k_KiK;
    String k_maaltIPunkt;
    String s_gruppe;
    String s_U;
    String s_maaltIPunkt;
    int fk_inspectionInformationID;


    public Kortslutningsstrom() {

    }

    public Kortslutningsstrom(int ID, String k_gruppe, String k_KiK, String k_maaltIPunkt, String s_gruppe, String s_U, String s_maaltIPunkt, int fk_inspectionInformationID) {
        this.ID = ID;
        this.k_gruppe = k_gruppe;
        this.k_KiK = k_KiK;
        this.k_maaltIPunkt = k_maaltIPunkt;
        this.s_gruppe = s_gruppe;
        this.s_U = s_U;
        this.s_maaltIPunkt = s_maaltIPunkt;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getK_gruppe() {
        return k_gruppe;
    }

    public void setK_gruppe(String k_gruppe) {
        this.k_gruppe = k_gruppe;
    }

    public String getK_KiK() {
        return k_KiK;
    }

    public void setK_KiK(String k_KiK) {
        this.k_KiK = k_KiK;
    }

    public String getK_maaltIPunkt() {
        return k_maaltIPunkt;
    }

    public void setK_maaltIPunkt(String k_maaltIPunkt) {
        this.k_maaltIPunkt = k_maaltIPunkt;
    }

    public String getS_gruppe() {
        return s_gruppe;
    }

    public void setS_gruppe(String s_gruppe) {
        this.s_gruppe = s_gruppe;
    }

    public String getS_U() {
        return s_U;
    }

    public void setS_U(String s_U) {
        this.s_U = s_U;
    }

    public String getS_maaltIPunkt() {
        return s_maaltIPunkt;
    }

    public void setS_maaltIPunkt(String s_maaltIPunkt) {
        this.s_maaltIPunkt = s_maaltIPunkt;
    }

    public int getFk_inspectionInformationID() {
        return fk_inspectionInformationID;
    }

    public void setFk_inspectionInformationID(int fk_inspectionInformationID) {
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }
}
