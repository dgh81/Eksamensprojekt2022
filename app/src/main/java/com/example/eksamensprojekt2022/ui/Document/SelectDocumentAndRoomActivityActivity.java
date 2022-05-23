package com.example.eksamensprojekt2022.ui.Document;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.eksamensprojekt2022.LoginAuthentication;
import com.example.eksamensprojekt2022.Objeckts.InspectionInformation;
import com.example.eksamensprojekt2022.Objeckts.ProjectInformation;
import com.example.eksamensprojekt2022.PostCode.PostNumberToCity;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UserCase;
import com.example.eksamensprojekt2022.ui.Login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SelectDocumentAndRoomActivityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_document_and_room_activity);

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout , new DocumentFragment()  );
        fragmentTransaction.commit();

        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        assert ab != null;
        ab.setTitle("\t Zealand");


        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        if (getParentActivityIntent() != null)
            ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));







        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("DocumentFragment")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);

                    final View popUp = getLayoutInflater().inflate(R.layout.project_pop_up, null);

                    EditText customerName = popUp.findViewById(R.id.customerName);
                    EditText customerAddress = popUp.findViewById(R.id.customerAddress);
                    EditText customerPostCode = popUp.findViewById(R.id.customerPostCode);
                    EditText customerCity = popUp.findViewById(R.id.customerCity);
                    EditText installationIdentification = popUp.findViewById(R.id.InstallationIdentification);
                    EditText installationName = popUp.findViewById(R.id.InstallationName);

                    customerPostCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (!hasFocus) {
                               String city =  PostNumberToCity.getCityFromPostCode( Integer.parseInt( customerPostCode.getText().toString()) );
                                customerCity.setText(city);
                            }
                        }
                    });
                    

                    ArrayList<EditText> fields = new ArrayList<>();

                    fields.add(installationName);
                    fields.add(installationIdentification);
                    fields.add(customerCity);
                    fields.add(customerPostCode);
                    fields.add(customerAddress);
                    fields.add(customerName);


                    builder.setView(popUp);
                    AlertDialog alert = builder.create();
                    alert.show();

                    Button button = popUp.findViewById(R.id.projectCreateBtn);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            boolean allFieldsFilledCorrectly;

                            allFieldsFilledCorrectly = checkIfTextIsEmpty(fields);

                            allFieldsFilledCorrectly = checkIfValidPostNumber(customerPostCode);

                            if (allFieldsFilledCorrectly) {

                                ProjectInformation projectInformation = new ProjectInformation(
                                        customerName.getText().toString(),
                                        customerAddress.getText().toString(),
                                        customerPostCode.getText().toString(),
                                        customerCity.getText().toString(),
                                        installationIdentification.getText().toString(),
                                        installationName.getText().toString()
                                );
                                createProjectInformationInDatabase(projectInformation);

                                alert.hide();

                                ArrayList<ProjectInformation> projectInformations = UserCase.getAllProjectInformation();

                                goToDocumentPage(projectInformations.get(projectInformations.size() - 1));

                            }
                        }
                    });


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);

                    final View popUp = getLayoutInflater().inflate(R.layout.pop_up_room, null);

                    EditText roomName =  popUp.findViewById(R.id.roomName);

                    Button button = popUp.findViewById(R.id.roomCreateBtn);


                    builder.setView(popUp);
                    AlertDialog alert = builder.create();
                    alert.show();


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ArrayList<EditText> editTexts = new ArrayList<>();

                            editTexts.add(roomName);

                            if (checkIfTextIsEmpty(editTexts)) {

                                ProjectFragment projectFragment =  (ProjectFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);

                                ProjectInformation projectInformation = projectFragment.getProjectInformation();

                                projectInformation.getInspectionInformations().clear();

                                FragmentManager fragmentManager = getSupportFragmentManager();

                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment(projectInformation)  );

                                fragmentTransaction.addToBackStack(null);

                                fragmentTransaction.commit();

                                createRoom(roomName , projectInformation );

                                alert.hide();

                            }
                        }
                    });
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu ,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {

        if (item.getTitle().equals("Log ud")) {
            LoginAuthentication.logOut();

            Intent intent = new Intent(this,  LoginActivity.class);
            startActivity(intent);

        }

        return true;
    }




    private void createRoom(EditText text, ProjectInformation projectInformation ) {

        ProjectFragment projectFragment =  (ProjectFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        int id =  projectFragment.getProjectInformation().getProjectInformationID();

        UserCase.createRoomFromName(text.getText().toString() , id );

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment(projectInformation)  );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        fragmentManager.popBackStack();

        fragmentManager.popBackStack();

    }




    private void createProjectInformationInDatabase(ProjectInformation projectInformation) {
        UserCase.createProjectInformationInDataBase(projectInformation);
    }


    private boolean checkIfValidPostNumber(EditText text) {
        if (text.length() < 3) {
            text.setError("Ikke rigtigt post nr.");
            text.requestFocus();
            return  false;
        }
        return true;

    }


    private boolean checkIfTextIsEmpty( ArrayList<EditText> text ) {

        System.out.println(text.size());

        boolean value = true;

        for (EditText t: text) {

            if (t.getText().toString().equals("")) {

                t.setError("Feltet må ikke være tomt");

                t.requestFocus();

                value = false;
            }

        }

        return value;
    }


    public void goToDocumentPage(ProjectInformation projectInformation) {


        FragmentManager fragmentManager = getSupportFragmentManager();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left , R.anim.exit_right_to_left ,
                R.anim.enter_left_to_right , R.anim.exit_left_to_right );
        fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment(projectInformation)  );


        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


    }



    public void goToQuestionListPage() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left , R.anim.exit_right_to_left ,
                R.anim.enter_left_to_right , R.anim.exit_left_to_right );
        fragmentTransaction.replace(R.id.frameLayout , new QuestionList() );


        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }


    public void goToQuestionPage(int group , int question) {

        FragmentManager fragmentManager = getSupportFragmentManager();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top , R.anim.exit_bottom_to_top ,
                R.anim.enter_top_to_bottom , R.anim.exit_top_to_bottom );
        fragmentTransaction.replace(R.id.frameLayout , new Question(group , question ) );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();



    }

    @Override
    public void onBackPressed() {

        System.out.println("back pressed");

        if (InspectionInformation.getInstance().getQuestionGroups() == null || InspectionInformation.getInstance().getQuestionGroups().size() == 0 ||
                getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("Question")  ) {

            System.out.println("we go back");

            super.onBackPressed();

        } else {

           // AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);
            //final View popUp = getLayoutInflater().inflate(R.layout.pop_up_save_changes, null);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setPositiveButton("Gem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            UserCase.createInspectionInDataBase();

                            onBackPressed();

                        }
                    });

            builder.setNegativeButton("Gem ikke", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    InspectionInformation.getInstance().getQuestionGroups().clear();
                    onBackPressed();
                }
            });

            builder.setNeutralButton("Annuller", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.setTitle("Vil du gemme ænderingerne");

            AlertDialog dialog = builder.create();

            dialog.show();

            //TODO Prompt to save or discard changes or to cancel  the go back action

            System.out.println("you sure?");


        }




    }




}










