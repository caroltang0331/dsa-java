package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     *
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> result = new HashSet<>();
        for (Edge edge : graph.getIncomingEdges(target)) {
            Subgraph tmp = new Subgraph();
            tmp.addEdge(edge);
            result.addAll(getAugmentingPathAux(graph, tmp, source, edge.getSource()));
        }
            return result;
    }


  private Set<Subgraph> getAugmentingPathAux(Graph graph, Subgraph sub, int source, int target) {
        Set<Subgraph> result =  new HashSet<>();
      if (source == target) {
          result.add(sub);
          return result;
      }
      Subgraph tmp;

      for (Edge edge : graph.getIncomingEdges(target)) {
          if (sub.contains(edge.getSource())) continue;    // cycle
          tmp = new Subgraph(sub);
          tmp.addEdge(edge);
          result.addAll(getAugmentingPathAux(graph, tmp, source, edge.getSource()));

      }
      return result;

  }
}