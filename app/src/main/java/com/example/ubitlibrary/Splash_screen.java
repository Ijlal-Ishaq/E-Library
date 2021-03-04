package com.example.ubitlibrary;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;



public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Intent intent = new Intent(this, Sign_in.class);
        startActivity(intent);
        finish();


    }
}