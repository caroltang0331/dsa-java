package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.HeapSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.SelectionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.sort.divide_conquer.MergeSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;

import edu.emory.cs.utils.Utils;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {
    private final AbstractSort<T> engineQuick;
    private final AbstractSort<T> engineInsertion;

    public HybridSortHW() {
        engineQuick = new QuickSort<T>();
        engineInsertion = new InsertionSort<T>();
    }

    public boolean checkAscend(T[] rowsChecked) {
        if (rowsChecked.length == 1 || rowsChecked.length == 0) return true;
        int i = 1;
        while (i < rowsChecked.length) {
            if (rowsChecked[i].compareTo(rowsChecked[i - 1]) >= 0) i++;
            else return false;
        }
        return true;
    }
    public boolean checkDescend(T[] rowsChecked) {
        if (rowsChecked.length == 1 || rowsChecked.length == 0) return true;
        int i = 1;
        while (i < rowsChecked.length) {
            if (rowsChecked[i].compareTo(rowsChecked[i - 1]) <= 0) i++;
            else return false;
        }
        return true;
    }
    public boolean checkKindaAscend(T[] rowsChecked) {
        int count = 1;
        if (rowsChecked.length == 1 || rowsChecked.length == 0) return true;
        for (int i = 1; i < rowsChecked.length; i++) {
            if (rowsChecked[i].compareTo(rowsChecked[i - 1]) >= 0) count++;
        }
        if (count >= 0.75 * rowsChecked.length) return true;
        else return false;

    }

    public boolean checkKindaDescend(T[] rowsChecked) {
        int count = 1;
        if (rowsChecked.length == 1 || rowsChecked.length == 0) return true;
        for (int i = 1; i < rowsChecked.length; i++) {
            if (rowsChecked[i].compareTo(rowsChecked[i - 1]) <= 0) count++;
        }
        if (count >= 0.75 * rowsChecked.length) return true;

        else return false;

    }


    public void merge(int[] trackCount, T[] input, T[] copy, int beginIndex, int middleIndex, int endIndex) {
        int fst = trackCount[beginIndex], snd = trackCount[middleIndex], n = trackCount[endIndex] - trackCount[beginIndex];
        System.arraycopy(input, fst, copy, fst, n);

        for (int k = fst; k < trackCount[endIndex]; k++) {
            if (fst >= trackCount[middleIndex])
                input[k] = copy[snd++];
            else if (snd >= trackCount[endIndex])
                input[k] = copy[fst++];
            else if (copy[fst].compareTo(copy[snd]) < 0)
                input[k] = copy[fst++];
            else
                input[k] = copy[snd++];

        }
    }
    public void sort(int[] count, T[] input, T[] copy, int begin, int end) {
        if (begin + 1 >= end) return;
        int middleIndex = Utils.getMiddleIndex(begin, end);
        sort(count, input, copy, begin, middleIndex);
        sort(count, input, copy, middleIndex, end);
        merge(count, input, copy, begin, middleIndex, end);
    }

    @Override
    public T[] sort(T[][] input) {
        int[] countCase = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            if (checkAscend(input[i])) {
                countCase[i] = 1;
            }
            else if (checkDescend(input[i])) {
                countCase[i] = 2;
            }
            else if (checkKindaAscend(input[i])) {
                countCase[i] = 3;
            }
            else if (checkKindaDescend(input[i])) {
                countCase[i] = 4;
            }
            else countCase[i] = 5;
        }


        for (int i = 0; i < input.length; i++) {
            if (countCase[i] == 2) {
                List<T> CopyReverse = Arrays.asList(input[i]);
                Collections.reverse(CopyReverse);
                System.arraycopy(CopyReverse.toArray(),0, input[i], 0, input[i].length);
            }
            else if (countCase[i] == 3) engineInsertion.sort(input[i]);

            else if (countCase[i] == 4) {
                List<T> CopyCase4 = Arrays.asList(input[i]);
                Collections.reverse(CopyCase4);
                System.arraycopy(CopyCase4.toArray(), 0, input[i], 0, input[i].length);
                engineInsertion.sort(input[i]);
            }
            else if (countCase[i] == 5) engineQuick.sort(input[i]);

        }

        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[])Array.newInstance(input[0][0].getClass(), size);
        T[] temp = (T[])Array.newInstance(input[0][0].getClass(), size);
        int beginIndex = 0;
        for (T[] t : input) {
            System.arraycopy(t, 0, output, beginIndex, t.length);
            beginIndex += t.length;
        }

        int[] incrementedLength = new int[input.length + 1];
        incrementedLength[0] = 0;
        for (int i = 1; i < input.length + 1; i++) {
            incrementedLength[i] = incrementedLength[i - 1] + input[i - 1].length;
        }

        sort(incrementedLength, output, temp, 0, input.length);
        return output;
}}