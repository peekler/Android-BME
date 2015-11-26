package hu.bme.aut.moneyconverter.moneyconverterretrofit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hu.bme.aut.moneyconverter.moneyconverterretrofit.data.MoneyResult;
import hu.bme.aut.moneyconverter.moneyconverterretrofit.data.Rates;
import hu.bme.aut.moneyconverter.moneyconverterretrofit.network.CurrencyExchangeService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.fixer.io/")
                .build();

        CurrencyExchangeService service = restAdapter.create(
                CurrencyExchangeService.class);

        service.getRatesToUsd(
                new Callback<MoneyResult>() {
            @Override
            public void success(MoneyResult moneyResult,
                                Response response) {
                Toast.makeText(MainActivity.this, "" +
                        moneyResult.getRates().getHUF(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this,
                        "error: "+error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
