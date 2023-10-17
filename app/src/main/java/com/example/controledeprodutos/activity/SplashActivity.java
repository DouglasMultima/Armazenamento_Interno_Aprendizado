package com.example.controledeprodutos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controledeprodutos.autenticacao.LoginActivity;
import com.example.controledeprodutos.helper.FirebaseHelper;
import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::verificaLogin,3000);


    }


    private void verificaLogin(){

        if(FirebaseHelper.getAutenticado()){
            startActivity(new Intent(this,MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}