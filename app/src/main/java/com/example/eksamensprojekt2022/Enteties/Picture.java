package com.example.eksamensprojekt2022.Enteties;

import android.graphics.Bitmap;

public class Picture {
    int pictureID;
    Bitmap bitmap;
    String name;
    int inspectionInformationID;
    String comment;

    public Picture(Bitmap bitmap, String name, int inspectionInformationID, String comment) {
        this.bitmap = bitmap;
        this.name = name;
        this.inspectionInformationID = inspectionInformationID;
        this.comment = comment;
    }

    public Picture(Bitmap bitmap, int inspectionInformationID, String comment) {
        this.bitmap = bitmap;
        this.inspectionInformationID = inspectionInformationID;
        this.comment = comment;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInspectionInformationID() {
        return inspectionInformationID;
    }

    public void setInspectionInformationID(int inspectionInformationID) {
        this.inspectionInformationID = inspectionInformationID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "bitmap=" + bitmap +
                ", name='" + name + '\'' +
                ", inspectionInformationID=" + inspectionInformationID +
                ", comment='" + comment + '\'' +
                '}';
    }
}
