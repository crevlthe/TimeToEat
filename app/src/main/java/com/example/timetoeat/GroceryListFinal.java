package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


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

   ArrayList<String> GroceryList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_final);

        //Intent intent = getIntent();
        //Bundle extras = intent.getExtras();
        //String groceries = extras.getString("GROCERIES");
        //GroceryList = new ArrayList(Collections.singleton(groceries));
        GroceryList = getIntent().getStringArrayListExtra("GROCERIES");

        Log.i("TEEEEST", "groceries - " + GroceryList);

        final CustomGrid adapter = new CustomGrid(GroceryListFinal.this, GroceryList, imageId);
        final GridView grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                
                final String item = (String) parent.getItemAtPosition(position);

                Log.i("TEEEST#$%$", item);
                GroceryList.remove(item);

                //String secondItemText = GroceryList.get(1);
                //LinkedList ll = new LinkedList(Collections.singletonList(GroceryList));
                //Log.i("TEEEST$", String.valueOf(ll));
                //ll.remove(GroceryList.get(+position));
                adapter.notifyDataSetChanged();
                ;

                //Toast.makeText(GroceryListFinal.this, "You Clicked at " + GroceryList.get(+position),Toast.LENGTH_SHORT).show();

            }
        });

    }
    }




