package com.laura.marvel.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.laura.marvel.Login;
import com.laura.marvel.R;
import com.laura.marvel.adapters.CharacterAdapter;
import com.laura.marvel.classes.Characters;
import com.laura.marvel.classes.Comics;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog.Builder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComicFragment extends Fragment {
    Spinner spinner_comics;
    List<Comics> comics = new ArrayList<>();
    Button btn_solicitar;
     Builder builder;

    public ComicFragment() {
        // Required empty public constructor
    }


    public static ComicFragment newInstance(String param1, String param2) {
        ComicFragment fragment = new ComicFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comic, container, false);

        spinner_comics = view.findViewById(R.id.spinner_comics);
        btn_solicitar = view.findViewById(R.id.btn_solicitar);
        cargarInformacion(spinner_comics);

        btn_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comics selectedComic = (Comics) spinner_comics.getSelectedItem();

                LayoutInflater inflater = LayoutInflater.from(requireContext());
                View dialogView = inflater.inflate(R.layout.comic_required, null);

                ImageView imageView = dialogView.findViewById(R.id.comic_image);
                TextView titleView = dialogView.findViewById(R.id.comic_title);

                titleView.setText(selectedComic.getTitle());

                Picasso.get().load(selectedComic.getImg().replace("http://", "https://")).into(imageView);

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Comic solicitado ");
                builder.setView(dialogView);

                builder.setNegativeButton("Regresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void cargarInformacion(Spinner sc) {
        String url = "https://gateway.marvel.com/v1/public/comics?apikey=abe596e378d15df42bf47df38ed72c10&hash=b3ce0fed375fa2b9fbb027fdf8962b56&ts=1749138215113";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        procesarRespuesta(new JSONObject(response), sc);
                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Error al procesar JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(requireContext(), "Error en el servidor", Toast.LENGTH_SHORT).show()
        );

        RequestQueue req = Volley.newRequestQueue(requireContext());
        req.add(request);
    }

    private void procesarRespuesta(JSONObject response, Spinner s) {
        try {
            JSONArray results = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                int id = obj.getInt("id");
                String title = obj.getString("title");
                String path = obj.getJSONObject("thumbnail").getString("path");
                String extension = obj.getJSONObject("thumbnail").getString("extension");

                String img = path + "." + extension;

                comics.add(new Comics(id, title, img));
            }


            ArrayAdapter<Comics> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, comics);
            s.setAdapter(arrayAdapter);
        } catch (JSONException je) {
            Toast.makeText(requireContext(), "Error en formato de respuesta", Toast.LENGTH_LONG).show();
        }
    }
}