package edu.emory.cs.graph;

import edu.emory.cs.graph.span.MST;
import edu.emory.cs.graph.span.MSTKruskal;
import edu.emory.cs.graph.span.MSTPrim;
import edu.emory.cs.graph.span.SpanningTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class GraphTest {
    @Test
    public void GraphQuiz() {
        GraphQuiz graph0 = new GraphQuiz(5);
        GraphQuiz graph1 = new GraphQuiz(5);
        GraphQuiz graph2 = new GraphQuiz(6);
        GraphQuiz graph3 = new GraphQuiz(5);
        GraphQuiz graph4 = new GraphQuiz(6);
        GraphQuiz graph5 = new GraphQuiz(4);
        GraphQuiz graph6 = new GraphQuiz(6);


//Test0#1
        graph0.setDirectedEdge(0, 1, 1);
        graph0.setDirectedEdge(0, 2, 1);
        graph0.setDirectedEdge(2, 1, 1);
        graph0.setDirectedEdge(2, 3, 1);
        graph0.setDirectedEdge(3, 4, 1);
        graph0.setDirectedEdge(4, 2, 1);
        graph0.numberOfCycles();

//Test1#2
        graph1.setDirectedEdge(0, 2, 1);
        graph1.setDirectedEdge(1, 0, 1);
        graph1.setDirectedEdge(2, 1, 1);
        graph1.setDirectedEdge(2, 3, 1);
        graph1.setDirectedEdge(3, 4, 1);
        graph1.setDirectedEdge(4, 2, 1);
        System.out.println(graph1.numberOfCycles());
//Test2#2
        graph2.setDirectedEdge(0, 2, 1);
        graph2.setDirectedEdge(1, 0, 1);
        graph2.setDirectedEdge(2, 1, 1);
        graph2.setDirectedEdge(3, 4, 1);
        graph2.setDirectedEdge(4, 5, 1);
        graph2.setDirectedEdge(5, 3, 1);
        System.out.println(graph2.numberOfCycles());
//Test3#2
        graph3.setDirectedEdge(0, 1, 1);
        graph3.setDirectedEdge(1, 2, 1);
        graph3.setDirectedEdge(1, 3, 1);
        graph3.setDirectedEdge(2, 0, 1);
        graph3.setDirectedEdge(3, 4, 1);
        graph3.setDirectedEdge(4, 2, 1);
        System.out.println(graph3.numberOfCycles());
//Test4#5
        graph4.setDirectedEdge(0, 1, 1);
        graph4.setDirectedEdge(1, 2, 1);
        graph4.setDirectedEdge(1, 3, 1);
        graph4.setDirectedEdge(2, 0, 1);
        graph4.setDirectedEdge(2, 4, 1);
        graph4.setDirectedEdge(3, 4, 1);
        graph4.setDirectedEdge(4, 1, 1);
        graph4.setDirectedEdge(4, 5, 1);
        graph4.setDirectedEdge(5, 2, 1);
        System.out.println(graph4.numberOfCycles());
//Test5#6
        graph5.setDirectedEdge(0, 1, 1);
        graph5.setDirectedEdge(0, 3, 1);
        graph5.setDirectedEdge(1, 0, 1);
        graph5.setDirectedEdge(1, 2, 1);
        graph5.setDirectedEdge(2, 1, 1);
        graph5.setDirectedEdge(2, 3, 1);
        graph5.setDirectedEdge(3, 0, 1);
        graph5.setDirectedEdge(3, 2, 1);
        System.out.println(graph5.numberOfCycles());
//Test6#7
        graph6.setDirectedEdge(0, 1, 1);
        graph6.setDirectedEdge(0, 4, 1);
        graph6.setDirectedEdge(1, 2, 1);
        graph6.setDirectedEdge(1, 4, 1);
        graph6.setDirectedEdge(2, 0, 1);
        graph6.setDirectedEdge(2, 5, 1);
        graph6.setDirectedEdge(3, 0, 1);
        graph6.setDirectedEdge(4, 2, 1);
        graph6.setDirectedEdge(4, 3, 1);
        graph6.setDirectedEdge(5, 4, 1);
        System.out.println(graph6.numberOfCycles());
        //assertTrue(graph.containsCycle());
    }

//    @Test
//    public void testTopologicalSort() {
//        Graph graph = new Graph(8);
//
//        graph.setDirectedEdge(0, 3, 1);
//        graph.setDirectedEdge(0, 4, 1);
//        graph.setDirectedEdge(1, 3, 1);
//        graph.setDirectedEdge(2, 4, 1);
//        graph.setDirectedEdge(3, 5, 1);
//        graph.setDirectedEdge(3, 6, 1);
//        graph.setDirectedEdge(3, 7, 1);
//        graph.setDirectedEdge(4, 6, 1);
//        graph.setDirectedEdge(2, 7, 1);
//
//        assertEquals("[0, 1, 2, 3, 4, 5, 7, 6]", new Graph(graph).topological_sort(false).toString());
//        assertEquals("[0, 1, 3, 5, 2, 4, 6, 7]", new Graph(graph).topological_sort(true).toString());
//    }
}