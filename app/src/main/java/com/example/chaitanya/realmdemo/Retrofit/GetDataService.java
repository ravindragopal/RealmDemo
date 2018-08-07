package com.example.chaitanya.realmdemo.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 7/8/18,1:04 PM.
 * For : ISS 24/7, Pune.
 */
public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
