package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;


    public ExpandableListViewAdapter(Context context ) {
        this.context = context;
    }


    @Override
    public int getGroupCount() {
        return InspectionInformation.getInstance().getQuestionGroups().size() + 1 ;
    }

    @Override
    public int getChildrenCount(int i) {

        if (i > InspectionInformation.getInstance().getQuestionGroups().size() - 1 ) {
            return 0;
        } else {
            int size = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() + 1;

            System.out.println(size + " size");

            return  size ;
        }


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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (i > InspectionInformation.getInstance().getQuestionGroups().size() - 1  ) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);

        } else {
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

        System.out.println(i + " i");

        System.out.println(i1 + " i1 ");

        System.out.println(InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() - 1 + " size of questinos");

        if (i1 > InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() - 1  ) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list_add, null);



        } else {

            String groupTitle = InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getQuestion();


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.question_list, null);


            TextView text = view.findViewById(R.id.questionText);

            text.setText(groupTitle);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {








        return true;
    }






}
