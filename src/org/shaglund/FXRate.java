package org.shaglund;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shaglund on 2016-10-23.
 */
/*
    "base": "USD",
    "date": "2015-01-02",
    "rates": {
      "SEK": 7.863655235406461
    }
 */
public class FXRate {
    private String base;
    private String date;
    private Rates rates;

    @JsonCreator
    public FXRate(@JsonProperty("base") String base,
                  @JsonProperty("date") String date,
                  @JsonProperty("rates") Rates rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}
