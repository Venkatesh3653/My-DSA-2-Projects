
import java.util.*;

public class SlackGraphAnalysis {

    static class UnionFind {
        int[] parent, rank, size;
        int components;
        int maxSize;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            components = n;
            maxSize = 1;

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return;

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
                maxSize = Math.max(maxSize, size[rootY]);
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
                maxSize = Math.max(maxSize, size[rootX]);
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
                rank[rootX]++;
                maxSize = Math.max(maxSize, size[rootX]);
            }

            components--;
        }
    }

    public static void main(String[] args) {

        int channels = 12000;

        int[][] edges = {
            {0, 1}, {1, 2}, {2, 3},
            {4, 5}, {6, 7}, {7, 8}
        };

        UnionFind uf = new UnionFind(channels);

        for (int[] e : edges) {
            uf.union(e[0], e[1]);
        }

        System.out.println("Connected Components: " + uf.components);
        System.out.println("Largest Component Size: " + uf.maxSize);
        System.out.println("Time Complexity: O(E α(N)) ≈ O(E)");
    }
}