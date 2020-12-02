package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;


/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        MST tree = new MSTKruskal();
        SpanningTree first = tree.getMinimumSpanningTree(graph);
        double minWeight = first.getTotalWeight();

        Map<Integer, Edge> Must = new HashMap<>();
        Map<Integer, Edge> Cannot = new HashMap<>();
        return new ArrayList<>(MSTAux(graph, minWeight, Must, Cannot));
    }
    public SpanningTree Kruskal1(double first, Graph graph, Map<Integer, Edge> Must, Map<Integer, Edge> Cannot) {
        graph.getAllEdges().removeAll(Cannot.values());
        ArrayDeque<Edge> queue = new ArrayDeque<>(graph.getAllEdges());
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        for (Edge edge : Must.values()) queue.addFirst(edge);
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                if (tree.size() + 1 == graph.size() && tree.getTotalWeight() == first) break;
                if (tree.size() + 1 == graph.size() && tree.getTotalWeight() > first)
                    return null;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }
        return tree;
    }
    public List<SpanningTree> MSTAux(Graph graph, double weight, Map<Integer, Edge> MustHave, Map<Integer, Edge> CannotHave) {
        SpanningTree found = Kruskal1(weight, graph, MustHave, CannotHave);
        List<SpanningTree> result = new ArrayList<>();
        if (found != null) {
            result.add(found);
            for (int i = MustHave.size(); i < graph.size() - 1; i++) {
                Map<Integer, Edge> subCannot = new HashMap<>(CannotHave);
                Edge edge = found.getEdges().get(i);
                subCannot.put(CannotHave.size(), edge);
                List<Edge> reverse = graph.getIncomingEdges(edge.getSource());
                for (Edge edge1 : reverse) {
                    if (edge1.getSource() == edge.getTarget()) subCannot.put(subCannot.size(), edge1);
                }

                Map<Integer, Edge> subMust = new HashMap<>(MustHave);
                for (int j = MustHave.size(); j < i; j++)
                    subMust.put(j, found.getEdges().get(j));
                result.addAll(MSTAux(graph, weight, subMust, subCannot));
            }
        }
        return result;
    }
}