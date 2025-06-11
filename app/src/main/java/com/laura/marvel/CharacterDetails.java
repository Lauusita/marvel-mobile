package com.laura.marvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;

public class CharacterDetails extends AppCompatActivity {
    private ImageView img_details;
    private TextView txt_name, txt_desc, txt_comics, txt_series, txt_stories, txt_id_badge;
    private MaterialButton btn_back;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_black));
        setContentView(R.layout.activity_character_details);
        
        // Inicializar la Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        
        // Inicializar el CollapsingToolbarLayout
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        
        // Encontrar las vistas
        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);
        txt_comics = findViewById(R.id.txt_comics);
        txt_series = findViewById(R.id.txt_series);
        txt_stories = findViewById(R.id.txt_stories);
        txt_id_badge = findViewById(R.id.txt_id_badge);
        img_details = findViewById(R.id.img_details);
        btn_back = findViewById(R.id.btn_back);
        
        // Configurar evento del botón volver
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener los datos del intent
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String desc = i.getStringExtra("desc");
        String comics = i.getStringExtra("comics");
        String series = i.getStringExtra("series");
        String stories = i.getStringExtra("stories");
        String imageUrl = i.getStringExtra("img");
        String characterId = i.getStringExtra("id");
        
        // Establecer los datos en las vistas
        collapsingToolbar.setTitle(name);
        txt_name.setText(name);
        
        // Manejar descripción vacía
        if (desc != null && !desc.isEmpty()) {
            txt_desc.setText(desc);
        } else {
            txt_desc.setText("Sin descripción disponible para este personaje.");
        }
        
        // Mostrar estadísticas
        try {
            // Intentar convertir los valores a números para asegurar que sean válidos
            txt_comics.setText(comics != null ? comics : "0");
            txt_series.setText(series != null ? series : "0");
            txt_stories.setText(stories != null ? stories : "0");
            
            // Log para depuración
            System.out.println("Comics: " + comics + ", Series: " + series + ", Stories: " + stories);
        } catch (Exception e) {
            // En caso de error, establecer valores predeterminados
            txt_comics.setText("0");
            txt_series.setText("0");
            txt_stories.setText("0");
            System.out.println("Error al mostrar estadísticas: " + e.getMessage());
        }
        
        // Mostrar ID en badge
        if (characterId != null && !characterId.isEmpty()) {
            txt_id_badge.setText("ID: " + characterId);
            txt_id_badge.setVisibility(View.VISIBLE);
        } else {
            txt_id_badge.setVisibility(View.GONE);
        }
        
        // Cargar imagen con Picasso
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                   .load(imageUrl)
                   .placeholder(R.drawable.marvel_logo)
                   .error(R.drawable.marvel_logo)
                   .into(img_details);
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}