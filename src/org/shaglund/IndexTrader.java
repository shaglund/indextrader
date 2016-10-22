package org.shaglund;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shaglund on 2016-10-22.
 */
/*
{
    "puzzle": "Write something in any language that figures out at what date and at what price to buy and sell the index in order to get the highest return. Only one buy and one sell transaction is allowed. Should work in O(n)",
    "info" : "[data] contains price data for OMXS30.",
    "submission" : "push your code to a git repo and send us a link at rasmus.holm@modularfinance.se",
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
public class IndexTrader {

    private String puzzle;
    private String info;
    private String submission;
    private List<TraderData> traderData;

    @JsonCreator
    public IndexTrader(@JsonProperty("puzzle") String puzzle,
                       @JsonProperty("info") String info,
                       @JsonProperty("submission") String submission,
                       @JsonProperty("data") List<TraderData> traderData) {

        this.puzzle = puzzle;
        this.info = info;
        this.submission = submission;
        this.traderData = traderData;
    }

    public String getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public List<TraderData> getTraderData() {
        return traderData;
    }

    public void setTraderData(List<TraderData> traderData) {
        this.traderData = traderData;
    }
}
