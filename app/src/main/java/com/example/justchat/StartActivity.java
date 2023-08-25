package com.example.justchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justchat.R;

public class StartActivity extends AppCompatActivity {

    Button login,register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);

        login.setOnClickListener(v -> {
            Intent intent=new Intent(StartActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(v -> {
            Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });


    }


}