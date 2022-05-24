package com.example.eksamensprojekt2022.Objeckts;

public class Kredsdetaljer {
    int ID;
    String group;
    String oB;
    String karakteristik;
    String tvaersnit;
    String MaksOB;
    String zsRa;
    // zs = false
    // ra = true
    boolean zSRa;
    String isolation;
    int fk_inspectionInformationID;


    public Kredsdetaljer() {

    }

    public Kredsdetaljer(int ID,String group, String oB, String karakteristik, String tvaersnit, String maksOB, String zsRa, String isolation , int fk_inspectionInformationID) {
        this.ID = ID;
        this.group = group;
        this.oB = oB;
        this.karakteristik = karakteristik;
        this.tvaersnit = tvaersnit;
        MaksOB = maksOB;
        this.zsRa = zsRa;
        this.isolation = isolation;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }





}
