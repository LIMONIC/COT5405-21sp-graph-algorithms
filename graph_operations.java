import java.util.*;

public class graph_operations {

    /**
     * @MethodName: connected_components
     * @Param: [Map<Int, Set < Integer>>]
     * @Return: [List<List < Integer>>]
     * @Description: using depth-First search to return the list of connected components
     */
    public static List<List<Integer>> connected_components(Map<Integer, Set<Integer>> graph) {
        List<List<Integer>> result = new ArrayList<>();
        if (graph == null) {
            return result;
        }
        Set<Integer> visited = new HashSet<>();
        for (Integer vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                result.add(findConnectedComponents(vertex, visited, graph));
            }
        }
        return result;
    }

    /**
     * @MethodName: findConnectedComponents
     * @Param: [int, Set<Integer>, Map<Integer, Set<Integer>>]
     * @Return: [List<Integer>]
     * @Description: using depth-First search to recursively return the list of connected components of the given vertex
     */
    private static List<Integer> findConnectedComponents(int vertex, Set<Integer> visited, Map<Integer, Set<Integer>> graph) {
        visited.add(vertex);
        List<Integer> res = new ArrayList<>();
        res.add(vertex);
        Set<Integer> neighborVertices = graph.get(vertex);

        for (Integer v : neighborVertices) {
            if (!visited.contains(v)) {
                res.addAll(findConnectedComponents(v, visited, graph));
            }
        }
        return res;
    }

    /**
     * @MethodName: one_cycle
     * @Param: [Map<Int, Set < Integer>>]
     * @Return: [List<Integer>]
     * @Description: using depth-First search to return a cycle, or an empty list.
     */
    public List<Integer> one_cycle(Map<Integer, Set<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (Integer vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                findCycle(vertex, vertex, visited, stack, result, graph);
            }
        }
        if (result.size() > 0) {
            System.out.println("Cycle found!");
        } else {
            System.out.println("cycle not found!");
        }
        return result;

    }

    /**
     * @MethodName: findCycle
     * @Param: [int, int, Set<Integer>, Deque<Integer>, List<Integer>, Map<Integer, List<Integer>>]
     * @Return: []
     * @Description:
     */
    private void findCycle(int currVertex, int prevVertex, Set<Integer> visited, Deque<Integer> stack, List<Integer> res, Map<Integer, Set<Integer>> graph) {

        if (res.size() != 0) {
            return;
        }
        visited.add(currVertex);
        stack.add(currVertex);

        for (int v : graph.get(currVertex)) {
            if (!visited.contains(v)) {
                findCycle(v, currVertex, visited, stack, res, graph);
            } else {
                if (v != prevVertex) {
                    while (!stack.isEmpty()) {
                        int node = stack.pollLast();
                        res.add(node);
                        if (node == v) {
                            res.add(currVertex);
                            break;
                        }
                    }
                }
            }
        }
        stack.pollLast();
    }

    /**
     * @MethodName: shortest_paths
     * @Param: [int, Map<Integer, List<Integer>>]
     * @Return: [Map<Integer, List < Integer>>]
     * @Description: using Dijkstra's algorithm and returns a map of shortest paths
     */
    public Map<Integer, List<Integer>> shortest_paths(int s, Map<Integer, Set<Integer>> graph) {
        int gSize = graph.size();
        Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>(gSize);
        Map<Integer, Integer> dist = new HashMap<Integer, Integer>(gSize);
        Set<Integer> visited = new HashSet<Integer>();

        for (Integer k : graph.keySet())
            dist.put(k, Integer.MAX_VALUE);

        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(gSize, new Vertex());

        pq.add(new Vertex(s, 0));
        List<Integer> list = new ArrayList<>();
        list.add(s);
        result.put(s, list);
        dist.replace(s - 1, 0);

        while (visited.size() != gSize && !pq.isEmpty()) {
            int v = pq.remove().getID();
            visited.add(v);
            getPath(v, visited, dist, pq, result, graph);
        }

        return result;
    }

    /**
     * @MethodName: getPath
     * @Param: [int, Map<Integer, Integer>, PriorityQueue<Vertex>, List<Integer>>, Map<Integer,List<Integer>>]
     * @Return: []
     * @Description: getting the shortest path distance of from the source to neighbours of v
     */
    private void getPath(int v, Set<Integer> visited, Map<Integer, Integer> dist, PriorityQueue<Vertex> pq, Map<Integer, List<Integer>> result, Map<Integer, Set<Integer>> graph) {
        int edgeWeight = -1;
        int newDis = -1;

        // All the neighbors of v
        for (Integer uv : graph.get(v)) {
            if (!visited.contains(uv)) {
                edgeWeight = 1;
                newDis = dist.get(v) + edgeWeight;

                if (newDis < dist.get(uv)) {
                    dist.replace(uv, newDis);
                    List<Integer> list = new ArrayList<>();
                    list.addAll(result.get(v));
                    list.add(uv);
                    result.put(uv, list);
                }

                pq.add(new Vertex(uv, dist.get(uv)));
            }
        }
    }

    // Class to represent a node in the graph
    class Vertex implements Comparator<Vertex> {
        private Integer id;
        private Integer dis;

        public Vertex() {
        }
        public Vertex(Integer id, Integer dis) {
            this.id = id;
            this.dis = dis;
        }
        public Integer getDis() {
            return dis;
        }
        public Integer getID() {
            return id;
        }
        @Override
        public int compare(Vertex v1, Vertex v2) {
            if (v1.dis < v2.dis)
                return -1;
            if (v1.dis > v2.dis)
                return 1;
            return 0;
        }
    }

}
