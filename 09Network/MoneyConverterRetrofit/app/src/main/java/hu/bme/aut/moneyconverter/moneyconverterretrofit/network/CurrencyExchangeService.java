package hu.bme.aut.moneyconverter.moneyconverterretrofit.network;

import hu.bme.aut.moneyconverter.moneyconverterretrofit.data.MoneyResult;
import hu.bme.aut.moneyconverter.moneyconverterretrofit.data.Rates;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Peter on 2015.05.19..
 */
public interface CurrencyExchangeService {
    @GET("/latest?base=USD")
    void getRatesToUsd(Callback<MoneyResult> callback);

}
