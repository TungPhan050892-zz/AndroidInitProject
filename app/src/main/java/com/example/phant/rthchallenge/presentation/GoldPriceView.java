package com.example.phant.rthchallenge.presentation;

import com.example.phant.rthchallenge.datalayer.model.Data;

import java.util.ArrayList;

/**
 * Created by phant on 5/27/2017.
 */

public interface GoldPriceView {

    void hideLoading();

    void showLoading();

    void showRetryBtn();

    void hideRetryBtn();

    void finishGetGoldPrice(ArrayList<Data> data);

    void errorWhenGetGoldPrice(String errorMessage);

}
