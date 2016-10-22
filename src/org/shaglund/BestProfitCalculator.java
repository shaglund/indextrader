package org.shaglund;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by shaglund on 2016-10-23.
 */
public class BestProfitCalculator {
    private List<TraderData> traderData;

    public BestProfitCalculator(List<TraderData> data) {
        this.traderData = data;
    }

    private TraderData minTD(TraderData a, TraderData b) {
        return a.getLow() < b.getLow() ? a : b;
    }

    private void sortTD(List<TraderData> traderData) {
        traderData.sort((o1, o2) -> o1.getDate() < o2.getDate() ? -1 : o1.getDate() == o2.getDate() ? 0 : 1);
    }

    public Pair<TraderData, TraderData> calcBestProfit() {
        if(traderData == null || traderData.size() == 0) {
            return null;
        }

        ListIterator<TraderData> iterator = traderData.listIterator(traderData.size()-1);
        TraderData buy = iterator.previous();
        TraderData min = buy;
        TraderData sell = buy;
        float maxProfit = 0;
        while(iterator.hasPrevious()) {
            TraderData curr = iterator.previous();
            min = minTD(min, curr);
            float profit = curr.getHigh() - min.getLow();
            if (profit > maxProfit) {
                maxProfit = profit;
                buy = min;
                sell = curr;
            }

        }
        return new Pair<>(buy, sell);
    }
}
