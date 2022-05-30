package com.example.eksamensprojekt2022.Enteties;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler {

    public static String currentImagePath;
    public static String currentPDFPath;

    public File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName,".jpg",storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }
    public String bitmapEncodeToBaseString(Bitmap bitmapToDecode) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //OBS 50% quality giver ca 10 gange mindre data i db!!:
        bitmapToDecode.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImageString;
    }
    public Bitmap bitmapDecodeFromBaseString(String encodedImageString) {
        byte[] decodedString = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }
    public File getPDFFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pdfName = "pdf_"+timeStamp+"_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File pdfFile = File.createTempFile(pdfName,".pdf",storageDir);
        currentPDFPath = pdfFile.getAbsolutePath();
        return pdfFile;
    }
}
