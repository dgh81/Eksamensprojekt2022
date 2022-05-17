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

    InspectionInformation inspectionInformation;
    View view;

    ExpandableListViewAdapter listViewAdapter;






    public QuestionList(InspectionInformation inspectionInformation) {
        this.inspectionInformation = inspectionInformation;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_question_list, container, false);

        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        ExpandableListView listView = view.findViewById(R.id.expanded_list);

        System.out.println( view.findViewById(R.id.expanded_list) + " find me ");

        listViewAdapter = new ExpandableListViewAdapter(getActivity(), inspectionInformation);



        listView.setAdapter(listViewAdapter);







        return view;
    }





}




