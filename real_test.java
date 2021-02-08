import java.util.List;

public class real_test {
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
            graph_operations op = new graph_operations();

            // one_movie
            // ----------------------------------------
            System.out.println("one movie:");
            gm.one_movie();
            /*
            for (Integer key : gm.graph.keySet()) {
                List<Integer> values = gm.graph.get(key);
                System.out.println("key: " + key + ", value: " + values.toString());
            }
             */

            // connected components
            List<List<Integer>> cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }

            // three_ratings
            // ----------------------------------------
            System.out.println("three ratings:");
            gm.three_ratings();
            /*
            for (Integer key : gm.graph.keySet()) {
                List<Integer> values = gm.graph.get(key);
                System.out.println("key: " + key + ", value: " + values.toString());
            }
             */

            // connected components
            cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }

            // one_data
            // ----------------------------------------
            System.out.println("one data:");
            gm.one_data();
            /*
            for (Integer key : gm.graph.keySet()) {
                List<Integer> values = gm.graph.get(key);
                System.out.println("key: " + key + ", value: " + values.toString());
            }
             */

            // connected components
            cp = op.connected_components(gm.graph);
            System.out.println("The number of connected components: " + cp.size());
            System.out.print("Size of each connected component: ");
            for(List<Integer> c : cp){
                System.out.print(c.size() + " ");
            }

            // ...
        } catch (Exception e) {
//            System.out.println("File not found at: " + args[0]);
        }
    }
}
