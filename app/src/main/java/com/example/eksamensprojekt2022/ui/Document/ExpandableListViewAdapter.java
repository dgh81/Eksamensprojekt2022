package com.example.eksamensprojekt2022.ui.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.eksamensprojekt2022.Objeckts.Inspection;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private InspectionInformation inspection;


    public ExpandableListViewAdapter(Context context, InspectionInformation inspection) {
        this.context = context;
        this.inspection = inspection;
    }


    @Override
    public int getGroupCount() {
        return inspection.getQuestionGroups().size();
    }

    @Override
    public int getChildrenCount(int i) {
        return inspection.getQuestionGroups().get(i).getQuestions().size();
    }

    @Override
    public Object getGroup(int i) {
        return inspection.getQuestionGroups().get(i);
    }

    @Override
    public Object getChild(int i, int i1) {

        System.out.println("????");

        return inspection.getQuestionGroups().get(i).getQuestions().get(i1);
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
        String groupTitle = inspection.getQuestionGroups().get(i).getTitle();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.question_group_list , null);
        }

        TextView text = view.findViewById(R.id.question_group);

        text.setText(groupTitle);

        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String groupTitle = inspection.getQuestionGroups().get(i).getQuestions().get(i1).getQuestion();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.question_list , null);
        }

        TextView text = view.findViewById(R.id.questionText);

        text.setText(groupTitle);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {


      //  System.out.println(inspection.getQuestionGroups().get(i).getTitle() + " + " + inspection.getQuestionGroups().get(i).getQuestions().get(i1).getQuestion() );





        return true;
    }






}
