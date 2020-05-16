package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference DataReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //private List<DataSetFire> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataReference = FirebaseDatabase.getInstance().getReference().child("SavedRecipes");

        LoadData();
    }

    private void LoadData(){

        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(DataReference, DataSetFire.class).build();
        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {

            private ArrayList<DataSetFire> data = new ArrayList<>();


            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, final int i, @NonNull final DataSetFire dataSetFire) {
                data.add(dataSetFire);

               // String newName = holder.recipeName.substring(holder.recipeName.indexOf(">") + 1, name.indexOf("<"));
                holder.recipeName.setText(data.get(i).getRecipeName());
                Glide.with(SavedRecipes.this).load(data.get(i).getImgUrl()).into(holder.imgUrl);
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
