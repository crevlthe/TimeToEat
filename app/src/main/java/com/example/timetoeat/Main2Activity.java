package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String recname = getIntent().getStringExtra("recname");
        String imgUrl = getIntent().getStringExtra("imgUrl");
        Log.i("OUR VALUE", recname);
        Log.i("OUR VALUE 2", imgUrl);
    }
}
