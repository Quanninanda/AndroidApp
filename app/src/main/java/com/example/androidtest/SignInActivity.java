package com.example.androidtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.database.UserDB;
import com.example.androidtest.model.UserModel;


public class SignInActivity extends AppCompatActivity {
    ImageView ivIcon;
    EditText edtEmail, edtPassword;
    Button btn1;
    View tvSignup;
    UserDB userDB;
    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btn1 = findViewById(R.id.btn1);
        tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent1);
                finish();
            }
        });


        userDB = new UserDB(SignInActivity.this);
        checkLoginWithDatabase();
    }

    private void checkLoginWithDatabase(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email can be not empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPassword.setError("Password can be not empty");
                    return;
                }
                userModel = userDB.getInfoUser(email, pass); // lay du lieu tu database
                assert userModel != null;
                if (userModel.getUsername() != null){
                    // dang nhap thanh cong
                    Intent intent = new Intent(SignInActivity.this, MenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME_ACCOUNT", email);
                    bundle.putInt("ID_ACCOUNT", userModel.getId());
                    intent.putExtras(bundle);
                    startActivity(intent); // chuyen sang HomeActivity
                    finish();
                } else {
                    // dang nhap that bai
                    Toast.makeText(SignInActivity.this, "Account invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

