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

            // one_movie
            // ----------------------------------------
            System.out.println("one movie:");
            gm.one_movie();

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

            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();
            // three_ratings
            // ----------------------------------------
            System.out.println("three ratings:");
            gm.three_ratings();

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

            // connected components
            cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            // one_data
            // ----------------------------------------
            System.out.println("one data:");
            gm.one_data();

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
            cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }
            System.out.println();

            // ...
        } catch (Exception e) {
//            System.out.println("File not found at: " + args[0]);
        }
    }
}
