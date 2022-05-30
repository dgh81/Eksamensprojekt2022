package com.example.eksamensprojekt2022.UI.DisplayFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UI.Adaptors.HeaderSlideAdapter;
import com.example.eksamensprojekt2022.UI.Adaptors.SliderAdapter;

import java.util.ArrayList;


public class QuestionFragment extends Fragment {

    View view;

    private ViewPager slideVeiwPager;
    private ViewPager headlineBox;
    private LinearLayout dotLayout;
    private PagerAdapter headerSlideAdapter;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;

    int groupIndex;
    int questionIndex;




    public QuestionFragment(int groupIndex , int questionIndex) {
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

        headerSlideAdapter = new HeaderSlideAdapter(getActivity() , headlineBox );

        headlineBox.setAdapter(headerSlideAdapter);

        slideVeiwPager.setAdapter(sliderAdapter);

        slideVeiwPager.addOnPageChangeListener(listener);

        headlineBox.addOnPageChangeListener(headerListener);







        headerIsUpdateFromPageMoving = true;

        if (groupIndex >= InspectionInformation.getInstance().getQuestionGroups().size()) {
            groupIndex --;
            addDots();
            updateDots(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex));
            slideVeiwPager.setCurrentItem(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex));
            headlineBox.setCurrentItem( InspectionInformation.getInstance().getQuestionGroupIndexByQuestionID(  InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex) ));
            groupIndex ++;

        } else {
            addDots();
            updateDots(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex));
            slideVeiwPager.setCurrentItem(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex));
            headlineBox.setCurrentItem( InspectionInformation.getInstance().getQuestionGroupIndexByQuestionID(  InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(groupIndex , questionIndex) ));
        }










        headerIsUpdateFromPageMoving = false;

        return view;
    }



    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            ArrayList<com.google.android.material.textfield.TextInputLayout> textFields = new ArrayList<>();

            textFields.add(slideVeiwPager.findViewById(R.id.notes));
            textFields.add(slideVeiwPager.findViewById(R.id.group));
            textFields.add(slideVeiwPager.findViewById(R.id.overgangsmodstandR));
            textFields.add(slideVeiwPager.findViewById(R.id.RCD));
            textFields.add(slideVeiwPager.findViewById(R.id.k_group));

            for (com.google.android.material.textfield.TextInputLayout text : textFields) {
                if (text != null) {
                    text.requestFocus();
                    text.clearFocus();
                }
            }





            if (dots != null)
                updateDots(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }




    };

        ViewPager.OnPageChangeListener headerListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (!headerIsUpdateFromPageMoving) {
                    slideVeiwPager.setCurrentItem(InspectionInformation.getInstance().getTotalQuestionIndexFromQuestionGroupIDAndQuestionID(position, 0), true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };






    public void addDots() {

        int dotsSize = 0;

        if (groupIndex < InspectionInformation.getInstance().getQuestionGroups().size() ) {
            dotsSize = InspectionInformation.getInstance().getQuestionGroups().get(groupIndex).getQuestions().size();
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size()   ) {
            dotsSize = InspectionInformation.getInstance().getKredsdetaljer().size();
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size() + 1){
            dotsSize = 1;
        } else if (groupIndex == InspectionInformation.getInstance().getQuestionGroups().size() + 2) {
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

            updateHeadline(pos);

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

    private boolean headerIsUpdateFromPageMoving = false;



    public void updateHeadline(int position) {


        headerIsUpdateFromPageMoving = true;

        headlineBox.setCurrentItem( InspectionInformation.getInstance().getQuestionGroupIndexByQuestionID(position) , false  );

        headerIsUpdateFromPageMoving = false;

    }


    public void updateGroup(int id) {
        groupIndex = InspectionInformation.getInstance().getQuestionGroupIndexByQuestionID(id);

        addDots();


    }


    @Override
    public String toString() {
        return "Question";
    }


    public void dotsIsVisible(boolean visibility) {


        System.out.println("???");

        if (visibility) {
            dotLayout.setVisibility(View.VISIBLE);
        } else {
            dotLayout.setVisibility(View.GONE);
        }






    }

}