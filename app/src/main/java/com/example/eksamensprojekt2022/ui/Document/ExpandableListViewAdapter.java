package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
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
                view = inflater.inflate(R.layout.question_list_add, null);




        }  else if (i > InspectionInformation.getInstance().getQuestionGroups().size()) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.question_group_list, null);

            TextView text = view.findViewById(R.id.question_group);




            int size = InspectionInformation.getInstance().getQuestionGroups().size();

            if (size + 1 == i) {text.setText("Kredsdetaljer");}
            if (size + 2 == i) {text.setText("Overgangsmodstand for jordingsleder og jordelektrode");}
            if (size + 3 == i) {text.setText("Afprøvning");}
            if (size + 4 == i) {text.setText("Kortslutningsstrøm");}

        }  else {

            String groupTitle = InspectionInformation.getInstance().getQuestionGroups().get(i).getTitle();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_group_list, null);


            TextView text = view.findViewById(R.id.question_group);

            text.setText(groupTitle);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        // i = group
        // i1 = groupIndex

        if (i < InspectionInformation.getInstance().getQuestionGroups().size()   ) {

            if (i1 == InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size()) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);
            } else {

                String groupTitle = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getQuestion();


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText(groupTitle);
            }
        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 1  ) {

            if (i1 < InspectionInformation.getInstance().getKredsdetaljer().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText("Test");


            } else {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);

            }


        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 3  ) {

            if (i1 < InspectionInformation.getInstance().getAfprøvningAfRCD().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText("Test");


            } else {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);

            }

        } else if (i == InspectionInformation.getInstance().getQuestionGroups().size() + 4  ) {

            if (i1 < InspectionInformation.getInstance().getKortslutningsstroms().size() ) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


                TextView text = view.findViewById(R.id.questionText);

                text.setText("Test");


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
