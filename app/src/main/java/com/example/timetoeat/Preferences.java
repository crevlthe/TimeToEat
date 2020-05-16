package com.example.timetoeat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Preferences extends AppCompatActivity {
    public FirebaseAuth.AuthStateListener authListener;
    public FirebaseUser user;
    public String CurrentUser;
    private ArrayList<String> mCuisines = new ArrayList<>();
    private ArrayList<String> mCuisinesExcl = new ArrayList<>();
    private ArrayList<String> mAllergies = new ArrayList<>();
    private ArrayList<String> mAllergiesExcl = new ArrayList<>();
    private ChipGroup chipGroup1;
    private ChipGroup chipGroup2;
    private Button saveBtn;
    private DatabaseReference mDatabase;

    private EditText chooseMorningTime;
    private TimePickerDialog timeMorningPickerDialog;
    private EditText chooseLunchTime;
    private TimePickerDialog timeLunchPickerDialog;

    public int morningCurrentHour;
    public int morningCurrentMinute;
    public int lunchCurrentHour;
    public int lunchCurrentMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        chipGroup1 = findViewById(R.id.chipgroup1);
        chipGroup2 = findViewById(R.id.chipgroup2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        saveBtn = (Button) findViewById(R.id.button_saveprefs);
        chooseMorningTime = (EditText) findViewById(R.id.input_morningalarm);
        chooseLunchTime = (EditText) findViewById(R.id.input_lunchalarm);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    CurrentUser = firebaseAuth.getUid();

                    //Get current settings first and load them into chipgroups
                    getPreferences(); //This must be placed within the auth state as this has to wait until the listener has completed initialisation
                } else {
                    //User is signed out
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authListener);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCuisines.clear();
                mCuisinesExcl.clear();
                mAllergies.clear();
                mAllergiesExcl.clear();
                savePreferences();

                if(chooseMorningTime.getText().toString().trim().length() > 0){
                    Log.d("chosenTime", chooseMorningTime.getText().toString());

                    morningCurrentHour = Integer.parseInt(chooseMorningTime.getText().toString().substring(0,2));
                    morningCurrentMinute = Integer.parseInt(chooseMorningTime.getText().toString().substring(3,5));

                    scheduleMorning(morningCurrentHour, morningCurrentMinute);
                }

                if(chooseLunchTime.getText().toString().trim().length() > 0){
                    Log.d("chosenTime", chooseLunchTime.getText().toString());

                    lunchCurrentHour = Integer.parseInt(chooseLunchTime.getText().toString().substring(0,2));
                    lunchCurrentMinute = Integer.parseInt(chooseLunchTime.getText().toString().substring(3,5));

                    scheduleLunch(lunchCurrentHour, lunchCurrentMinute);
                }

                Toast.makeText(Preferences.this, "Preferences saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        chooseMorningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calendar = Calendar.getInstance();
                //morningCurrentHour = calendar.get(Calendar.HOUR_OF_DAY);
                //morningCurrentMinute = calendar.get(Calendar.MINUTE);
                morningCurrentHour = 8;
                morningCurrentMinute = 0;


                timeMorningPickerDialog = new TimePickerDialog(Preferences.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseMorningTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, morningCurrentHour, morningCurrentMinute, true);

                timeMorningPickerDialog.show();
            }
        });

        chooseLunchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calendar = Calendar.getInstance();
                morningCurrentHour = 12;
                morningCurrentMinute = 0;


                timeLunchPickerDialog = new TimePickerDialog(Preferences.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseLunchTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, lunchCurrentHour, lunchCurrentMinute, true);

                timeLunchPickerDialog.show();
            }
        });

    }

    private void getPreferences() {
        final DatabaseReference refPreferences = mDatabase.child("preferences").child(CurrentUser);

        refPreferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int chipCount1 = chipGroup1.getChildCount();
                int chipCount2 = chipGroup2.getChildCount();

                for (DataSnapshot Snap : dataSnapshot.child("cuisines").getChildren()) {
                    String isKeyIncluded = Snap.getValue(String.class);
                    if (isKeyIncluded != null) {
                        if (Snap.getValue(String.class).equals("True")) {
                            String key = Snap.getKey();
                            mCuisines.add(key);
                        }
                    }
                }

                for (DataSnapshot Snap : dataSnapshot.child("allergies").getChildren()) {
                    String isKeyIncluded = Snap.getValue(String.class);
                    if (isKeyIncluded != null) {
                        if (Snap.getValue(String.class).equals("True")) {
                            String key = Snap.getKey();
                            mAllergies.add(key);
                        }
                    }
                }

                if (chipCount1 == 0) {
                    //Do nothing
                } else {
                    int i = 0;
                    while (i < chipCount1) {
                        Chip chip = (Chip) chipGroup1.getChildAt(i);
                        if (mCuisines.contains(chip.getText())) {
                            chip.setChecked(true);
                        } else {
                            //Do nothing if not in the list
                        }
                        i++;
                    }

                }

                if (chipCount2 == 0) {
                    //Do nothing
                } else {
                    int i = 0;
                    while (i < chipCount2) {
                        Chip chip = (Chip) chipGroup2.getChildAt(i);
                        if (mAllergies.contains(chip.getText())) {
                            chip.setChecked(true);
                        } else {
                            //Do nothing if not in the list
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Preferences.this, "Retrieval of preferences failed. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void savePreferences() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int chipCount1 = chipGroup1.getChildCount();
                    int chipCount2 = chipGroup2.getChildCount();
                    final String PrefString = "Hello";

                    if (chipCount1 == 0) {
                        //Do nothing
                    } else {
                        int i = 0;
                        while (i < chipCount1) {
                            Chip chip = (Chip) chipGroup1.getChildAt(i);
                            if (chip.isChecked()) {
                                mCuisines.add(chip.getText().toString());
                            } else {
                                mCuisinesExcl.add(chip.getText().toString());
                            }
                            i++;
                        }
                    }

                    if (chipCount2 == 0) {
                        //Do nothing
                    } else {
                        int i = 0;
                        while (i < chipCount2) {
                            Chip chip = (Chip) chipGroup2.getChildAt(i);
                            if (chip.isChecked()) {
                                mAllergies.add(chip.getText().toString());
                            } else {
                                mAllergiesExcl.add(chip.getText().toString());
                            }
                            i++;
                        }
                    }

                    // Up date Firebase, true values and false/other values
                    for (String mCuisine : mCuisines) {
                        mDatabase.child("preferences").child(CurrentUser).child("cuisines").child(mCuisine).setValue("True");
                    }
                    for (String mCuisineExcl : mCuisinesExcl) {
                        mDatabase.child("preferences").child(CurrentUser).child("cuisines").child(mCuisineExcl).setValue("False");
                    }
                    for (String mAllergy : mAllergies) {
                        mDatabase.child("preferences").child(CurrentUser).child("allergies").child(mAllergy).setValue("True");
                    }
                    for (String mAllergyExcl : mAllergiesExcl) {
                        mDatabase.child("preferences").child(CurrentUser).child("allergies").child(mAllergyExcl).setValue("False");
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

    private void scheduleMorning(int Hour, int Minute) {
        Log.d("scheduleMorning","Morning notification scheduled");
        //final int Hour = currentHour;
        //final int Minute = currentMinute;
        long remainingMillis;

        long currentTimeMillis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),Hour,Minute);
        remainingMillis = calendar.getTimeInMillis() - currentTimeMillis;

        if(remainingMillis>0){
            Log.d("remainingMillis",Long.toString(remainingMillis));
            Intent intent = new Intent(getApplicationContext(), ReminderBreakfast.class);
            intent.setAction("morning_notification");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + remainingMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

    private void scheduleLunch(int Hour, int Minute) {
        Log.d("scheduleLunch","Lunch notification scheduled");
        long remainingMillis;

        long currentTimeMillis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),Hour,Minute);
        remainingMillis = calendar.getTimeInMillis() - currentTimeMillis;

        if(remainingMillis>0){
            Log.d("remainingMillis",Long.toString(remainingMillis));
            Intent intent = new Intent(getApplicationContext(), ReminderLunch.class);
            intent.setAction("lunch_notification");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),200,intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + remainingMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

}