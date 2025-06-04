package com.laura.marvel.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.laura.marvel.Login;
import com.laura.marvel.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    // Variables para el SharedPreferences
    private static final String dataUserCache = "user";
    private static final int privateMode = Context.MODE_PRIVATE;
    private SharedPreferences sharedPreferences;
    
    // Elementos de la UI
    private TextView txtNameValue, txtEmailValue, txtBirthdateValue;
    private Button btnLogout;
    private androidx.appcompat.widget.SwitchCompat switchNotifications, switchTheme;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        
        // Inicializar SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(dataUserCache, privateMode);
        
        // Referencias UI
        txtNameValue = view.findViewById(R.id.txt_name_value);
        txtEmailValue = view.findViewById(R.id.txt_email_value);
        txtBirthdateValue = view.findViewById(R.id.txt_birthdate_value);
        btnLogout = view.findViewById(R.id.btn_logout);
        switchNotifications = view.findViewById(R.id.switch_notifications);
        switchTheme = view.findViewById(R.id.switch_theme);
        
        // Cargar datos de usuario
        loadUserData();
        
        // Configurar switches con valores guardados
        setupSwitches();
        
        // Configurar botón de logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        
        return view;
    }
    
    /**
     * Carga los datos del usuario desde SharedPreferences y los muestra en la UI
     */
    private void loadUserData() {
        String name = sharedPreferences.getString("name", "Usuario");
        String email = sharedPreferences.getString("mail", "correo@ejemplo.com");
        String birthdate = sharedPreferences.getString("birthdate", "01/01/2000");
        
        txtNameValue.setText(name);
        txtEmailValue.setText(email);
        txtBirthdateValue.setText(birthdate);
    }
    
    /**
     * Configura los switches con los valores guardados y establece sus listeners
     */
    private void setupSwitches() {
        // Recuperar valores guardados de los switches (predeterminado: true)
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        boolean darkThemeEnabled = sharedPreferences.getBoolean("dark_theme_enabled", true);
        
        // Establecer estados iniciales
        switchNotifications.setChecked(notificationsEnabled);
        switchTheme.setChecked(darkThemeEnabled);
        
        // Configurar listeners
        switchNotifications.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                // Guardar la preferencia de notificaciones
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notifications_enabled", isChecked);
                editor.apply();
                
                // Aquí se podría implementar la lógica para habilitar/deshabilitar notificaciones
                Toast.makeText(getActivity(), 
                        isChecked ? R.string.notifications_enabled : R.string.notifications_disabled, 
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        switchTheme.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                // Guardar la preferencia de tema
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("dark_theme_enabled", isChecked);
                editor.apply();
                
                // Aquí se podría implementar la lógica para cambiar el tema
                Toast.makeText(getActivity(), 
                        isChecked ? R.string.dark_theme_enabled : R.string.light_theme_enabled, 
                        Toast.LENGTH_SHORT).show();
                
                // Nota: El cambio real de tema requeriría una implementación más compleja,
                // posiblemente reiniciando la actividad o aplicando estilos dinámicamente
            }
        });
    }
    
    /**
     * Realiza el proceso de cierre de sesión
     */
    private void logout() {
        // Mostrar diálogo de confirmación antes de cerrar sesión
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.logout_title);
        builder.setMessage(R.string.logout_message);
        
        // Botón de confirmación
        builder.setPositiveButton(R.string.logout_confirm, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                // Eliminar los datos guardados en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                
                // Mostrar mensaje
                Toast.makeText(getActivity(), R.string.logout_success, Toast.LENGTH_SHORT).show();
                
                // Redirigir a la pantalla de login
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        
        // Botón de cancelación
        builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                // Simplemente cerrar el diálogo
                dialog.dismiss();
            }
        });
        
        // Mostrar el diálogo
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}