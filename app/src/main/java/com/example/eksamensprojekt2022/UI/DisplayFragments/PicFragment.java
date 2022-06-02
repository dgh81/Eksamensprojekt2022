package com.example.eksamensprojekt2022.UI.DisplayFragments;

import static android.app.Activity.RESULT_OK;
import static com.example.eksamensprojekt2022.Enteties.FileHandler.currentImagePath;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.eksamensprojekt2022.DBController.MySQL;
import com.example.eksamensprojekt2022.Enteties.FileHandler;
import com.example.eksamensprojekt2022.Enteties.InspectionInformation;
import com.example.eksamensprojekt2022.Enteties.Picture;
import com.example.eksamensprojekt2022.Enteties.ProjectInformation;
import com.example.eksamensprojekt2022.Enteties.Room;
import com.example.eksamensprojekt2022.Enteties.User;
import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.Tools.UserCase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PicFragment extends Fragment {

    Button takePictureButton;
    Button savePictureButton;
    public static Bitmap bitmapFromTempFile;
    final int CAMERA_CODE = 100;
    Uri imageUri;
    private int id;


    public View view;

    public PicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pic, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        if (fab != null) {
            fab.setVisibility(View.GONE);
        }


        com.google.android.material.textfield.TextInputLayout comment = view.findViewById(R.id.comment);

        if (InspectionInformation.getInstance().getInspectionInformationID() != 0) {
            id = InspectionInformation.getInstance().getInspectionInformationID();
        }


        takePictureButton = (Button) view.findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    android.content.Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File imageFile = null;
                    try{
                        imageFile = new FileHandler(getActivity()).getImageFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(imageFile!=null){
                        if (Build.VERSION.SDK_INT >= 21) {
                            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                            } else {
                                imageUri = FileProvider.getUriForFile(view.getContext(),"com.example.eksamensprojekt2022.fileprovider",imageFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(cameraIntent, CAMERA_CODE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        savePictureButton = view.findViewById(R.id.savePictureButton);

        AutoCompleteTextView autoCompleteTextProjectCustomerName = view.findViewById(R.id.autoComplete);

        AutoCompleteTextView autoCompleteTextInspectionID = view.findViewById(R.id.autoComplete2);

        ArrayList<ProjectInformation> projectInformations = UserCase.getAllProjectInformation();

        String[] testItems = new String[projectInformations.size()];

        for (int i = 0; i < projectInformations.size(); i++) {
            testItems[i] = projectInformations.get(i).getCustomerName();
        }




        ArrayAdapter<String> arrayAdapter;

        arrayAdapter = new ArrayAdapter<String>( getActivity() , com.google.android.material.R.layout.support_simple_spinner_dropdown_item , testItems);

        autoCompleteTextProjectCustomerName.setText(ProjectInformation.getInstance().getCustomerName());

        autoCompleteTextInspectionID.setText(InspectionInformation.getInstance().getRoomName());

        autoCompleteTextProjectCustomerName.setAdapter(arrayAdapter);

        final ArrayList<Room>[] rooms = new ArrayList[]{new ArrayList<>()};

        autoCompleteTextProjectCustomerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rooms[0] = ProjectInformation.getRoomsFromProjectInformationID(projectInformations.get(position).getProjectInformationID() );

                String[] inspectionNames = new String[rooms[0].size()];

                for (int i = 0; i < rooms[0].size(); i++) {
                    inspectionNames[i] = rooms[0].get(i).getRoomName();
                }

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getActivity() , com.google.android.material.R.layout.support_simple_spinner_dropdown_item , inspectionNames);

                autoCompleteTextInspectionID.setAdapter(arrayAdapter2);

            }
        });

        autoCompleteTextInspectionID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {

                id = rooms[0].get(position).getRoomID();

                System.out.println(id + " this is the id inside picClass");

            }
        });




        ImageView image = view.findViewById(R.id.imageView3);

        image.setImageDrawable(getResources().getDrawable(R.drawable.place_holder_image));

        comment.clearFocus();



        savePictureButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                boolean canContinue = true;

                if (bitmapFromTempFile == null) { canContinue = false;
                    Toast.makeText(getActivity(), "Du skal tage et billede", Toast.LENGTH_SHORT).show(); }

                if (id == 0 ) {canContinue = false;
                    autoCompleteTextInspectionID.setError("Skal udfyldes");
                }
                if (autoCompleteTextProjectCustomerName.equals("")) {canContinue = false; autoCompleteTextInspectionID.setError("SKal udfyldes"); }
                if (autoCompleteTextInspectionID.equals("")) {canContinue = false; autoCompleteTextInspectionID.setError("Skal udfyldes"); }

                if (canContinue) {

                    autoCompleteTextInspectionID.setError(null);
                    autoCompleteTextProjectCustomerName.setError(null);

                    String name = "Sagnr.: " + autoCompleteTextProjectCustomerName.getText().toString();

                    Picture pic = new Picture(bitmapFromTempFile, name, id, comment.getEditText().getText().toString());

                    UserCase.savePictureButtonClicked(pic);

                    bitmapFromTempFile = null;

                    image.setImageDrawable(getResources().getDrawable(R.drawable.place_holder_image));

                    Toast.makeText(getActivity(), "Billedet er gemt", Toast.LENGTH_LONG).show();

                }

            }
        });



        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " - " + resultCode);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            File imgFile = new File(currentImagePath);
            String path = imgFile.getAbsolutePath();
            bitmapFromTempFile = BitmapFactory.decodeFile(path);
            ImageView fragmentImageView = (ImageView) getView().findViewById(R.id.imageView3);
            fragmentImageView.setImageBitmap(bitmapFromTempFile);

            System.out.println(bitmapFromTempFile + "bit from temp");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    // Hvordan kan vi fortsætte hvor vi slap, hvis der bliver givet permission?? - og er det nødvendigt?
                } else {
                    Toast.makeText(getContext(), "This Permission is needed for the app to work perfectly!", Toast.LENGTH_SHORT).show();
                }
            default:
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void buttonsVisible(boolean visible ) {

        if (visible) {
            takePictureButton.setVisibility(View.VISIBLE);
            savePictureButton.setVisibility(View.VISIBLE);
        } else {
            takePictureButton.setVisibility(View.GONE);
            savePictureButton.setVisibility(View.GONE);
        }



    }


    @Override
    public String toString() {
        return "PicFragment";
    }
}