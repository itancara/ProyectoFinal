package com.example.ivan.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ivan.proyectofinal.adapter.ComidasAdapter;
import com.example.ivan.proyectofinal.model.Comida;
import com.example.ivan.proyectofinal.model.Respuesta;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComidasActivity extends AppCompatActivity implements ComidasAdapter.onComidaSelectedListener{

    private static final Gson gson = new Gson();

    private RecyclerView comidasRecyclerView;
    private ComidasAdapter comidasAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_comidas);

        comidasRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_comidas);
        comidasRecyclerView.setHasFixedSize(true);
        comidasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        comidasAdapter = new ComidasAdapter(this, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        comidasRecyclerView.setLayoutManager(layoutManager);
        comidasRecyclerView.setAdapter(comidasAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        swipeRefreshLayout.setRefreshing(true);
        cargarDatos();
    }

    private void cargarDatos() {
        DatosF2FService service = ServiceGenerator.createService(DatosF2FService.class);
        Call<Respuesta> call= service.recetas("2c780541ba163193c556b5c1b12ea320");
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    ArrayList<Comida> listita = response.body().getRecipes();
                    comidasAdapter.setDataset(listita);
                }
            }
            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onComidaSelectedListener(Comida miinterface) {
        Intent intent = new Intent(this, DetalleAtivity.class);
        intent.putExtra("comidita", gson.toJson(miinterface));
        startActivity(intent);

    }
}
