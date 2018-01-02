/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.phant.rthchallenge.domain;

import com.example.phant.rthchallenge.datalayer.NetworkService;
import com.example.phant.rthchallenge.datalayer.model.Data;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Data}.
 */
public class GetGoldPriceDataUseCase extends UseCase<ArrayList<Data>, Void> {

    private final NetworkService networkService;

    @Inject
    GetGoldPriceDataUseCase(NetworkService networkService) {
        super();
        this.networkService = networkService;
    }

    @Override
    Observable<ArrayList<Data>> buildUseCaseObservable(Void params) {
        return networkService.getGoldPriceData().map(ArrayList::new);
    }
}
