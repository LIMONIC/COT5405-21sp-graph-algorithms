import java.util.*;

public class graph_simulator {
    final int GRAPH_SIZE;

    graph_simulator(int size) {
        this.GRAPH_SIZE = size;
    }

    /**
     * @MethodName: nCycle
     * @Param: []
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: An n-cycle: The vertices are integers from 0 through n - 1. The vertices u and v are connected by an edge if u - v = ±1 or u - v = ± (n - 1). There is one connected component, every shortest path has length at most n=2, and there is a unique cycle of length n.
     */
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

    /**
     * @MethodName: completeGraph
     * @Param: []
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: The vertices are integers from 0 through n-1. Every pair of distinct vertices forms an edge. There is one connected component, every shortest path has unit length, and there are many cycles.
     */
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

    /**
     * @MethodName: emptyGraph
     * @Param: []
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: An empty graph on n vertices: The vertices are integers from 0 through n - 1. There are no edges. There are n connected components, no paths, and no cycles.
     */
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

    /**
     * @MethodName: heap
     * @Param: []
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: A heap: The vertices are integers from 0 through n - 1. The neighbors of a vertex v are (v - 1)/2, 2v + 1, and 2v + 2, provided those numbers are in the range for vertices. There is one connected component, the paths are short, and there are no cycles.
     */
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

    /**
     * @MethodName: truncatedHeap
     * @Param: [int]
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: A truncated heap: The vertices are integers from m through n - 1. The edge relationship is the same as for the heap. There are n - 1 - 2m edges, m+ 1 connected components, and no cycles. The paths, when they exist, are short.
     */
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

    /**
     * @MethodName: equivalenceModK
     * @Param: [int]
     * @Return: java.util.Map<java.lang.Integer, java.util.Set < java.lang.Integer>>
     * @Description: Equivalence mod k: The vertices are integers from 0 to n - 1, where k <= n. The vertices u and v are connected by an edge if u - v is evenly divisible by k. There are k components, and each component is a complete graph.
     */
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
