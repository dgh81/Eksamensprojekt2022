package com.example.eksamensprojekt2022.ui.Document;

import static android.app.Activity.RESULT_OK;
import static com.example.eksamensprojekt2022.Objeckts.FileHandler.currentImagePath;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eksamensprojekt2022.MySQL;
import com.example.eksamensprojekt2022.Objeckts.FileHandler;
import com.example.eksamensprojekt2022.R;

import java.io.File;
import java.io.IOException;


public class PicFragment extends Fragment {

    public static ImageView picFragmentImageView;
    Button takePictureButton;
    Button savePictureButton;
    public static Bitmap bitmapFromTempFile;

    final int CAMERA_CODE = 100;

    Uri imageUri;

    public PicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        return inflater.inflate(R.layout.fragment_pic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takePictureButton = (Button) view.findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test on click button from fragment");
                try {
                    android.content.Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File imageFile = null;
                    try{
                        imageFile= new FileHandler().getImageFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(imageFile!=null){
                        if (Build.VERSION.SDK_INT >= 21) {
                            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                            } else {
                                imageUri = FileProvider.getUriForFile(view.getContext(),"com.example.eksamensprojekt2022.fileprovider",imageFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(cameraIntent, CAMERA_CODE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        savePictureButton = view.findViewById(R.id.savePictureButton);
        savePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encodedImageString = new FileHandler().bitmapEncodeToBaseString(bitmapFromTempFile);
                MySQL mysql = new MySQL();
                mysql.createBitmapString(encodedImageString, 1, "sagnr + rumnavn + username: + comment?");
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " - " + resultCode);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            File imgFile = new File(currentImagePath);
            String path = imgFile.getAbsolutePath();
            bitmapFromTempFile = BitmapFactory.decodeFile(path);
            ImageView fragmentImageView = (ImageView) getView().findViewById(R.id.imageView3);
            fragmentImageView.setImageBitmap(bitmapFromTempFile);
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Toast.makeText(getContext(), "This Permission is needed for the app to work perfectly!", Toast.LENGTH_SHORT).show();
                }
            default:
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}