package edu.emory.cs.queue;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** @author Jinho D. Choi */
public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }
    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }

    @Override
    public void add(T key) {
            keys.add(key);
            swim(size() - 1);
    }
    private void swim(int k) {
        for (; 0 < k && compare((k - 1)/3, k) < 0; k = (k - 1)/3)
            Collections.swap(keys, (k - 1)/3, k);
    }
    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 0, size() - 1);
        T max = keys.remove(size() - 1);
        sink();
        return max;
    }
    private void sink() {

        for (int k = 0, i = 0; i < size() - 1; k = i, i *= 3) {
               if ((i + 3) < size()) {
                   if (compare(i + 1, i + 3) < 0 && compare(i + 2, i + 3) < 0) {
                   i = i + 2; }
                   else if (compare(i + 1, i + 2) < 0) i = i + 1;
               }
               else if ((i + 2) < size() && compare(i + 1, i + 2) < 0) {
                   i = i + 1;
               }
               if (compare(k, i + 1) >= 0) break;
               Collections.swap(keys, k, i + 1);
               i++;
       }
    }

    @Override
    public int size() {
        return keys.size();
    }
}