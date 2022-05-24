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




}
