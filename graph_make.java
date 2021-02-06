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

//    public static String filePath;
    public void readRatingFiles() throws Exception {
        /* read input */

         String filePath = "C:\\Users\\ztyam\\OneDrive - University of Florida\\0_UF Class\\2021 Spring\\01_AOA\\Assignment\\01\\assignment1-data\\ratings_data_1.txt";
        File file = new File(filePath);

        Map<Integer, Customer> customerList = new HashMap<>();

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
            // ...
        } catch (Exception e) {
//            System.out.println("File not found at: " + args[0]);
        }
    }

}
