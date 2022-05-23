package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;

public class HeaderSlideAdapter extends PagerAdapter {


    Context context;
    LayoutInflater layoutInflater;

    private ViewPager sliderAdapter;




    public HeaderSlideAdapter (Context context , ViewPager slideVeiwPager  ) {
        this.context = context;
        this.sliderAdapter = slideVeiwPager;
    }


    @Override
    public int getCount() {
        return InspectionInformation.getInstance().getQuestionGroups().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_header_layout , container , false);



        return view;
    }
}
