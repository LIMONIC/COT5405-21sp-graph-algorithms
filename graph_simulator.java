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
            List<Integer> adjVertices = new ArrayList<>();
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

    public Map<Integer, List<Integer>> heap() {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);

        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int parent = q.poll();
                int leftChild = parent * 2 + 1;
                int rightChild = parent * 2 + 2;
                List<Integer> adjVertices = graph.getOrDefault(parent, new ArrayList<Integer>());
                if (leftChild < GRAPH_SIZE){
                    adjVertices.add(leftChild);
                    List<Integer> leftChildAdj = new ArrayList<>();
                    leftChildAdj.add(parent);
                    graph.put(leftChild, leftChildAdj);
                    q.offer(leftChild);
                }
                if (rightChild < GRAPH_SIZE){
                    adjVertices.add(rightChild);
                    List<Integer> rightChildAdj = new ArrayList<>();
                    rightChildAdj.add(parent);
                    graph.put(rightChild, rightChildAdj);
                    q.offer(rightChild);
                }
                graph.put(parent, adjVertices);
            }
        }
        return graph;
    }

    public Map<Integer, List<Integer>> truncatedHeap(int m) {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }

        for (int i = m; i < GRAPH_SIZE; i++) {
            int parent = (i - 1) / 2;
            int leftChild = i * 2 + 1;
            int rightChild = i * 2 + 2;
            List<Integer> adjVertices = new ArrayList<>();
            if (parent >= m) {
                adjVertices.add(parent);
            }
            if (leftChild < GRAPH_SIZE) {
                adjVertices.add(leftChild);
            }
            if (rightChild < GRAPH_SIZE) {
                adjVertices.add(rightChild);
            }
            graph.put(i, adjVertices);
        }

        return graph;
    }

    public Map<Integer, List<Integer>> equivalenceModK(int k) {
        Map<Integer,List<Integer>> graph = new HashMap<>();
        if (k > GRAPH_SIZE) {
            return null;
        }
        for (int u = 0; u < GRAPH_SIZE; u++) {
            for (int v = u; v < GRAPH_SIZE; v = v + k) {
                if (v == u) continue;
                List<Integer> adjVerticesOfU = graph.getOrDefault(u, new ArrayList<>());
                adjVerticesOfU.add(v);
                graph.put(u, adjVerticesOfU);
                List<Integer> adjVerticesOfV = graph.getOrDefault(v, new ArrayList<>());
                adjVerticesOfV.add(u);
                graph.put(v, adjVerticesOfV);
            }

        }
        return graph;
    }

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
