package com.fabianpalacios.superhero;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fabianpalacios.superhero.Services.model.Marvel;
import com.fabianpalacios.superhero.Services.model.MarvelService;
import com.fabianpalacios.superhero.Services.model.Result;
import com.fabianpalacios.superhero.Services.model.Root;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cafsoft.foundation.Data;
import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {
    private MarvelService service = null;
    final String PUBLIC_API_KEY = "502d6509cdb6d800b2374d6f4fb7698d";
    final String TS = "1";
    final String HASH = "b77a90841ab448f028fe4a42926c7197";

    RecyclerView recyclerView;
    private pokemonListAdapter listaPokemonAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewPokemon);
        listaPokemonAdapt = new pokemonListAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapt);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        service = new MarvelService(PUBLIC_API_KEY, TS, HASH);

        this.getSuperHeroInfo();


    }

    public static void Log(Object obj){
        System.out.println(obj);
    }

    public void getSuperHeroInfo() {
            service.requestSuperHeroData((isNetworkError, statusCode, root) -> {
                if (!isNetworkError) {
                    if (statusCode == 200) {
                        //Log.d("Super Hero", String.valueOf(root.getData().getResults().isEmpty()));
                        showSuperHeroInfo(root);
                    } else {
                        Log.d("Super Hero", "Service error");
                    }
                } else {
                    Log.d("Super Hero", "Network error");
                }
            });
    }

    @SuppressLint("SetTextI18n")
    public  void showSuperHeroInfo(Root root) {
        runOnUiThread(() ->{
            ArrayList<Result> listPokemon = (ArrayList<Result>) root.getData().getResults();

            listaPokemonAdapt.adicionarListaPokemon(listPokemon);
            /*for (int i = 0; i < root.getData().getResults().size(); i++) {
                String nameMarvel = String.valueOf(root.getData().getResults().get(i).getName());
                String urlImage ="";
                urlImage = root.getData().getResults().get(i).getThumbnail().getPath()+ "."+root.getData().getResults().get(i).getThumbnail().getExtension();
            }*/
        });
        //String urlImage ="";
        //urlImage = root.getData().getResults().get(0).getThumbnail().getPath()+ "."+root.getData().getResults().get(0).getThumbnail().getExtension();
        //getImage(urlImage);

    }

    public void getImage(String urlImage){
        URL url = null;
        urlImage = urlImage.replace("http", "https");
        try {
            url = new URL(urlImage);
            downloadImage(url);
        }catch (MalformedURLException e){
            Log.d("Error",e.toString());
        }
    }
    public void downloadImage(URL url){
        URLRequest request = new URLRequest(url);
        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            if(error == null && resp.getStatusCode() == 200){
                //showImage(dataToImage(data),imgSuperHero);
            }
        }).resume();
    }

    /*public void showImage(Bitmap image, ImageView imageView){
        runOnUiThread(() -> {
            progress.setVisibility(View.INVISIBLE);
            lblName.setVisibility(View.VISIBLE);
            imgSuperHero.setImageBitmap(image);
            imgSuperHero.setVisibility(View.VISIBLE);
            lblDescribed.setVisibility(View.VISIBLE);
        });
    }*/

    public  Bitmap dataToImage(Data data){
        Bitmap bitmap = BitmapFactory.decodeByteArray(data.toBytes(), 0,data.length());
        return bitmap;
    }

}

