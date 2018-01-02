package com.example.phant.rthchallenge.di.component;

import com.example.phant.rthchallenge.di.module.NetworkModule;
import com.example.phant.rthchallenge.presentation.GoldPriceActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tung phan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {

    void inject(GoldPriceActivity goldPriceActivity);

}
