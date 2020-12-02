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
        List<SpanningTree> for2 = new ArrayList<>();
        double minWeight = first.getTotalWeight();
        System.out.println("first tree is "+ first);
        System.out.println("minWeight is" + minWeight);

        Map<Integer, Edge> Must = new HashMap<>();
        Map<Integer, Edge> Cannot = new HashMap<>();
        return new ArrayList<>(MSTAux(graph, minWeight, Must, Cannot));
    }
    public SpanningTree Kruskal1(double first, Graph graph, Map<Integer, Edge> Must, Map<Integer, Edge> Cannot) {
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

        for (Edge edge : Must.values()) queue2.addFirst(edge);
        while (!queue2.isEmpty()) {
            Edge edge = queue2.poll();
            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                if (tree.size() + 1 == graph1.size() && tree.getTotalWeight() == first) break;
                if (tree.size() + 1 == graph1.size() && tree.getTotalWeight() > first)
                    return null;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }
        if (tree.size() + 1 != graph.size()) return null;//System.out.println("krusakl retun tree " + tree);
        return tree;
    }
    public List<SpanningTree> MSTAux(Graph graph, double weight, Map<Integer, Edge> MustHave, Map<Integer, Edge> CannotHave) {
        SpanningTree found = Kruskal1(weight, graph, MustHave, CannotHave);
        List<SpanningTree> result = new ArrayList<>();
        System.out.println("found is " + found);
        if (found != null) {
            result.add(found);
            System.out.println("result + found " + result);
            System.out.println("musthave.size " + MustHave.size());
            for (int i = MustHave.size()/2 + MustHave.size()%2 ; i < graph.size() - 1; i++) {
                Map<Integer, Edge> subCannot = new HashMap<>(CannotHave);
                Edge edge = found.getEdges().get(i);
                subCannot.put(subCannot.size(), edge);
                List<Edge> reverse = graph.getIncomingEdges(edge.getSource());

                for (Edge edge1 : reverse) {
                    if (edge1.getSource() == edge.getTarget()) subCannot.put(subCannot.size(), edge1);
                }
                System.out.println("subCannot " + subCannot);

                Map<Integer, Edge> subMust = new HashMap<>(MustHave);
                System.out.println("i is " + i);
                System.out.println("subMust.size" + subMust.size());
                for (int j = MustHave.size()/2 + MustHave.size()%2; j < i; j++) {
                    Edge front = found.getEdges().get(j);
                    subMust.put(subMust.size(), front);
                    for (Edge back : graph.getIncomingEdges(front.getSource()))
                        if (back.getSource() == front.getTarget()) {
                            subMust.put(subMust.size(), back);
                        }
                }
                System.out.println("subMust " + subMust);
                result.addAll(MSTAux(graph, weight, subMust, subCannot));
            }
        }
        System.out.println("result: " + result);
        return result;
    }
}