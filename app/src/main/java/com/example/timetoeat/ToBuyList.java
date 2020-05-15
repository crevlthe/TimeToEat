package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ToBuyList extends AppCompatActivity {

    //private static final String TAG = "ToBuyList";

    private ListView listView;
    private ListViewAdapter adapter;
    private List<String> Ingredients = new ArrayList<>();


    public static boolean isActionMode = false;
    public static List<String> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;

    private DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        listView = (ListView) findViewById(R.id.myListView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ingredients = extras.getString("INGREDIENTS");

        assert ingredients != null;
        String[] ingredients_split = ingredients.split("\u25AA");
        Ingredients = new ArrayList(Arrays.asList(ingredients_split));
        Ingredients.remove(0);

        Log.i("TEEEEST", "ingredients - " + Ingredients);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        adapter = new ListViewAdapter(Ingredients, this);
        listView.setAdapter(adapter);

    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu,menu);
            isActionMode = true;
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_delete:
                    adapter.removeItems(UserSelection);
                    mode.finish();
                    return true;
                case R.id.action_share:
                    Intent intent = new Intent(ToBuyList.this, GroceryListFinal.class);
                    Bundle extras = new Bundle();
                    intent.putStringArrayListExtra("GROCERIES", (ArrayList<String>) UserSelection);
                    startActivity(intent);
                    //Log.i("TEEEEST", "ingredients - " + UserSelection);

                    //Send the list to the database
                    dbReference = FirebaseDatabase.getInstance().getReference().child("IngredientsList");

                    final String myCurrentDateTime = DateFormat.getDateTimeInstance()
                            .format(Calendar.getInstance().getTime());

                    //Map<String, IngredientLst> myIngredients = new HashMap<>();
                    Map<String, Object> myIngredients = new HashMap<>();
                    myIngredients.put(myCurrentDateTime, UserSelection);

                    dbReference.setValue(myIngredients)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
                                        //Toast.makeText(ToBuyList.this, "Image Saved to the Database", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(ToBuyList.this, "Error" + message, Toast.LENGTH_SHORT);
                                    }
                                }
                            });


                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;
            UserSelection.clear();
        }
    };


}






















