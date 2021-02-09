import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.List;

public class real_test {
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

            /*
            // output user list
            try {
                writeName = new File("user_list_1.txt");
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

            // Super Reviewer
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("Super Reviewer:");
            gm.super_reviewer(265);

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


            // Same Data
            // ----------------------------------------
            startTime = System.currentTimeMillis();

            System.out.println("Same Data:");
            gm.same_data(2004,8,5);

            endTime = System.currentTimeMillis();
            System.out.println("graph construction running time： "+(endTime-startTime)+"ms");


            // output the graph
            try {
                writeName = new File("Same_Data_graph.txt");
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
}
