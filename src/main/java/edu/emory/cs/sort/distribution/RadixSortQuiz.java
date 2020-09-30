package edu.emory.cs.sort.distribution;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {


    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sort(array, beginIndex, endIndex, maxBit - 1);
    }

    public void sort(Integer[] array, int beginIndex, int endIndex, int iteration) {
            int gap = endIndex - beginIndex;
            int div = (int)Math.pow(10, iteration);
            sort(array, beginIndex, endIndex, key -> (key / div) % 10);
            iteration--;

            int[] count = new int[10];
            for (int i = 0; i < 10; i++) {
                while (!buckets.get(i).isEmpty()) {
                    count[i]++;
                    array[beginIndex++] = buckets.get(i).remove();
                }
            }

            if (iteration == -1) return;
            beginIndex = endIndex - gap;
            for (int i = 0; i < 10; i++) {
                if (count[i] != 0) {
                    int end = beginIndex + count[i];
                    sort(array, beginIndex, end, iteration);
                }
                beginIndex += count[i];
            }
    }

    @Override
    protected void sort(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        // add each element in the input array to the corresponding bucket
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);

    }
}