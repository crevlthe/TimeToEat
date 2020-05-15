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

        scheduleMorning();
        scheduleLunch();
    }

    private void scheduleMorning() {
        final int Hour = 9;
        final int Minute = 0;
        long remainingMillis;

        long currentTimeMillis = System.currentTimeMillis();

        Calendar MorningCalendar = Calendar.getInstance();
        MorningCalendar.set(MorningCalendar.get(Calendar.YEAR),MorningCalendar.get(Calendar.MONTH),MorningCalendar.get(Calendar.DAY_OF_MONTH),Hour,Minute);
        remainingMillis = MorningCalendar.getTimeInMillis() - currentTimeMillis;

        if(remainingMillis>0){
            Log.d("remainingMillis",Long.toString(remainingMillis));
            Intent intent = new Intent(getApplicationContext(), ReminderBreakfast.class);
            intent.setAction("morning_notification");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + remainingMillis,pendingIntent);
        }
    }

    private void scheduleLunch() {
        final int Hour = 12;
        final int Minute = 0;
        long remainingMillis;

        long currentTimeMillis = System.currentTimeMillis();

        Calendar MorningCalendar = Calendar.getInstance();
        MorningCalendar.set(MorningCalendar.get(Calendar.YEAR),MorningCalendar.get(Calendar.MONTH),MorningCalendar.get(Calendar.DAY_OF_MONTH),Hour,Minute);
        remainingMillis = MorningCalendar.getTimeInMillis() - currentTimeMillis;

        if(remainingMillis>0){
            Log.d("remainingMillis",Long.toString(remainingMillis));
            Intent intent = new Intent(getApplicationContext(), ReminderLunch.class);
            intent.setAction("lunch_notification");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),200,intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + remainingMillis,pendingIntent);
        }
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
    }



}



