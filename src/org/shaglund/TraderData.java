package org.shaglund;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shaglund on 2016-10-22.
 */
/*
    "data": [
        {
            "quote_date": 20161018,
            "paper": "OMXS30",
            "exch": "Stockholm",
            "open": 1441.43,
            "high": 1459.94,
            "low": 1441.43,
            "close": 1458.63,
            "volume": 0,
            "value": 0
        },
 */
public class TraderData {

    private long date;
    private String paper;
    private String exchange;
    private float open;
    private float close;
    private float low;
    private float high;
    private int volume;
    private int value;

    @JsonCreator
    public TraderData(@JsonProperty("quote_date") long quoteDate,
                    @JsonProperty("paper") String paper,
                    @JsonProperty("exch") String exchange,
                    @JsonProperty("open") float open,
                    @JsonProperty("high") float high,
                    @JsonProperty("low") float low,
                    @JsonProperty("close") float close,
                    @JsonProperty("volume") int volume,
                    @JsonProperty("value") int value) {

        this.date = quoteDate;
        this.paper = paper;
        this.exchange = exchange;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.value = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
