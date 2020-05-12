package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_final);

        GroceryList = getIntent().getStringArrayListExtra("GROCERIES");


        //Log.i("TEEEEST", "groceries - " + GroceryList);

        final CustomGrid adapter = new CustomGrid(GroceryListFinal.this, GroceryList, imageId);

        GridView grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {

                    String el = (String) GroceryList.get(position);
                    adapter.removeItem(el);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),
                            "Removed : " + GroceryList.get(+position), Toast.LENGTH_SHORT).show();
                }

        });


    }
    }




