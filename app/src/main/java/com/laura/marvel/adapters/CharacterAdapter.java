package com.laura.marvel.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.laura.marvel.CharacterDetails;
import com.laura.marvel.R;
import com.laura.marvel.classes.Characters;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public class ViewHolder extends  RecyclerView.ViewHolder {
        Button btn_name;
        ImageView img_details;
        TextView txt_desc, txt_id, txt_comics, txt_series, txt_stories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_name = itemView.findViewById(R.id.txt_name);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_comics = itemView.findViewById(R.id.item_comic);
            txt_series = itemView.findViewById(R.id.txt_series);
            txt_stories = itemView.findViewById(R.id.txt_stories);
            img_details = itemView.findViewById(R.id.img_pfp);
        }

        public void bind(Characters character) {
            btn_name.setText(character.getName());
            txt_desc.setText(character.getDesc());
            Picasso.get().load(character.getImg()).into(img_details);
            System.out.println("----" + character.getImg());
//            txt_comics.setText(character.getComics());
//            txt_series.setText(character.getSeries());
//            txt_stories.setText(character.getStories());

            btn_name.setOnClickListener(new View.OnClickListener() {
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
