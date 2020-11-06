package edu.emory.cs.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class GraphTest {
    @Test
    public void testContainsCycle() {
        GraphQuiz graph = new GraphQuiz(6);

//Test0
//        graph.setDirectedEdge(0, 1, 1);
//        graph.setDirectedEdge(0, 2, 1);
//        graph.setDirectedEdge(2, 1, 1);
//        graph.setDirectedEdge(2, 3, 1);
//        graph.setDirectedEdge(3, 4, 1);
//        graph.setDirectedEdge(4, 2, 1);

//Test1
//        graph.setDirectedEdge(0, 2, 1);
//        graph.setDirectedEdge(1, 0, 1);
//        graph.setDirectedEdge(2, 1, 1);
//        graph.setDirectedEdge(2, 3, 1);
//        graph.setDirectedEdge(3, 4, 1);
//        graph.setDirectedEdge(4, 2, 1);

//Test2
//        graph.setDirectedEdge(0, 2, 1);
//        graph.setDirectedEdge(1, 0, 1);
//        graph.setDirectedEdge(2, 1, 1);
//        graph.setDirectedEdge(3, 4, 1);
//        graph.setDirectedEdge(4, 5, 1);
//        graph.setDirectedEdge(5, 3, 1);

//Test3
//        graph.setDirectedEdge(0, 1, 1);
//        graph.setDirectedEdge(1, 2, 1);
//        graph.setDirectedEdge(1, 3, 1);
//        graph.setDirectedEdge(2, 0, 1);
//        graph.setDirectedEdge(3, 4, 1);
//        graph.setDirectedEdge(4, 2, 1);

//Test4DIDNTPASS 1 over
        graph.setDirectedEdge(0, 1, 1);
        graph.setDirectedEdge(1, 2, 1);
        graph.setDirectedEdge(1, 3, 1);
        graph.setDirectedEdge(2, 0, 1);
        graph.setDirectedEdge(2, 4, 1);
        graph.setDirectedEdge(3, 4, 1);
        graph.setDirectedEdge(4, 1, 1);
        graph.setDirectedEdge(4, 5, 1);
        graph.setDirectedEdge(5, 2, 1);

//Test5DIDNTPASS 2 over
//        graph.setDirectedEdge(0, 1, 1);
//        graph.setDirectedEdge(0, 3, 1);
//        graph.setDirectedEdge(1, 0, 1);
//        graph.setDirectedEdge(1, 2, 1);
//        graph.setDirectedEdge(2, 1, 1);
//        graph.setDirectedEdge(2, 3, 1);
//        graph.setDirectedEdge(3, 0, 1);
//        graph.setDirectedEdge(3, 2, 1);

//Test6DIDNTPASS 1 over
//        graph.setDirectedEdge(0, 1, 1);
//        graph.setDirectedEdge(0, 4, 1);
//        graph.setDirectedEdge(1, 2, 1);
//        graph.setDirectedEdge(1, 4, 1);
//        graph.setDirectedEdge(2, 0, 1);
//        graph.setDirectedEdge(2, 5, 1);
//        graph.setDirectedEdge(3, 0, 1);
//        graph.setDirectedEdge(4, 2, 1);
//        graph.setDirectedEdge(4, 3, 1);
//        graph.setDirectedEdge(5, 4, 1);










        System.out.println(graph.numberOfCycles());
        System.out.println(graph.toString());
        assertTrue(graph.containsCycle());
    }

    @Test
    public void testTopologicalSort() {
        Graph graph = new Graph(8);

        graph.setDirectedEdge(0, 3, 1);
        graph.setDirectedEdge(0, 4, 1);
        graph.setDirectedEdge(1, 3, 1);
        graph.setDirectedEdge(2, 4, 1);
        graph.setDirectedEdge(3, 5, 1);
        graph.setDirectedEdge(3, 6, 1);
        graph.setDirectedEdge(3, 7, 1);
        graph.setDirectedEdge(4, 6, 1);
        graph.setDirectedEdge(2, 7, 1);

        assertEquals("[0, 1, 2, 3, 4, 5, 7, 6]", new Graph(graph).topological_sort(false).toString());
        assertEquals("[0, 1, 3, 5, 2, 4, 6, 7]", new Graph(graph).topological_sort(true).toString());
    }
}