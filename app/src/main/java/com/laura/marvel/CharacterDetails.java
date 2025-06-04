package com.laura.marvel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharacterDetails extends AppCompatActivity {
    CircleImageView img_details;
    TextView txt_name, txt_desc, txt_comics, txt_series, txt_stories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main_bg));
        setContentView(R.layout.activity_character_details);

        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);
        txt_comics = findViewById(R.id.txt_comics);
        txt_series = findViewById(R.id.txt_series);
        txt_stories = findViewById(R.id.txt_stories);

        img_details = findViewById(R.id.img_details);

        Intent i = getIntent();
        txt_name.setText(i.getStringExtra("name"));
        txt_desc.setText(i.getStringExtra("desc"));
        txt_comics.setText(i.getStringExtra("comics"));
        txt_series.setText(i.getStringExtra("series"));
        txt_stories.setText(i.getStringExtra("stories"));
        Picasso.get().load(i.getStringExtra("img")).into(img_details);
    }
}