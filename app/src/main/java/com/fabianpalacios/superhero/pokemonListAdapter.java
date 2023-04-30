package com.fabianpalacios.superhero;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fabianpalacios.superhero.Services.model.Result;

import java.util.ArrayList;

public class pokemonListAdapter extends RecyclerView.Adapter<pokemonListAdapter.ViewHolder> {

    private ArrayList<Result> datos;
    Context context;

    public pokemonListAdapter(Context context){
        this.context = context;
        datos = new ArrayList<>();
    }


    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder( @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_pokemon,viewGroup,false);
        return new ViewHolder(vista);
    }

    public static void Log(Object obj){
        System.out.println(obj);
    }

    @Override
    public void onBindViewHolder( @androidx.annotation.NonNull ViewHolder viewHolder, int i) {
        Result pokemon = datos.get(i);

        viewHolder.nombrePokemon.setText(pokemon.getName());
        Log("urle imagen");
        String urlImage = pokemon.getThumbnail().getPath()+ "."+pokemon.getThumbnail().getExtension();
        Log(urlImage);

        Glide.with(context).load(urlImage).into(viewHolder.fotoPokemon);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void adicionarListaPokemon(ArrayList<Result> listPokemon) {
        datos.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoPokemon;
        private TextView nombrePokemon;

        public ViewHolder(View itemView){
            super(itemView);

            fotoPokemon = itemView.findViewById(R.id.fotoPokemon);
            nombrePokemon = itemView.findViewById(R.id.nombrePokemon);
        }
    }

}
