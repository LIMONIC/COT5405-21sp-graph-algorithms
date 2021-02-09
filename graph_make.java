/*
* contains functions to read in the movie reviews data and create a graph based on some adjacency criteria.
* At least three functions should be tried with a different adjacency criteria.
* The criteria should be mentioned as comments.
* */


import java.io.*;
import java.util.*;

public class graph_make {

    Map<Integer, Customer> customerList = new HashMap<>();
    Map<Integer,List<Integer>> graph;
    Map<Integer,Set<Integer>> movieToCustomer = new HashMap<>();
    Map<Integer,Set<Integer>> graphTest;
    Map<Integer,Set<Integer>> customerToMovie;

//    public static String filePath;
    public void readRatingFiles(String path) throws Exception {
        /* read input */
        String filePath = path;
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
                        if (movieId % 1000 == 0) {
                            System.out.println(movieId);
                        }
                        if(movieId > 16000 && movieId % 100 == 0){
                            System.out.println(movieId);
                        }

                    } else {
                        try {
                            content = lineContent.split(",");
                            if (content.length != 3){
                                throw new Exception();
                            }
                            int customerID = Integer.parseInt(content[0]);
                            int rating = Integer.parseInt(content[1]);
                            String date = content[2];

//                            Customer currCustomer = customerList.getOrDefault(customerID, new Customer(customerID));
//                            currCustomer.rateMovie(movieId, rating, date);
//                            customerList.put(customerID,currCustomer);

                            Set<Integer> customers = movieToCustomer.getOrDefault(movieId, new HashSet<>());
                            customers.add(customerID);
                            movieToCustomer.put(movieId, customers);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

//                for (int key : movieToCustomer.keySet()) {
//
//                    System.out.println("movieid:"+key+" " + movieToCustomer.get(key).toString());
//                }
/*
                //################ Test ################
//                for (int key : customerList.keySet()) {
//                    Customer c = customerList.get(key);
//                    System.out.println("id:"+c.id+" movie" + c.movieList.keySet().toString());
//                }
                Runtime runtime = Runtime.getRuntime();
                runtime.gc();
                // Calculate the used memory
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Used memory is bytes: " + memory);

                //################ Test ################
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
    }

    public void printCustomToMovie() {
        FileWriter writer;
        try {
            writer = new FileWriter("C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\customer-to-movie.txt");
            for (Integer key : customerList.keySet()) {
                Set<Integer> values = customerList.get(key).movieList.keySet();
                List<Integer> movieList = new ArrayList<>();
                movieList.addAll(values);
                Collections.sort(movieList);
//                System.out.println("key: " + key + ", value: " + values.toString());
                String movieListSteing = movieList.toString();
                writer.write(key + ": " + movieListSteing.substring(1,movieListSteing.length() - 1)+"\r\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMovieToCustomer() {
        FileWriter writer;
        try {
            writer = new FileWriter("C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\movie-to-customer.txt");
            for (Integer key : movieToCustomer.keySet()) {
                Set<Integer> customers  = movieToCustomer.get(key);
                List<Integer> customersList = new ArrayList<>();
                customersList.addAll(customers);
                Collections.sort(customersList);
//                System.out.println("key: " + key + ", value: " + values.toString());
                String customersListString = customersList.toString();
                writer.write(key + ": " + customersListString.substring(1,customersListString.length() - 1)+"\r\n");
            }


            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildGraph() {
        graphTest = new HashMap<>();
        for (int key : customerToMovie.keySet()) {
            if (!graphTest.containsKey(key)) {
                graphTest.put(key, new HashSet<>());
            }
        }

    }

    // one movie in common
    public void oneMovieTest() {
        graphTest = new HashMap<>();

        String filePath = "C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\movie-to-customer.txt";
        File file = new File(filePath);

        if (file.exists()) {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);

                String lineContent = null;


                String[] content;
                while ((lineContent = br.readLine())!=null) {
                    try {
                        content = lineContent.split(": ");
                        if (content.length != 2){
                            throw new Exception();
                        }
                        int customerID = Integer.parseInt(content[0]);
                        String[] movieListString = content[1].split(", ");
                        Set<Integer> movieList = new HashSet<>();
                        for (String s : movieListString) {
                            movieList.add(Integer.parseInt(s));
                        }

                        movieToCustomer.put(customerID, movieList);
//                            Set<Integer> movieList = customerToMovie.getOrDefault(customerID,new HashSet<>());
//                            movieList.add(movieId);
//                            customerToMovie.put(customerID, movieList);


//                            Set<Integer> customerList = movieToCustomer.getOrDefault(movieId, new HashSet<>());
//                            customerList.add(customerID);
//                            movieToCustomer.put(movieId, customerList);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//                for (int key : customerToMovie.keySet()) {
//
////                    System.out.println("customerId:"+key+" " + customerToMovie.get(key).toString());
//                }


                System.out.println(graphTest.size());
                Runtime runtime = Runtime.getRuntime();
                runtime.gc();
                // Calculate the used memory
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Used memory is bytes: " + memory);
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



                for (int movieId : movieToCustomer.keySet()) {
                    Set<Integer> customerSet = movieToCustomer.get(movieId);
                    int cnt = 0;

                    for (int customerId : customerSet) {
                        Set<Integer> currCustomerSet = new HashSet<>(customerSet);
                        Set<Integer> adjCustomer = graphTest.getOrDefault(customerId, new HashSet<>());
                        currCustomerSet.remove(customerId);
                        adjCustomer.addAll(currCustomerSet);
                        graphTest.put(customerId, currCustomerSet);
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("count" + cnt);
                        }
                    }
                    System.out.println("movie " + movieId + "finished");
                    System.out.println(graphTest.keySet().size());
                }

//        for (int key : customerList.keySet()) {
//            Customer c = customerList.get(key);
//            System.out.println("id:"+c.id+" movie" + c.movieList.keySet().toString());
//        }

            }




    public void oneMovieTest2() {
        /* read input */

        customerToMovie = new HashMap<>();
        int maxMovieNum = 0;

        String filePath = "C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\customer-to-movie.txt";
        File file = new File(filePath);

        if (file.exists()) {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);

                String lineContent = null;


                String[] content;
                while ((lineContent = br.readLine())!=null) {
                        try {
                            content = lineContent.split(": ");
                            if (content.length != 2){
                                throw new Exception();
                            }
                            int customerID = Integer.parseInt(content[0]);
                            String[] movieListString = content[1].split(", ");
                            Set<Integer> movieList = new HashSet<>();
                            for (String s : movieListString) {
                                movieList.add(Integer.parseInt(s));
                            }

                            customerToMovie.put(customerID, movieList);
//                            Set<Integer> movieList = customerToMovie.getOrDefault(customerID,new HashSet<>());
//                            movieList.add(movieId);
//                            customerToMovie.put(customerID, movieList);


//                            Set<Integer> customerList = movieToCustomer.getOrDefault(movieId, new HashSet<>());
//                            customerList.add(customerID);
//                            movieToCustomer.put(movieId, customerList);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }

//                for (int key : customerToMovie.keySet()) {
//
////                    System.out.println("customerId:"+key+" " + customerToMovie.get(key).toString());
//                }

                buildGraph();
                System.out.println(graphTest.size());
                Runtime runtime = Runtime.getRuntime();
                runtime.gc();
                // Calculate the used memory
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Used memory is bytes: " + memory);
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
        // add all vertices to graph
        graphTest = new HashMap<>();

        /*
        Integer[] id = customerToMovie.keySet().toArray(new Integer[0]);
//        for (int key : customerList.keySet()) {
//            Customer c = customerList.get(key);
//            System.out.println("id:"+c.id+" movie" + c.movieList.keySet().toString());
//        }

        int id_num = customerToMovie.keySet().size();

        for (int i = 0; i < id_num; ++i){ // number id: customer id
            //System.out.println(i + " " +id[i]);
            graphTest.putIfAbsent(id[i], new HashSet<>());// 建图， id ： list

                System.out.println(i);

            for(int j = i + 1; j < id_num; ++j){
                Set<Integer> im = new HashSet<>(customerToMovie.get(id[i]));
                Set<Integer> jm = new HashSet<>(customerToMovie.get(id[j]));
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()!=0) {
                    Set<Integer> adjList_i = graphTest.getOrDefault(id[i], new HashSet<>());
                    Set<Integer> adjList_j = graphTest.getOrDefault(id[j], new HashSet<>());
                    adjList_i.add(id[j]);
                    adjList_j.add(id[i]);
                    graphTest.put(id[i], adjList_i);
                    graphTest.put(id[j], adjList_j);
                }
            }
        }
*/
//        for (int movieID : movieToCustomer.keySet()) {
//            Set<Integer> customersRatedThisMovie = new HashSet<>(movieToCustomer.get(movieID));
//            int cot = 0;
//            for (int customerId : movieToCustomer.get(movieID)) {
//                customersRatedThisMovie.remove(customerId);
//                Set<Integer> adjCustomer = graphTest.getOrDefault(customerId, new HashSet<>());
//                adjCustomer.addAll(customersRatedThisMovie);
//                graphTest.put(customerId, adjCustomer);
//                cot++;
//                if (cot % 1000 == 0) {
//                    System.out.println("count" + cot);
//                }
//            }
//            System.out.println("movie "+movieID+ "finished");
//            System.out.println(graphTest.keySet().size());
//        }


    }

    public void one_movie() {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
//        for (int key : customerList.keySet()) {
//            Customer c = customerList.get(key);
//            System.out.println("id:"+c.id+" movie" + c.movieList.keySet().toString());
//        }

        int id_num = customerList.keySet().size();

        for (int i = 0; i < id_num; ++i){ // number id: customer id
            //System.out.println(i + " " +id[i]);
            graph.putIfAbsent(id[i], new ArrayList<>());// 建图， id ： list

            for(int j = i + 1; j < id_num; ++j){
                Set<Integer> im = new HashSet<>(customerList.get(id[i]).movieList.keySet());
                Set<Integer> jm = new HashSet<>(customerList.get(id[j]).movieList.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()!=0) {
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
    public void threeRatingTest() {
        graphTest = new HashMap<>();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i){
            graph.putIfAbsent(id[i], new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for(int j = i + 1; j < id_num; ++j){
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
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

    // 3 ratings in common
    public void three_ratings() {
        graph.clear();
        Integer[] id = customerList.keySet().toArray(new Integer[0]);
        int id_num = customerList.keySet().size();
        for (int i = 0; i < id_num; ++i){
            graph.putIfAbsent(id[i], new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for(int j = i + 1; j < id_num; ++j){
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
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
        for (int i = 0; i < id_num; ++i){
            graph.putIfAbsent(id[i], new ArrayList<>());
            Map<Integer, Rating> ml_i = new HashMap<>(customerList.get(id[i]).movieList);
            for(int j = i + 1; j < id_num; ++j){
                Map<Integer, Rating> ml_j = new HashMap<>(customerList.get(id[j]).movieList);
                Set<Integer> im = new HashSet<>(ml_i.keySet());
                Set<Integer> jm = new HashSet<>(ml_j.keySet());
                im.retainAll(jm);
                // If two customers have rated one movie in common, they are adjacent
                if(im.size()!=0) {
                    for(Integer ri : im){
                        int yi = ml_i.get(ri).year;
                        int mi = ml_i.get(ri).month;
                        int di = ml_i.get(ri).day;
                        int yj = ml_j.get(ri).year;
                        int mj = ml_j.get(ri).month;
                        int dj = ml_j.get(ri).day;
                        if(yi == yj && mi == mj && di == dj)
                        {
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

    public static void main(String[] args) {
        graph_make gm = new graph_make();
        String path = null;
        for (int i = 1; i < 5; i++) {
            path = "C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\ratings_data_"+i+".txt";
            System.out.println(path);
            try {
//                gm.readRatingFiles(path);
//                gm.printMovieToCustomer();

            } catch (Exception e){
                System.out.println("error");
            }
        }

        gm.oneMovieTest();
//        gm.oneMovieTest2();
//        gm.buildGraph();

////        gm.oneMovieTest2();
//        for (int key : gm.graphTest.keySet()) {
//            Set<Integer> c = gm.graphTest.get(key);
//            System.out.println("key:"+key+" " + c.size()+" " +c.toString());
//        }
//        System.out.println(gm.graphTest.keySet().size());
    }

}
