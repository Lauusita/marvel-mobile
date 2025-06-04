package com.laura.marvel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.laura.marvel.R;
import com.laura.marvel.adapters.CharacterAdapter;
import com.laura.marvel.classes.Characters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    List<Characters> datos = new ArrayList<Characters>();
    RecyclerView rcv_users;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcv_users = view.findViewById(R.id.rcv_users);

        cargarInformacion(rcv_users);
        return view;
    }

    private void cargarInformacion(RecyclerView rv) {
        String url = "https://gateway.marvel.com/v1/public/characters?apikey=abe596e378d15df42bf47df38ed72c10&hash=1a97c508727c3f3f762f62c4fcb4bdc7&ts=1748893004866";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        procesarRespuesta(new JSONObject(response), rv);
                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Error al procesar JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(requireContext(), "Error en el servidor", Toast.LENGTH_SHORT).show()
        );

        RequestQueue req = Volley.newRequestQueue(requireContext());
        req.add(request);
    }

    private void procesarRespuesta(JSONObject response, RecyclerView rv) {
        try {
            JSONArray results = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name").trim();
                String path = obj.getJSONObject("thumbnail").getString("path");
                String extension = obj.getJSONObject("thumbnail").getString("extension");
                String desc = obj.getString("description");
                int comics = obj.getJSONObject("comics").getInt("available");
                int series = obj.getJSONObject("series").getInt("available");
                int stories = obj.getJSONObject("stories").getInt("available");

                String img = path + "." + extension;
                Characters character = new Characters(comics, desc, id, img.replace("http://", "https://"), name, series, stories);
                datos.add(character);

                rv.setLayoutManager(new LinearLayoutManager(requireContext()));
                rv.setAdapter(new CharacterAdapter(datos));
            }
        } catch (JSONException je) {
            Toast.makeText(requireContext(), "Error en formato de respuesta", Toast.LENGTH_LONG).show();
        }
    }
}