package org.shaglund;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by shaglund on 2016-10-23.
 *
 * Class to find best index trade in list of quotes sorted by date.
 * Idea is to traverse list and calculate profit between current and lowest quotes.
 */
public class BestProfitCalculator {
    private List<Quote> quotes;

    public BestProfitCalculator(List<Quote> quotes) {
        this.quotes = quotes;
    }

    /**
     * Get lowest quotes of a and b
     */
    private Quote minQuote(Quote a, Quote b) {
        return a.getLow() < b.getLow() ? a : b;
    }

    private void sortQuotes(List<Quote> quotes) {
        quotes.sort((o1, o2) -> o1.getDate() < o2.getDate() ? -1 : o1.getDate() == o2.getDate() ? 0 : 1);
    }

    public Pair<Quote, Quote> calcBestProfit() {
        if(quotes == null || quotes.size() == 0) {
            return null;
        }

        // Traverse list in reverse, i.e start from oldest quotes
        ListIterator<Quote> iterator = quotes.listIterator(quotes.size()-1);
        Quote buy = iterator.previous();
        Quote min = buy;
        Quote sell = buy;
        float maxProfit = 0;
        while(iterator.hasPrevious()) {
            Quote curr = iterator.previous();
            // Keep reference to lowest quotes in list
            min = minQuote(min, curr);
            float profit = curr.getHigh() - min.getLow();
            if (profit > maxProfit) {
                maxProfit = profit;
                // Current max profit found, keep references to quotes
                buy = min;
                sell = curr;
            }

        }
        // Done traversing, best trade is between buy and sell quotes
        return new Pair<>(buy, sell);
    }
}
