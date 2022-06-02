package com.example.eksamensprojekt2022.UI.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksamensprojekt2022.Enteties.EmailThread;
import com.example.eksamensprojekt2022.Tools.CreatePDF;
import com.example.eksamensprojekt2022.Tools.LoginAuthentication;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Tools.PostNumberToCity;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.UI.DisplayFragments.DocumentFragment;
import com.example.eksamensprojekt2022.UI.DisplayFragments.PicFragment;
import com.example.eksamensprojekt2022.UI.DisplayFragments.ProjectFragment;
import com.example.eksamensprojekt2022.UI.DisplayFragments.QuestionFragment;
import com.example.eksamensprojekt2022.UI.DisplayFragments.QuestionListFragment;
import com.example.eksamensprojekt2022.Tools.UserCase;
import com.example.eksamensprojekt2022.UI.Login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SelectDocumentAndRoomActivityActivity extends AppCompatActivity implements OnKeyboardVisibilityListener {


    private ImageView backButton;
    private TextView topText;
    private TextView bottomText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.select_document_and_room_activity);

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout , new DocumentFragment()  );
        fragmentTransaction.commit();

        setKeyboardVisibilityListener(this);
        
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        backButton = findViewById(R.id.backButton);
        topText = findViewById(R.id.topText);
        bottomText = findViewById(R.id.bottomText);

        Toolbar toolbar = findViewById(R.id.customToolbar);

        setSupportActionBar(toolbar);




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                    EditText caseNumber = popUp.findViewById(R.id.caseNumber);
                    EditText installationName = popUp.findViewById(R.id.InstallationName);


                    customerPostCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (!hasFocus) {

                                //TODO: handle not interger input

                               String city =  PostNumberToCity.getCityFromPostCode( Integer.parseInt( customerPostCode.getText().toString()) );
                                customerCity.setText(city);
                            }
                        }
                    });


                    ArrayList<EditText> fields = new ArrayList<>();

                    fields.add(installationName);
                    fields.add(caseNumber);
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
                                        caseNumber.getText().toString(),
                                        installationName.getText().toString()
                                );
                                createProjectInformationInDatabase(projectInformation);

                                alert.hide();

                                ArrayList<ProjectInformation> projectInformations = UserCase.getAllProjectInformation();

                                ProjectInformation.setInstance( projectInformations.get( projectInformations.size() - 1)  );

                                goToDocumentPage();

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

                                fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment()  );

                                fragmentTransaction.addToBackStack(null);

                                fragmentTransaction.commit();

                                createRoom(roomName);

                                alert.hide();

                            }
                        }
                    });
                }
            }
        });


        updateToolBar();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu ,menu );
        return true;
    }

    public AlertDialog loadingAlert;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.myLogout:
                LoginAuthentication.logOut();

                Intent intent = new Intent(this,  LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.printPDF:

                if (ProjectInformation.getInstance().getProjectInformationID() == 0) {
                    Toast.makeText(this, "Du skal vælge et project", Toast.LENGTH_LONG).show();
                } else {
                            closeOptionsMenu();

                            AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);
                            LayoutInflater layoutInflater = SelectDocumentAndRoomActivityActivity.this.getLayoutInflater();
                            builder.setView(layoutInflater.inflate(R.layout.pop_up_loading , null));
                            builder.setCancelable(false);
                            SelectDocumentAndRoomActivityActivity.this.setFinishOnTouchOutside(false);

                            loadingAlert = builder.create();
                            loadingAlert.show();


                    CreatePDF createPDF = new CreatePDF(SelectDocumentAndRoomActivityActivity.this , ProjectInformation.getInstance().getProjectInformationID());

                    Thread thread = new Thread(createPDF);
                    thread.start();

                }






                break;
            case R.id.myHelp:
                Toast.makeText(this, "Hjælp ikke lavet endnu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cam:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout , new PicFragment()  );

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();



        }


        return true;
    }



    public void pdfCreated() {

        AlertDialog.Builder diaBuilder1 = new AlertDialog.Builder(this);

        diaBuilder1.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                EmailThread emailIntent = new EmailThread(SelectDocumentAndRoomActivityActivity.this);
                emailIntent.start();

                dialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);
                LayoutInflater layoutInflater = SelectDocumentAndRoomActivityActivity.this.getLayoutInflater();
                builder.setView(layoutInflater.inflate(R.layout.pop_up_loading , null));
                builder.setCancelable(false);
                SelectDocumentAndRoomActivityActivity.this.setFinishOnTouchOutside(false);

                loadingAlert = builder.create();

                TextView text = loadingAlert.findViewById(R.id.textView2);
                text.setText("Sender...");


                loadingAlert.show();

                try {
                    emailIntent.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                loadingAlert.dismiss();

            }
        });

        diaBuilder1.setNeutralButton("Annuller", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        diaBuilder1.setTitle("Vil du sende denne PDF til mester?");

        AlertDialog dialog1 = diaBuilder1.create();

        dialog1.show();



    }


    private void createRoom(EditText text ) {

        ProjectFragment projectFragment =  (ProjectFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        int id =  projectFragment.getProjectInformation().getProjectInformationID();

        UserCase.userCreatedNewRoom(text.getText().toString() , id );

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment()  );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        fragmentManager.popBackStack();

        fragmentManager.popBackStack();

    }




    private void createProjectInformationInDatabase(ProjectInformation projectInformation) {
        UserCase.userCreatedNewProject(projectInformation);
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

    public void goToDocumentPage() {


        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left , R.anim.exit_right_to_left ,
                R.anim.enter_left_to_right , R.anim.exit_left_to_right );
        fragmentTransaction.replace(R.id.frameLayout , new ProjectFragment()  );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }



    public void goToQuestionListPage(int startPage ) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left , R.anim.exit_right_to_left ,
                R.anim.enter_left_to_right , R.anim.exit_left_to_right );
        fragmentTransaction.replace(R.id.frameLayout , new QuestionListFragment(startPage) );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }


    public void goToQuestionPage(int group , int question) {

        FragmentManager fragmentManager = getSupportFragmentManager();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top , R.anim.exit_bottom_to_top ,
                R.anim.enter_top_to_bottom , R.anim.exit_top_to_bottom );
        fragmentTransaction.replace(R.id.frameLayout , new QuestionFragment(group , question ) );

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();




    }

    public boolean savePressed = false;


    public boolean isSavePressed() {
        return savePressed;
    }

    public void setSavePressed(boolean savePressed) {
        this.savePressed = savePressed;
    }

    @Override
    public void onBackPressed() {

        System.out.println("back pressed");

        if (InspectionInformation.getInstance().getQuestionGroups() == null || InspectionInformation.getInstance().getQuestionGroups().size() == 0 || getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("ProjectFragment")
                || getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("PicFragment") ) {

            System.out.println("we go back");

            super.onBackPressed();

            updateToolBar();

        } else if (getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("Question")) {


            super.onBackPressed();

            updateToolBar();
        } else {

           // AlertDialog.Builder builder = new AlertDialog.Builder(SelectDocumentAndRoomActivityActivity.this);
            //final View popUp = getLayoutInflater().inflate(R.layout.pop_up_save_changes, null);

           QuestionListFragment questionList =  (QuestionListFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);

           questionList.listViewAdapter.notifyDataSetChanged();



            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setPositiveButton("Gem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            UserCase.userPressedSaveButton();
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

    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        final View parentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
                parentView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    Log.i("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                alreadyOpen = isShown;
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }


    @Override
    public void onVisibilityChanged(boolean visible) {

        if (getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("Question")) {

            ((QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout)).dotsIsVisible(!visible);

        } else if (getSupportFragmentManager().findFragmentById(R.id.frameLayout).toString().equals("PicFragment")) {
            ((PicFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout)).buttonsVisible(!visible);
        }

    }

    public void updateToolBar() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 ) {
            backButton.setVisibility(View.GONE);
            topText.setText("");
            bottomText.setText("");

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            topText.setText(ProjectInformation.getInstance().getCustomerName());
            bottomText.setText(ProjectInformation.getInstance().getCustomerAddress());
            backButton.setVisibility(View.VISIBLE);
            backButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 2 ) {
            backButton.setVisibility(View.VISIBLE);
            topText.setText(ProjectInformation.getInstance().getCustomerName());
            bottomText.setText(InspectionInformation.getInstance().getRoomName());
            backButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 3) {
            topText.setText(ProjectInformation.getInstance().getCustomerName());
            bottomText.setText(InspectionInformation.getInstance().getRoomName());
            backButton.setVisibility(View.VISIBLE);
            backButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
        } else {
            backButton.setVisibility(View.VISIBLE);
        }
    }



}










