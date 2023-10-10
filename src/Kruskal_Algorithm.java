import java.util.*;
import java.util.stream.Collectors;

public class Kruskal_Algorithm {
    private static final int V = 6;
    private static int[][] graph = new int[V][V];
    private static int[] parent = new int[V];
    private static List<Pair> edgeList;
    private static List<Pair> tList = new ArrayList<>();

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        init();
        edgeList = getEdgeList();

        // 정렬된 간선으로부터 Tree를 확장한다.
        for (int i = 0; i < edgeList.size(); i++) {
            Pair pair = edgeList.get(i);
            int v1 = pair.getV1();
            int v2 = pair.getV2();
            int w = pair.getW();

            // cycle 생성 여부를 확인한다
            boolean isCycle = union(v1, v2);
            // cycle 만들어지지 않을 때만 Tree에 확장시킨다.
            if (!isCycle) {
                tList.add(pair);
                sb.append(String.format("(%d %d %d)\n", v1, v2, w));
            }
            // Tree에 포함된 간선 개수가 (노드 개수 -1)이면 종료한다.
            if (tList.size() == V - 1) {
                break;
            }
        }

        System.out.println(sb);
    }

    /**
     * 가중치를 오름차순으로 리스트에 저장한다.
     * 양방향 그래프이므로 b->c 엣지가 존재한다면 c->b는 리스트에 저장하면 안된다.
     */
    private static List<Pair> getEdgeList() {
        // 모든 edge 저장
        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                // 가중치 0 -> 연결된 간선 없음
                if (graph[i][j] == 0)
                    continue;
                list.add(new Pair(i, j, graph[i][j]));
            }
        }

        // a-b 양방향 간선이 존재할 때, 한 방향의 간선만 저장한다.
        return list.stream().filter(p -> p.getV1() < p.getV2()).
                sorted((e1, e2) -> {
                    if (e1.getW() == e2.getW())
                        return Integer.compare(e1.getV1(), e2.getV1());
                    return Integer.compare(e1.getW(), e2.getW());
                })
                .collect(Collectors.toList());
    }

    /**
     * 초기 설정
     */
    private static void init() {

        // 노드에 대한 모든 엣지를 입력 받는다.
        addEdge(1, 2, 1);
        addEdge(2, 5, 1);
        addEdge(1, 5, 2);
        addEdge(0, 3, 2);
        addEdge(3, 4, 3);
        addEdge(0, 4, 4);
        addEdge(1, 3, 4);
        addEdge(3, 5, 7);
        addEdge(0, 1, 8);
        addEdge(4, 5, 9);

        // Cycle 여부 확인을 위해 Union-Find 알고리즘을 사용한다.
        for (int i = 0; i < V; i++)
            parent[i] = i;
    }

    /**
     * 양방향 가중치 그래프 설정
     */
    private static void addEdge(int v1, int v2, int w) {
        graph[v1][v2] = w;
        graph[v2][v1] = w;
    }

    /**
     * @Param v -> v노드가 포함된 집합의 부모 노드를 반환한다
     */
    private static int find(int v) {
        if (parent[v] == v)
            return v;
        return parent[v] = find(parent[v]);
    }

    /**
     * Union-Find
     * 두 노드가 같은 집합에 포함되어 있는지 확인한다
     * 같은 집합 : cycle을 만든다 -> return false
     * 다른 집합 : cycle을 만들 수 없음 -> return true
     */
    private static boolean union(int v1, int v2) {
        int a = find(v1);
        int b = find(v2);

        // 부모 노드는 두 노드 중에 더 작은 숫자로 설정한다.
        if (find(a) != find(b)) {
            if (a > b)
                parent[a] = b;
            else
                parent[b] = a;
            return false;
        } else
            return true;
    }
}


class Pair {
    private int v1;
    private int v2;
    private int w;

    public Pair(int v1, int v2, int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public int getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", w=" + w +
                '}';
    }
}