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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

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

                    registerUser(txtNameStr, txtEmailStr, txtPassStr, txtDateStr);
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

    private void registerUser(String name, String email, String password, String birthDate) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("name", name);
        json.put("email", email);
        json.put("password", password);

        try {
            String[] dateParts = birthDate.split("/");
            if (dateParts.length == 3) {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                
                // Format to YYYY-MM-DD
                String formattedDate = String.format("%04d-%02d-%02d", year, month, day);
                json.put("birthDate", formattedDate);
            } else {
                json.put("birthDate", birthDate);
            }
        } catch (Exception e) {
            json.put("birthDate", birthDate);
            System.out.println("Error formatting date: " + e.getMessage());
        }

        JSONObject jsonObject = new JSONObject(json);
        
        String url = "https://auth-nestjs-umda.onrender.com/api/users";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    if (statusCode == 409) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject errorObj = new JSONObject(responseBody);
                            String message = errorObj.getString("message");
                            Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Register.this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show();
                        }
                    } else if (statusCode == 400) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject errorObj = new JSONObject(responseBody);
                            JSONArray messageArray = errorObj.getJSONArray("message");
                            String firstMessage = messageArray.getString(0);
                            Toast.makeText(Register.this, firstMessage, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Register.this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Error en el servidor, intente más tarde", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Error de conexión", Toast.LENGTH_LONG).show();
                }
            }
        });

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(req);


    }

    private void handleResponse(JSONObject res) {
        Toast.makeText(Register.this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
        System.out.println("PETICIÓN CONTESTADA");

        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }
}