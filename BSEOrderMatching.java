import java.util.*;

public class BSEOrderMatching {

    static void addEdge(List<List<Integer>> graph, int[] indegree,
                        int u, int v) {
        graph.get(u).add(v);
        indegree[v]++;
    }

    public static void main(String[] args) {

        String[] orders = {
                "m1", "s1", "b1", "b2", "b3", "c1", "c2"
        };

        int n = orders.length;

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[n];

        addEdge(graph, indegree, 0, 1);
        addEdge(graph, indegree, 0, 2);
        addEdge(graph, indegree, 0, 3);
        addEdge(graph, indegree, 0, 4);
        addEdge(graph, indegree, 2, 5);
        addEdge(graph, indegree, 3, 5);
        addEdge(graph, indegree, 4, 6);
        addEdge(graph, indegree, 1, 6);

        System.out.println("Initial In-Degrees:");
        for (int i = 0; i < n; i++) {
            System.out.println(orders[i] + " = " + indegree[i]);
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<String> topoOrder = new ArrayList<>();

        System.out.println("\nKahn's Algorithm Trace:");

        int step = 1;

        while (!queue.isEmpty()) {

            int u = queue.poll();

            System.out.println("\nStep " + step++);
            System.out.println("Removed Node: " + orders[u]);

            topoOrder.add(orders[u]);

            for (int v : graph.get(u)) {

                indegree[v]--;

                System.out.println(
                        "Updated In-Degree of "
                                + orders[v]
                                + " = "
                                + indegree[v]
                );

                if (indegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        System.out.println("\nValid Topological Order:");

        for (String order : topoOrder) {
            System.out.print(order + " ");
        }

        System.out.println();

        int V = n;
        int E = 8;

        System.out.println("\nTime Complexity:");
        System.out.println("O(V + E)");
        System.out.println("O(" + V + " + " + E + ")");
        System.out.println("O(" + (V + E) + ")");

        System.out.println("\nBSE Application:");
        System.out.println(
                "Orders are processed in topological order " +
                "so that all prerequisite orders are executed " +
                "before dependent orders. This guarantees that " +
                "market updates, stop-loss orders, and basket-leg " +
                "fills are completed before combined orders are processed."
        );
    }
}