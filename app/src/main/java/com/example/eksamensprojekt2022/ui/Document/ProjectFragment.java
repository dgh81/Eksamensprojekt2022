package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.Objeckts.Room;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UserCase;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {


    View view;

    ProjectInformation projectInformation;


    ArrayAdapter arrayAdapter;

    List textList = new ArrayList();

    public ProjectFragment( ) {
        // Required empty public constructor
    }

    public ProjectFragment(ProjectInformation projectInformation) {
        this.projectInformation = projectInformation;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.project_fragment, container, false);


        System.out.println(projectInformation);

        TextView header = view.findViewById(R.id.header_title);

        header.setText(projectInformation.getInstallationName());

        ListView listView = view.findViewById(R.id.documentListView);

        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);

        ArrayList<Room> rooms =  UserCase.getRoomsFromProjectInformationID(projectInformation.getProjectInformationID());

        textList.clear();

        for (Room r: rooms ) {
            textList.add(r.getRoomName());
        }


        System.out.println(listView + " help me find");

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, textList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {


                UserCase.setInspectionInformationFromDB( rooms.get(pos).getRoomID() , projectInformation.getProjectInformationID());

                System.out.println(InspectionInformation.getInstance().getInspectorInformationID() + " FROM Trans");

                System.out.println(InspectionInformation.getInstance());

                ((SelectDocumentAndRoomActivityActivity)getActivity()).goToQuestionListPage( - 1 );


            }
        });

        return  view;
    }

    public ProjectInformation getProjectInformation() {
        return projectInformation;
    }

    @Override
    public String toString() {
        return "ProjectFragment";
    }
}