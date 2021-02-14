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

    /**
     * @MethodName: readRatingFiles
     * @Param: []
     * @Return: void
     * @Description: Read data files
     */
    public void readRatingFiles(String path) throws Exception {
        customerList = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            String filePath = path + "ratings_data_" + i + ".txt";
            System.out.println("Start Reading file: " + filePath);
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader br = new BufferedReader(fileReader);
                    String lineContent = null;
                    String[] content;
                    int movieId = 0;

                    while ((lineContent = br.readLine()) != null) {
                        if (lineContent.toCharArray()[lineContent.length() - 1] == ':') {
                            movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                            /*
                            if (movieId % 1000 == 0) {
                                System.out.println(movieId + " movies have been read.");
                            }
                             */
                        } else {
                            try {
                                content = lineContent.split(",");
                                if (content.length != 3) {
                                    throw new Exception();
                                }
                                int customerID = Integer.parseInt(content[0]);
                                int rating = Integer.parseInt(content[1]);
                                String date = content[2];

                                Customer currCustomer = customerList.getOrDefault(customerID, new Customer(customerID));
                                currCustomer.rateMovie(movieId, rating, date);
                                customerList.put(customerID, currCustomer);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    br.close();
                    fileReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("file not found!");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("io exception");
                    e.printStackTrace();
                }
            }
        }

    }

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
    public void oneMovieInCommon(String path) {
        Map<Integer, Set<Integer>> movieToCustomer = new HashMap<>();
        Set<Integer> customerSet = new HashSet<>();

        for (int i = 1; i < 5; i++) {
            String filePath = path + "ratings_data_" + i + ".txt";
            System.out.println("Start Reading file: " + filePath);
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader br = new BufferedReader(fileReader);
                    String lineContent = null;
                    String[] content;
                    int movieId = 0;

                    while ((lineContent = br.readLine()) != null) {
                        if (lineContent.toCharArray()[lineContent.length() - 1] == ':') {
                            movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                            if (movieId % 1000 == 0) {
                                System.out.println(movieId + " movies have been read.");
                            }
                        } else {
                            try {
                                content = lineContent.split(",");
                                if (content.length != 3) {
                                    throw new Exception();
                                }
                                int customerID = Integer.parseInt(content[0]);
                                customerSet.add(customerID);
                                Set<Integer> customers = movieToCustomer.getOrDefault(movieId, new HashSet<>());
                                customers.add(customerID);
                                movieToCustomer.put(movieId, customers);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    br.close();
                    fileReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("file not found!");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("io exception");
                    e.printStackTrace();
                }
            }
        }

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
        writeGraph("oneMovieInCommon");
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
     * @MethodName: nRatingInCommon
     * @Param: []
     * @Return: void
     * @Description: This method output a graph in which all vertices are connected when they at least watched n movie in common.
     */
    public void nRatingInCommon(int nInCommon) {
        graph = new HashMap<>();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new HashSet<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for (int j = i + 1; j < id_num; ++j) {
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated nInCommon movies in common, they are adjacent
                if (im.size() >= nInCommon && jm.size() >= nInCommon) {
                    int n = 0;
                    for (Integer ri : im)
                        if (ml_i.get(ri).val == ml_j.get(ri).val)
                            ++n;
                    if (n >= nInCommon) {
                        Set<Integer> adjList_i = graph.getOrDefault(id[i], new HashSet<>());
                        Set<Integer> adjList_j = graph.getOrDefault(id[j], new HashSet<>());
                        adjList_i.add(id[j]);
                        adjList_j.add(id[i]);
                        graph.put(id[i], adjList_i);
                        graph.put(id[j], adjList_j);
                    }
                }
            }
        }
    }

    /**
     * @MethodName: oneDataInCommon
     * @Param: []
     * @Return: void
     * @Description: This method output a graph in which all vertices are connected when they at least watched one movie on the same data.
     */
    public void oneDataInCommon() {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new HashSet<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for (int j = i + 1; j < id_num; ++j) {
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if (im.size() != 0) {
                    for (Integer ri : im) {
                        int yi = ml_i.get(ri).year;
                        int mi = ml_i.get(ri).month;
                        int di = ml_i.get(ri).day;
                        int yj = ml_j.get(ri).year;
                        int mj = ml_j.get(ri).month;
                        int dj = ml_j.get(ri).day;
                        if (yi == yj && mi == mj && di == dj) {
                            Set<Integer> adjList_i = graph.getOrDefault(id[i], new HashSet<>());
                            Set<Integer> adjList_j = graph.getOrDefault(id[j], new HashSet<>());
                            adjList_i.add(id[j]);
                            adjList_j.add(id[i]);
                            graph.put(id[i], adjList_i);
                            graph.put(id[j], adjList_j);
                        }
                    }
                }
            }
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
        boolean q1 = false, q2 = false, q3 = false, q4 = false;

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
        writeGraph("superReviewer");
    }

    /**
     * @MethodName: goodReview
     * @Param: [int]
     * @Return: void
     * @Description: This method output a graph, in which all vertices are connected based on the average score of their comments.
     * 3 tires are applied. Users with average Score between 4~5 considered as "Tolerant Reviewer", users with average Score between 2~4 considered as "Normal Reviewer",
     * users with average Score between 0~2 considered as "Picky Reviewer"
     * The average number of comments per user is 209.
     */
    public void goodReview() {
        System.out.println("Start create superReviewer graph...");
        graph = new HashMap<>();

        int highReviewer = -1, mediumReviewer = -1, lowReviewer = -1;
        boolean q1 = false, q2 = false, q3 = false;

        // use union find to construct graph
        Map<Integer, Integer> father = new HashMap<>();
        initUnionFind(customerList.keySet(), father);
        for (Integer customerId : customerList.keySet()) {
            int num = customerList.get(customerId).movieList.size();
            // compute aveScore
            int aveScore = 0;
            for (Integer key : customerList.get(customerId).movieList.keySet()){
                Rating rating = customerList.get(customerId).movieList.get(key);
                aveScore += rating.val;
            }
            aveScore /= num;

            if (aveScore < 2.0) {
                if (lowReviewer == -1) {
                    lowReviewer = find(customerId, father);
                } else {
                    assert lowReviewer > -1 : "inactiveReviewer not initialized!";
                    join(customerId, lowReviewer, father);
                }
            } else if (aveScore >= 2.0 && aveScore < 4.0) {
                if (mediumReviewer == -1) {
                    mediumReviewer = find(customerId, father);
                } else {
                    assert mediumReviewer > -1 : "regularReviewer not initialized!";
                    join(customerId, mediumReviewer, father);
                }
            }  else {
                if (highReviewer == -1) {
                    highReviewer = find(customerId, father);
                } else {
                    assert highReviewer > -1 : "superReviewer not initialized!";
                    join(customerId, highReviewer, father);
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
        writeGraph("goodReview");
    }

}