package com.example.phant.rthchallenge.datalayer;


import com.example.phant.rthchallenge.datalayer.model.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by tung phan on 04-18-2017.
 * implement call to the interface here.
 */
public interface NetworkService {

    String BASE_URL = "https://rth-recruitment.herokuapp.com/api/";

    @GET("prices/chart_data")
    @Headers("X-App-Token:76524a53ee60602ac3528f38")
    Observable<List<Data>> getGoldPriceData();
}
