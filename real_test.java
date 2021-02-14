import java.io.*;
import java.util.*;

public class real_test {

    Map<Integer, Set<Integer>> graph;

    public static void main(String[] args) throws Exception{
        int option = 1;
        String filePath = "assignment1-data/";
        try {
            System.out.println("This class generates graph, outputs a txt file and find connected components on the graph.");
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
            System.out.println("\t2: Super reviewer vs active reviewer vs regular reviewer vs inactive reviewer");
            System.out.println("\t3: Tolerant Reviewer vs Normal Reviewer vs Picky Reviewer");
            System.out.println("Your input:");
            Scanner in = new Scanner(System.in);
            option = in.nextInt();
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        long startTime, endTime, graphMakeMemory, graphConnectMemory;
        Runtime runtime;
        graph_make gm = new graph_make();
        startTime = System.currentTimeMillis();
        String graphName = "";
        switch (option) {
            case 1:
                gm.oneMovieInCommon(filePath);
                graphName = "oneMovieInCommon";
                break;
            case 2:
                gm.readRatingFiles(filePath);
                gm.superReviewer();
                graphName = "superReviewer";
                break;
            case 3:
                gm.readRatingFiles(filePath);
                gm.goodReview();
                graphName = "goodReview";
                break;
            default:
                System.out.println("Option dose not exist!");
        }
        endTime = System.currentTimeMillis();
        System.out.println( graphName + " graph make running time： " + (endTime - startTime) + "ms");

        runtime = Runtime.getRuntime();
        runtime.gc();
        // Calculate the used memory
        graphMakeMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + graphMakeMemory);

        graph_operations go = new graph_operations();
        startTime = System.currentTimeMillis();
        List<List<Integer>> connectedComponents = go.connected_components(gm.graph);
        endTime = System.currentTimeMillis();
        System.out.println("connected_components running time： " + (endTime - startTime) + "ms");

        System.out.println("The number of connected components: " + connectedComponents.size());
        System.out.print("Size of each connected component: ");
        for (List<Integer> c : connectedComponents) {
            System.out.print(c.size() + " ");
        }
        System.out.println();

        runtime = Runtime.getRuntime();
        runtime.gc();
        // Calculate the used memory
        graphConnectMemory = runtime.totalMemory() - runtime.freeMemory() - graphMakeMemory;    // 不知道这里是否要减去，明天我查一下
        System.out.println("Used memory is bytes: " + graphConnectMemory);
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

                        Set<Integer> adjCustomer = graph.getOrDefault(customerID, new HashSet<>());
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
