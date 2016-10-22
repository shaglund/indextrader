package org.shaglund;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class Main {

    private static final String PROXY = null;
    private static final String GET_URL = "http://www.modularfinance.se/api/puzzles/index-trader.json";

    public static void main(String[] args) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestBuilder requestBuilder = new RequestBuilder();
        if(PROXY != null) {
            String[] proxy = PROXY.split(":");
            requestBuilder.setProxyServer(new ProxyServer(proxy[0], Integer.parseInt(proxy[1])));
        }

        Request request = requestBuilder.setMethod("GET").setUrl(GET_URL).build();

        ListenableFuture<Response> response = client.executeRequest(request);
        ObjectMapper om = new ObjectMapper();
        try {
            IndexTrader it = om.readValue(response.get().getResponseBodyAsStream(), IndexTrader.class);
            client.close();

            Pair<TraderData, TraderData> bestProfit = new BestProfitCalculator(it.getTraderData()).calcBestProfit();
            if (bestProfit != null) {
                System.out.print("BUY:  ");
                System.out.println("" + bestProfit.getFirst().getDate() + " " + bestProfit.getFirst().getLow());
                System.out.print("SELL: ");
                System.out.println("" + bestProfit.getSecond().getDate() + " " + bestProfit.getSecond().getHigh());
            } else {
                System.out.println("Can't find best trade in sequence");
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            System.out.println("Exception");
            e.printStackTrace();
        }

    }
}
