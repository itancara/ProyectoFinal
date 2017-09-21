package com.example.ivan.proyectofinal;

import com.example.ivan.proyectofinal.model.Respuesta;
import com.example.ivan.proyectofinal.model.RespuestaReceta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DatosF2FService {

    @GET("search")
    Call<Respuesta> recetas(@Query("key") String key);

    @GET("get")
    Call<RespuestaReceta> recetas(@Query("key") String key, @Query("rId") String rId);
}
