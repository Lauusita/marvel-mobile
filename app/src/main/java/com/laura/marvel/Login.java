package com.laura.marvel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    Button btn_viewregister, btn_login;
    EditText txt_email, txt_password;

    private static final String dataUserCache = "user";
    private static final int privateMode = Context.MODE_PRIVATE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main_bg));
        setContentView(R.layout.activity_login);

        btn_viewregister = findViewById(R.id.btn_viewregister);
        txt_email = findViewById(R.id.txt_user);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        
        sharedPreferences = getSharedPreferences(dataUserCache, privateMode);
        editor = sharedPreferences.edit();
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUser = txt_email.getText().toString();
                String txtPass = txt_password.getText().toString();

                if (txtUser.isEmpty() || txtPass.isEmpty()) {
                    Toast.makeText(Login.this, "Missing fields", Toast.LENGTH_LONG).show();
                } else if (txtPass.length() < 6) {
                    Toast.makeText(Login.this, "Password less than 8 characters", Toast.LENGTH_LONG).show();
                } else {
                    // En un sistema real se verificaría con BD y se obtendrían los datos completos
                    // Aquí simulamos que tenemos los datos
                    editor.putString("mail", txtUser);
                    editor.putString("name", "Usuario Marvel"); // Valores por defecto para login demo
                    editor.putString("birthdate", "01/01/2000");
                    editor.putString("password", txtPass);

                    editor.commit();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        btn_viewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }
}