package com.example.yehongjiang.booklist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yehongjiang.booklist.R;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/2/4</date>
 * <summary>booklist</summary>
 */
public class LoginActivity extends AppCompatActivity {
    ImageView loginBackgroud;
    ImageView loginHead;
    EditText username;
    EditText password;
    Button login;
    Button forget;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBackgroud = findViewById(R.id.login_backgroud);
        loginHead = findViewById(R.id.login_head);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_sign_in);
        forget = findViewById(R.id.btn_forget);
        register = findViewById(R.id.btn_register);


    }
}
