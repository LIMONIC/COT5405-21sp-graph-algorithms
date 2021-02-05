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

    public Map<Integer, List<Integer>> heapGraph() {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int v = 0; v < GRAPH_SIZE; v++) {
            List<Integer> adjVertices = new ArrayList<Integer>();
            if (v > 0) {
                adjVertices.add((v-1)/2);
            }
            if(2 * v + 1 < GRAPH_SIZE){
                adjVertices.add(2 * v + 1);
            }
            if(2 * v + 2 < GRAPH_SIZE){
                adjVertices.add(2 * v + 2);
            }
            graph.put(v, adjVertices);
        }
        return graph;
    }

    public Map<Integer, List<Integer>> truncatedGraph(int m) {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int v = m; v < GRAPH_SIZE; v++) {
            List<Integer> adjVertices = new ArrayList<Integer>();
            if ((v-1)/2 >= m) {
                adjVertices.add((v-1)/2);
            }
            if(2 * v + 1 < GRAPH_SIZE){
                adjVertices.add(2 * v + 1);
            }
            if(2 * v + 2 < GRAPH_SIZE){
                adjVertices.add(2 * v + 2);
            }
            graph.put(v, adjVertices);
        }
        return graph;
    }

    public Map<Integer, List<Integer>> modkGraph(int k) {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int v = 0; v < GRAPH_SIZE; v++) {
            List<Integer> adjVertices = new ArrayList<Integer>();
            for (int u = 0; u < GRAPH_SIZE; u++) {
                if ((u-v)%k!=0) {
                    continue;
                }
                adjVertices.add(u);
            }
            graph.put(v, adjVertices);
        }
        return graph;
    }

    public static void main(String[] args) {
        graph_simulator gs = new graph_simulator(10);
        Map<Integer, List<Integer>> graph1 = gs.nCycle();
        Map<Integer, List<Integer>> graph2 = gs.completeGraph();
        Map<Integer, List<Integer>> graph3 = gs.emptyGraph();
        Map<Integer, List<Integer>> graph4 = gs.heapGraph();
        Map<Integer, List<Integer>> graph5 = gs.truncatedGraph(3);
        Map<Integer, List<Integer>> graph6 = gs.modkGraph(2);

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
        System.out.println("heapGraph:");
        for (Integer v : graph4.keySet()) {
            System.out.println(v + "->" + graph4.get(v).toString());
        }
        System.out.println("truncatedGraph:");
        for (Integer v : graph5.keySet()) {
            System.out.println(v + "->" + graph5.get(v).toString());
        }
        System.out.println("modkGraph:");
        for (Integer v : graph6.keySet()) {
            System.out.println(v + "->" + graph6.get(v).toString());
        }
    }

}
