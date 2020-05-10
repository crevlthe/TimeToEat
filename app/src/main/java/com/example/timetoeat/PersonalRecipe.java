package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ModelLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalRecipe extends AppCompatActivity {

    Button btnUploadRecipe;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference DataReference;

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

        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        LoadData();

    }

    private void getUrl(){
        storageReference.child("RecipeImages.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                String url = downloadUrl.toString();
                Log.i("FUCKING URL",url);

            }
        });
    }

    private void LoadData(){

        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(DataReference, DataSetFire.class).build();
        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int i, @NonNull final DataSetFire dataSetFire) {

                holder.recName.setText(dataSetFire.getRecName());
                //Glide.with(PersonalRecipe.this).load().into(holder.recImg);
                //Picasso.get().load(dataSetFire.getImgUrl()).into(firebaseViewHolder.recImg);


               /* firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(PersonalRecipe.this, Main2Activity.class);
                        intent.putExtra("recname", dataSetFire.getRecName());
                        intent.putExtra("imageUrl", dataSetFire.getImgUrl());
                        startActivity(intent);
                    }
                });*/
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




