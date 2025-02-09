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
    Button backToMain;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference DataReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private List<DataSetFire> items = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recipe);

        backToMain = findViewById(R.id.go_to_main);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        btnUploadRecipe = findViewById(R.id.button_uploadRecipe);
        btnUploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Upload_Recipe.class);
                startActivity(intent);
            }

        });

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataReference = FirebaseDatabase.getInstance().getReference().child("Recipes");

        /*DataReference.addListenerForSingleValueEvent(
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
        storageReference=storage.getReference();*/


        LoadData();

    }



    private void LoadData(){



        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(DataReference, DataSetFire.class).build();
        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {

            private ArrayList<DataSetFire> data = new ArrayList<>();


            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, final int i, @NonNull final DataSetFire dataSetFire) {
                data.add(dataSetFire);

                holder.recipeName.setText(data.get(i).getRecipeName());
                Glide.with(PersonalRecipe.this).load(data.get(i).getImgUrl()).into(holder.imgUrl);
                //Picasso.get().load(dataSetFire.getImgUrl()).into(firebaseViewHolder.recImg);

                holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View View) {
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra("RecipeName", data.get(i).getRecipeName());
                        intent.putExtra("imgUrl", data.get(i).getImgUrl());
                        intent.putExtra("RecIngredients", data.get(i).getRecipeIngredients());
                        intent.putExtra("RecDescription",data.get(i).getRecipeDescription());

                        startActivity(intent);
                    }
                });

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
