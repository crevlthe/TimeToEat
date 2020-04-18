package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {

    TextView t;
    TextView register;
    Button login;
    EditText etEmail, etPassword, etUserName, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);


        t = (TextView) findViewById(R.id.appName);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/FugazOne-Regular.ttf");
        t.setTypeface(myCustomFont);

        register = (TextView) findViewById(R.id.textView2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstPage.this, Register.class));
            }
        });

        Button login = (Button) findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstPage.this, Login.class));

            }
        });




    }
    }

