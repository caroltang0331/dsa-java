package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g);}

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        Set<String> cycles = new HashSet<>();

        while (!notVisited.isEmpty())
            numberOfCyclesAux(notVisited.poll(), notVisited, new ArrayList<>(), cycles);

            return cycles.size();
    }

    private void numberOfCyclesAux(int target, Deque<Integer> notVisited, List<Integer> visited, Set<String> cycles) {
        notVisited.remove(target);
        visited.add(target);

        for (Edge edge : getIncomingEdges(target)) {
            int idx = visited.indexOf(edge.getSource());
            if (idx >= 0)
                cycles.add(cyclePath(visited, idx));
            else
                numberOfCyclesAux(edge.getSource(), notVisited, new ArrayList<>(visited), cycles);
        }
    }

    private String cyclePath(List<Integer> list, int beginIndex) {
        //0-1-2-3-4 beginIndex=1   5-2-3-4-1 beginIndex=1  3-4-1-2 beginIndex=0  6-7-4-1-2-3 beginIndex=2
                // min = 1, 1, 0, 3
                // (2-5) (2-5) (1-4) (2,6)
        int min = beginIndex;
        for (int i = beginIndex + 1; i < list.size(); i++) {
            if (list.get(i) < list.get(min)) min = i; //find(1234 inside the list of 4, 5, 6)
        }

        StringJoiner join = new StringJoiner(" -> ");
        for (int i = min; i >= beginIndex; i--)
            //i = 1 begin = 1(only add 1); i = 4 begin = 1(add 1432); i = 2 begin = 0(add 143); i = 3, begin = 2(add 14
            join.add(list.get(i).toString());
        for (int i = list.size() - 1; i > min; i--)
            //i = 4, min = 1; i = 4, min = 4; i = 3, min = 2; i = 5, min = 3
            //(second add (1)432; (1432), (143)2, (14)32
            join.add(list.get(i).toString());
        return join.toString();
    }
}









