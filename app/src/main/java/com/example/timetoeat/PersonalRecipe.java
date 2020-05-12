package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;



public class PersonalRecipe extends AppCompatActivity {

    Button btnUploadRecipe;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference DataReference;

    private List<DataSetFire> items = new ArrayList<>();

    private FirebaseStorage storage;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recipe);


        btnUploadRecipe = findViewById(R.id.button_uploadRecipe);
        btnUploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Upload_Recipe.class);
                startActivity(intent);
            }

        });

        recyclerView = findViewById(R.id.recyclerView2);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DataReference = FirebaseDatabase.getInstance().getReference().child("Recipes");


        /*DataReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<String> PersonalRecipeInfo = new ArrayList<>();

                        for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                            DataSetFire item = itemSnapshot.getValue(DataSetFire.class);

                            items.add(item);

                        }
                        Log.i("INFOOOOOO", PersonalRecipeInfo.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });*/

        DataReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<String> PersonalRecipeInfo = new ArrayList<>();

                        for(DataSnapshot dsp: dataSnapshot.getChildren()){
                            PersonalRecipeInfo.add(String.valueOf(dsp.getValue()));

                        }
                        Log.i("INFOOOOOO", PersonalRecipeInfo.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        LoadData();

    }

    /*private void collectReferences(Map<String, PersonalRecipeInfo> recipes) {
        ArrayList<String> PersonalRecipeInfo = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, PersonalRecipeInfo> entry : recipes.entrySet()){

            //Get user map
            Map singleRecipe = (Map) entry.getValue();
            //Get phone field and append to list
            PersonalRecipeInfo.add((String) singleRecipe.get("imgUrl"));
            PersonalRecipeInfo.add((String) singleRecipe.get("recipeName"));
        }

        Log.i("INFOOOOOO", PersonalRecipeInfo.toString());
    }*/


    private void LoadData(){



        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(DataReference, DataSetFire.class).build();
        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {

            private ArrayList<DataSetFire> data = new ArrayList<>();


            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int i, @NonNull final DataSetFire dataSetFire) {
                data.add(dataSetFire);

                holder.recipeName.setText(data.get(i).getRecipeName());
                Glide.with(PersonalRecipe.this).load(data.get(i).getImgUrl()).into(holder.imgUrl);
                //Picasso.get().load(dataSetFire.getImgUrl()).into(firebaseViewHolder.recImg);

            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_recipe_row,parent,false);
                return new FirebaseViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }





}
