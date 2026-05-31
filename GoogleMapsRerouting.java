import java.util.*;

public class GoogleMapsRerouting {

    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node {
        int vertex, dist;

        Node(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
    }

    static final int INF = Integer.MAX_VALUE;

    static class Result {
        int[] dist;
        int[] parent;

        Result(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    static Result dijkstra(int n, List<List<Edge>> adj, int source) {
        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));

        pq.offer(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            int u = cur.vertex;
            int d = cur.dist;

            if (d > dist[u])
                continue;

            for (Edge e : adj.get(u)) {
                int v = e.to;
                int w = e.weight;

                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;

                    pq.offer(new Node(v, dist[v]));
                }
            }
        }

        return new Result(dist, parent);
    }

    static void addEdge(List<List<Edge>> adj, int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w));
    }

    static void printPath(int[] parent, int dest, String[] names) {
        List<String> path = new ArrayList<>();

        for (int cur = dest; cur != -1; cur = parent[cur]) {
            path.add(names[cur]);
        }

        Collections.reverse(path);
        System.out.println(String.join(" -> ", path));
    }

    public static void main(String[] args) {

        String[] names = {
                "IND", "KOR", "MGR", "HSR",
                "BTM", "JPN", "SRJ", "EC"
        };

        int IND = 0;
        int KOR = 1;
        int MGR = 2;
        int HSR = 3;
        int BTM = 4;
        int JPN = 5;
        int SRJ = 6;
        int EC = 7;

        int n = 8;

        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        addEdge(graph, IND, KOR, 8);
        addEdge(graph, IND, JPN, 14);
        addEdge(graph, KOR, MGR, 6);
        addEdge(graph, KOR, HSR, 5);
        addEdge(graph, HSR, MGR, 7);
        addEdge(graph, HSR, BTM, 4);
        addEdge(graph, HSR, SRJ, 6);
        addEdge(graph, MGR, BTM, 9);
        addEdge(graph, BTM, EC, 12);
        addEdge(graph, SRJ, EC, 15);
        addEdge(graph, JPN, SRJ, 8);

        Result before = dijkstra(n, graph, KOR);

        System.out.println("Before Closure");
        System.out.println("Distance: " + before.dist[EC]);
        System.out.print("Path: ");
        printPath(before.parent, EC, names);

        List<List<Edge>> updatedGraph = new ArrayList<>();

        for (int i = 0; i < n; i++)
            updatedGraph.add(new ArrayList<>());

        addEdge(updatedGraph, IND, KOR, 8);
        addEdge(updatedGraph, IND, JPN, 14);
        addEdge(updatedGraph, KOR, MGR, 6);
        addEdge(updatedGraph, KOR, HSR, 5);
        addEdge(updatedGraph, HSR, BTM, 4);
        addEdge(updatedGraph, HSR, SRJ, 6);
        addEdge(updatedGraph, MGR, BTM, 9);
        addEdge(updatedGraph, BTM, EC, 12);
        addEdge(updatedGraph, SRJ, EC, 15);
        addEdge(updatedGraph, JPN, SRJ, 8);

        Result after = dijkstra(n, updatedGraph, KOR);

        System.out.println("\nAfter Closure");
        System.out.println("Distance: " + after.dist[EC]);
        System.out.print("Path: ");
        printPath(after.parent, EC, names);
    }
}

