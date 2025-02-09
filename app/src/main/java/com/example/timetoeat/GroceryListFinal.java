package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class GroceryListFinal extends AppCompatActivity {



    int[] imageId = {
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries,
            R.drawable.groceries};

   ArrayList GroceryList;
   Button back_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_final);

        back_to_main = findViewById(R.id.back_to_nav);
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        GroceryList = getIntent().getStringArrayListExtra("GROCERIES");

        final CustomGrid adapter = new CustomGrid(GroceryListFinal.this, GroceryList, imageId);

        final GridView grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {

                String el = (String) GroceryList.get(position);
                final int size = grid.getChildCount();
                //Log.i("CHILDREN COUNT", String.valueOf(size));
                for(int i = 0; i < size; i++) {
                    ViewGroup gridChild = (ViewGroup) grid.getChildAt(i);
                    if (gridChild == itemClicked){
                        gridChild.setVisibility(View.GONE);}

                }
                }

        });


    }
    }




