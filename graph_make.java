/*
 * contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
 * At least three functions should be tried with a different adjacency criteria.
 * The criteria should be mentioned as comments.
 * */


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class graph_make {

    Map<Integer, Customer> customerList;
    Map<Integer, List<Integer>> graph;

    Map<Integer, List<user>> movieList = new HashMap<>();

    public static void main(String[] args) throws Exception {
        graph_make gm = new graph_make();
        gm.readRatingFiles();
        gm.superReviewer();
    }

    /**
     * @MethodName: readRatingFiles
     * @Param: []
     * @Return: void
     * @Description: Read data files
     */
    public void readRatingFiles() throws Exception {
        customerList = new HashMap<>();
        for (int i = 1; i < 2; i++) {
            String filePath = "assignment1-data/ratings_data_" + i + ".txt";
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

    public void writeGraph(String name) {
        System.out.println("Starting writing graph " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name+".txt");
            for (Integer customer : graph.keySet()) {
                List<Integer> adjCustomers = graph.get(customer);
                String adjCustomersString = adjCustomers.toString();
                writer.write(customer + ": " + adjCustomersString.substring(1,adjCustomersString.length() - 1)+"\r\n");

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // one movie in common
    public void one_movie() {
        graph = new HashMap<>();
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

    // Super Reviewer
    // ----------------------------

    /**
     * @MethodName: superReviewer
     * @Param: [int]
     * @Return: void
     * @Description: This method output a graph, in which all vertices are connected based on number of their comment.
     * 3 tires are applied. The average number of comments per user is 209.
     */
    public void superReviewer() {
        System.out.println("Start create superReviewer graph...");
        graph = new HashMap<>();
        int superReviewer = -1, regularReviewer = -1, inactiveReviewer = -1;
        boolean sr = false, rr = false, ir = false;

        for (Integer customerId : customerList.keySet()) {
            int numOfMovieViewed = customerList.get(customerId).movieList.size();
            if (numOfMovieViewed < 100) {
                if (!ir) {
                    inactiveReviewer = customerId;
                    graph.put(inactiveReviewer, new ArrayList<>());
                    ir = true;
                } else {
                    assert inactiveReviewer > -1 : "inactiveReviewer not initialized!";
                    List<Integer> neighbors = graph.getOrDefault(customerId, new ArrayList<>());
                    List<Integer> inactiveReviewerNeighbors = graph.get(inactiveReviewer);
                    neighbors.add(inactiveReviewer);
                    inactiveReviewerNeighbors.add(customerId);
                    graph.put(customerId, neighbors);
                    graph.put(inactiveReviewer, inactiveReviewerNeighbors);
                }
            } else if (100 <= numOfMovieViewed && numOfMovieViewed < 300) {
                if (!rr) {
                    regularReviewer = customerId;
                    graph.put(regularReviewer, new ArrayList<>());
                    rr = true;
                } else {
                    assert regularReviewer > -1 : "regularReviewer not initialized!";
                    List<Integer> neighbors = graph.getOrDefault(customerId, new ArrayList<>());
                    List<Integer> inactiveReviewerNeighbors = graph.get(regularReviewer);
                    neighbors.add(regularReviewer);
                    inactiveReviewerNeighbors.add(customerId);
                    graph.put(customerId, neighbors);
                    graph.put(regularReviewer, inactiveReviewerNeighbors);
                }

            } else {
                if (!sr) {
                    superReviewer = customerId;
                    graph.put(superReviewer, new ArrayList<>());
                    sr = true;
                } else {
                    assert superReviewer > -1 : "superReviewer not initialized!";
                    List<Integer> neighbors = graph.getOrDefault(customerId, new ArrayList<>());
                    List<Integer> inactiveReviewerNeighbors = graph.get(superReviewer);
                    neighbors.add(superReviewer);
                    inactiveReviewerNeighbors.add(customerId);
                    graph.put(customerId, neighbors);
                    graph.put(superReviewer, inactiveReviewerNeighbors);
                }
            }
        }
        writeGraph("superReviewer");
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