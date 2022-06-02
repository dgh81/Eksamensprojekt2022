package com.example.eksamensprojekt2022.UI.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Enteties.AfproevningAfRCD;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Kortslutningsstrom;
import com.example.eksamensprojekt2022.Enteties.Kredsdetaljer;
import com.example.eksamensprojekt2022.Enteties.Question;
import com.example.eksamensprojekt2022.Enteties.QuestionGroup;
import com.example.eksamensprojekt2022.R;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    private ViewPager sliderAdapter;






    public SliderAdapter (Context context , ViewPager slideVeiwPager  ) {
        this.context = context;
        this.sliderAdapter = slideVeiwPager;
    }



    @Override
    public int getCount() {

        int size = 0;

        for (QuestionGroup group: InspectionInformation.getInstance().getQuestionGroups() ) {
            size += group.getQuestions().size();
        }

        size += InspectionInformation.getInstance().getKredsdetaljer().size();

        size ++;

        size += InspectionInformation.getInstance().getAfprøvningAfRCD().size();

        size += InspectionInformation.getInstance().getKortslutningsstroms().size();

        return size;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position < InspectionInformation.getInstance().getTotalNumberOfQuestions()) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

            TextView text = (TextView) view.findViewById(R.id.questionText);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);
            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button notRelevantButton = (Button) view.findViewById(R.id.notRelevantButton);

            com.google.android.material.textfield.TextInputLayout s = view.findViewById(R.id.notes);

            s.clearFocus();


            Question question = InspectionInformation.getInstance().getQuestionGroups().get(InspectionInformation.instance.getQuestionGroupIndexByQuestionID(position)
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

            colorButtons(question, yesButton, noButton, notRelevantButton);

            yesButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    question.setAnswerID(1);

                    colorButtons(question, yesButton, noButton, notRelevantButton);

                    s.clearFocus();

                    // sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);

                }
            });


            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setAnswerID(2);
                    colorButtons(question, yesButton, noButton, notRelevantButton);

                    s.clearFocus();
                    //  sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);
                }
            });

            notRelevantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setAnswerID(3);
                    colorButtons(question, yesButton, noButton, notRelevantButton);

                    s.clearFocus();
                    // sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);
                }
            });


            text.setText(question.getQuestion());

            container.addView(view);

            return view;


        } else if (position < InspectionInformation.getInstance().getTotalNumberOfQuestions() + InspectionInformation.getInstance().getKredsdetaljer().size()) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_tabel_input_kredsdetaljer, container, false);

            com.google.android.material.textfield.TextInputLayout groupTextField = view.findViewById(R.id.group);
            com.google.android.material.textfield.TextInputLayout oBTextField = view.findViewById(R.id.OB);
            com.google.android.material.textfield.TextInputLayout karakteristikTextField = view.findViewById(R.id.Karakteristik);
            com.google.android.material.textfield.TextInputLayout tvaersnitTextField = view.findViewById(R.id.Tvaersnit);
            com.google.android.material.textfield.TextInputLayout maksOBTextField = view.findViewById(R.id.MaksOB);
            //TODO Lav boolean værdi
            CheckBox zs = view.findViewById(R.id.zsCheckBox);
            CheckBox ra = view.findViewById(R.id.raCheckBox);


            com.google.android.material.textfield.TextInputLayout zsRaTextField = view.findViewById(R.id.zsRa);
            com.google.android.material.textfield.TextInputLayout isolationTextField = view.findViewById(R.id.Isolation);

            Kredsdetaljer kredsdetaljer = InspectionInformation.getInstance().getKredsdetaljer().get(
                    InspectionInformation.getInstance().getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(position));

            groupTextField.getEditText().setText( kredsdetaljer.getGroup());
            oBTextField.getEditText().setText(kredsdetaljer.getoB());
            karakteristikTextField.getEditText().setText(kredsdetaljer.getKarakteristik());
            tvaersnitTextField.getEditText().setText(kredsdetaljer.getTvaersnit());
            maksOBTextField.getEditText().setText(kredsdetaljer.getMaksOB());
            zsRaTextField.getEditText().setText(kredsdetaljer.getZsRaValue());
            isolationTextField.getEditText().setText(kredsdetaljer.getIsolation());
            zs.setChecked(kredsdetaljer.iszSRa());
            ra.setChecked(!kredsdetaljer.iszSRa());

            zs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    zs.setChecked(isChecked);
                    ra.setChecked(!isChecked);

                    for (int i = 0; i < InspectionInformation.getInstance().getKredsdetaljer().size(); i++) {
                        InspectionInformation.getInstance().getKredsdetaljer().get(i).setzSRa(isChecked);
                    }

                    notifyDataSetChanged();

                }
            });

            ra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    zs.setChecked(!isChecked);
                    ra.setChecked(isChecked);

                    for (int i = 0; i < InspectionInformation.getInstance().getKredsdetaljer().size(); i++) {
                        InspectionInformation.getInstance().getKredsdetaljer().get(i).setzSRa(!isChecked);
                    }

                    notifyDataSetChanged();

                }
            });





            groupTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                    kredsdetaljer.setGroup(groupTextField.getEditText().getText().toString());
                }
            });

            oBTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setoB(oBTextField.getEditText().getText().toString());
                }
            });

            karakteristikTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setKarakteristik(karakteristikTextField.getEditText().getText().toString());
                }
            });

            tvaersnitTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setTvaersnit(tvaersnitTextField.getEditText().getText().toString());
                }
            });

            maksOBTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setMaksOB(maksOBTextField.getEditText().getText().toString());
                }
            });

            zsRaTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setZsRaValue(zsRaTextField.getEditText().getText().toString());
                }
            });


            isolationTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kredsdetaljer.setIsolation(isolationTextField.getEditText().getText().toString());
                }
            });


            container.addView(view);

            return view;

        } else if (position < InspectionInformation.getInstance().getTotalNumberOfQuestions() + InspectionInformation.getInstance().getKredsdetaljer().size() + 1) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_overgangsmodstand, container, false);

            com.google.android.material.textfield.TextInputLayout overgangsmodstandR = view.findViewById(R.id.overgangsmodstandR);
            overgangsmodstandR.getEditText().setText( InspectionInformation.getInstance().getOvergangsmodstandR());

            overgangsmodstandR.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        InspectionInformation.getInstance().setOvergangsmodstandR(overgangsmodstandR.getEditText().getText().toString());
                }
            });


            container.addView(view);

            return view;


        } else if (position < InspectionInformation.getInstance().getTotalNumberOfQuestions() + InspectionInformation.getInstance().getKredsdetaljer().size()
                + 1 + InspectionInformation.getInstance().getAfprøvningAfRCD().size()) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_afproevning_af_rcd, container, false);

            com.google.android.material.textfield.TextInputLayout rCDTextField = view.findViewById(R.id.RCD);
            com.google.android.material.textfield.TextInputLayout math1TextField = view.findViewById(R.id.math1);
            com.google.android.material.textfield.TextInputLayout math2TextField = view.findViewById(R.id.math2);
            com.google.android.material.textfield.TextInputLayout math3TextField = view.findViewById(R.id.math3);
            com.google.android.material.textfield.TextInputLayout math4TextField = view.findViewById(R.id.math4);
            com.google.android.material.textfield.TextInputLayout math5TextField = view.findViewById(R.id.math5);
            com.google.android.material.textfield.TextInputLayout math6TextField = view.findViewById(R.id.math6);


            AfproevningAfRCD afproevningAfRCD = InspectionInformation.getInstance().getAfprøvningAfRCD().get(
                    InspectionInformation.getInstance().getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(position));

            rCDTextField.getEditText().setText(afproevningAfRCD.getRCD());
            math1TextField.getEditText().setText(afproevningAfRCD.getField1());
            math2TextField.getEditText().setText(afproevningAfRCD.getField2());
            math3TextField.getEditText().setText(afproevningAfRCD.getField3());
            math4TextField.getEditText().setText(afproevningAfRCD.getField4());
            math5TextField.getEditText().setText(afproevningAfRCD.getField5());
            math6TextField.getEditText().setText(afproevningAfRCD.getField6());

            rCDTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setRCD(rCDTextField.getEditText().getText().toString());
                }
            });

            math1TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField1(math1TextField.getEditText().getText().toString());
                }
            });

            math2TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField2(math2TextField.getEditText().getText().toString());
                }
            });

            math3TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField3(math3TextField.getEditText().getText().toString());
                }
            });

            math4TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField4(math4TextField.getEditText().getText().toString());
                }
            });

            math5TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField5(math5TextField.getEditText().getText().toString());
                }
            });

            math6TextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        afproevningAfRCD.setField6(math6TextField.getEditText().getText().toString());
                }
            });

            container.addView(view);

            return view;


        } else if (position < InspectionInformation.getInstance().getTotalNumberOfQuestions() + InspectionInformation.getInstance().getKredsdetaljer().size()
                + 1 + InspectionInformation.getInstance().getAfprøvningAfRCD().size() + InspectionInformation.getInstance().getKortslutningsstroms().size() ) {


            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_kortslutningsstroem, container, false);

            com.google.android.material.textfield.TextInputLayout k_groupTextField = view.findViewById(R.id.k_group);
            com.google.android.material.textfield.TextInputLayout k_IKTextField = view.findViewById(R.id.k_IK);
            com.google.android.material.textfield.TextInputLayout k_measuredTextField = view.findViewById(R.id.k_measured);

            com.google.android.material.textfield.TextInputLayout s_groupTextField = view.findViewById(R.id.s_group);
            com.google.android.material.textfield.TextInputLayout s_IKTextField = view.findViewById(R.id.s_IK);
            com.google.android.material.textfield.TextInputLayout s_measuredTextField = view.findViewById(R.id.s_measured);

            Kortslutningsstrom kortslutningsstrom = InspectionInformation.getInstance().getKortslutningsstroms().get(
                    InspectionInformation.getInstance().getQuestionIndexLeftOverAfterGetQuestionGroupIndexByQuestionID(position));

            k_groupTextField.getEditText().setText(kortslutningsstrom.getK_gruppe());
            k_IKTextField.getEditText().setText(kortslutningsstrom.getK_KiK());
            k_measuredTextField.getEditText().setText(kortslutningsstrom.getK_maaltIPunkt());

            s_groupTextField.getEditText().setText(kortslutningsstrom.getS_gruppe());
            s_IKTextField.getEditText().setText(kortslutningsstrom.getS_U());
            s_measuredTextField.getEditText().setText(kortslutningsstrom.getS_maaltIPunkt());

            k_groupTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setK_gruppe(k_groupTextField.getEditText().getText().toString());
                }
            });

            k_IKTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setK_KiK(k_IKTextField.getEditText().getText().toString());
                }
            });

            k_measuredTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setK_maaltIPunkt(k_measuredTextField.getEditText().getText().toString());
                }
            });

            s_groupTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setS_gruppe(s_groupTextField.getEditText().getText().toString());
                }
            });

            s_IKTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setS_U(s_IKTextField.getEditText().getText().toString());
                }
            });

            s_measuredTextField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        kortslutningsstrom.setS_maaltIPunkt(s_measuredTextField.getEditText().getText().toString());
                }
            });


            container.addView(view);

            return view;

        }

        return null;

    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);

    }


    public void colorButtons(Question question, Button yesButton , Button noButton , Button notRelevantButton ) {

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

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }



}










