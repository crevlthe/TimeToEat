package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Main2Activity extends AppCompatActivity {


    private TextView RecipeTitle;
    private TextView RecipeIngredients;
    private TextView RecipeBody;
    private ImageView RecipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecipeTitle = (TextView) findViewById(R.id.text_recipe_title);
        RecipeIngredients = (TextView) findViewById(R.id.text_recipe_ingredients);
        RecipeBody = (TextView) findViewById(R.id.text_recipe_body);
        RecipeImage = (ImageView) findViewById(R.id.image_recipe);


        Intent intent = getIntent();
        String recName = intent.getStringExtra("RecipeName");
        String imgUrl = intent.getStringExtra("imgUrl");
        String RecIngredients = intent.getStringExtra("RecIngredients");
        String RecDescription = intent.getStringExtra("RecDescription");



        RecipeTitle.setText(recName);
        RecipeIngredients.setText(RecIngredients);
        RecipeBody.setText(RecDescription);
        Glide.with(Main2Activity.this).load(imgUrl).into(RecipeImage);

    }
}
