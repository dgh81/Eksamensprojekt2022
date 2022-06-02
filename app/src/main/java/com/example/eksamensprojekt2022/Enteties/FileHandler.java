package com.example.eksamensprojekt2022.Enteties;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler  {

    Context mContext;


    public static String currentImagePath;
    public static String currentPDFPath;


    public FileHandler() {

    }

    public FileHandler(Context mContext) {
        this.mContext = mContext;
    }

    public File getImageFile() throws IOException {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, 112 );
            } else {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageName = "jpg_"+timeStamp+"_";
                File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File imageFile = File.createTempFile(imageName,".jpg",storageDir);
                currentImagePath = imageFile.getAbsolutePath();
                return imageFile;
            }
        } else {
            return  null;
        }

        return  null;
    }


    public String bitmapEncodeToBaseString(Bitmap bitmapToDecode) {

        System.out.println(bitmapToDecode + " help me find it");


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


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
