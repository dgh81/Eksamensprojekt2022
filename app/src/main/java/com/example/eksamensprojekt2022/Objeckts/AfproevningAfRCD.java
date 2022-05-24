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
}
