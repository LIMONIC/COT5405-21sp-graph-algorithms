/*
* contains the main function for generating simulated data and running the operations on it.
* Output from graph operations can be written into a separate file or just printed out.
* */

import java.util.List;
import java.util.Map;

// implement Runtime / Memory Space monitoring function here
public class simulated_test {

    public static void main(String[] args) {
        graph_simulator gs = new graph_simulator(10);
        Map<Integer, List<Integer>> graph1 = gs.nCycle();
        Map<Integer, List<Integer>> graph2 = gs.completeGraph();
        Map<Integer, List<Integer>> graph3 = gs.emptyGraph();
        Map<Integer, List<Integer>> graph4 = gs.heap();
        Map<Integer, List<Integer>> graph5 = gs.truncatedHeap(2);
        Map<Integer, List<Integer>> graph6 = gs.equivalenceModK(2);



        System.out.println("nCycle:");
        for (Integer v : graph1.keySet()) {
            System.out.println(v + "->" + graph1.get(v).toString());
        }
        System.out.println("completeGraph:");
        for (Integer v : graph2.keySet()) {
            System.out.println(v + "->" + graph2.get(v).toString());
        }
        System.out.println("emptyGraph:");
        for (Integer v : graph3.keySet()) {
            System.out.println(v + "->" + graph3.get(v).toString());
        }
        System.out.println("heap:");
        for (Integer v : graph4.keySet()) {
            System.out.println(v + "->" + graph4.get(v).toString());
        }
        System.out.println("truncatedHeap:");
        for (Integer v : graph5.keySet()) {
            System.out.println(v + "->" + graph5.get(v).toString());
        }
        System.out.println("equivalenceModK:");
        for (Integer v : graph6.keySet()) {
            System.out.println(v + "->" + graph6.get(v).toString());
        }
    }
}
