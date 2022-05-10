package com.example.eksamensprojekt2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class documentActivity extends AppCompatActivity {

    private ViewPager slideVeiwPager;
    private LinearLayout dotLayout;

    private SliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        slideVeiwPager = findViewById(R.id.contentBox);
        dotLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this , slideVeiwPager);

        slideVeiwPager.setAdapter(sliderAdapter);

    }











}