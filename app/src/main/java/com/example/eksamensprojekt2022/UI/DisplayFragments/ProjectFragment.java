package com.example.eksamensprojekt2022.UI.DisplayFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Enteties.Room;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UI.Activitys.SelectDocumentAndRoomActivityActivity;
import com.example.eksamensprojekt2022.UserCases.UserCase;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {


    View view;

    ArrayAdapter arrayAdapter;

    List textList = new ArrayList();

    public ProjectFragment( ) {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.project_fragment, container, false);

        ListView listView = view.findViewById(R.id.documentListView);

        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);

        ArrayList<Room> rooms =  ProjectInformation.getRoomsFromProjectInformationID(ProjectInformation.getInstance().getProjectInformationID());

        textList.clear();

        for (Room r: rooms ) {
            textList.add(r.getRoomName());
        }


        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, textList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {

                UserCase.userSelectedRoom( rooms.get(pos).getRoomID() , ProjectInformation.getInstance().getProjectInformationID());

                ((SelectDocumentAndRoomActivityActivity)getActivity()).goToQuestionListPage( - 1 );

            }
        });

        ((SelectDocumentAndRoomActivityActivity)getActivity()).updateToolBar();

        return  view;
    }

    public ProjectInformation getProjectInformation() {
        return ProjectInformation.getInstance();
    }

    @Override
    public String toString() {
        return "ProjectFragment";
    }
}