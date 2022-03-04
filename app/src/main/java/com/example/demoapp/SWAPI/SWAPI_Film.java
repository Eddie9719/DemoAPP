package com.example.demoapp.SWAPI;

import com.example.demoapp.Models.SWAPI_Film_Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SWAPI_Film {
    @GET("films/")
    Call<SWAPI_Film_Response> obtenerListaPeliculasSWAPI();
}
