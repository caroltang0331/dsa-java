package edu.emory.cs.graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        int count = 0;
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while (!notVisited.isEmpty()) {
            count += (containsCycleAux(notVisited.poll(), notVisited, new HashSet<>()));
        }

        return count;
    }

    private int containsCycleAux(int target, Deque<Integer> notVisited, Set<Integer> visited) {
        int count = 0;
        notVisited.remove(target);
        visited.add(target);

        for (Edge edge : getIncomingEdges(target)) {
            if (visited.contains(edge.getSource())) count++;


            else count += (containsCycleAux(edge.getSource(), notVisited, new HashSet<>(visited)));

        }
        return count;


    }

}