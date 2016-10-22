package org.shaglund;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Main {

    private static final String PROXY = null;
    private static final String GET_URL = "http://www.modularfinance.se/api/puzzles/index-trader.json";

    private static TraderData minTD(TraderData a, TraderData b) {
        return a.getLow() < b.getLow() ? a : b;
    }

    private static void sortTD(List<TraderData> traderData) {
        traderData.sort((o1, o2) -> o1.getDate() > o2.getDate() ? -1 : o1.getDate() == o2.getDate() ? 0 : 1);
    }

    private static Map.Entry<TraderData, TraderData> calcBestProfit(List<TraderData> traderData) {
        if(traderData.size() == 0) {
            return null;
        }
        // sortTD(traderData);
        int idx = traderData.size()-1;
        TraderData buy = traderData.get(idx);
        TraderData sell = buy;
        float maxProfit = 0;

        while(--idx > 0) {
            TraderData curr = traderData.get(idx);
            buy = minTD(buy, curr);
            float profit = curr.getHigh() - buy.getLow();
            if (profit > maxProfit) {
                maxProfit = profit;
                sell = curr;
            }

        }
        return new AbstractMap.SimpleImmutableEntry<>(buy, sell);
    }

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

            Map.Entry<TraderData, TraderData> bestProfit = calcBestProfit(it.getTraderData());
            System.out.print("BUY:  ");
            System.out.println("" + bestProfit.getKey().getDate() + " " + bestProfit.getKey().getLow());
            System.out.print("SELL: ");
            System.out.println("" + bestProfit.getValue().getDate() + " " + bestProfit.getValue().getLow());
        } catch (ExecutionException | InterruptedException | IOException e) {
            System.out.println("Exception");
            e.printStackTrace();
        }

    }
}
