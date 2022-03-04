package com.example.demoapp.SWAPI;

import com.example.demoapp.Models.SWAPI_Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SWAPI_Service {
 @GET("people/")
 Call<SWAPI_Response> obtenerListaPersonasSWAPI();


}
