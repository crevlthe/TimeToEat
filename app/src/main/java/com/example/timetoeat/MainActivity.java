package com.example.timetoeat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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

    public void FindNearest(View view){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=supermarkets");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void MyRecipe(View view) {
        Intent intent = new Intent(this, PersonalRecipe.class);
        startActivity(intent); }

    public void Logout(View view){
        finish();
    }
}
