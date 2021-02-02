import java.util.*;

public class operations {
    // for data structure: adjacency list
    // Storing graph : adjacency list. map<userID, List<userID>> ; since all user id are randomly assigned

    // return the list of connected components. Each component is itself a list.
    // List<List<Integer>> graph; 0:a,b,c,d...; 1:a,b,c,d...
    // dfs
    public static List<List<Integer>>  connected_components(Map<Integer,List<Integer>> graph) {
        List<List<Integer>> result = new ArrayList<>();
        if (graph == null) {
            return result;
        }
        Set<Integer> visited = new HashSet<>();
        for (Integer vertex : graph.keySet()) {
            if (!visited.contains(vertex)){
                result.add(findConnectedComponents(vertex, visited, graph));
            }
        }
        return result;
    }

    private static List<Integer> findConnectedComponents(int vertex, Set<Integer> visited, Map<Integer,List<Integer>> graph) {
        visited.add(vertex);
        List<Integer> res = new ArrayList<>();
        res.add(vertex);
        List<Integer> neighborVertices = graph.get(vertex);

        for (Integer v : neighborVertices) {
            if (!visited.contains(v)) {
                res.addAll(findConnectedComponents(v, visited, graph));
            }
        }

        return res;
    }

    /*
    // return a cycle, or an empty list.
    private static List<Integer> one_cycle(List<List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();

        if (graph == null) {
            return result;
        }

        int gSize = graph.size();
        boolean[] visited = new boolean[gSize];
        boolean[] recursion = new boolean[gSize];

        for (int i = 0; i < gSize; i++)
            result.addAll(isCyclic(i, visited, recursion, graph));

        return result;
    }

    private static List<Integer> isCyclic(int i, boolean[] visited, boolean[] recursion, List<List<Integer>> graph) {
        List<Integer> res = new ArrayList<>();

        if (recursion[i]) {
            res.add(i);
            return res;
        }
        if (visited[i]){
            res.clear();
            return res;
        }

        visited[i] = true;
        recursion[i] = true;

        for (Integer v: graph.get(i))
            return isCyclic(v, visited, recursion, graph);

        recursion[i] = false;

        return res;
    }
     */



    /*
    // returns a map of shortest paths.
    // minheap
    public Map<Integer,List<Integer>> shortest_paths(List<List<Integer>> graph, int s) {
        Map<Integer,List<Integer>> result = new HashMap<>();

        int gSize = graph.size();
        int dist[] = new int[gSize];;
        Set<Integer> visited;

        for (int i = 0; i < gSize; i++)
            dist[i] = Integer.MAX_VALUE;

        PriorityQueue<> pq(gSize);

        pq.add(new Node(src, 0));

        // Distance to the source is 0
        dist[src] = 0;
        while (settled.size() != V) {

            // remove the minimum distance node
            // from the priority queue
            int u = pq.remove().node;

            // adding the node whose distance is
            // finalized
            settled.add(u);

            e_Neighbours(u);
        }

        return result;
    }
     */


    public static void main(String[] args) {
        //generate random graph
        final int maxNumOfVertices = 100;
        final int rangeOfVertices = 1000;

        int listOfVertices[] = new int[maxNumOfVertices];
        Random random = new Random();


        Map<Integer,List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < maxNumOfVertices; i++) {
            int vertices =  random.nextInt(rangeOfVertices) + 1;
            listOfVertices[i] = vertices;
            graph.put(vertices,new ArrayList<Integer>());
        }

        int numOfVertices = listOfVertices.length;
//        int NumOfEdges = 100;
        int NumOfEdges = random.nextInt(numOfVertices * ((numOfVertices - 1) / 2)) + 1;
        NumOfEdges /= 15;
        for (int i = 0; i < NumOfEdges; i++) {
            int v = listOfVertices[random.nextInt(numOfVertices)];
            int u = listOfVertices[random.nextInt(numOfVertices)];
            if (v != u) {
                List<Integer> vList = graph.get(v);
                List<Integer> uList = graph.get(u);
                vList.add(u);
                uList.add(v);
                graph.put(v,vList);
                graph.put(u,uList);
            }
        }

        // print graph
        for (Integer key : graph.keySet()) {
            List<Integer> values = graph.get(key);
            System.out.println("key: " + key + ", value: " + values.toString());
        }
        System.out.println("graph size is: " + graph.size());

        // call function
        List<List<Integer>> cp = connected_components(graph);

        // print result
        for (List<Integer> res : cp) {
            System.out.println(res.toString());
        }
    }

}
