package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.R;


public class QuestionList extends Fragment {

    View view;

    ExpandableListViewAdapter listViewAdapter;






    public QuestionList() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_question_list, container, false);

        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        ExpandableListView listView = view.findViewById(R.id.expanded_list);

        System.out.println( view.findViewById(R.id.expanded_list) + " find me ");

        listViewAdapter = new ExpandableListViewAdapter(getActivity());

        listView.setAdapter(listViewAdapter);


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                if ( i1 > InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() - 1 ) {
                    //TODO add a question

                    System.out.println("you want to add a question?");

                } else {
                    System.out.println(InspectionInformation.getInstance().getQuestionGroups().get(i).getTitle() + " + " + InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().get(i1).getQuestion());

                    ((SelectDocumentAndRoomActivityActivity)getActivity()).goToQuestionPage(i , i1);

                }
                return true;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition > InspectionInformation.getInstance().getQuestionGroups().size() - 1 ) {
                    System.out.println("so you want to add a question group?");
                    return true;
                }
                return false;



            }
        });





        return view;
    }





}




