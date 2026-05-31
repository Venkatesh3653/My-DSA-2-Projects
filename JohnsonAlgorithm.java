import java.util.*;

public class JohnsonAlgorithm {

    static class Edge {
        int from, to, weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static class Pair {
        int node, dist;

        Pair(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    static final int INF = Integer.MAX_VALUE;

    static int[] dijkstra(int n, List<List<Edge>> adj, int source) {

        int[] dist = new int[n];
        Arrays.fill(dist, INF);

        dist[source] = 0;

        PriorityQueue<Pair> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));

        pq.offer(new Pair(source, 0));

        while (!pq.isEmpty()) {

            Pair cur = pq.poll();

            int u = cur.node;
            int d = cur.dist;

            if (d > dist[u])
                continue;

            for (Edge e : adj.get(u)) {

                int v = e.to;
                int w = e.weight;

                if (dist[u] != INF && dist[u] + w < dist[v]) {

                    dist[v] = dist[u] + w;

                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        return dist;
    }

    static int[][] johnson(int n, List<Edge> edges) {

        List<Edge> augmented = new ArrayList<>(edges);

        for (int i = 0; i < n; i++) {
            augmented.add(new Edge(n, i, 0));
        }

        int[] h = new int[n + 1];

        Arrays.fill(h, INF);

        h[n] = 0;

        for (int i = 0; i < n; i++) {

            boolean updated = false;

            for (Edge e : augmented) {

                if (h[e.from] != INF &&
                        h[e.from] + e.weight < h[e.to]) {

                    h[e.to] = h[e.from] + e.weight;
                    updated = true;
                }
            }

            if (!updated)
                break;
        }

        for (Edge e : augmented) {

            if (h[e.from] != INF &&
                    h[e.from] + e.weight < h[e.to]) {

                throw new RuntimeException(
                        "Negative Cycle Detected");
            }
        }

        List<Edge> reweightedEdges = new ArrayList<>();

        for (Edge e : edges) {

            int newWeight =
                    e.weight + h[e.from] - h[e.to];

            reweightedEdges.add(
                    new Edge(e.from, e.to, newWeight)
            );
        }

        List<List<Edge>> adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (Edge e : reweightedEdges) {
            adj.get(e.from).add(e);
        }

        int[][] dist = new int[n][n];

        for (int u = 0; u < n; u++) {

            int[] d = dijkstra(n, adj, u);

            for (int v = 0; v < n; v++) {

                if (d[v] == INF)
                    dist[u][v] = INF;
                else
                    dist[u][v] =
                            d[v] - h[u] + h[v];
            }
        }

        System.out.println("h(v) Values:");

        for (int i = 0; i < n; i++) {
            System.out.println("h(c" + (i + 1) + ") = " + h[i]);
        }

        System.out.println("\nReweighted Edges:");

        for (Edge e : reweightedEdges) {

            System.out.println(
                    "c" + (e.from + 1)
                            + " -> c" + (e.to + 1)
                            + " = " + e.weight
            );
        }

        return dist;
    }

    public static void main(String[] args) {

        int n = 5;

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 2, 8));
        edges.add(new Edge(1, 2, -3));
        edges.add(new Edge(1, 3, 5));
        edges.add(new Edge(1, 4, -2));
        edges.add(new Edge(2, 3, 2));
        edges.add(new Edge(3, 4, 6));

        int[][] dist = johnson(n, edges);

        System.out.println("\nAll-Pairs Shortest Paths:");

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println("\nTime Complexity:");
        System.out.println("Johnson = O(VE + V(E log V))");
        System.out.println("Sparse Graphs: O(V² log V)");
        System.out.println("Floyd-Warshall = O(V³)");
    }
}