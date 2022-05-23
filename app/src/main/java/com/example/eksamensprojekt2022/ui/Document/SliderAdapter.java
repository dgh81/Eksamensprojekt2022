package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.Question;
import com.example.eksamensprojekt2022.Objeckts.QuestionGroup;
import com.example.eksamensprojekt2022.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    private ViewPager sliderAdapter;

    private InspectionInformation inspectionInformation;







    public SliderAdapter (Context context , ViewPager slideVeiwPager , InspectionInformation inspectionInformation) {
        this.context = context;
        this.sliderAdapter = slideVeiwPager;
        this.inspectionInformation = inspectionInformation;
    }





    public String[] testQuestion = {
            "this is test question 1",
            "this is test question 2",
            "this is test question 3",
            "This is a long text to  simulate a long text to see how it will handle a long text and if there should be a limit to how long the text can be i guess this is long enough for all? Or the text should just get smaller as the question gets larger it does good"
    };

    public String[] titles = {
            "Generalt",
            "Generalt",
            "Generalt",
            "Generalt",
    };









    @Override
    public int getCount() {

        int size = 0;

        for (QuestionGroup group: inspectionInformation.getQuestionGroups() ) {
            size += group.getQuestions().size();
        }
        return size;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        String questionText = inspectionInformation.getQuestionGroups().get(inspectionInformation.getQuestionGroupIndexByQuestionID(position)).getQuestions().get(inspectionInformation.getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(position)).getQuestion();



        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout , container , false);

        TextView text = (TextView) view.findViewById(R.id.questionText);
        Button yesButton = (Button) view.findViewById(R.id.yesButton);
        Button noButton = (Button) view.findViewById(R.id.noButton);
        Button notRelevantButton = (Button) view.findViewById(R.id.notRelevantButton);

        com.google.android.material.textfield.TextInputLayout s = view.findViewById(R.id.notes);

        s.clearFocus();



        Question question = InspectionInformation.getInstance().getQuestionGroups().get( InspectionInformation.instance.getQuestionGroupIndexByQuestionID(position)
        ).getQuestions().get(InspectionInformation.getInstance().getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(position));


        s.getEditText().setText(question.getComment());

        s.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                   question.setComment(s.getEditText().getText().toString());


                }
            }
        });

        colorButtons(question , yesButton ,noButton, notRelevantButton );

        yesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                question.setAnswerID(1);

                colorButtons(question , yesButton , noButton , notRelevantButton);

                s.clearFocus();

               // sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);

            }
        });



            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setAnswerID(2);
                    colorButtons(question , yesButton , noButton , notRelevantButton);

                    s.clearFocus();
                  //  sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);
                }
            });

            notRelevantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setAnswerID(3);
                    colorButtons(question , yesButton , noButton , notRelevantButton);

                    s.clearFocus();
                   // sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);
                }
            });


        text.setText(questionText);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);

    }


    public void colorButtons(Question question, Button yesButton , Button noButton , Button notRelevantButton ) {

        System.out.println(question.getAnswerID() + " color ");

        System.out.println(question.getFk_questionGroup() + " group");

        System.out.println(question.getQuestion());

        switch (question.getAnswerID()   ) {


            case 1:
                yesButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));
                noButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                notRelevantButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                break;
            case 2:
                noButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                yesButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                notRelevantButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                break;
            case 3:
                notRelevantButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.dotsColor));
                yesButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                noButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                break;
        }




    }



}










