import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealTest {
    Map<Integer, List<Integer>> graph;

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

    public static void main(String[] args) {
        RealTest rt = new RealTest();
        rt.readGraph("oneMovieInCommon.txt");
        graph_operations go = new graph_operations();

        long startTime = System.currentTimeMillis();
        List<List<Integer>> connectedComponents = go.connected_components(rt.graph);
        long endTime = System.currentTimeMillis();
        System.out.println("connected_components running time： " + (endTime - startTime) + "ms");

        System.out.println("The number of connected components: " + connectedComponents.size());
        System.out.print("Size of each connected component: ");
        for(List<Integer> c : connectedComponents){
            System.out.print(c.size() + " ");
        }
//        System.out.print("print connected component: ");
//        for (List<Integer> c : connectedComponents) {
//            if (c.size() > 1) {
//                System.out.print(c.toString());
//            }
//        }
        System.out.println();
        Runtime runtime;
        runtime = Runtime.getRuntime();
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
    }
}
