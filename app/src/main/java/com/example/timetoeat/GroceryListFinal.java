package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GroceryListFinal extends AppCompatActivity {

    TextView groceriesFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_final);


        groceriesFinal = (TextView) findViewById(R.id.groceryList);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String groceries = extras.getString("GROCERIES");

        groceriesFinal.setText(groceries);

    }
}
