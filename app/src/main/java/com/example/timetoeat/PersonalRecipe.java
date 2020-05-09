package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PersonalRecipe extends AppCompatActivity {

    Button btnUploadRecipe;

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
    }
}
