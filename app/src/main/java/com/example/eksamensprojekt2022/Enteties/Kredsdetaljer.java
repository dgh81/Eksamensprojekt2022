package com.example.eksamensprojekt2022.Enteties;

public class Kredsdetaljer {
    int ID = 0;
    String group = "";
    String oB = "";
    String karakteristik = "";
    String tvaersnit = "";
    String MaksOB = "";
    String zsRaValue = "";
    // zs = false = 0
    // ra = true = 1
    boolean zSRa = true;
    String isolation = "";
    int fk_inspectionInformationID = 0;


    public Kredsdetaljer() {

    }

    public Kredsdetaljer(int ID, String group, String oB, String karakteristik, String tvaersnit, String maksOB, boolean zSRa, String zsRaValue, String isolation , int fk_inspectionInformationID) {
        this.ID = ID;
        this.group = group;
        this.oB = oB;
        this.karakteristik = karakteristik;
        this.tvaersnit = tvaersnit;
        MaksOB = maksOB;
        this.zSRa = zSRa;
        this.zsRaValue = zsRaValue;
        this.isolation = isolation;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getoB() {
        return oB;
    }

    public void setoB(String oB) {
        this.oB = oB;
    }

    public String getKarakteristik() {
        return karakteristik;
    }

    public void setKarakteristik(String karakteristik) {
        this.karakteristik = karakteristik;
    }

    public String getTvaersnit() {
        return tvaersnit;
    }

    public void setTvaersnit(String tvaersnit) {
        this.tvaersnit = tvaersnit;
    }

    public String getMaksOB() {
        return MaksOB;
    }

    public void setMaksOB(String maksOB) {
        MaksOB = maksOB;
    }

    public String getZsRaValue() {
        return zsRaValue;
    }

    public void setZsRaValue(String zsRaValue) {
        this.zsRaValue = zsRaValue;
    }

    public boolean iszSRa() {
        return zSRa;
    }

    public void setzSRa(boolean zSRa) {
        this.zSRa = zSRa;
    }

    public String getIsolation() {
        return isolation;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public int getFk_inspectionInformationID() {
        return fk_inspectionInformationID;
    }

    public void setFk_inspectionInformationID(int fk_inspectionInformationID) {
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }

    public boolean isAnswered() {
        boolean returnValue = true;


        if (group == null || group.equals("")) {returnValue = false;}
        if (oB == null || oB.equals("")) {returnValue = false;}
        if (karakteristik == null || karakteristik.equals("")) {returnValue = false;}
        if (tvaersnit == null ||tvaersnit.equals("")) {returnValue = false;}
        if (MaksOB == null ||MaksOB.equals("")) {returnValue = false;}
        if (zsRaValue == null ||zsRaValue.equals("")) {returnValue = false;}
        if (isolation == null ||isolation.equals("")) {returnValue = false;}

        return returnValue;

    }






}
