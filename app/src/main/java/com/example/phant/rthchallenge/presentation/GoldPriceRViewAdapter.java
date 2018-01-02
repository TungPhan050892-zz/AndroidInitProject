package com.example.phant.rthchallenge.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phant.rthchallenge.R;
import com.example.phant.rthchallenge.datalayer.model.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Created by phant on 5/27/2017.
 */

public class GoldPriceRViewAdapter extends RecyclerView.Adapter<GoldPriceRViewAdapter.ViewHolder> {

    private final String TAG = GoldPriceRViewAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<Data> goldPriceData;

    public GoldPriceRViewAdapter(Context context, ArrayList<Data> goldPriceData) {
        this.context = context;
        this.goldPriceData = goldPriceData;
    }

    public void setData(ArrayList<Data> goldPriceData) {
        this.goldPriceData = goldPriceData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gold_price_rview_item
                , viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Data data = goldPriceData.get(i);
        viewHolder.time.setText(convertDateTime(data.getDate()));
        viewHolder.price.setText(convertCurrency(data.getAmount()));
    }

    /**
     * convert date time format dd/mm/yy to dd month yyyy
     */
    private String convertDateTime(String input) {
        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = null;
        try {
            date = originalFormat.parse(input);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return targetFormat.format(date);
    }

    /**
     * convert float currency to $ currency
     */
    private String convertCurrency(String input) {
        StringBuilder outPut = new StringBuilder("$");
        outPut.append(input.substring(0, input.length() - 2));
        return outPut.toString();
    }

    @Override
    public int getItemCount() {
        return goldPriceData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView time, price;

        public ViewHolder(View view) {
            super(view);
            time = ButterKnife.findById(view, R.id.time);
            price = ButterKnife.findById(view, R.id.price);
        }
    }
}
