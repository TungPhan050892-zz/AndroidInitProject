package com.example.phant.rthchallenge.presentation;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.phant.rthchallenge.App;
import com.example.phant.rthchallenge.R;
import com.example.phant.rthchallenge.datalayer.model.Data;
import com.example.phant.rthchallenge.di.component.AppComponent;
import com.example.phant.rthchallenge.presentation.broadcastreceiver.NetworkChangeReceiver;
import com.example.phant.rthchallenge.presentation.charviewlib.ValueLabelAdapter;
import com.example.phant.rthchallenge.presentation.charviewlib.ChartView;
import com.example.phant.rthchallenge.presentation.charviewlib.LinearSeries;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoldPriceActivity extends AppCompatActivity implements GoldPriceView {

    @Inject
    GoldPricePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnRetry)
    AppCompatButton btnRetry;
    @BindView(R.id.recyclerView)
    RecyclerView goldPriceRView;
    @BindView(R.id.chartView)
    ChartView chartView;
    NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInjector(App.getAppComponent(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        ButterKnife.bind(this);
        initView();
        presenter.setView(this);
        if (savedInstanceState == null) {
            initData();
        }
        networkChangeReceiver = new NetworkChangeReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filters = new IntentFilter();
        filters.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, filters);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeReceiver);
    }

    private void initInjector(AppComponent appComponent) {
        appComponent.inject(this);
    }

    private void initView() {
        initToolbar();
        initDrawerLayout();
        initChartView();
        btnRetry.setOnClickListener(v -> {
            initData();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        goldPriceRView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        if (presenter != null) {
            presenter.getGoldPriceData();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.blue_sky));
        toolbar.setTitle(getResources().getString(R.string.title_gold_price_activity));
        toolbar.setTitleMarginStart((int) getResources().getDimension(R.dimen.margin_normal));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetryBtn() {
        btnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryBtn() {
        btnRetry.setVisibility(View.GONE);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.info_icon);
        toggle.setToolbarNavigationClickListener(view -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initChartView() {
        // Create the data points
        LinearSeries series = new LinearSeries();
        series.setLineColor(0xFF0099CC);
        series.setLineWidth(10);
        for (double i = (2d * Math.PI); i >= 0; i -= 0.1d) {
            series.addPoint(new LinearSeries.LinearPoint(i, i * Math.cos(i)));
        }
        // Add chart view data
        chartView.addSeries(series);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void finishGetGoldPrice(ArrayList<Data> data) {
        GoldPriceRViewAdapter adapter = new GoldPriceRViewAdapter(this, data);
        goldPriceRView.setAdapter(adapter);
    }

    @Override
    public void errorWhenGetGoldPrice(String errorMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errorMessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
