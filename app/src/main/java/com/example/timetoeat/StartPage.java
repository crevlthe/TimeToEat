package com.example.timetoeat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartPage extends AppCompatActivity {

    TextView t;
    TextView register;
    Button login;
    EditText etEmail, etPassword, etUserName, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        createNotificationChannel(); //Used to register the app's notification channel with the system

        t = (TextView) findViewById(R.id.title_appname);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/FugazOne-Regular.ttf");
        t.setTypeface(myCustomFont);

        register = (TextView) findViewById(R.id.text_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartPage.this, Register.class));
            }
        });

        Button login = (Button) findViewById(R.id.button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartPage.this, Login.class));

            }
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("createNotificationChannel","Channel created");
            CharSequence name = "Time to Eat Channel";
            String description = "Channel for Time to Eat";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyEat", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

