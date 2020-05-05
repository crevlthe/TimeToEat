package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToBuyList extends AppCompatActivity {

    //private static final String TAG = "ToBuyList";

    private ListView listView;
    private ListViewAdapter adapter;
    private List<String> Ingredients = new ArrayList<>();


    public static boolean isActionMode = false;
    public static List<String> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;


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

                    //extras.putString("GROCERIES", String.valueOf(UserSelection));
                    //intent.putExtras(extras);
                    startActivity(intent);
                    Log.i("TEEEEST", "ingredients - " + UserSelection);
                    //Log.i("TEEEEST2", "ingredients - " + Arrays.asList(userselection));


                    //Bundle extras = new Bundle();
                    //extras.putStringArray("GROCERIES", UserSelection);
                    //Intent intent = new Intent(ToBuyList.this, GroceryListFinal.class);
                    //intent.putExtras(extras);


                    /*case R.id.select_all:
                        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                        //int itemCount = listView.getCount();
                        for(int i=0; i < listView.getChildCount(); i++){
                            listView.setItemChecked(i, checkBox.isChecked());
                        }
                        /*int checkedItemCount = getCheckedItemCount();
                        if(listView.getCount() == checkedItemCount){
                            checkBox.setChecked(true);
                        } else{
                            checkBox.setChecked(false);
                        }*/
                default:
                    return false;
            }
        }

        /*private int getCheckedItemCount(){
            int cnt = 0;
            SparseBooleanArray positions = listView.getCheckedItemPositions();
            int itemCount = listView.getCount();

            for (int i=0; i<itemCount; i++){
                if(positions.get(i)){
                    cnt++;
                }
            }
            return cnt;
        }*/

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;
            UserSelection.clear();
        }
    };


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





























