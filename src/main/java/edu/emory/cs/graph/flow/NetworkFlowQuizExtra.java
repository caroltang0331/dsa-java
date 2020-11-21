package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> result = new HashSet<>();
        Set<Subgraph> temp = new HashSet<>();
        for (Edge edge : graph.getIncomingEdges(target)) {
            Subgraph level1 = new Subgraph();
            level1.addEdge(edge);
            temp.add(level1);
            if (level1.contains(source)) result.add(level1);
        }
        return result;
    }


}