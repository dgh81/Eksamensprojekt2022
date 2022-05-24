package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;

import java.util.Objects;


public class Question extends Fragment {

    View view;

    private ViewPager slideVeiwPager;
    private ViewPager headlineBox;
    private LinearLayout dotLayout;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;

    int groupIndex;
    int questionIndex;




    public Question(int groupIndex , int questionIndex) {
        this.groupIndex = groupIndex;
        this.questionIndex = questionIndex;



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_question, container, false);

        slideVeiwPager = view.findViewById(R.id.contentBox);

        headlineBox = view.findViewById(R.id.headlineBox);

        dotLayout = view.findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(getActivity() , slideVeiwPager );

        PagerAdapter headerSlideAdapter = new HeaderSlideAdapter(getActivity() , headlineBox );

        headlineBox.setAdapter(headerSlideAdapter);

        slideVeiwPager.setAdapter(sliderAdapter);

        slideVeiwPager.addOnPageChangeListener(listener);

        addDots();

        updateDots(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex));

        System.out.println(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex)  + " current item");

        slideVeiwPager.setCurrentItem(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex)  );


        return view;
    }



    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            com.google.android.material.textfield.TextInputLayout s =  slideVeiwPager.findViewById(R.id.notes);

          //  s.clearFocus();

            if (s != null) {

                s.getEditText().clearFocus();

            }


            if (dots != null)
                updateDots(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };







    public void addDots() {

        int dotsSize = 0;

        if (groupIndex < InspectionInformation.getInstance().getQuestionGroups().size() ) {
            dotsSize = InspectionInformation.getInstance().getQuestionGroups().get(groupIndex).getQuestions().size();
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size()  + 1 ) {
            dotsSize = InspectionInformation.getInstance().getKredsdetaljer().size();
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size() + 2){
            dotsSize = 1;
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size() + 3) {
            dotsSize = InspectionInformation.getInstance().getAfprøvningAfRCD().size();
        } else {
            dotsSize = InspectionInformation.getInstance().getKortslutningsstroms().size();
        }

        System.out.println(InspectionInformation.getInstance().getQuestionGroups().size());
        System.out.println(dotsSize + " dotsize" + groupIndex + " groupIndex");

        dots = new TextView[dotsSize];
        dotLayout.removeAllViews();

        System.out.println(dots.length + " length");


        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotLayout.addView(dots[i]);
        }
    }




    public void updateDots(int pos) {

        if (!InspectionInformation.getInstance().isTotalIndexInsideQuestionGroup(pos , groupIndex)) {

            System.out.println("false");

            updateGroup(pos);
        }


        for (int i = 0; i < dots.length; i++) {
            dots[i].setTextColor(getResources().getColor(R.color.dotsColor));
        }


        if (dots.length > 0) {
            dots[InspectionInformation.getInstance().getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(pos)].setTextColor(getResources().getColor(R.color.selectedDotColor));
        }
    }

    public void updateGroup(int id) {
        groupIndex = InspectionInformation.getInstance().getQuestionGroupIndexByQuestionID(id);

        addDots();


    }


    @Override
    public String toString() {
        return "Question";
    }
}