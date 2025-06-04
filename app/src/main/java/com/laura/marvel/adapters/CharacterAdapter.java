package com.laura.marvel.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.laura.marvel.CharacterDetails;
import com.laura.marvel.R;
import com.laura.marvel.classes.Characters;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Characters> datos;
    public Fragment fragment;

    public CharacterAdapter(List<Characters> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Characters character = datos.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView img_details;
        FloatingActionButton btn_favorite;
        TextView txt_desc, txt_id, txt_comics, txt_series, txt_stories;
        View itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_comics = itemView.findViewById(R.id.txt_comics);
            txt_series = itemView.findViewById(R.id.txt_series);
            txt_stories = itemView.findViewById(R.id.txt_stories);
            img_details = itemView.findViewById(R.id.img_pfp);
            btn_favorite = itemView.findViewById(R.id.btn_favorite);
            itemContainer = itemView;
        }

        public void bind(Characters character) {
            txt_name.setText(character.getName());
            txt_desc.setText(character.getDesc());
            Picasso.get().load(character.getImg()).into(img_details);
            
            // Manejo del evento de clic en el botón favorito
            btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Aquí se puede implementar la lógica para marcar como favorito
                    // Por ejemplo, cambiar el icono o guardar en una lista de favoritos
                    btn_favorite.setImageResource(android.R.drawable.btn_star_big_on);
                }
            });
            
            // Hacer que toda la tarjeta sea clickeable para ver detalles
            itemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), CharacterDetails.class);
                    i.putExtra("name", character.getName());
                    i.putExtra("desc", character.getDesc());
                    i.putExtra("img", character.getImg());
                    i.putExtra("comics", character.getComics());
                    i.putExtra("series", character.getSeries());
                    i.putExtra("stories", character.getStories());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
