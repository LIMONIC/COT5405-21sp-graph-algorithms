/*
* contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
* At least three functions should be tried with a different adjacency criteria.
* The criteria should be mentioned as comments.
* */

//以每个customer为顶点建图
//新建customer class

import java.io.*;
import java.util.*;

public class graph_make {

    Map<Integer, Customer> customerList = new HashMap<>();
    Map<Integer,List<Integer>> graph = new HashMap<>();

//    public static String filePath;
    public void readRatingFiles() throws Exception {
        /* read input */
        String filePath = "assignment_data/test.txt";
        File file = new File(filePath);

        if (file.exists()) {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);

                String lineContent = null;
                int movieId = 0;
                String[] content;
                while ((lineContent = br.readLine())!=null) {

                    if (lineContent.toCharArray()[lineContent.length() - 1] == ':'){
                        movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                    } else {
                        try {
                            content = lineContent.split(",");
                            if (content.length != 3){
                                throw new Exception();
                            }
                            int customerID = Integer.parseInt(content[0]);
                            int rating = Integer.parseInt(content[1]);
                            String date = content[2];

                            Customer currCustomer = customerList.getOrDefault(customerID, new Customer(customerID));
                            currCustomer.rateMovie(movieId, rating, date);
                            customerList.put(customerID,currCustomer);

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
        // todo: 时间复杂读好像可以优化一下，i是j的neighbour意味着j也是i的，所以i判断过j之后，到j时就不需要再来判断i了
        for (Integer i : customerList.keySet()){
            graph.put(i, new ArrayList<>());
            for(Integer j : customerList.keySet()){
                if(i == j)
                    continue;
                Set<Integer> im = customerList.get(i).movieList.keySet();
                Set<Integer> jm = customerList.get(j).movieList.keySet();
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()!=0) {
                    List<Integer> adjList = graph.getOrDefault(i, new ArrayList<>());
                    adjList.add(j);
                    graph.put(i, adjList);
                }
            }
        }
    }

    // 3 ratings in common
    public void three_ratings() {
        // todo: 时间复杂读好像可以优化一下，i是j的neighbour意味着j也是i的，所以i判断过j之后，到j时就不需要再来判断i了
        for (Integer i : customerList.keySet()){
            graph.put(i, new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(i).movieList);
            for(Integer j : customerList.keySet()){
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(j).movieList);
                if(i == j)
                    continue;
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()>=3) {
                    int n = 0;
                    for(Integer ri : im)
                        if(ml_i.get(ri).val == ml_j.get(ri).val)
                            ++n;
                    if(n>=3){
                        List<Integer> adjList = graph.getOrDefault(i, new ArrayList<>());
                        adjList.add(j);
                        graph.put(i, adjList);
                    }
                }
            }
        }
    }

    // 1 data in common
    public void one_data() {
        // todo: 时间复杂读好像可以优化一下，i是j的neighbour意味着j也是i的，所以i判断过j之后，到j时就不需要再来判断i了
        for (Integer i : customerList.keySet()){
            graph.put(i, new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(i).movieList);
            for(Integer j : customerList.keySet()){
                if(i == j)
                    continue;
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(j).movieList);
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()!=0) {
                    for(Integer ri : im){
                        int yi = ml_i.get(ri).year;
                        int mi = ml_i.get(ri).year;
                        int di = ml_i.get(ri).year;
                        int yj = ml_j.get(ri).year;
                        int mj = ml_j.get(ri).year;
                        int dj = ml_j.get(ri).year;
                        if(yi == yj && mi == mj && di == dj)
                        {
                            List<Integer> adjList = graph.getOrDefault(i, new ArrayList<>());
                            adjList.add(j);
                            graph.put(i, adjList);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
//            if (args.length < 1) {
//                System.out.println("The Input File name is not specified!");
//                System.out.println("path:[input.txt]");
//                return;
//            }
//            filePath = args[0];
            graph_make gm = new graph_make();
            gm.readRatingFiles();
            gm.three_ratings();

            for (Integer key : gm.graph.keySet()) {
                List<Integer> values = gm.graph.get(key);
                System.out.println("key: " + key + ", value: " + values.toString());
            }

            // ...
        } catch (Exception e) {
//            System.out.println("File not found at: " + args[0]);
        }
    }

}
