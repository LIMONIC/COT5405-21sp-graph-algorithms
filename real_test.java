import java.io.*;
import java.util.*;

public class real_test {

    Map<Integer, Set<Integer>> graph;
    String filePath;
    String graphName = "";

    public static void main(String[] args) throws Exception{
        int option = 1;
        real_test rt = new real_test();
        rt.filePath = "";
        try {
            System.out.println("This class generates graph, outputs a txt file and find connected components on the graph.");
            if (args.length < 1) {
                // System.out.println("The Input File name is not specified!");
                // System.out.println("This class take file path as argument.");
                System.out.println("Default: Read the ratings data file from the same fold with the java_file");
                return;
            } else if (args.length == 1) {
                rt.filePath = args[0];
                File file = new File(rt.filePath);
                if (!file.exists()) {
                    System.out.println("File not exist! Check your input.");
                }
            } else if (args.length > 1) {
                System.out.println("Too many arguments!");
            }
            System.out.println("Options: ");
            System.out.println("\t1: Read Netflix data, make graph and connect components");
            System.out.println("\t2: Read graph data and connect components");
            System.out.println("\t3: Conduct autoTest()");
            System.out.println("Your input");
            Scanner in = new Scanner(System.in);
            option = in.nextInt();
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        long startTime, endTime, memory;
        Runtime runtime;
        startTime = System.currentTimeMillis();
        switch (option){
            case 1:
                rt.option1();
                break;
            case 2:
                rt.option2();
                break;
            case 3:
                rt.autoTest();
                break;
        }
        endTime = System.currentTimeMillis();
        System.out.println( rt.graphName + " graph make running time： " + (endTime - startTime) + "ms");

        runtime = Runtime.getRuntime();
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Graph making used memory is bytes: " + memory);
        System.out.println("Finished!");
    }

    /**
     * @MethodName: option1
     * @Param: []
     * @Return: void
     * @Description: Read Netflix data, make graph and find connected components.
     */
    public void option1() throws Exception{
        int option = -1;
        try{
            System.out.println("\t1: At least watched one movie in common");
            System.out.println("\t2: Super reviewer vs active reviewer vs regular reviewer vs inactive reviewer");
            System.out.println("\t3: Tolerant Reviewer vs Objective Reviewer vs Picky Reviewer");
            System.out.println("Your input:");
            Scanner in = new Scanner(System.in);
            option = in.nextInt();
        } catch (Exception e){
            System.out.println("Error");
        }

        graph_make gm = new graph_make();
        switch (option) {
            case 1:
                if(gm.movieToCustomer == null)
                    gm.movieToCustomer = new HashMap<>();
                if(gm.customerSet == null)
                    gm.customerSet = new HashSet<>();
                readMovieToCustomer(filePath, 0, gm.movieToCustomer, gm.customerSet);
                gm.oneMovieInCommon();
                graphName = "oneMovieInCommon";
                break;
            case 2:
                if(gm.customerList == null)
                    gm.customerList = new HashMap<>();
                readCustomerList(filePath, 0, gm.customerList);
                gm.superReviewer();
                graphName = "superReviewer";
                break;
            case 3:
                if(gm.customerList == null)
                    gm.customerList = new HashMap<>();
                readCustomerList(filePath, 0, gm.customerList);
                gm.goodReview();
                graphName = "goodReview";
                break;
            default:
                System.out.println("Option dose not exist!");
        }
        graph = gm.graph;
        writeGraph(graphName);
        connectedComponentExp(graphName);
    }

    /**
     * @MethodName: option2
     * @Param: []
     * @Return: void
     * @Description: Read graph data and find connected components
     */
    public void option2(){
        readGraph(filePath);
        connectedComponentExp(filePath);
    }

    /**
     * @MethodName: connectedComponentExp
     * @Param: [java.lang.String]
     * @Return: void
     * @Description: Perform experiment of connected components on given graph.
     */
    public void connectedComponentExp(String name) {
        List<List<Integer>> connectedComponents = new ArrayList<>();
        graph_operations go = new graph_operations();
        connectedComponents = go.connected_components(graph);
        // output
        System.out.println("Start write result of connected component of " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + "_connectedComponents.txt");

            for (List<Integer> cc : connectedComponents) {
                writer.write(cc.toString() + "\r\n");
            }

            writer.write("The number of connected components: " + connectedComponents.size() + "\r\n");
            writer.write("Size of each connected component: \r\n");
            for (List<Integer> c : connectedComponents) {
                writer.write(c.size() + "\r\n");
            }
            System.out.println();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("write finished!");
    }

    public void autoTest() throws Exception{
        graph_make gm = new graph_make();
        List<String> nameList = new ArrayList<>();
        //nameList.add("oneMovieInCommon");
        nameList.add("superReviewer");
        nameList.add("goodReview");
        long mgStartTime = 0, ccStartTime = 0, stopTime, memory;
        Runtime runtime;
        FileWriter writer;
        try {
            writer = new FileWriter("autoTest.txt");

            for(String name : nameList){
                writer.write("\r\n\r\n********************************************\r\n");
                writer.write(name + ":\r\n");
                for(long i = 10000000; i < 110000000; i+=10000000){
                    writer.write("---------------------------------------------\r\n");
                    writer.write("line number:" + i + ":\r\n");
                    // read Netflix
                    writer.write(".............................................\r\n");
                    writer.write("Read Netflix Dta:\r\n");
                    switch (name){
                        case "oneMovieInCommon":
                            if(gm.movieToCustomer == null)
                                gm.movieToCustomer = new HashMap<>();
                            if(gm.customerSet == null)
                                gm.customerSet = new HashSet<>();
                            readMovieToCustomer(filePath, i, gm.movieToCustomer, gm.customerSet);
                            mgStartTime = System.nanoTime();
                            gm.oneMovieInCommon();
                            break;
                        case "superReviewer":
                            if(gm.customerList == null)
                                gm.customerList = new HashMap<>();
                            readCustomerList(filePath, i, gm.customerList);
                            mgStartTime = System.nanoTime();
                            gm.superReviewer();
                            break;
                        case "goodReview":
                            if(gm.customerList == null)
                                gm.customerList = new HashMap<>();
                            readCustomerList(filePath, i, gm.customerList);
                            mgStartTime = System.nanoTime();
                            gm.goodReview();
                            break;
                    }
                    graph = gm.graph;
                    ccStartTime = System.nanoTime();
                    connectedComponentExp("netflix_" + name + "_" + i/10000000);
                    stopTime = System.nanoTime();
                    writeGraph(name);
                    writer.write("make_graph run time(ms):" + ((ccStartTime - mgStartTime) / 10 / 1000) + "ms\r\n");
                    writer.write("connected_componnets run time(ms):" + ((stopTime - ccStartTime) / 10 / 1000) + "ms\r\n");
                    writer.write("total run time(ms):" + ((stopTime - mgStartTime) / 10 / 1000) + "ms\r\n");
                    runtime = Runtime.getRuntime();
                    runtime.gc(); // garbage collector
                    memory = runtime.totalMemory() - runtime.freeMemory();
                    writer.write("Used memory:" + memory + "bytes\r\n");

                    // read graph
                    /*
                    writer.write(".............................................\r\n");
                    writer.write("Read Graph Data:\r\n");
                    readGraph(name+".txt");
                    startTime = System.nanoTime();
                    connectedComponentExp("graph" + name + "_" + i/10000000);
                    stopTime = System.nanoTime();
                    writer.write("Run Time(ms):" + ((stopTime - startTime) / 10 / 1000) + "ms\r\n");
                    runtime = Runtime.getRuntime();
                    runtime.gc(); // garbage collector
                    memory = runtime.totalMemory() - runtime.freeMemory();
                    writer.write("Used memory:" + memory + "bytes\r\n");
                     */
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * @MethodName: readCustomerList
     * @Param: []
     * @Return: void
     * @Description: Read data files
     */
    public void readCustomerList(String path, long lineNum, Map<Integer, Customer> customerList) throws Exception {
        customerList.clear();
        long num = 0;
        boolean breakOrNot = false;
        for (int i = 1; i < 5; i++) {
            if(breakOrNot)
                break;
            String filePath = path + "ratings_data_" + i + ".txt";
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
                        if(++num >= lineNum && lineNum != 0){
                            breakOrNot = true;
                            break;
                        }
                        if (lineContent.toCharArray()[lineContent.length() - 1] == ':') {
                            movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                            /*
                            if (movieId % 1000 == 0) {
                                System.out.println(movieId + " movies have been read.");
                            }
                             */
                        } else {
                            try {
                                content = lineContent.split(",");
                                if (content.length != 3) {
                                    throw new Exception();
                                }
                                int customerID = Integer.parseInt(content[0]);
                                int rating = Integer.parseInt(content[1]);
                                String date = content[2];

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
            System.out.println(num + "lines data has been read");
        }
    }

    private void readMovieToCustomer(String path, long lineNum, Map<Integer, Set<Integer>> movieToCustomer, Set<Integer> customerSet){
        movieToCustomer.clear();
        customerSet.clear();
        long num = 0;
        boolean breakOrNot = false;

        for (int i = 1; i < 5; i++) {
            if(breakOrNot)
                break;
            String filePath = path + "ratings_data_" + i + ".txt";
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
                        if(++num >= lineNum && lineNum != 0){
                            breakOrNot = true;
                            break;
                        }
                        if (lineContent.toCharArray()[lineContent.length() - 1] == ':') {
                            movieId = Integer.parseInt(lineContent.substring(0, lineContent.length() - 1));
                            /*
                            if (movieId % 1000 == 0) {
                                System.out.println(movieId + " movies have been read.");
                            }
                             */
                        } else {
                            try {
                                content = lineContent.split(",");
                                if (content.length != 3) {
                                    throw new Exception();
                                }
                                int customerID = Integer.parseInt(content[0]);
                                customerSet.add(customerID);
                                Set<Integer> customers = movieToCustomer.getOrDefault(movieId, new HashSet<>());
                                customers.add(customerID);
                                movieToCustomer.put(movieId, customers);
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
            System.out.println(num + " lines data has been read");
        }
    }

    /**
     * @MethodName: writeGraph
     * @Param: [String]
     * @Return: void
     * @Description: Output a txt file with given name that contains the structure of the graph. graph represented by adjacency list.
     */
    public void writeGraph(String name) {
        System.out.println("Start writing graph " + name + "...");
        FileWriter writer;
        try {
            writer = new FileWriter(name + ".txt");
            for (Integer customer : graph.keySet()) {
                Set<Integer> adjCustomers = graph.get(customer);
                String adjCustomersString = adjCustomers.toString();
                writer.write(customer + ": " + adjCustomersString.substring(1, adjCustomersString.length() - 1) + "\r\n");

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
    }

}
