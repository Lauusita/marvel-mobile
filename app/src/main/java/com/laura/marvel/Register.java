package com.laura.marvel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText txtDate, txt_name, txt_email, txt_password;
    Button btn_viewlogin, btn_register;

    private static final String dataUserCache = "user";
    private static final int privateMode = Context.MODE_PRIVATE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main_bg));
        setContentView(R.layout.activity_register);

        btn_viewlogin = findViewById(R.id.btn_viewlogin);
        txtDate = findViewById(R.id.txt_date);
        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_password = findViewById(R.id.txt_password);
        btn_register = findViewById(R.id.btn_register);

        sharedPreferences = getSharedPreferences(dataUserCache, privateMode);
        editor = sharedPreferences.edit();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtDateStr = txtDate.getText().toString().trim();
                String txtEmailStr = txt_email.getText().toString().trim();
                String txtNameStr = txt_name.getText().toString().trim();
                String txtPassStr = txt_password.getText().toString().trim();

                if (txtDateStr.isEmpty() || txtEmailStr.isEmpty() || txtNameStr.isEmpty() || txtPassStr.isEmpty()) {
                    Toast.makeText(Register.this, "Missing fields", Toast.LENGTH_SHORT).show();
                } else if (txtPassStr.length() < 6) {
                    Toast.makeText(Register.this, "Password less than 8 characters", Toast.LENGTH_LONG).show();
                } else {
                    // Guardar todos los datos del usuario
                    editor.putString("mail", txtEmailStr);
                    editor.putString("name", txtNameStr);
                    editor.putString("birthdate", txtDateStr);
                    editor.putString("password", txtPassStr);

                    editor.commit();
                    Intent i = new Intent(Register.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        btn_viewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear el DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                txtDate.setText(selectedDate);
                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });
    }
}