package com.xtorres.restpets.services;

import com.xtorres.restpets.models.Pet;
import com.xtorres.restpets.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://10.0.2.2:8080";

    @GET("/pets")
    Call<List<Pet>> getPet();

    @FormUrlEncoded
    @POST("/api/mascotas")
    Call<Pet> createPet(@Part("nombre")RequestBody nombre,
                        @Part("raza") RequestBody raza,
                        @Part("edad") RequestBody edad,
                        @Part MultipartBody.Part imagen);

    @DELETE("/api/productos/{id}")
    Call<String> destroyProducto(@Path("id") Long id);

    @GET("/api/productos/{id}")
    Call<Pet> showProducto(@Path("id") Long id);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<User> login(@Field("username") String username,
                     @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/usuario")
    Call<User> createUser(@Part("nombre") String nombre,
                          @Part("correo") String correo,
                          @Part("contraseña") String contraseña);

    @GET("/api/profile")
    Call<User> getProfile();

}
