package com.example.eksamensprojekt2022.ui.QuestionPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eksamensprojekt2022.R;
import com.example.eksamensprojekt2022.ui.QuestionPage.SliderAdapter;

import org.w3c.dom.Text;

public class DocumentActivity extends AppCompatActivity {

    private ViewPager slideVeiwPager;
    private LinearLayout dotLayout;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        slideVeiwPager = findViewById(R.id.contentBox);
        dotLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this , slideVeiwPager);

        slideVeiwPager.setAdapter(sliderAdapter);

        slideVeiwPager.addOnPageChangeListener(listener);

        addDots();

        updateDots(0);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu ,menu );
        return true;
    }


    public void addDots() {

        dots = new TextView[4];
        dotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotLayout.addView(dots[i]);
        }
    }

    public void updateDots(int pos) {

        for (int i = 0; i < dots.length; i++) {
            dots[i].setTextColor(getResources().getColor(R.color.dotsColor));
        }


        if (dots.length > 0) {
            dots[pos].setTextColor(getResources().getColor(R.color.selectedDotColor));
        }
    }




    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
            public void onPageSelected(int position) {
            updateDots(position);

                System.out.println("works?");
            }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };












}