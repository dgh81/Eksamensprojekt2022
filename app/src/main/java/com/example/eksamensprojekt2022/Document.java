package com.example.eksamensprojekt2022;

import java.util.ArrayList;

public class Document {

    private InspectionInformation inspectionInformation;
    private ArrayList<Inspection> inspections;
    private ArrayList<Room> rooms;

    //



    private static Document documentInstance = null;

    public static Document getInstance() {
        if (documentInstance == null)
            documentInstance = new Document();

        return documentInstance;
        }












}
