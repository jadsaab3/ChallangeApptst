package com.challangeapp.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.challangeapp.R;

public class MainActivity extends AppCompatActivity {

    Activity act;
    Chronometer chronometer;
    private long lastpause;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act=this;

        chronometer     = (Chronometer)findViewById(R.id.chronometer);
        toggleButton    = (ToggleButton)findViewById(R.id.toggleButton);
        initializePage();

    }

    private void initializePage(){
        startTimer();
        Fragment mainfragment = new MainFragment(act);
        Config.goToFragment(act, mainfragment, null, false, true,
                false, null, true, false);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //ascending
                    FragmentManager ascendingfuntion = getSupportFragmentManager();
                    MainFragment fragmentmain = (MainFragment) ascendingfuntion.findFragmentByTag(MainFragment.class.getName());
                    if (fragmentmain != null)
                        fragmentmain.drawRecyle(true,true);
                }else{
                    //normal
                    FragmentManager ascendingfuntion = getSupportFragmentManager();
                    MainFragment fragmentmain = (MainFragment) ascendingfuntion.findFragmentByTag(MainFragment.class.getName());
                    if (fragmentmain != null)
                        fragmentmain.drawRecyle(false,true);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if(count==1){
            finish();
        }else{
            ((MainActivity)act).toggleButton.setVisibility(View.VISIBLE);
            fragmentManager.popBackStackImmediate();
        }
    }

    public void startTimer(){
        if(lastpause!=0){
            chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastpause);
        }else{
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        chronometer.start();
    }

    public void pauseTimer(){
        lastpause = SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    @Override
    protected void onStop() {
        pauseTimer();
        super.onStop();
    }

    @Override
    protected void onResume() {
        startTimer();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        lastpause =0;
        super.onDestroy();
    }
}