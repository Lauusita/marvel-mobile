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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
                    loginUser(txtUser, txtPass);
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

    private void loginUser(String email, String password) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("email", email);
        json.put("password", password);

        JSONObject jsonObject = new JSONObject(json);
        
        String url = "https://auth-nestjs-umda.onrender.com/api/auth/login";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleLoginSuccess(response, email);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_login.setEnabled(true);
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    if (statusCode == 401) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject errorObj = new JSONObject(responseBody);
                            String message = errorObj.getString("message");
                            Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                        }
                    } else if (statusCode == 400) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject errorObj = new JSONObject(responseBody);
                            JSONArray messageArray = errorObj.getJSONArray("message");
                            String firstMessage = messageArray.getString(0);
                            Toast.makeText(Login.this, firstMessage, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Login.this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Error en el servidor, intente más tarde", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Error de conexión", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Desactivar el botón mientras se procesa la solicitud
        btn_login.setEnabled(false);
        
        // Mostrar un mensaje de carga
        Toast.makeText(Login.this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();
        
        // Enviar la solicitud
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(req);
    }

    private void handleLoginSuccess(JSONObject response, String email) {
        try {
            // Verificar si la respuesta contiene un token y los datos del usuario
            if (response.has("access_token") && response.has("user")) {
                String token = response.getString("access_token");
                JSONObject userData = response.getJSONObject("user");
                
                // Extraer todos los datos del usuario
                String userId = userData.getString("id");
                String userName = userData.getString("name");
                String userEmail = userData.getString("email");
                String userBirthDate = userData.has("birthDate") ? userData.getString("birthDate") : "";
                
                // Guardar todos los datos en SharedPreferences
                editor.putString("user_id", userId);
                editor.putString("user_name", userName);
                editor.putString("user_email", userEmail);
                editor.putString("user_birth_date", userBirthDate);
                editor.putString("access_token", token);
                editor.commit();
                
                // Mensaje de log para depuración
                System.out.println("Usuario guardado: " + userName + ", ID: " + userId);
                
                // Mostrar mensaje de éxito
                Toast.makeText(Login.this, "¡Bienvenido " + userName + "!", Toast.LENGTH_SHORT).show();
                
                // Navegar a MainActivity
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish(); // Cierra la actividad de login para que no pueda volver atrás
            } else {
                Toast.makeText(Login.this, "Respuesta inválida del servidor", Toast.LENGTH_LONG).show();
                btn_login.setEnabled(true);
            }
        } catch (JSONException e) {
            Toast.makeText(Login.this, "Error al procesar la respuesta: " + e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("Error JSON: " + e.getMessage());
            btn_login.setEnabled(true);
        }
    }
}