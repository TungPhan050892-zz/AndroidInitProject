package com.example.phant.rthchallenge.presentation;

import com.example.phant.rthchallenge.datalayer.model.Data;
import com.example.phant.rthchallenge.domain.GetGoldPriceDataUseCase;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by phant on 5/27/2017.
 */
public class GoldPricePresenter implements Presenter{

    private GetGoldPriceDataUseCase getGoldPriceDataUseCase;
    private GoldPriceView goldPriceView;

    @Inject
    public GoldPricePresenter(GetGoldPriceDataUseCase getGoldPriceDataUseCase) {
        this.getGoldPriceDataUseCase = getGoldPriceDataUseCase;
    }

    public void setView(GoldPriceView goldPriceView){
        this.goldPriceView = goldPriceView;
    }

    public void getGoldPriceData() {
        goldPriceView.showLoading();
        goldPriceView.hideRetryBtn();
        getGoldPriceDataUseCase.execute(new GetGoldPriceData(), null);
    }

    @Override
    public void onDestroy() {
        getGoldPriceDataUseCase.dispose();
    }

    private final class GetGoldPriceData extends DisposableObserver<ArrayList<Data>> {

        @Override
        public void onComplete() {
            goldPriceView.hideLoading();
        }

        @Override
        public void onNext(@NonNull ArrayList<Data> data) {
            goldPriceView.finishGetGoldPrice(data);
        }

        @Override
        public void onError(Throwable e) {
            goldPriceView.showRetryBtn();
            goldPriceView.errorWhenGetGoldPrice(e.getMessage());
        }
    }
}
