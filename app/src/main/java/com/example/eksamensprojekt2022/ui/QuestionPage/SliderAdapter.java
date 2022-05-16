package com.example.eksamensprojekt2022.ui.QuestionPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt2022.Objeckts.QuestionGroup;
import com.example.eksamensprojekt2022.R;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    private ViewPager sliderAdapter;


    public ArrayList<QuestionGroup> questionGroups;




    public SliderAdapter (Context context , ViewPager slideVeiwPager) {
        this.context = context;
        this.sliderAdapter = slideVeiwPager;
    }

    public String[] testQuestion = {
            "this is test question 1",
            "this is test question 2",
            "this is test question 3",
            "This is a long text to  simulate a long text to see how it will handle a long text and if there should be a limit to how long the text can be i guess this is long enough for all? Or the text should just get smaller as the question gets larger it does good"
    };

    public String[] titles = {
            "Generalt",
            "Generalt",
            "Generalt",
            "Generalt",
    };









    @Override
    public int getCount() {
        return testQuestion.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {




        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View veiw = layoutInflater.inflate(R.layout.slide_layout , container , false);

        TextView text = (TextView) veiw.findViewById(R.id.questionText);
        Button yesButton = (Button) veiw.findViewById(R.id.yesButton);






        yesButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                yesButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

                sliderAdapter.setCurrentItem(sliderAdapter.getCurrentItem() + 1 , true);

            }
        });


        text.setText(testQuestion[position]);







        container.addView(veiw);

        return veiw;

    }



    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);





    }
}










