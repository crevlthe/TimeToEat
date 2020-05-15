package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListGridRetrieve extends AppCompatActivity {

    private DatabaseReference dbReference;
    CustomGridFireBase adapter;
    GridView grid;
    DataSnapshot dataSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grid_retrieve);

        dbReference = FirebaseDatabase.getInstance().getReference().child("IngredientsList");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> lstIngredients = new ArrayList<>();
                for(DataSnapshot dsp: dataSnapshot.getChildren()){
                    lstIngredients.add(String.valueOf(dsp.getValue()));

                    //Log.i("FB LIST", String.valueOf(lstIngredients3));

                }

                String lstIngredients2 = String.valueOf(lstIngredients);
                String[] lstIngredients3 = lstIngredients2.split("\n\n");
                List<String> lstIngredients4 = new ArrayList<String>(Arrays.asList(lstIngredients3));
                lstIngredients4.remove("]]");
                final List<String> lstIngredients5 = new ArrayList<String>();
                for (int i = 0; i < lstIngredients4.size(); i++){
                    if (lstIngredients4.get(i).contains("[[")){
                        String noBrack = lstIngredients4.get(i).replace("[["," ");
                        lstIngredients5.add(noBrack);
                    }
                    if(lstIngredients4.get(i).contains(",")){
                        String noComma = lstIngredients4.get(i).replace(","," ");
                        lstIngredients5.add(noComma);
                    }
                }
                //Log.i("FB LIST", String.valueOf(lstIngredients5));



                final int[] imageId = {
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

                grid = (GridView)findViewById(R.id.grid);
                adapter = new CustomGridFireBase(ListGridRetrieve.this, lstIngredients5, imageId);
                grid.setAdapter(adapter);

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View itemClicked,
                                            int position, long id) {

                        final String el = (String) lstIngredients5.get(+position);

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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


