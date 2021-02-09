/*
 * contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
 * At least three functions should be tried with a different adjacency criteria.
 * The criteria should be mentioned as comments.
 * */

//以每个customer为顶点建图
//新建customer class

import java.io.*;
import java.util.*;
import java.lang.Object;

public class graph_make {

    Map<Integer, Customer> customerList = new HashMap<>();
    Map<Integer, List<Integer>> graph = new HashMap<>();

    Map<Integer, List<user>> movieList = new HashMap<>();

    //    public static String filePath;
    public void readRatingFiles() throws Exception {
        /* read input */
        String filePath = "assignment/ratings_data_1.txt";
        File file = new File(filePath);

        if (file.exists()) {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);

                String lineContent = null;
                int movieId = 0;
                String[] content;
                while ((lineContent = br.readLine()) != null) {

                    if (lineContent.toCharArray()[lineContent.length() - 1] == ':') {
                        movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                    } else {
                        try {
                            content = lineContent.split(",");
                            if (content.length != 3) {
                                throw new Exception();
                            }
                            int customerID = Integer.parseInt(content[0]);
                            int rating = Integer.parseInt(content[1]);
                            String date = content[2];

                            user u = new user(customerID, rating);
                            movieList.get(movieId).add(u);

                            Customer currCustomer = customerList.getOrDefault(customerID, new Customer(customerID));
                            currCustomer.rateMovie(movieId, rating, date);
                            customerList.put(customerID, currCustomer);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                /*
                ################ Test ################
                for (int key : customerList.keySet()) {
                    Customer c = customerList.get(key);
                    System.out.println("id:"+c.id+" movie" + c.movieList.keySet().toString());
                }
                Runtime runtime = Runtime.getRuntime();
                runtime.gc();
                // Calculate the used memory
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Used memory is bytes: " + memory);

                ################ Test ################
                */

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

        Map<Integer, Integer> input = new HashMap<>(); // input, freq
        //...

    }

    // one movie in common
    public void one_movie() {
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
    public void super_reviewer(int N) {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i) {
            graph.putIfAbsent(id[i], new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            if (ml_i.size() < N)
                continue;
            for (int j = i + 1; j < id_num; ++j) {
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
                // If two customers have rated one movie in common, they are adjacent
                if (ml_j.size() > N) {
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

    // Top k movies
    // ----------------------------------
    public void top_k_movie(int k) {
        graph.clear();
        Map<Double, Integer> ave_score = new TreeMap<>();
        for (Integer mid : movieList.keySet()) {
            double ave = 0;
            for (user u : movieList.get(mid)) {
                ave += u.score;
            }
            ave /= movieList.get(mid).size();
            ave_score.put(ave, mid);
        }

        Iterator<Double> it= ave_score.keySet().iterator();

        // connect users in top k list
        List<Integer> graph_list = new ArrayList<Integer>();
        for(int i = 0; i < k && it.hasNext(); ++i){
            for(user uid : movieList.get(ave_score.get(it.next())))
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



}