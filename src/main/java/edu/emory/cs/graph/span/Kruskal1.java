package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kruskal1 implements MST{

    public SpanningTree getMinimumSpanningTree(Graph graph) {
        Map<Integer, Edge> Must = new HashMap<>();
        Map<Integer, Edge> Cannot = new HashMap<>();
        List<Edge> for0 = graph.getIncomingEdges(0);
        for (Edge edge : for0) if (edge.getSource() ==2)
            Cannot.put(0,edge);
        List<Edge> for2 = graph.getIncomingEdges(2);
        for (Edge edge : for2) if (edge.getSource() == 0)
            Cannot.put(Cannot.size(), edge);
        return Kruskal1(2, graph, Must, Cannot);
    }
    public SpanningTree Kruskal1(double first, Graph graph, Map<Integer, Edge> Must, Map<Integer, Edge> Cannot) {
        int size = graph.size();
        for (int i = 0; i < Cannot.size(); i++) {
            int finalI = i;
            graph.getIncomingEdges(Cannot.get(i).getTarget()).removeIf(Edges -> Edges.getTarget() == Cannot.get(finalI).getTarget());
        }
        ArrayDeque<Edge> queue = new ArrayDeque<>(graph.getAllEdges());
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        for (Edge edge : Must.values()) queue.addFirst(edge);
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                System.out.println("weight" + tree.getTotalWeight());
                System.out.println("first" + first);
                System.out.println("tree" + tree.size());
                System.out.println("grpah.size" + size);
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

