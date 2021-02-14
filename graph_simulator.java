import java.io.File;
import java.util.*;

public class graph_simulator {
    final int GRAPH_SIZE;

    graph_simulator(int size) {
        this.GRAPH_SIZE = size;
    }

    public Map<Integer, Set<Integer>> nCycle() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            int vertices = i;
            Set<Integer> adjVertices = new HashSet<>();
            if (i == 0) {
                adjVertices.addAll(Arrays.asList(1, GRAPH_SIZE - 1));
                graph.put(vertices, adjVertices);
            } else if (i == GRAPH_SIZE - 1) {
                adjVertices.addAll(Arrays.asList(GRAPH_SIZE - 2, 0));
                graph.put(vertices, adjVertices);
            } else {
                adjVertices.addAll(Arrays.asList(i - 1, i + 1));
                graph.put(vertices, adjVertices);
            }
        }
        return graph;
    }

    public Map<Integer, Set<Integer>> completeGraph() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        Set<Integer> adjVertices = new HashSet<>();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            adjVertices.addAll(Arrays.asList(i));
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            adjVertices.remove(i);
            Set<Integer> adj = new HashSet<>();
            adj.addAll(adjVertices);
            graph.put(i, adj);
            adjVertices.add(i);
        }
        return graph;
    }

    public Map<Integer, Set<Integer>> emptyGraph() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            graph.put(i, new HashSet<>());
        }
        return graph;
    }

    public Map<Integer, Set<Integer>> heap() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int parent = q.poll();
                int leftChild = parent * 2 + 1;
                int rightChild = parent * 2 + 2;
                Set<Integer> adjVertices = graph.getOrDefault(parent, new HashSet<Integer>());
                if (leftChild < GRAPH_SIZE) {
                    adjVertices.add(leftChild);
                    Set<Integer> leftChildAdj = new HashSet<>();
                    leftChildAdj.add(parent);
                    graph.put(leftChild, leftChildAdj);
                    q.offer(leftChild);
                }
                if (rightChild < GRAPH_SIZE) {
                    adjVertices.add(rightChild);
                    Set<Integer> rightChildAdj = new HashSet<>();
                    rightChildAdj.add(parent);
                    graph.put(rightChild, rightChildAdj);
                    q.offer(rightChild);
                }
                graph.put(parent, adjVertices);
            }
        }
        return graph;
    }

    public Map<Integer, Set<Integer>> truncatedHeap(int m) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (GRAPH_SIZE == 0) {
            return graph;
        }

        for (int i = m; i < GRAPH_SIZE; i++) {
            int parent = (i - 1) / 2;
            int leftChild = i * 2 + 1;
            int rightChild = i * 2 + 2;
            Set<Integer> adjVertices = new HashSet<>();
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

    public Map<Integer, Set<Integer>> equivalenceModK(int k) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        if (k > GRAPH_SIZE) {
            return null;
        }
        for (int u = 0; u < GRAPH_SIZE; u++) {
            for (int v = u; v < GRAPH_SIZE; v = v + k) {
                if (v == u) continue;
                Set<Integer> adjVerticesOfU = graph.getOrDefault(u, new HashSet<>());
                adjVerticesOfU.add(v);
                graph.put(u, adjVerticesOfU);
                Set<Integer> adjVerticesOfV = graph.getOrDefault(v, new HashSet<>());
                adjVerticesOfV.add(u);
                graph.put(v, adjVerticesOfV);
            }
        }
        return graph;
    }

}
