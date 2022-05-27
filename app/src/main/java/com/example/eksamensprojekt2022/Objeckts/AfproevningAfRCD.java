package com.example.eksamensprojekt2022.Objeckts;

public class AfproevningAfRCD {

    int ID;
    String RCD;
    String field1;
    String field2;
    String field3;
    String field4;
    String field5;
    String field6;
    String OK;
    int fk_inspectionInformationID;


    public AfproevningAfRCD() {


    }

    public AfproevningAfRCD(int ID, String RCD, String field1, String field2, String field3, String field4, String field5, String field6, String OK, int fk_inspectionInformationID) {
        this.ID = ID;
        this.RCD = RCD;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.OK = OK;
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }

    public int getID() {
        return ID;
    }

    public String getRCD() {
        return RCD;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    public String getField4() {
        return field4;
    }

    public String getField5() {
        return field5;
    }

    public String getField6() {
        return field6;
    }

    public String getOK() {
        return OK;
    }

    public int getFk_inspectionInformationID() {
        return fk_inspectionInformationID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRCD(String RCD) {
        this.RCD = RCD;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public void setField6(String field6) {
        this.field6 = field6;
    }

    public void setOK(String OK) {
        this.OK = OK;
    }

    public void setFk_inspectionInformationID(int fk_inspectionInformationID) {
        this.fk_inspectionInformationID = fk_inspectionInformationID;
    }


    public boolean isAnswered() {
        boolean returnValue = true;

        if (RCD == null || RCD.equals("")) {returnValue = false;}
        if (field1 == null || field1.equals("")) {returnValue = false;}
        if (field2 == null || field2.equals("")) {returnValue = false;}
        if (field3 == null || field3.equals("")) {returnValue = false;}
        if (field4 == null || field4.equals("")) {returnValue = false;}
        if (field5 == null || field5.equals("")) {returnValue = false;}
        if (field6 == null || field6.equals("")) {returnValue = false;}

        //TODO: all boolean here

        return  returnValue;

    }

}
