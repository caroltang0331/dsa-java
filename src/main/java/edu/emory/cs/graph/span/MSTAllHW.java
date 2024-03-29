package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;
import java.util.PriorityQueue;

import java.sql.SQLOutput;
import java.util.*;


/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {

        MST tree = new MSTKruskal();
        SpanningTree first = tree.getMinimumSpanningTree(graph);
        double minWeight = first.getTotalWeight();

        List<Edge> Must = new ArrayList<>();
        List<Edge> Cannot = new ArrayList<>();
        return new ArrayList<>(MSTAux(graph, minWeight, Must, Cannot));
    }
    public SpanningTree Kruskal1(double first, Graph graph, List<Edge> Must, List<Edge> Cannot) {
        Graph graph1 = new Graph(graph);
        for (int i = 0; i < Cannot.size(); i++) {
            int finalI = i;
            graph1.getIncomingEdges(Cannot.get(i).getTarget()).removeIf(Edges -> Edges.getSource() == Cannot.get(finalI).getSource());
        }
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph1.getAllEdges());
        ArrayDeque<Edge> queue2 = new ArrayDeque<>();
        while (!queue.isEmpty()) queue2.addLast(queue.poll());

        DisjointSet forest = new DisjointSet(graph1.size());
        SpanningTree tree = new SpanningTree();

        for (Edge edge : Must) queue2.addFirst(edge);
        while (!queue2.isEmpty()) {
            Edge edge = queue2.poll();
            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                if (tree.size() + 1 == graph1.size() && tree.getTotalWeight() == first) break;
                if (tree.size() + 1 == graph1.size() && tree.getTotalWeight() > first)
                    return null;

                forest.union(edge.getTarget(), edge.getSource());
            }
        }
        if (tree.size() + 1 != graph.size()) return null;
        return tree;
    }
    public List<SpanningTree> MSTAux(Graph graph, double weight, List<Edge> MustHave, List<Edge> CannotHave) {
        SpanningTree found = Kruskal1(weight, graph, MustHave, CannotHave);
        List<SpanningTree> result = new ArrayList<>();
        if (found != null) {
            result.add(found);

            for (int i = MustHave.size()/2 + MustHave.size()%2 ; i < graph.size() - 1; i++) {
                List<Edge> subCannot = new ArrayList<>(CannotHave);
                Edge edge = found.getEdges().get(i);
                subCannot.add(subCannot.size(), edge);
                List<Edge> reverse = graph.getIncomingEdges(edge.getSource());

                for (Edge edge1 : reverse) {
                    if (edge1.getSource() == edge.getTarget()) subCannot.add(subCannot.size(), edge1);
                }

                List<Edge> subMust = new ArrayList<>(MustHave);
                for (int j = MustHave.size()/2 + MustHave.size()%2; j < i; j++) {
                    Edge front = found.getEdges().get(j);
                    subMust.add(subMust.size(), front);
                    for (Edge back : graph.getIncomingEdges(front.getSource()))
                        if (back.getSource() == front.getTarget()) {
                            subMust.add(subMust.size(), back);
                        }
                }
                result.addAll(MSTAux(graph, weight, subMust, subCannot));
            }
        }
        return result;
    }
}