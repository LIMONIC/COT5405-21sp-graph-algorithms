import java.util.*;

public class graph_simulator {
    final int GRAPH_SIZE;
    graph_simulator(int size) {
        this.GRAPH_SIZE = size;
    }

    public Map<Integer, List<Integer>> nCycle() {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            int vertices = i;
            List<Integer> adjVertices = new ArrayList<Integer>();
            if (i == 0) {
                adjVertices.addAll(Arrays.asList(1, GRAPH_SIZE - 1));
                graph.put(vertices, adjVertices);
            }
            else if (i == GRAPH_SIZE - 1) {
                adjVertices.addAll(Arrays.asList(GRAPH_SIZE - 2, 0));
                graph.put(vertices, adjVertices);
            }
            else {
                adjVertices.addAll(Arrays.asList(i - 1, i + 1));
                graph.put(vertices, adjVertices);
            }
        }
        return graph;
    }


    public Map<Integer, List<Integer>> completeGraph() {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            int vertices = i;
            List<Integer> adjVertices = new ArrayList<Integer>();
            for (int j = 0; j < GRAPH_SIZE; j++) {
                if (i == j) {
                    continue;
                }
                adjVertices.addAll(Arrays.asList(j));
            }
            graph.put(vertices, adjVertices);
        }
        return graph;
    }


    public Map<Integer, List<Integer>> emptyGraph() {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            graph.put(i, new ArrayList<Integer>());
        }
        return graph;
    }

    public static void main(String[] args) {
        graph_simulator gs = new graph_simulator(10);
        Map<Integer, List<Integer>> graph1 = gs.nCycle();
        Map<Integer, List<Integer>> graph2 = gs.completeGraph();
        Map<Integer, List<Integer>> graph3 = gs.emptyGraph();
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
    }

}
