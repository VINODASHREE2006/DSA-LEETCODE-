
import java.util.*;

class Solution {
    List<Integer>[] graph;
    int[][] up;
    int[] tin, tout, depth, initialMask, bitTree;
    int timer, LOG, n;
    char[] chars;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        chars = s.toCharArray();

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];
        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        initialMask = new int[n];
        bitTree = new int[n + 2];

        int[] parent = new int[n];
        int[] next = new int[n];
        Arrays.fill(parent, -2);

        // Iterative DFS avoids stack overflow.
        Deque<Integer> stack = new ArrayDeque<>();
        parent[0] = -1;
        tin[0] = timer++;
        initialMask[0] = 1 << (chars[0] - 'a');
        stack.push(0);

        while (!stack.isEmpty()) {
            int u = stack.peek();

            if (next[u] < graph[u].size()) {
                int v = graph[u].get(next[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                initialMask[v] =
                    initialMask[u] ^ (1 << (chars[v] - 'a'));

                tin[v] = timer++;
                stack.push(v);
            } else {
                tout[u] = timer - 1;
                stack.pop();
            }
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i] == -1 ? i : parent[i];
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.trim().split("\\s+");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int delta =
                    (1 << (chars[u] - 'a')) ^
                    (1 << (c - 'a'));

                if (delta != 0) {
                    // Update prefix masks for the entire subtree.
                    xorUpdate(tin[u] + 1, delta);
                    xorUpdate(tout[u] + 2, delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int lca = getLCA(u, v);

                int maskU = getCurrentMask(u);
                int maskV = getCurrentMask(v);

                int pathMask = maskU ^ maskV
                    ^ (1 << (chars[lca] - 'a'));

                result.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return result;
    }

    int getCurrentMask(int u) {
        return initialMask[u] ^ xorQuery(tin[u] + 1);
    }

    void xorUpdate(int index, int value) {
        while (index < bitTree.length) {
            bitTree[index] ^= value;
            index += index & -index;
        }
    }

    int xorQuery(int index) {
        int result = 0;

        while (index > 0) {
            result ^= bitTree[index];
            index -= index & -index;
        }

        return result;
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }
}
