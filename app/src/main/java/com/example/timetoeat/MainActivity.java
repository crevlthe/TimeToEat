package com.example.timetoeat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void RecommendRecipe(View view) {
        Intent intent = new Intent(this, RecipeRecommend.class);
        startActivity(intent);
    }

    public void ChangePreferences(View view) {
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
    }

    public void SearchRecipes(View view) {
        Intent intent = new Intent(this, RecipeSearch.class);
        startActivity(intent);
    }

    public void ViewBuyList(View view) {
        Intent intent = new Intent(this, ListGridRetrieve.class);
        startActivity(intent);
    }

    public void FindNearest(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=supermarkets");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void MyRecipe(View view) {
        Intent intent = new Intent(this, PersonalRecipe.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        finish();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }



}



