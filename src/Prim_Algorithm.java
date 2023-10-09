import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Prim_Algorithm {
    private static List<List<Pair>> graph = new ArrayList<>();
    private static final int V = 6; // 노드 개수
    private static int[] distance = new int[V]; // 거리
    private static List<Integer> tList = new ArrayList<>(); // T에 포함된 node
    public static void main(String[] args) throws IOException {

        // 노드 연결 정보 추가
        init();

        for(int i = 0; i < graph.size(); i++)
            System.out.println(graph.get(i));

        // 시작점을 C(index=2)로 잡는다
        int start = 2;
        // 거리 0으로 설정
        distance[start] = 0;
        // T에 추가
        tList.add(start);

        // t에 모든 node가 포함되면 종료
        while(tList.size() != V) {
            // 현재 node와 연결된 node들의 distance 수정
            modifyDistance(graph.get(start));
            // T에 포함되지 않은 node 중에서 distance가 가장 작은 node를 찾는다.
            int smallestNode = findSmallestNode();
            // T에 가장 가까운 점 추가
            tList.add(smallestNode);
            System.out.println(String.format("(%d %d %d)", start, smallestNode, distance[smallestNode]));
            start = smallestNode;

        }

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
     * 노드와 연결된 간선의 거리 수정
     * 기존의 distance 값과 현재 노드에서의 distance를 바교한다
     * @param edge
     */
    private static void modifyDistance(List<Pair> edge){
        for(int i = 0; i < edge.size(); i++) {
            Pair pair = edge.get(i);
            int v = pair.getV();
            int w = pair.getW();
            // 현재 노드와 distance가 더 짧을 때
            if(distance[v] > w)
                distance[v] = w;
        }
    }

    /**
     * distance가 가장 작은 node를 반환한다.
     */
    private static int findSmallestNode() {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceNode = -1;
        for(int i = 0; i < V; i++) {
            // 이미 t에 포함된 노드는 필요x
            if(tList.contains(i))
                continue;
            if(minDistance > distance[i]) {
                minDistance = distance[i];
                minDistanceNode = i;
            }
        }
        return minDistanceNode;
    }

    /**
     * 노드 간의 간선 추가
     */
    private static void addEdge(int u, int v, int w) {
        graph.get(u).add(new Pair(v, w));
        graph.get(v).add(new Pair(u, w));
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