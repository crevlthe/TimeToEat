package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Preferences extends AppCompatActivity {
    public FirebaseAuth.AuthStateListener authListener;
    public FirebaseUser user;
    public String CurrentUser;
    private ArrayList<String> mCuisines = new ArrayList<>();
    private ArrayList<String> mCuisinesExcl = new ArrayList<>();
    private ChipGroup chipGroup;
    private Button saveBtn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        chipGroup = findViewById(R.id.chipgroup);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    CurrentUser = firebaseAuth.getUid();
                } else {
                    // User is signed out

                }
                // ...
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authListener);

        saveBtn = (Button) findViewById(R.id.button_saveprefs);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCuisines.clear();
                mCuisinesExcl.clear();
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        final String CuisinesString = "";
        new Thread() {
            @Override
            public void run() {
                try {
                    int chipCount = chipGroup.getChildCount();
                    final String PrefString = "Hello";

                    if (chipCount == 0) {

                    } else {
                        int i = 0;
                        while (i < chipCount) {
                            Chip chip = (Chip) chipGroup.getChildAt(i);
                            if (chip.isChecked()) {
                                mCuisines.add(chip.getText().toString());
                            } else {
                                mCuisinesExcl.add(chip.getText().toString());
                            }
                            i++;
                        }

                    }

                    // update Firebase, true values
                    for (String mCuisine : mCuisines){
                        mDatabase.child("preferences").child(CurrentUser).child(mCuisine).setValue("True");
                    }

                    // update Firebase, false/other values
                    for (String mCuisineExcl : mCuisinesExcl){
                        mDatabase.child("preferences").child(CurrentUser).child(mCuisineExcl).setValue("False");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // for anything to show on the UI
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    // Error
                }



            }
        }.start();
    }
}
