package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToBuyList extends AppCompatActivity {

    TextView groceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        //groceryList = (TextView) findViewById(R.id.groceryList);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ingredients = extras.getString("INGREDIENTS");

        String[] arr = ingredients.split("\u25AA");

        LinearLayout linear=(LinearLayout)findViewById(R.id.checkbox_container);


        /*for (int i = 0; i < arr.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(arr[i]);
            checkboxContainer.addView(checkBox);}*/


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<arr.length;i++){

            CheckBox checkBox = new CheckBox(this);
            checkBox.setLayoutParams(lparams);
            checkBox.setText(arr[i]);
            //checkBox.setPadding(0, 0, 0, 5);
            linear.addView(checkBox);

        }


        //groceryList.setText(ingredients);


    }
}


