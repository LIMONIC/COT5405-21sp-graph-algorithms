import java.util.*;

public class operations {
    // for data structure: adjacency matrix, adjacency list, heap?
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


    // return a cycle, or an empty list.
    // use indegree? adjacency list + indegree.
//    public List<Integer> one_cycle() {
//
//    }

    // returns a map of shortest paths.
    // minheap
//    public Map<Integer,List<Integer>> shortest_paths() {
//
//    }

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
