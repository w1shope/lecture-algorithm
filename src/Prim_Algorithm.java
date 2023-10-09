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

        sortList();
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

//        System.out.println(tList);


    }

    /**
     * 초기 Edge 연결 설정
     */
    private static void init() {
        // 인접리스트 노드 개수만큼 초기화
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        // a <-> b, 3
        graph.get(0).add(new Pair(1, 3));
        graph.get(1).add(new Pair(0, 3));
        // a <-> d, 2
        graph.get(0).add(new Pair(3, 2));
        graph.get(3).add(new Pair(0, 2));
        // a <-> e, 4
        graph.get(0).add(new Pair(4, 4));
        graph.get(4).add(new Pair(0, 4));
        // b <-> c, 1
        graph.get(1).add(new Pair(2, 1));
        graph.get(2).add(new Pair(1, 1));
        // b <-> d, 4
        graph.get(1).add(new Pair(3, 4));
        graph.get(3).add(new Pair(1, 4));
        // b <-> f, 2
        graph.get(1).add(new Pair(5, 2));
        graph.get(5).add(new Pair(1, 2));
        // c <-> f, 1
        graph.get(2).add(new Pair(5, 1));
        graph.get(5).add(new Pair(2, 1));
        // d <-> e, 5
        graph.get(3).add(new Pair(4, 5));
        graph.get(4).add(new Pair(3, 5));
        // d <-> f, 7
        graph.get(3).add(new Pair(5, 7));
        graph.get(5).add(new Pair(3, 7));
        // e <-> f, 9
        graph.get(4).add(new Pair(5, 9));
        graph.get(5).add(new Pair(4, 9));

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

    private static void sortList() {
        for(int i = 0; i < graph.size(); i++) {
            List<Pair> pairs = graph.get(i);
            Collections.sort(pairs, (p1, p2) -> {
                return Integer.compare(p1.getV(), p2.getV());
            });
        }
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