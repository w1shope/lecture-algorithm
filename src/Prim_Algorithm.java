import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prim_Algorithm {
    // 간선 정보를 담고 있는 graph
    private static List<List<Pair>> graph = new ArrayList<>();
    // node 개수
    private static final int V = 6;
    // 간선 간의 distance -> 초기값 무한대
    private static int[] distance = new int[V];
    // T에 포함된 node
    private static List<Integer> tList = new ArrayList<>();

    public static void main(String[] args) {

        // 출력용 buffer
        StringBuilder sb = new StringBuilder();

        // 노드 연결 정보 설정
        init();

        // 시작점을 C(index=2)로 잡는다
        int start = 2;
        // 거리 0으로 설정
        distance[start] = 0;
        // T에 추가
        tList.add(start);

        // t에 모든 node가 포함되면 종료
        while (tList.size() != V) {
            // 현재 node와 연결된 node들의 distance 수정
            modifyDistance(graph.get(start));
            // T에 포함되지 않은 node 중에서 distance가 가장 작은 node를 찾는다.
            int smallestNode = findSmallestNode();
            /**
             * 중요한 부분
             * 간선과 연결된 노드를 추적하는 메서드
             * 메서드 선언부에 설명한 주석 참고
             */
            int edgeNode = getConnectedNode(smallestNode);
            sb.append(String.format("(%d, %d, %d)", edgeNode, smallestNode, distance[smallestNode]) + "\n");

            // T에 가장 가까운 점 추가
            tList.add(smallestNode);
            // 다음 반복문을 수행할 node
            start = smallestNode;
        }

        System.out.println(sb);
    }

    /**
     * 초기 Edge 연결 설정
     */
    private static void init() {
        // 인접리스트 노드 개수만큼 초기화
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        // 노드 연결 정보 추가
        addEdge(0, 1, 3);
        addEdge(0, 3, 2);
        addEdge(0, 4, 4);
        addEdge(1, 2, 1);
        addEdge(1, 3, 4);
        addEdge(1, 5, 2);
        addEdge(2, 5, 1);
        addEdge(3, 4, 5);
        addEdge(3, 5, 7);
        addEdge(4, 5, 9);

        // 초기 기중치를 MAX로 설정
        Arrays.fill(distance, Integer.MAX_VALUE);
    }

    /**
     * 노드 간의 간선 추가
     */
    private static void addEdge(int u, int v, int w) {
        graph.get(u).add(new Pair(v, w));
        graph.get(v).add(new Pair(u, w));
    }

    /**
     * 노드와 연결된 간선의 거리 수정
     * 기존의 distance 값과 현재 노드에서의 distance를 바교한다
     */
    private static void modifyDistance(List<Pair> edge) {
        for (int i = 0; i < edge.size(); i++) {
            Pair pair = edge.get(i);
            int v = pair.getV();
            int w = pair.getW();
            // 현재 노드와 distance가 더 짧을 때
            if (distance[v] > w)
                distance[v] = w;
        }
    }

    /**
     * distance가 가장 작은 node를 반환한다.
     */
    private static int findSmallestNode() {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceNode = -1;
        for (int i = 0; i < V; i++) {
            // 이미 t에 포함된 노드는 필요x
            if (tList.contains(i))
                continue;
            if (minDistance > distance[i]) {
                minDistance = distance[i];
                minDistanceNode = i;
            }
        }
        return minDistanceNode;
    }

    /**
     * @Param smallestNode : 가장 작은 distance를 갖는 node
     * distance가 가장 작은 smallestNode 가져왔을 때, 현재 node와 연결된 것인지 아니면 tList에 포함된 node 중에 연결된 것인지 확인해야한다.
     * why? -> smallestNode의 distance가 현재 node에 의해 갱신이 된 distance인지, 아니면 tList에 포함된 node에 의해 수정된 것인지 반드시 확인해야한다.
     */
    private static int getConnectedNode(int smallestNode) {
        for (int i = 0; i < graph.get(smallestNode).size(); i++) {
            Pair pair = graph.get(smallestNode).get(i);
            if (pair.getW() == distance[smallestNode]) {
                return pair.getV();
            }
        }
        return -1;
    }
}

class Pair {
    private int v;
    private int w;

    public Pair(int v, int w) {
        this.v = v;
        this.w = w;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "v=" + v +
                ", w=" + w +
                '}';
    }
}
