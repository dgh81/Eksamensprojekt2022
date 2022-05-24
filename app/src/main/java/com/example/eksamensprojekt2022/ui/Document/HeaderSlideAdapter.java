package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;
import com.google.android.material.resources.TextAppearanceFontCallback;

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

        TextView headlineText = view.findViewById(R.id.header_title);

        headlineText.setText(InspectionInformation.getInstance().getQuestionGroups().get(position).getTitle());

        headlineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(position  + " title clicked");

            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);

    }

}
