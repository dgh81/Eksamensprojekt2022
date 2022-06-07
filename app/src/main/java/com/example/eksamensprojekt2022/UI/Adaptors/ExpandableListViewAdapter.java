package com.example.eksamensprojekt2022.UI.Adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.R;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;


    public ExpandableListViewAdapter(Context context ) {
        this.context = context;
    }





    @Override
    public int getGroupCount() {
        return InspectionInformation.getInstance().getQuestionGroups().size() + 5 ;
    }

    @Override
    public int getChildrenCount(int i) {

        if (i == InspectionInformation.getInstance().getQuestionGroups().size() ) {
            return 0;

        } else if (i >InspectionInformation.getInstance().getQuestionGroups().size() ) {

            if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 1 ) {
                return InspectionInformation.getInstance().getKredsdetaljer().size() + 1;
            }

            if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 2 ) {
                return 0;
            }

            if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 3) {
                return InspectionInformation.getInstance().getAfprøvningAfRCD().size() + 1;
            }

            if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 4) {
                return InspectionInformation.getInstance().getKortslutningsstroms().size() + 1;
            }


        } else {
            int size = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() + 1;

            System.out.println(size + " size");

            return  size ;

        }

        return 0;
    }

    @Override
    public Object getGroup(int i) {

        System.out.println(i);

        return InspectionInformation.getInstance().getQuestionGroups().get(i);
    }

    @Override
    public Object getChild(int i, int i1) {

        System.out.println("????");

        return InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        notifyDataSetChanged();
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {



        if (i == InspectionInformation.getInstance().getQuestionGroups().size()  ) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add_group, null);




        }  else if (i > InspectionInformation.getInstance().getQuestionGroups().size()) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.question_group_list, null);

            TextView text = view.findViewById(R.id.question_group);






            int size = InspectionInformation.getInstance().getQuestionGroups().size();

            if (size + 1 == i) {text.setText("Kredsdetaljer"); text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grid, 0, 0, 0);}
            if (size + 2 == i) {text.setText("Overgangsmodstand for jordingsleder og jordelektrode");text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grid, 0, 0, 0);}
            if (size + 3 == i) {text.setText("Afprøvning");text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grid, 0, 0, 0);}
            if (size + 4 == i) {text.setText("Kortslutningsstrøm");text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grid, 0, 0, 0);}

            LinearLayout layout = view.findViewById(R.id.layout);

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layout.getLayoutParams());

            if (b && size + 2 != i) {
                marginLayoutParams.setMargins(0 , 0 ,0 , 0);
                layout.setBackground(context.getResources().getDrawable(R.drawable.custom_text_field_open));
            }  else{
                marginLayoutParams.setMargins(0 , 0 ,0 , 5);
                layout.setBackground(context.getResources().getDrawable(R.drawable.custom_text_field));
            }

        }  else {

            String groupTitle = InspectionInformation.getInstance().getQuestionGroups().get(i).getTitle();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_group_list, null);


            LinearLayout layout = view.findViewById(R.id.layout);

            TextView text = view.findViewById(R.id.question_group);

            text.setText(groupTitle);

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layout.getLayoutParams());

            if (b) {
                marginLayoutParams.setMargins(0 , 0 ,0 , 0);
                layout.setBackground(context.getResources().getDrawable(R.drawable.custom_text_field_open));
            }  else{
                marginLayoutParams.setMargins(0 , 0 ,0 , 5);
                layout.setBackground(context.getResources().getDrawable(R.drawable.custom_text_field));
            }
        }

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        // i = group
        // i1 = groupIndex

        if (i < InspectionInformation.getInstance().getQuestionGroups().size()   ) {

            if (i1 == InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size()) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);
            } else {

                String questionText = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getQuestion();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);

                TextView text = view.findViewById(R.id.questionText);





                switch (InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getAnswerID()) {
                    case 1:
                        if (InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getComment().equals("")) {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_yes, 0, 0, 0);
                        } else {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_yes, 0, R.drawable.ic_comment_icon, 0);
                    }
                        break;
                    case 2:
                        if (InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getComment().equals("")) {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_no, 0, 0, 0);
                        } else {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_no, 0, R.drawable.ic_comment_icon , 0);
                        }
                        break;
                    case 3:
                        if (InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getComment().equals("")) {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_not_relavant, 0, 0, 0);
                        } else {
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_answard_not_relavant, 0,  R.drawable.ic_comment_icon , 0);
                        }
                    default:
                        if (!InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getComment().equals("")){
                            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question_not_answard, 0,  R.drawable.ic_comment_icon , 0);
                        }
                }

                text.setText(questionText);
            }
        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 1  ) {

            if (i1 < InspectionInformation.getInstance().getKredsdetaljer().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);

                TextView text = view.findViewById(R.id.questionText);

                text.setText("Kredsdetaljer " + (i1 + 1));

                if (InspectionInformation.getInstance().getKredsdetaljer().get(i1).isAnswered()) {
                    text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_is_answered, 0, R.drawable.ic_remouve, 0);
                } else {
                    text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty, 0, R.drawable.ic_remouve, 0);
                }

                text.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                            if(event.getRawX() >= (text.getRight() - text.getCompoundDrawables()[2].getBounds().width()) && event.getAction() == MotionEvent.ACTION_DOWN ) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        InspectionInformation.getInstance().getKredsdetaljer().remove(i1);

                                        notifyDataSetChanged();

                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.setTitle("Sikker på du vil slette Kredsdetaljer " + (i1 + 1));

                                AlertDialog dialog = builder.create();

                                dialog.show();

                                return true;
                            } else {
                                return  false;
                            }

                    }
                });

            } else {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);

            }


        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 3  ) {

            if (i1 < InspectionInformation.getInstance().getAfprøvningAfRCD().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText("Afprøvning Af RCD " + (i1 + 1) );

                    if (InspectionInformation.getInstance().getAfprøvningAfRCD().get(i1).isAnswered()) {
                        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_is_answered, 0, R.drawable.ic_remouve, 0);
                    } else {
                        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty, 0, R.drawable.ic_remouve, 0);
                    }



                text.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                            if(event.getRawX() >= (text.getRight() - text.getCompoundDrawables()[2].getBounds().width()) && event.getAction() == MotionEvent.ACTION_DOWN ) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        InspectionInformation.getInstance().getAfprøvningAfRCD().remove(i1);

                                        notifyDataSetChanged();

                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.setTitle("Sikker på du vil slette Afprøvning Af RCD " + (i1 + 1));

                                AlertDialog dialog = builder.create();

                                dialog.show();

                                return true;
                            } else {
                                return  false;
                            }
                    }
                });

            } else {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);
            }

        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 4  ) {

            if (i1 < InspectionInformation.getInstance().getKortslutningsstroms().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText("Kortslutnings strøm " + (i1 + 1) );

                if (InspectionInformation.getInstance().getKortslutningsstroms().get(i1).isAnswered()) {
                    text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_is_answered, 0, R.drawable.ic_remouve, 0);
                } else {
                    text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty, 0, R.drawable.ic_remouve, 0);
                }



                text.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                            if(event.getRawX() >= (text.getRight() - text.getCompoundDrawables()[2].getBounds().width()) && event.getAction() == MotionEvent.ACTION_DOWN ) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setTitle("Sikker på du vil slette Kortslutningsstrøm " + (i1 + 1));






                                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        InspectionInformation.getInstance().getKortslutningsstroms().remove(i1);

                                        notifyDataSetChanged();

                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                System.out.println("dialog building");

                                AlertDialog dialog = builder.create();
                                dialog.show();


                                return true;
                            } else {
                                return  false;
                            }
                    }
                });



            } else {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);

            }


        }






        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {








        return true;
    }






}
