import java.util.*;


public class graph_operations {
//    Map<Integer,List<Integer>> graph;
//    Set<Integer> visited;
//    boolean hasCycle;
//    public operations(Map<Integer,List<Integer>> graph){
//        this.graph = graph;
//        this.visited = new HashSet<>();
//        this.hasCycle = false;
//    }


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


    // return a cycle, or an empty list.
    public List<Integer> one_cycle(Map<Integer,List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (Integer vertex : graph.keySet()){
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

    private void findCycle(int currVertex, int prevVertex, Set<Integer> visited, Deque<Integer> stack, List<Integer> res, Map<Integer,List<Integer>> graph) {

        if (res.size() != 0) {
            return;
        }

        visited.add(currVertex);
        stack.add(currVertex);

        for (int v : graph.get(currVertex)) {
            if (!visited.contains(v)){
                findCycle(v, currVertex, visited, stack, res, graph);
            } else {
                if (v != prevVertex) {
                    while (!stack.isEmpty()){
                        int node = stack.pollLast();
                        res.add(node);
                        if (node == v){
                            res.add(currVertex);
                            break;
                        }
                    }
                }
            }
        }
        stack.pollLast();
    }




    /*
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
        /*
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
*/
        // call function
//        List<List<Integer>> cp = connected_components(graph);
        // Test one cycle
//        RandomGraph randomGraph = new RandomGraph();
//        List<List<Integer>> graph = randomGraph.outputGraph();
//        List<Integer> oc = one_cycle(graph);


// List<List<Integer>>
/*

        List<List<Integer>> graph = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();
        list1.addAll(Arrays.asList(2));
        List<Integer> list2 = new ArrayList<>();
        list2.addAll(Arrays.asList(1,3));
        List<Integer> list3 = new ArrayList<>();
        list3.addAll(Arrays.asList(2,4));
        List<Integer> list4 = new ArrayList<>();
        list4.addAll(Arrays.asList(3,5));
        List<Integer> list5 = new ArrayList<>();
        list5.addAll(Arrays.asList(4));
        graph.addAll(Arrays.asList(list1, list2, list3, list4, list5));
        List<Integer> oc = one_cycle(graph);
        for (List<Integer> li : graph){
            System.out.println(li.toString());
        }
*/


        Map<Integer,List<Integer>> graph = new HashMap<>();

        List<Integer> list1 = new ArrayList<>();
        list1.addAll(Arrays.asList(2,3));
        graph.put(1, list1);
        List<Integer> list2 = new ArrayList<>();
        list2.addAll(Arrays.asList(1,3,5));
        graph.put(2, list2);
        List<Integer> list3 = new ArrayList<>();
        list3.addAll(Arrays.asList(1,2,4));
        graph.put(3, list3);
        List<Integer> list4 = new ArrayList<>();
        list4.addAll(Arrays.asList(3));
        graph.put(4, list4);
        List<Integer> list5 = new ArrayList<>();
        list5.addAll(Arrays.asList(2));
        graph.put(5, list5);

//        List<Integer> list1 = new ArrayList<>();
//        list1.addAll(Arrays.asList(2));
//        graph.put(1, list1);
//        List<Integer> list2 = new ArrayList<>();
//        list2.addAll(Arrays.asList(1,3));
//        graph.put(2, list2);
//        List<Integer> list3 = new ArrayList<>();
//        list3.addAll(Arrays.asList(2,4));
//        graph.put(3, list3);
//        List<Integer> list4 = new ArrayList<>();
//        list4.addAll(Arrays.asList(3,5));
//        graph.put(4, list4);
//        List<Integer> list5 = new ArrayList<>();
//        list5.addAll(Arrays.asList(4));
//        graph.put(5, list5);

//        List<Integer> list1 = new ArrayList<>();
//        list1.addAll(Arrays.asList(3));
//        graph.put(1, list1);
//        List<Integer> list2 = new ArrayList<>();
//        list2.addAll(Arrays.asList(3,5));
//        graph.put(2, list2);
//        List<Integer> list3 = new ArrayList<>();
//        list3.addAll(Arrays.asList(1,2,4));
//        graph.put(3, list3);
//        List<Integer> list4 = new ArrayList<>();
//        list4.addAll(Arrays.asList(3,5));
//        graph.put(4, list4);
//        List<Integer> list5 = new ArrayList<>();
//        list5.addAll(Arrays.asList(2,4));
//        graph.put(5, list5);

        graph_operations op = new graph_operations();
        List<Integer> oc = op.one_cycle(graph);
        for (Integer key : graph.keySet()) {
            List<Integer> values = graph.get(key);
            System.out.println("key: " + key + ", value: " + values.toString());
        }


        // print result

            System.out.println(oc.toString());

    }

}
