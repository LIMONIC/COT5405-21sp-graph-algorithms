import java.io.*;
import java.util.*;

public class real_test {

    Map<Integer, List<Integer>> graph;

    public static void main(String[] args) {
        try {
            int option = 1;
            System.out.println("Input: [filename] [option]");
            System.out.println("option: ");
            System.out.println("\t1: one movie (default)");
            System.out.println("\t2: three same ratings");
            System.out.println("\t3: one data");
            System.out.println("\t4: run all three methods");
            System.out.println("Your input:");
//            if (args.length < 1) {
//                System.out.println("The Input File name is not specified!");
//                System.out.println("path:[input.txt]");
//                return;
//            }
//            else if (args.length == 2){
//                option = args[1];
//            }
//            else if (args.length > 2){
//                System.out.println("Too many arguments!");
//            }
//            filePath = args[0];
            graph_make gm = new graph_make();
            gm.readRatingFiles();
            graph_operations op = new graph_operations();
            File writeName;
            long startTime, endTime;
            Runtime runtime;
            long memory;

            /*
             // output user list
            try {
                writeName = new File("user_list_1.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for(Integer uid : gm.customerList.keySet()){
                        out.write(uid + ":\r\n");
                        Customer u = gm.customerList.get(uid);
                        for(Integer mid : u.movieList.keySet()){
                            Rating m = u.movieList.get(mid);
                            out.write(mid + "," + m.val + "," + m.year + "-" + m.month + "-" + m.day + "\r\n");
                        }
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("User list output finished!");
             */


            /*
                        // Super Reviewer
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("Super Reviewer:");
            gm.super_reviewer(500);

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("Super_Reviewer_graph.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            runtime = Runtime.getRuntime();
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory is bytes: " + memory);
             */

            /*
                        // Same Day
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("Same Data:");
            gm.same_data(2004, 8,4);

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("Same_Data_graph_1_2004_8_4.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            runtime = Runtime.getRuntime();
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory is bytes: " + memory);
             */

            /*
                        // Top k movie
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("Top_k_Movie:");
            gm.top_k_movie(1);

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("Top_k_Movie_1_1st.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            runtime = Runtime.getRuntime();
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory is bytes: " + memory);
             */

            // Coldest N movies
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("coldest_movies:");
            gm.coldest_movies();

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： " + (endTime - startTime) + "ms");

            // output the graph
            try {
                writeName = new File("coldest_movies_1_2.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                FileWriter writer = new FileWriter(writeName);
                for (Integer key : gm.graph.keySet()) {
                    List<Integer> values = gm.graph.get(key);
                    Collections.sort(values);
//                System.out.println("key: " + key + ", value: " + values.toString());
                    String movieListString = values.toString();
                    writer.write(key + ": " + movieListString.substring(1, movieListString.length() - 1) + "\r\n");
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            startTime = System.currentTimeMillis();
            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： " + (endTime - startTime) + "ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for (List<Integer> c : cp) {
                if (c.size() > 1) {
                    System.out.print(c.toString());
                }

            }
            System.out.println();

            runtime = Runtime.getRuntime();
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory is bytes: " + memory);

            /*
                       // one_movie
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("one movie:");
            gm.one_movie();

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("one_movie_graph.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();


            // three_ratings
            // ----------------------------------------
            startTime = System.currentTimeMillis();
            System.out.println("three ratings:");

            gm.three_ratings();

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("three_ratings_graph.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            // connected components
            cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            // one_data
            // ----------------------------------------
            startTime = System.currentTimeMillis();
            System.out.println("one data:");

            gm.one_data();

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");

            // output the graph
            try {
                writeName = new File("one_data_graph.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Integer key : gm.graph.keySet()) {
                        List<Integer> values = gm.graph.get(key);
                        out.write(key + ":" + StringUtils.strip(values.toString(),"[]") + "\r\n");
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // connected components
            startTime = System.currentTimeMillis();
            cp = op.connected_components(gm.graph);
            endTime = System.currentTimeMillis();
            System.out.println("connected_components running time： "+(endTime-startTime)+"ms");

            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();
             */

            // ...
        } catch (Exception e) {
//            System.out.println("File not found at: " + args[0]);
        }
    }

    public void readGraph(String filePath) {
        graph = new HashMap<>();

        System.out.println("Start Reading file: " + filePath);
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                String[] content;
                String[] adjCustomerString;

                while ((lineContent = br.readLine()) != null) {
                    try {
                        content = lineContent.split(": ");
                        if (content.length != 2) {
                            throw new Exception();
                        }
                        int customerID = Integer.parseInt(content[0]);
                        adjCustomerString = content[1].split(", ");

                        List<Integer> adjCustomer = graph.getOrDefault(customerID, new ArrayList<>());
                        for (String s : adjCustomerString) {
                            int id = Integer.parseInt(s);
                            adjCustomer.add(id);
                        }
                        graph.put(customerID, adjCustomer);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        System.out.println("Graph successfully read");
    }
}
