package org.shaglund;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Main {

    private static final String PROXY = null;
    private static final String GET_URL = "http://www.modularfinance.se/api/puzzles/index-trader.json";
    private static final String FX_URL = "http://fx.modfin.se/2015-01-01/2015-12-31?base=usd&symbols=sek";

    private static ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        return om;
    }

    private static void doCurrencyAggregatorPuzzle(AsyncHttpClient client, RequestBuilder requestBuilder) {
        Request request = requestBuilder.setMethod("GET").setUrl(FX_URL).build();

        ListenableFuture<Response> response = client.executeRequest(request);
        try {
            List<FXRate> fxRates = getObjectMapper().readValue(response.get().getResponseBodyAsStream(),
                    new TypeReference<List<FXRate>>(){});
            MedianFinder medianFinder = new MedianFinder();
            fxRates.forEach(rate -> medianFinder.addValue(rate.getRates().getSek()));
            System.out.println("Median SEK/USD rate for 2015 is " + medianFinder.getMedian());
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    private static void doIndexTraderPuzzle(AsyncHttpClient client, RequestBuilder requestBuilder) {
        Request request = requestBuilder.setMethod("GET").setUrl(GET_URL).build();

        ListenableFuture<Response> response = client.executeRequest(request);
        ObjectMapper om = new ObjectMapper();
        try {
            IndexTrader it = om.readValue(response.get().getResponseBodyAsStream(), IndexTrader.class);

            Pair<Quote, Quote> bestProfit = new BestProfitCalculator(it.getQuotes()).calcBestProfit();
            if (bestProfit != null) {
                System.out.print("BUY:  ");
                System.out.println("" + bestProfit.getFirst().getDate() + " " + bestProfit.getFirst().getLow());
                System.out.print("SELL: ");
                System.out.println("" + bestProfit.getSecond().getDate() + " " + bestProfit.getSecond().getHigh());
            } else {
                System.out.println("Unable to find best trade");
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestBuilder requestBuilder = new RequestBuilder();
        if(PROXY != null) {
            String[] proxy = PROXY.split(":");
            requestBuilder.setProxyServer(new ProxyServer(proxy[0], Integer.parseInt(proxy[1])));
        }

        //doIndexTraderPuzzle(client, requestBuilder);
        doCurrencyAggregatorPuzzle(client, requestBuilder);

        client.close();

    }
}
