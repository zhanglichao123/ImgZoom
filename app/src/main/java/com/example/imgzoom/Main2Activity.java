package com.example.imgzoom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "zhang";
    private static final String KEY_TEST = "123";
    private Fragmet2 fragmet2;
    private Fragmet1 fragmet1;
    private Fragment fragmet22;
    private Fragment fragmet11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button1 = findViewById(R.id.buton1);
        Button button2 = findViewById(R.id.buton2);
        Button button3 = findViewById(R.id.buton3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        Log.e("zhang", "onCreate");
        if (savedInstanceState==null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmet2 = new Fragmet2();
            fragmet1 = new Fragmet1();
            Fragment fragmet11 = getSupportFragmentManager().findFragmentByTag("fragmet1");
            if (fragmet11 == null && !fragmet1.isAdded()) {
                fragmentTransaction.add(R.id.fff, this.fragmet1, "fragmet1").commit();
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zhang", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zhang", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zhang", "onDestroy");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zhang", "onRestart");

    }

    @Override
    public void onClick(View v) {
        fragmet22 = getSupportFragmentManager().findFragmentByTag("fragmet2");
        fragmet11 = getSupportFragmentManager().findFragmentByTag("fragmet1");
        switch (v.getId()) {
            case R.id.buton1:
                if (fragmet1.isAdded() && fragmet11 != null) {
                    if (fragmet22 != null) {
                        getSupportFragmentManager().beginTransaction().show(fragmet11).hide(fragmet22).commitAllowingStateLoss();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(fragmet11).commitAllowingStateLoss();
                    }

                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fff, fragmet1).commit();
                }
                break;
            case R.id.buton2:
                if (fragmet2.isAdded() && fragmet22 != null) {
                    if (fragmet11 != null) {
                        getSupportFragmentManager().beginTransaction().show(fragmet22).hide(fragmet11).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(fragmet22).commit();
                    }
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fff, fragmet2, "fragmet2").hide(fragmet1).commit();
                }

                break;
            case R.id.buton3:
                recreate();
                break;
        }
    }

    @Override
    public void recreate() {

        super.recreate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEST, "testing");
        Log.e(TAG," ---> onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String test = savedInstanceState.getString(KEY_TEST);
        Log.e(TAG, " ---> onRestoreInstanceState");
    }

}
