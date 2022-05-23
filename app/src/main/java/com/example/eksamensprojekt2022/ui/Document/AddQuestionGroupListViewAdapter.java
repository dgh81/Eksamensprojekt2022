package com.example.eksamensprojekt2022.ui.Document;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt2022.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class AddQuestionGroupListViewAdapter extends ArrayAdapter {

    Context context;
    int resource;
    int count = 1;


    ArrayList<String> array = new ArrayList<>();




    public AddQuestionGroupListViewAdapter(@NonNull Context context, int resource) {
        super(context , resource);
        this.context = context;
        this.resource = resource;
        array.add("");
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(R.layout.question_text_to_list , parent , false);

        com.google.android.material.textfield.TextInputLayout textField =  convertView.findViewById(R.id.questionToList);


        textField.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    System.out.println(hasFocus);
                    array.set(position ,   textField.getEditText().getText().toString());
                }
            }
        });





        if (!array.get(position).equals("")) {
            textField.getEditText().setText(array.get(position));
        } else {
            textField.setHint("Spørgsmål " + (position + 1 ));
        }

        if (array.size() == 1)  {

            textField.setEndIconMode(TextInputLayout.END_ICON_NONE);

        } else {
            textField.setEndIconOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    textField.getEditText().requestFocus();
                    textField.getEditText().clearFocus();

                    array.remove(position);
                    notifyDataSetChanged();

                }
            });
        }






        return convertView;
    }

    @Override
    public int getCount() {
        return  array.size();
    }

    public void addItem() {
        array.add("");
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public void notifyDataSetChanged() {

        System.out.println("test");

        super.notifyDataSetChanged();
    }

    public ArrayList<String>  getQuestions() {
        return array;
    }


}



