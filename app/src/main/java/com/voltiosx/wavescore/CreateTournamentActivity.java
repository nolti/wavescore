package com.voltiosx.wavescore;

import android.net.Uri;
import android.os.Bundle;

import com.voltiosx.wavescore.ui.fragments.FormTournament;
import com.voltiosx.wavescore.ui.fragments.StartTournament;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.voltiosx.wavescore.viewpagers.SectionsPagerTournamentAdapter;

public class CreateTournamentActivity extends AppCompatActivity  implements StartTournament.OnFragmentInteractionListener, FormTournament.OnFragmentInteractionListener {

    private ViewPager screenPager;
    SectionsPagerTournamentAdapter sectionsPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscren Activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide Action Bar
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_create_tournament);

        // Init views
        //btnNext = findViewById(R.id.buttonNext);
        tabIndicator = findViewById(R.id.tabs_tournament);

        // ViewPager
        sectionsPagerAdapter = new SectionsPagerTournamentAdapter(this, getSupportFragmentManager());
        screenPager = findViewById(R.id.view_pager);
        screenPager.setAdapter(sectionsPagerAdapter);

        // Setup points indicator
        tabIndicator.setupWithViewPager(screenPager);

        // Next Button
        /*btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                int limit=2;
                if (position < limit){
                    position++;
                    screenPager.setCurrentItem(position);
                }
            }
        });*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}