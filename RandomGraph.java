/**
 * #######################################
 * This class generates a random adjacency list of graph.
 * The vertex of the graph starts at 0.
 * The adjacency list stores in the form of List<List<Integer>>
 * #######################################
 */

import java.util.*;

public class RandomGraph {
    public int vertices;
    public int edges;

    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 20;

    // A Random instance to generate random values
    Random random = new Random();
    // An adjacency list to represent a graph
    public List<List<Integer>> adjacencyList;

    // Creating the constructor
    public RandomGraph()
    {
        // Set a maximum limit for
        // the number of vertices say 20
        this.vertices = random.nextInt(MAX_LIMIT) + 1;

        // compute the maximum possible number of edges
        // and randomly choose the number of edges less than
        // or equal to the maximum number of possible edges
        this.edges
                = random.nextInt(computeMaxEdges(vertices)) + 1;

        // Creating an adjacency list
        // representation for the random graph
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++)
            adjacencyList.add(new ArrayList<>());

        // A for loop to randomly generate edges
        for (int i = 0; i < edges; i++) {
            // randomly select two vertices to
            // create an edge between them
            int v = random.nextInt(vertices);
            int w = random.nextInt(vertices);

            // add an edge between them
            addEdge(v, w);
        }
    }

    // Method to compute the maximum number of possible
    // edges for a given number of vertices
    int computeMaxEdges(int numOfVertices)
    {
        // As it is an undirected graph
        // So, for a given number of vertices
        // there can be at-most v*(v-1)/2 number of edges
        return numOfVertices * ((numOfVertices - 1) / 2);
    }

    // Method to add edges between given vertices
    void addEdge(int v, int w)
    {
        // Note: it is an Undirected graph

        // Add w to v's adjacency list
        adjacencyList.get(v).add(w);

        // Add v to w's adjacency list
        adjacencyList.get(w).add(v);
    }

    public List<List<Integer>> outputGraph (){
        RandomGraph randomGraph = new RandomGraph();
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < randomGraph.adjacencyList.size(); i++) {
            List<Integer> list = randomGraph.adjacencyList.get(i);
            graph.add(list);
        }

        return graph;
    }

    public static void main(String[] args)
    {
        // Create a GFGRandomGraph object
        RandomGraph randomGraph = new RandomGraph();

        // Print the graph
        System.out.println("The generated random graph :");
        for (int i = 0;
             i < randomGraph.adjacencyList.size(); i++) {
            System.out.print(i + " -> { ");

            List<Integer> list
                    = randomGraph.adjacencyList.get(i);

            if (list.isEmpty())
                System.out.print(" No adjacent vertices ");
            else {
                int size = list.size();
                for (int j = 0; j < size; j++) {

                    System.out.print(list.get(j));
                    if (j < size - 1)
                        System.out.print(" , ");
                }
            }

            System.out.println("}");
        }
    }

}
