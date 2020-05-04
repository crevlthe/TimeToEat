package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChangePreferences(View view){
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
    }

    public void SearchRecipes(View view) {
        Intent intent = new Intent(this, RecipeSearch.class);
        startActivity(intent);
    }

    public void ViewBuyList(View view){
        Intent intent = new Intent(this, ToBuyList.class);
        startActivity(intent);
    }

    public void Logout(View view){
        finish();
    }

}
