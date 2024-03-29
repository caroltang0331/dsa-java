package edu.emory.cs.sort;

import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.comparison.ShellSortQuiz;
import edu.emory.cs.sort.distribution.LSDRadixSort;
import edu.emory.cs.sort.distribution.RadixSortQuiz;


/** @author Jinho D. Choi */
public class SortQuizTest extends SortTest {
    public void testRobustness() {

        testRobustness(new ShellSortQuiz<>());
        testRobustness(new RadixSortQuiz());
    }

    public void testRuntime() {
        testRuntime(new ShellSortKnuth<>(), new ShellSortQuiz<>());
        testRuntime(new LSDRadixSort(), new RadixSortQuiz());
    }
}