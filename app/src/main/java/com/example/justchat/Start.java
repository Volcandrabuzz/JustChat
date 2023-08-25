package com.example.justchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    Button login,register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);

        login.setOnClickListener(v -> {
            Intent intent=new Intent(Start.this, Login.class);
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(v -> {
            Intent intent=new Intent(Start.this, Register.class);
            startActivity(intent);
            finish();
        });


    }


}