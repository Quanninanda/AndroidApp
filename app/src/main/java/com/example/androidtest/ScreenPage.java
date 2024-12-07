package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScreenPage extends AppCompatActivity {
    TextView edtsignin, edtsignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_layout);

        edtsignin = findViewById(R.id.sign_in);
        edtsignup = findViewById(R.id.sign_up);

        edtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScreenPage.this ,SignInActivity.class);
                startActivity(intent);
            }
        });
        edtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ScreenPage.this , SignUpActivity.class);
                startActivity(intent2);
            }
        });

    }
}
