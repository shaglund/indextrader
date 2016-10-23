package org.shaglund;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by shaglund on 2016-10-23.
 *
 * Class to find median using min/max heap algorithm
 */
public class MedianFinder {
    private final PriorityQueue<Double> minHeap;
    private final PriorityQueue<Double> maxHeap;

    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addValue(double value) {
        maxHeap.offer(value);
        minHeap.offer(maxHeap.poll());
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double getMedian() {
        if (maxHeap.size() != minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek())/2;
        }

    }
}
