package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ToBuyList extends AppCompatActivity {

    private static final String TAG = "ToBuyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        ListView listView = (ListView) findViewById(R.id.myListView);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ingredients = extras.getString("INGREDIENTS");

        assert ingredients != null;
        String[] ingredients_split = ingredients.split("\u25AA");
        List<String> Ingredients = new ArrayList<String>();
        Ingredients = Arrays.asList(ingredients_split);

        Log.i(TAG, "ingredients - " + ingredients);

        ListViewAdapter adapter = new ListViewAdapter(Ingredients, this);
        listView.setAdapter(adapter);

    }
}




        /*final List<UserModel> users = new ArrayList<>();
        users.add(new UserModel(false, "Dharm"));
        users.add(new UserModel(false, "Sign"));
        users.add(new UserModel(false, "Mark"));

        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                UserModel model = users.get(i);

                if (model.isSelected())
                    model.setSelected(false);
                else
                    model.setSelected(true);

                users.set(i,model);

                adapter.upDateRecords(users);

            }

        });*/


       /*final String TAG = ToBuyList.class.getSimpleName();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ingredients = extras.getString("INGREDIENTS");

        String[] arr = ingredients.split("\u25AA");

        Log.i(TAG, "Inside array" + arr);

        for(String a : arr){
            Ingredients.add(a);
        }

        Log.i(TAG, "Inside Loop" + Ingredients);

        //Ingredients.addAll(Arrays.asList(arr));

        listView = findViewById(R.id.myListView);
        adapter = new ListViewAdapter(Ingredients, this);
        listView.setAdapter(adapter);*/





























