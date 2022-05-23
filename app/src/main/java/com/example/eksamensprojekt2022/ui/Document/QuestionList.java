package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.User;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UserCase;

import java.util.ArrayList;


public class QuestionList extends Fragment {

    View view;

    ExpandableListViewAdapter listViewAdapter;

    int startPoint;

    public QuestionList(int startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view =  inflater.inflate(R.layout.fragment_question_list, container, false);

        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        ExpandableListView listView = view.findViewById(R.id.expanded_list);

        listViewAdapter = new ExpandableListViewAdapter(getActivity());

        listView.setAdapter(listViewAdapter);

        if (startPoint >= 0) {
            listView.expandGroup(startPoint);
        }


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                if ( i1 > InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestions().size() - 1 ) {
                    //TODO add a question

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    final View popUp = getLayoutInflater().inflate(R.layout.add_questionto_qestion_group, null);

                    EditText editText =  popUp.findViewById(R.id.questionText);

                    Button button = popUp.findViewById(R.id.createQuestion);

                    builder.setView(popUp);
                    AlertDialog alert = builder.create();
                    alert.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (editText.getText().toString().equals("")) {

                                editText.setError("Skal udfyldes");

                            } else {

                                System.out.println("????");

                                UserCase.createNewQuestionInsideQuestionGroup(InspectionInformation.getInstance().getQuestionGroups().get(i).getQuestionGroupID()  , editText.getText().toString() , InspectionInformation.instance.getInspectorInformationID());

                                alert.dismiss();



                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout , new QuestionList(i) );
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.popBackStack();
                                fragmentTransaction.commit();


                            }
                        }
                    });


                } else {

                    ((SelectDocumentAndRoomActivityActivity)getActivity()).goToQuestionPage(i , i1);

                }
                return true;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition > InspectionInformation.getInstance().getQuestionGroups().size() - 1 ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    final View popUp = getLayoutInflater().inflate(R.layout.pop_up_create_qestion_group, null);

                    ListView listView1 =  popUp.findViewById(R.id.questionList);

                    listView1.setAdapter( new AddQuestionGroupListViewAdapter( getActivity() , R.layout.question_text_to_list) );

                    com.google.android.material.textfield.TextInputLayout questionHeader = popUp.findViewById(R.id.questionToList);

                    com.google.android.material.button.MaterialButton button = popUp.findViewById(R.id.outlinedButton);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            questionHeader.getEditText().requestFocus();

                            questionHeader.getEditText().clearFocus();

                            ((AddQuestionGroupListViewAdapter)  listView1.getAdapter()).addItem();



                        }
                    });

                    Button createButton = popUp.findViewById(R.id.createQuestionGroup);

                    builder.setView(popUp);
                    AlertDialog alert = builder.create();
                    alert.show();

                    createButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            questionHeader.getEditText().requestFocus();

                            questionHeader.getEditText().clearFocus();

                            ((AddQuestionGroupListViewAdapter)  listView1.getAdapter()).notifyDataSetChanged() ;

                            boolean canCreate = true;

                            System.out.println(questionHeader.getEditText().getText().toString().length());

                            if (questionHeader.getEditText().getText().toString().length() <= 0 ) {
                                canCreate = false;
                                questionHeader.setError("Skal udfyldes");
                            }
                            ArrayList<String> arrayList = ((AddQuestionGroupListViewAdapter)  listView1.getAdapter()).getQuestions();

                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).equals("")) {
                                        arrayList.remove(i);
                                    }
                                }

                            if (canCreate) {
                                UserCase.addQuestionGroupWithQuestions(questionHeader.getEditText().getText().toString(), InspectionInformation.getInstance().getInspectorInformationID(),  arrayList);

                                alert.dismiss();


                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout , new QuestionList(-1) );
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.popBackStack();
                                fragmentTransaction.commit();

                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        return view;
    }
}




