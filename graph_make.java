/*
 * contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
 * At least three functions should be tried with a different adjacency criteria.
 * The criteria should be mentioned as comments.
 * */

import java.io.*;
import java.util.*;
import java.lang.Math;

public class graph_make {

    Map<Integer, Customer> customerList;
    Map<Integer, Set<Integer>> graph;
    Map<Integer, Set<Integer>> movieToCustomer;
    Set<Integer> customerSet;

    /**
     * @MethodName: writeCustomerList
     * @Param: []
     * @Return: void
     * @Description: Output a txt file named "customerList.txt" that contains all customer ID.
     */
    public void writeCustomerList() {
        FileWriter writer;
        try {
            writer = new FileWriter("customerList.txt");
            Set<Integer> values = customerList.keySet();
            List<Integer> newCustomerList = new ArrayList<>();
            newCustomerList.addAll(values);
            Collections.sort(newCustomerList);
            for (int c : newCustomerList) {
                writer.write(c + "\r\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @MethodName: writeGraph
     * @Param: [String]
     * @Return: void
     * @Description: Output a txt file with given name that contains the structure of the graph. graph represented by adjacency list.
     */
    public void writeGraph(String name) {
        System.out.println("Start writing graph " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + ".txt");
            for (Integer customer : graph.keySet()) {
                Set<Integer> adjCustomers = graph.get(customer);
                String adjCustomersString = adjCustomers.toString();
                writer.write(customer + ": " + adjCustomersString.substring(1, adjCustomersString.length() - 1) + "\r\n");

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
    }


    /**
     * @MethodName: oneMovieInCommon
     * @Param: []
     * @Return: void
     * @Description: This method output a graph in which all vertices are connected when they at least watched one movie in common.
     * Implement union-find to reduce tree height in order to avoid stack over flow in other DFS algorithms.
     */
    public void oneMovieInCommon() {
        System.out.println("Start build oneMovieInCommon graph...");
        // use union find to construct graph
        Map<Integer, Integer> father = new HashMap<>();
        initUnionFind(customerSet, father);
        for (int movieId : movieToCustomer.keySet()) {
            Set<Integer> adjCustomers = movieToCustomer.get(movieId);
            int parent = -1;
            for (int c : adjCustomers) {
                if (parent == -1) {
                    parent = find(c, father);
                } else {
                    assert parent > -1 : "Parent not initialized!";
                    join(c, parent, father);
                }
            }
        }

        // generate formatted graph
        graph = new HashMap<>();
        for (int customerId : father.keySet()) {
            int parent = father.get(customerId);
            if (parent == customerId) {
                graph.put(customerId, new HashSet<>());
            } else {
                Set<Integer> neighbors = graph.getOrDefault(customerId, new HashSet<>());
                Set<Integer> parentNeighbors = graph.getOrDefault(parent, new HashSet<>());
                neighbors.add(parent);
                parentNeighbors.add(customerId);
                graph.put(customerId, neighbors);
                graph.put(parent, parentNeighbors);
            }
        }
        System.out.println("oneMovieInCommon graph created!");
    }

    /**
     * @MethodName: initUnionFind
     * @Param: [java.util.Set<java.lang.Integer>, java.util.Map<java.lang.Integer,java.lang.Integer>]
     * @Return: void
     * @Description: Initialize a map that storing a key and its father node. All father nodes are set to the key in the beginning.
     */
    private void initUnionFind(Set<Integer> customerSet, Map<Integer, Integer> father) {
        // all node point to itself.
        for (int key : customerSet) {
            father.put(key, key);
        }
    }

    /**
     * @MethodName: find
     * @Param: [int, java.util.Map<java.lang.Integer,java.lang.Integer>]
     * @Return: int
     * @Description: Find the root of current connected component. Once found the root, compressing the path by connecting current vertex directly to the root.
     */
    private int find(int x, Map<Integer, Integer> father) {
        int root = x;
        while (father.get(root) != root) {
            root = father.get(root);
        }
        while (x != root) {
            int fx = father.get(x);
            father.put(x, root);
            x = fx;
        }
        return root;
    }

    /**
     * @MethodName: join
     * @Param: [int, int, java.util.Map<java.lang.Integer,java.lang.Integer>]
     * @Return: void
     * @Description: This method connect two vertices by concatenating the root of those two vertices.
     */
    private void join(int x, int y, Map<Integer, Integer> father) {
        int fx = find(x, father);
        int fy = find(y, father);
        if (fx != fy) {
            father.put(fx, fy);
        }
    }

    /**
     * @MethodName: superReviewer
     * @Param: [int]
     * @Return: void
     * @Description: This method output a graph, in which all vertices are connected based on number of their comment.
     * 4 tires are applied. Top 10% of users with the highest number of comments are considered as "Super Reviewer", top 10% - 30% of users with the highest number of comments are considered as "Active Reviewer",
     * Top 30% - 60% of users with the highest number of comments are considered as "Regular Reviewer", last 40% of users with the lowest number of comments are considered as "Inactive Reviewer".
     * The average number of comments per user is 209.
     */
    public void superReviewer() {
        System.out.println("Start create superReviewer graph...");
        graph = new HashMap<>();
        int maxCommentsNum = Integer.MIN_VALUE;
        for (int customer : customerList.keySet()) {
            maxCommentsNum = Math.max(maxCommentsNum, customerList.get(customer).movieList.size());
        }

        int superReviewer = -1, activeReviewer = -1, regularReviewer = -1, inactiveReviewer = -1;

        // use union find to construct graph
        Map<Integer, Integer> father = new HashMap<>();
        initUnionFind(customerList.keySet(), father);
        for (Integer customerId : customerList.keySet()) {
            int numOfMovieViewed = customerList.get(customerId).movieList.size();
            if (numOfMovieViewed < maxCommentsNum * 0.1) {
                if (inactiveReviewer == -1) {
                    inactiveReviewer = find(customerId, father);
                } else {
                    assert inactiveReviewer > -1 : "inactiveReviewer not initialized!";
                    join(customerId, inactiveReviewer, father);
                }
            } else if (maxCommentsNum * 0.1 <= numOfMovieViewed && numOfMovieViewed < maxCommentsNum * 0.3) {
                if (regularReviewer == -1) {
                    regularReviewer = find(customerId, father);
                } else {
                    assert regularReviewer > -1 : "regularReviewer not initialized!";
                    join(customerId, regularReviewer, father);
                }
            } else if (maxCommentsNum * 0.3 <= numOfMovieViewed && numOfMovieViewed < maxCommentsNum * 0.6) {
                if (activeReviewer == -1) {
                    activeReviewer = find(customerId, father);
                } else {
                    assert activeReviewer > -1 : "activeReviewer not initialized!";
                    join(customerId, activeReviewer, father);
                }
            } else {
                if (superReviewer == -1) {
                    superReviewer = find(customerId, father);
                } else {
                    assert superReviewer > -1 : "superReviewer not initialized!";
                    join(customerId, superReviewer, father);
                }
            }
        }

        // generate formatted graph
        graph = new HashMap<>();
        for (int customerId : father.keySet()) {
            int parent = father.get(customerId);
            if (parent == customerId) {
                graph.put(customerId, new HashSet<>());
            } else {
                Set<Integer> neighbors = graph.getOrDefault(customerId, new HashSet<>());
                Set<Integer> parentNeighbors = graph.getOrDefault(parent, new HashSet<>());
                neighbors.add(parent);
                parentNeighbors.add(customerId);
                graph.put(customerId, neighbors);
                graph.put(parent, parentNeighbors);
            }
        }

        System.out.println("superReviewer graph created!");
    }

    /**
     * @MethodName: goodReview
     * @Param: [int]
     * @Return: void
     * @Description: This method output a graph, in which all vertices are connected based on the average score of their comments.
     * 3 tires are applied. Users with average Score between 4~5 considered as "Tolerant Reviewer", users with average Score between 2~4 considered as "Objective Reviewer",
     * users with average Score between 0~2 considered as "Picky Reviewer"
     */
    public void goodReview() {
        System.out.println("Start create superReviewer graph...");
        graph = new HashMap<>();

        int tolerantReviewer = -1, objectiveReviewer = -1, pickyReviewer = -1;

        // use union find to construct graph
        Map<Integer, Integer> father = new HashMap<>();
        initUnionFind(customerList.keySet(), father);
        for (Integer customerId : customerList.keySet()) {
            int num = customerList.get(customerId).movieList.size();
            // compute aveScore
            int aveScore = 0;
            for (Integer key : customerList.get(customerId).movieList.keySet()) {
                Rating rating = customerList.get(customerId).movieList.get(key);
                aveScore += rating.val;
            }
            aveScore /= num;

            if (aveScore < 2.0) {
                if (pickyReviewer == -1) {
                    pickyReviewer = find(customerId, father);
                } else {
                    assert pickyReviewer > -1 : "pickyReviewer not initialized!";
                    join(customerId, pickyReviewer, father);
                }
            } else if (aveScore >= 2.0 && aveScore < 4.0) {
                if (objectiveReviewer == -1) {
                    objectiveReviewer = find(customerId, father);
                } else {
                    assert objectiveReviewer > -1 : "objectiveReviewer not initialized!";
                    join(customerId, objectiveReviewer, father);
                }
            } else {
                if (tolerantReviewer == -1) {
                    tolerantReviewer = find(customerId, father);
                } else {
                    assert tolerantReviewer > -1 : "tolerantReviewer not initialized!";
                    join(customerId, tolerantReviewer, father);
                }
            }
        }

        // generate formatted graph
        graph = new HashMap<>();
        for (int customerId : father.keySet()) {
            int parent = father.get(customerId);
            if (parent == customerId) {
                graph.put(customerId, new HashSet<>());
            } else {
                Set<Integer> neighbors = graph.getOrDefault(customerId, new HashSet<>());
                Set<Integer> parentNeighbors = graph.getOrDefault(parent, new HashSet<>());
                neighbors.add(parent);
                parentNeighbors.add(customerId);
                graph.put(customerId, neighbors);
                graph.put(parent, parentNeighbors);
            }
        }

        System.out.println("goodReview graph created!");
    }

}