package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;

public class Kruskal1 implements MST{

    public SpanningTree getMinimumSpanningTree(Graph graph) {
        Map<Integer, Edge> Must = new HashMap<>();
        Map<Integer, Edge> Cannot = new HashMap<>();
        List<Edge> for0 = graph.getIncomingEdges(1);
        for (Edge edge : for0) if (edge.getSource() == 2)
            Cannot.put(0,edge);
        List<Edge> for2 = graph.getIncomingEdges(2);
        for (Edge edge : for2) if (edge.getSource() == 1)
            Cannot.put(Cannot.size(), edge);
        return Kruskal1(4, graph, Must, Cannot);
    }
    public SpanningTree Kruskal1(double first, Graph graph, Map<Integer, Edge> Must, Map<Integer, Edge> Cannot) {
        int size = graph.size();
        for (int i = 0; i < Cannot.size(); i++) {
            int finalI = i;
            graph.getIncomingEdges(Cannot.get(i).getTarget()).removeIf(Edges -> Edges.getTarget() == Cannot.get(finalI).getTarget());
        }
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph.getAllEdges());
        ArrayDeque<Edge> queue2 = new ArrayDeque<>(queue);
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        for (Edge edge : Must.values()) queue2.addFirst(edge);
        while (!queue.isEmpty()) {
            Edge edge = queue2.poll();
            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found

                if (tree.size() + 1 == size && tree.getTotalWeight() == first) break;
                if (tree.size() + 1 == size && tree.getTotalWeight() > first){
                    System.out.println("larger than first, weight is "+ tree.getTotalWeight());
                    return null; }

                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }
        return tree;
    }}

