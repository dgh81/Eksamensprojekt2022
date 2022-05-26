package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.eksamensprojekt2022.R;


public class PicFragment extends Fragment {

    public static ImageView picFragmentImageView;
    Button takePictureButton;

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

        /*view = inflater.inflate(R.layout.fragment_pic, container, false);
        takePictureButton = (Button) view.findViewById(R.id.takePictureButton);*/
        //System.out.println("pictureButton: " + takePictureButton);

        return inflater.inflate(R.layout.fragment_pic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_pic, container, false);
        picFragmentImageView = (ImageView) getView().findViewById(R.id.imageView3);
    }


}