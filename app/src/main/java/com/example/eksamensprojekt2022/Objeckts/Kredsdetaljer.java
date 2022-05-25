package com.example.eksamensprojekt2022.Objeckts;

public class Kredsdetaljer {
    int ID;
    String group;
    String oB;
    String karakteristik;
    String tvaersnit;
    String maksOB;
    String zsRa;
    // zs = false
    // ra = true
    boolean zSRa = false;
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
        this.maksOB = maksOB;
        this.zsRa = zsRa;
        this.isolation = isolation;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }


    public int getID() {
        return ID;
    }

    public String getGroup() {
        return group;
    }

    public String getoB() {
        return oB;
    }

    public String getKarakteristik() {
        return karakteristik;
    }

    public String getTvaersnit() {
        return tvaersnit;
    }

    public String getMaksOB() {
        return maksOB;
    }

    public String getZsRa() {
        return zsRa;
    }

    public int  iszSRa() {
        return zSRa ? 1 : 0 ;
    }

    public String getIsolation() {
        return isolation;
    }

    public int getFk_inspectionInformationID() {
        return fk_inspectionInformationID;
    }




    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setoB(String oB) {
        this.oB = oB;
    }

    public void setKarakteristik(String karakteristik) {
        this.karakteristik = karakteristik;
    }

    public void setTvaersnit(String tvaersnit) {
        this.tvaersnit = tvaersnit;
    }

    public void setMaksOB(String maksOB) {
        this.maksOB = maksOB;
    }

    public void setZsRa(String zsRa) {
        this.zsRa = zsRa;
    }

    public void setzSRa(boolean zSRa) {
        this.zSRa = zSRa;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public void setFk_inspectionInformationID(int fk_inspectionInformationID) {
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }
}
