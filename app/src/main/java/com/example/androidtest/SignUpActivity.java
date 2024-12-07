package com.example.androidtest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.androidtest.database.UserDB;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SignUpActivity extends AppCompatActivity {
    View tvSignin;
    EditText edtName, edtEmail, edtPassword, edtConfirmPassword ;

    Button btn2;
    UserDB userDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        tvSignin = findViewById(R.id.tvSignin);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btn2 = findViewById(R.id.btn2);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        userDB = new UserDB(SignUpActivity.this);

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //File file = null; // save data to file in local storage
                String name = edtName.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String passConfirm = edtConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    edtName.setError("Username can not empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPassword.setError("Password can not empty");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email can not empty");
                    return;
                }if (TextUtils.isEmpty(passConfirm)){
                    edtConfirmPassword.setError("Password need to be confirmed");
                    return;
                }
                if (!passConfirm.equals(pass)){
                    edtConfirmPassword.setError("Password does not match!");
                    return;
                }

                boolean checkName = userDB.checkUsernameEmail(email, 1);
                boolean checkEmail = userDB.checkUsernameEmail(pass, 2);
                if (checkName){
                    Toast.makeText(SignUpActivity.this, "Username Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkEmail){
                    Toast.makeText(SignUpActivity.this, "Email Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                long insert = userDB.addNewUser(name, pass, email);
                if (insert == -1){
                    Toast.makeText(SignUpActivity.this, "signup Fail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "signup successfully", Toast.LENGTH_SHORT).show();
                    // quay ve man hinh dang nhap
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
