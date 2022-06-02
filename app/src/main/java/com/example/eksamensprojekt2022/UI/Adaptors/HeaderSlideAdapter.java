package com.example.eksamensprojekt2022.UI.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.R;

import java.util.ArrayList;

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

        int size = InspectionInformation.getInstance().getQuestionGroups().size();

        if (InspectionInformation.getInstance().getKredsdetaljer().size() > 0 ) { size ++; }
        if (InspectionInformation.getInstance().getAfprøvningAfRCD().size() > 0) {size ++;}
        if (InspectionInformation.getInstance().getKortslutningsstroms().size() > 0) {size ++;}

        // there is always a Overgangsmodstand for jordingsleder og jordelektrode R
        size ++;

        return  size;

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

        ArrayList<String> titles = new ArrayList<>();

        titles.add("Kredsdetaljer");
        titles.add("Overgangsmodstand for jordingsleder og jordelektrode R: ");
        titles.add("Afprøvning af RCD’er");
        titles.add("Kortslutningsstrøm");

      //  if (position == InspectionInformation.getInstance().getQuestionGroups().size() ) {position --;}


        if (position < InspectionInformation.getInstance().getQuestionGroups().size() ) {

            headlineText.setText(InspectionInformation.getInstance().getQuestionGroups().get(position).getTitle());

        } else if (position == InspectionInformation.getInstance().getQuestionGroups().size()    ) {
            headlineText.setText(titles.get(0));
        } else if (position == InspectionInformation.getInstance().getQuestionGroups().size() + 1 ) {
            headlineText.setText(titles.get(1));
        } else if (position == InspectionInformation.getInstance().getQuestionGroups().size() + 2 ) {
            headlineText.setText(titles.get(2));
        } else if (position == InspectionInformation.getInstance().getQuestionGroups().size() + 3 ) {
            headlineText.setText(titles.get(3));
        }

        headlineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
