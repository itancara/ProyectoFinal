package com.example.ivan.proyectofinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.proyectofinal.model.Comida;
import com.example.ivan.proyectofinal.model.RespuestaReceta;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAtivity  extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private TextView textView2;
    private ImageView imageView;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_comida);

        textView2=(TextView)findViewById(R.id.textView2);
        imageView=(ImageView)findViewById(R.id.imageView2);
        textView3=(TextView)findViewById(R.id.textView3);

    }
    @Override
    protected void onStart() {
        super.onStart();
        cargarDatos();

    }
    private void cargarDatos() {
        if (getIntent().getExtras() != null) {
            String comidita = getIntent().getStringExtra("comidita");

            Comida comida = gson.fromJson(comidita, Comida.class);

            textView2.setText(comida.getTitle()+" "+comida.getRecipeId());
            Glide.with(this).load(comida.getImageUrl()).into(imageView);



            DatosF2FService service = ServiceGenerator.createService(DatosF2FService.class);
            Call<RespuestaReceta> call = service.recetas("7b790470dffb036404df84755ffa8405", comida.getRecipeId());
            call.enqueue(new Callback<RespuestaReceta>() {

                @Override
                public void onResponse(Call<RespuestaReceta> call, Response<RespuestaReceta> response) {
                    //Toast.makeText(DetalleAtivity.this, ""+ new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                    Log.d("este es el --nuevo", new Gson().toJson(response.body()));

                    if(response.isSuccessful()){
                        textView3.setText(response.body().getRecipe().getIngredients().toString());
                    }else{

                    }

                }

                @Override
                public void onFailure(Call<RespuestaReceta> call, Throwable t) {
                    //aqui viene si la peticion fue ...

                }
            });

        }
    }

}


