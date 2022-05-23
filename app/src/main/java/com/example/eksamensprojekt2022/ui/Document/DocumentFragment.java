package com.example.eksamensprojekt2022.ui.Document;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UserCase;

import java.util.ArrayList;
import java.util.List;


public class DocumentFragment extends Fragment {


    public View view;

    ArrayAdapter arrayAdapter;

    List textList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_document_fragment, container, false);

        ListView listView = view.findViewById(R.id.documentListView);

         getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);


        ArrayList<ProjectInformation> projectInformation =  UserCase.getAllProjectInformation();

        textList.clear();

        for (ProjectInformation p: projectInformation ) {
            textList.add(  p.getInstallationName());
        }


        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, textList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {

                ((SelectDocumentAndRoomActivityActivity)getActivity()).goToDocumentPage(projectInformation.get(pos));

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

                editDocuments(projectInformation.get(pos));

                return true;
            }
        });

        return view;
    }


    public void editDocuments(ProjectInformation projectInformation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View popUp = getLayoutInflater().inflate(R.layout.pop_up_edit_project, null);

        EditText customerName = popUp.findViewById(R.id.customerName);
        EditText customerAddress = popUp.findViewById(R.id.customerAddress);
        EditText customerPostCode = popUp.findViewById(R.id.customerPostCode);
        EditText customerCity = popUp.findViewById(R.id.customerCity);
        EditText installationIdentification = popUp.findViewById(R.id.InstallationIdentification);
        EditText installationName = popUp.findViewById(R.id.InstallationName);



        ArrayList<EditText> fields = new ArrayList<>();

        fields.add(installationName);
        fields.add(installationIdentification);
        fields.add(customerCity);
        fields.add(customerPostCode);
        fields.add(customerAddress);
        fields.add(customerName);

        customerName.setText(projectInformation.getCustomerName());
        customerAddress.setText(projectInformation.getCustomerAddress());
        customerPostCode.setText(projectInformation.getCustomerPostalCode());
        customerCity.setText(projectInformation.getCustomerCity());
        installationIdentification.setText(projectInformation.getInstallationName());
        installationName.setText(projectInformation.getInstallationName());


        builder.setView(popUp);
        AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    public String toString() {
        return "DocumentFragment";
    }
}
