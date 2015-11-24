package hu.bme.aut.moneyconverter.moneyconverterretrofit.data;

/**
 * Created by Peter on 2015.05.19..
 */

import java.util.Map;


import java.util.HashMap;
import java.util.Map;

public class MoneyResult {

    private String base;
    private String date;
    private Rates rates;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The rates
     */
    public Rates getRates() {
        return rates;
    }

    /**
     * @param rates The rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
