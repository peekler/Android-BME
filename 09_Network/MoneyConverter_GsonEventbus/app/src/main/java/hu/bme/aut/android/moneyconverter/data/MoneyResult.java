package hu.bme.aut.android.moneyconverter.data;

import com.google.gson.annotations.Expose;

/**
 * Created by Peter on 2015.04.20..
 */
public class MoneyResult {

    @Expose
    private String base;
    @Expose
    private String date;
    @Expose
    private Rates rates;

    /**
     *
     * @return
     * The base
     */
    public String getBase() {
        return base;
    }

    /**
     *
     * @param base
     * The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The rates
     */
    public Rates getRates() {
        return rates;
    }

    /**
     *
     * @param rates
     * The rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

}