/*
 * contains the main function for generating simulated data and running the operations on it.
 * Output from graph operations can be written into a separate file or just printed out.
 * */

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// implement Runtime / Memory Space monitoring function here
public class simulated_test {

    /**
    * @MethodName: main
    * @Param: [java.lang.String[]]
    * @Return: void
    * @Description: Main method for generating simulated data and running the graph operation on it.
     *              The performance of each operation on the graph are instrumented.
     *              Performance and results are written in txt files;
    */
    public static void main(String[] args) {
        // [Graph_size] [method_name]
        int graphSize;
        int method;

        try {
            System.out.println("This class generates graph with simulated data and run test the methods in graph_operation.");
            if (args.length == 2) {
                graphSize = Integer.parseInt(args[0]);
                method = Integer.parseInt(args[1]);

                if (0 >= graphSize || graphSize > 1000000) {
                    throw new IllegalArgumentException();
                }
                if (method <= 0 || method > 6) {
                    throw new IllegalArgumentException();
                }
            } else {
                System.out.println("Wrong argument!");
                System.out.println("[Graph_size] [method: 1-6]");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        graph_simulator gs = new graph_simulator(graphSize);
        simulated_test st = new simulated_test();
        Map<Integer, Set<Integer>> graph;
        String name;
        switch (method) {
            case 1:
                name = "nCycle";
                graph = gs.nCycle();
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            case 2:
                name = "completeGraph";
                graph = gs.completeGraph();
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            case 3:
                name = "emptyGraph";
                graph = gs.emptyGraph();
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            case 4:
                name = "heap";
                graph = gs.heap();
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            case 5:
                name = "truncatedHeap";
                graph = gs.truncatedHeap(graphSize * 3 / 10);
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            case 6:
                name = "equivalenceModK";
                graph = gs.equivalenceModK(graphSize * 3 / 10);
                st.connectedComponentExp(name, graph);
                st.oneCycleExp(name, graph);
                st.shortestPathExp(name, graph);
                break;
            default:
                System.out.println("Option dose not exist!");
        }

    }

    /**
    * @MethodName: connectedComponentExp
    * @Param: [java.lang.String, java.util.Map<java.lang.Integer,java.util.Set<java.lang.Integer>>]
    * @Return: void
    * @Description: Perform experiment of connected components on selected graph.
    */
    public void connectedComponentExp(String name, Map<Integer, Set<Integer>> graph) {
        List<List<Integer>> connectedComponents = new ArrayList<>();
        graph_operations go = new graph_operations();
        // get runtime
        long startTime, stopTime;
        startTime = System.nanoTime();
        connectedComponents = go.connected_components(graph);
        stopTime = System.nanoTime();
        System.out.println("Run time of connected_components() (microsecond): " + ((stopTime - startTime) / 10 / 1000));
        // get memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // garbage collector
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is " + memory + " bytes.");

        // output
        System.out.println("Start write result of connected component of " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + "_connectedComponents.txt");
            writer.write("Run time of connected_components() (microsecond): " + ((stopTime - startTime) / 10 / 1000) + "\r\n");
            writer.write("Used memory is " + memory + " bytes." + "\r\n");
            for (List<Integer> cc : connectedComponents) {
                writer.write(cc.toString() + "\r\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
        System.out.println("--------------");
    }

    /**
    * @MethodName: oneCycleExp
    * @Param: [java.lang.String, java.util.Map<java.lang.Integer,java.util.Set<java.lang.Integer>>]
    * @Return: void
    * @Description: Perform experiment of find cycle on selected graph.
    */
    public void oneCycleExp(String name, Map<Integer, Set<Integer>> graph) {
        List<Integer> cycle = new ArrayList<>();
        graph_operations go = new graph_operations();
        // get runtime
        long startTime, stopTime;
        startTime = System.nanoTime();
        cycle = go.one_cycle(graph);
        stopTime = System.nanoTime();
        System.out.println("Run time of one_cycle() (microsecond): " + ((stopTime - startTime) / 10 / 1000));
        // get memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // garbage collector
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is " + memory + " bytes.");

        // output
        System.out.println("Start write result of one cycle of " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + "_oneCycle.txt");
            writer.write("Run time of one_cycle() (microsecond): " + ((stopTime - startTime) / 10 / 1000) + "\r\n");
            writer.write("Used memory is " + memory + " bytes." + "\r\n");
            if (cycle.size() != 0) {
                writer.write(cycle.toString() + "\r\n");
            } else {
                writer.write("No cycle found!" + "\r\n");
                System.out.println("No cycle found!");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
        System.out.println("--------------");
    }

    /**
    * @MethodName: shortestPathExp
    * @Param: [java.lang.String, java.util.Map<java.lang.Integer,java.util.Set<java.lang.Integer>>]
    * @Return: void
    * @Description: Perform experiment of shortest path on selected graph. The start point are randomly selected from graph key set.
    */
    public void shortestPathExp(String name, Map<Integer, Set<Integer>> graph) {
        Map<Integer, List<Integer>> shortestPath = new HashMap<>();
        graph_operations go = new graph_operations();
        // get runtime
        long startTime, stopTime;
        startTime = System.nanoTime();
        List<Integer> mapKeyList = new ArrayList<>();
        mapKeyList.addAll(graph.keySet());
        Random R = new Random();
        int s = mapKeyList.get(R.nextInt(mapKeyList.size()));
        shortestPath = go.shortest_paths(s, graph);
        stopTime = System.nanoTime();
        System.out.println("Run time of shortest_paths() (microsecond): " + ((stopTime - startTime) / 10 / 1000));
        // get memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // garbage collector
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);

        // output
        System.out.println("Start write result of shortest path of " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + "_shortestPaths.txt");
            writer.write("Run time of shortest_paths() (microsecond): " + ((stopTime - startTime) / 10 / 1000) + "\r\n");
            writer.write("Used memory is " + memory + " bytes." + "\r\n");
            writer.write("Start point is " + s + "\r\n");
            for (Integer key : shortestPath.keySet()) {
                List<Integer> list = shortestPath.get(key);
                writer.write(key + ":" + list.toString() + "\r\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
        System.out.println("--------------");

    }


}
