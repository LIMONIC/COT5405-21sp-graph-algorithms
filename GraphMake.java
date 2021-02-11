/*
 * contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
 * At least three functions should be tried with a different adjacency criteria.
 * The criteria should be mentioned as comments.
 * */


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class GraphMake {

    Map<Integer, Customer> customerList;
    Map<Integer, List<Integer>> graph;
    Map<Integer, List<user>> movieList = new HashMap<>();

    public static void main(String[] args) throws Exception {
        int option = 1;
        String filePath = "assignment1-data/";
        try {
            System.out.println("This class generates graph and outputs a txt file.");
            if (args.length < 1) {
                System.out.println("The Input File name is not specified!");
                System.out.println("This class take file path as argument.");
                return;
            } else if (args.length == 1) {
                filePath = args[0];
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("File not exist! Check your input.");
                }
            } else if (args.length > 1) {
                System.out.println("Too many arguments!");
            }
            System.out.println("Options: ");
            System.out.println("\t1: At least watched one movie in common");
            System.out.println("\t2: Super reviewer vs inactive reviewer");
            System.out.println("\t3: ...");
            System.out.println("\t4: ...");
            System.out.println("Your input:");
            Scanner in = new Scanner(System.in);
            option = in.nextInt();
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
        GraphMake gm = new GraphMake();
        switch (option) {
            case 1:
                gm.oneMovieInCommon(filePath);
                break;
            case 2:
                gm.readRatingFiles(filePath);
                gm.superReviewer();
                break;
            case 3:
                // ...
                break;
            default:
                System.out.println("Option dose not exist!");
        }
    }

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
                            if (movieId % 1000 == 0) {
                                System.out.println(movieId + " movies have been read.");
                            }
//                            movieList.putIfAbsent(movieId, new ArrayList<>());
                        } else {
                            try {
                                content = lineContent.split(",");
                                if (content.length != 3) {
                                    throw new Exception();
                                }
                                int customerID = Integer.parseInt(content[0]);
                                int rating = Integer.parseInt(content[1]);
                                String date = content[2];

//                                user u = new user(customerID, rating);
//                                movieList.get(movieId).add(u);

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
/*
        //################ Test ################
        for (int key : customerList.keySet()) {
            Customer c = customerList.get(key);
            System.out.println("id:" + c.id + " movie" + c.movieList.keySet().toString());
        }
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);

        //################ Test ################
*/

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
     * @Param: [java.lang.String]
     * @Return: void
     * @Description: This function output a txt file containing a graph that represented with adjacency list.
     */
    public void writeGraph(String name) {
        System.out.println("Start writing graph " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + ".txt");
            for (Integer customer : graph.keySet()) {
                List<Integer> adjCustomers = graph.get(customer);
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
                graph.put(customerId, new ArrayList<>());
            } else {
                List<Integer> neighbors = graph.getOrDefault(customerId, new ArrayList<>());
                List<Integer> parentNeighbors = graph.getOrDefault(parent, new ArrayList<>());
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
                graph.put(customerId, new ArrayList<>());
            } else {
                List<Integer> neighbors = graph.getOrDefault(customerId, new ArrayList<>());
                List<Integer> parentNeighbors = graph.getOrDefault(parent, new ArrayList<>());
                neighbors.add(parent);
                parentNeighbors.add(customerId);
                graph.put(customerId, neighbors);
                graph.put(parent, parentNeighbors);
            }
        }

        System.out.println("superReviewer graph created!");
        writeGraph("superReviewer");
    }


    // 3 ratings in common
    public void three_ratings() {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for (int j = i + 1; j < id_num; ++j) {
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if (im.size() >= 3) {
                    int n = 0;
                    for (Integer ri : im)
                        if (ml_i.get(ri).val == ml_j.get(ri).val)
                            ++n;
                    if (n >= 3) {
                        List<Integer> adjList_i = graph.getOrDefault(id[i], new ArrayList<>());
                        List<Integer> adjList_j = graph.getOrDefault(id[j], new ArrayList<>());
                        adjList_i.add(id[j]);
                        adjList_j.add(id[i]);
                        graph.put(id[i], adjList_i);
                        graph.put(id[j], adjList_j);
                    }
                }
            }
        }
    }

    // 1 data in common
    public void one_data() {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
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
                            List<Integer> adjList_i = graph.getOrDefault(id[i], new ArrayList<>());
                            List<Integer> adjList_j = graph.getOrDefault(id[j], new ArrayList<>());
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


    // Top k movies
    // ----------------------------------
    public void top_k_movie(int k) {
        graph.clear();
        Map<Double, Integer> ave_score = new TreeMap<>(new MyComparatorBigtoSmall());
        for (Integer mid : movieList.keySet()) {
            double ave = 0;
            for (user u : movieList.get(mid)) {
                ave += u.score;
            }
            ave /= movieList.get(mid).size();
            ave_score.put(ave, mid);
        }

        Iterator<Double> it = ave_score.keySet().iterator();

        // connect users in top k list
        List<Integer> graph_list = new ArrayList<Integer>();
        for (int i = 0; i < k && it.hasNext(); ++i) {
            for (user uid : movieList.get(ave_score.get(it.next())))
                graph_list.add(uid.id);
        }

        Integer[] id = graph_list.toArray(new Integer[0]);
        int id_num = graph_list.size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
            for (int j = i + 1; j < id_num; ++j) {
                List<Integer> adjList_i = graph.getOrDefault(id[i], new ArrayList<>());
                List<Integer> adjList_j = graph.getOrDefault(id[j], new ArrayList<>());
                adjList_i.add(id[j]);
                adjList_j.add(id[i]);
                graph.put(id[i], adjList_i);
                graph.put(id[j], adjList_j);
            }
        }

    }

    // watched on the same day
    public void same_data(int y, int m, int d) {
        List<Integer> adjList = new ArrayList<>();
        for (Integer uid : customerList.keySet()) {
            Map<Integer, Rating> ml = customerList.get(uid).movieList;
            for (Integer mid : ml.keySet()) {
                if (ml.get(mid).year == y && ml.get(mid).month == m && ml.get(mid).day == d) {
                    adjList.add(uid);
                    break;
                }
            }
        }
        Integer[] id = adjList.toArray(new Integer[0]);
        int id_num = adjList.size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
            for (int j = i + 1; j < id_num; ++j) {
                List<Integer> adjList_i = graph.getOrDefault(id[i], new ArrayList<>());
                List<Integer> adjList_j = graph.getOrDefault(id[j], new ArrayList<>());
                adjList_i.add(id[j]);
                adjList_j.add(id[i]);
                graph.put(id[i], adjList_i);
                graph.put(id[j], adjList_j);
            }
        }
    }

    // Coldest Movie
    // ----------------------------
    public void coldest_movies() {
        graph.clear();

        // graph initialization
        for (Integer key : customerList.keySet()) {
            graph.putIfAbsent(key, new ArrayList<>());
        }

        Map<Integer, Integer> c_num = new TreeMap<>(new MyComparatorSmalltoBig());
        for (Integer mid : movieList.keySet()) {
            int num = movieList.get(mid).size();
            c_num.put(mid, num);
        }

        List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(c_num.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            //
            public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // connect customers in the coldest N movies respectively

        List<Integer> graph_list = new ArrayList<Integer>();
        for (user uid : movieList.get(list.get(0).getKey())) {
            graph_list.add(uid.id);
        }
        Integer[] id = graph_list.toArray(new Integer[0]);
        int id_num = graph_list.size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
            for (int j = i + 1; j < id_num; ++j) {
                List<Integer> adjList_i = graph.getOrDefault(id[i], new ArrayList<>());
                List<Integer> adjList_j = graph.getOrDefault(id[j], new ArrayList<>());
                adjList_i.add(id[j]);
                adjList_j.add(id[i]);
                graph.put(id[i], adjList_i);
                graph.put(id[j], adjList_j);
            }
        }

    }

    /* Comparator */
    class MyComparatorBigtoSmall implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            if (o2 > o1)
                return 1;
            else
                return 0;
        }
    }

    class MyComparatorSmalltoBig implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o2 < o1)
                return 1;
            else
                return 0;
        }
    }

}