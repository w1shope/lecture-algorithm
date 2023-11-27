import java.util.*;
import java.util.stream.Collectors;

public class TravelingSalesmanProblemAlgorithm {
    static final int NUM = 8; // 노드 개수
    static int[] visited = new int[NUM]; // 노드 방문 횟수
    static List<Pair> pairs = new ArrayList<>(); // 간선 정보
    static double[][] graph = new double[NUM][NUM]; // 노드 간의 거리

    public static void main(String[] args) {
        initNodeInfo(); // 노드가 위치한 정보 초기화
        initNodeDistance(); // 노드 간의 거리 정보 초기화
        List<Integer> result = new ArrayList<>();

        int startNode = 0; // 시작 노드
        visited[startNode] += 1; // 방문 회수 + 1
        result.add(0);
        while (visited[0] < 2) { // 출발 시에 시작점 방문 1번, 1번씩 방문 후 시작점 방문 1번 -> 2번 방문시 종료
            int nextNode = getMoveNodeIndex(startNode); // 현재 노드에서 이동할 노드 반환
            visited[nextNode] += 1; // 노드 이동 후 방문 횟수 1 증가
            startNode = nextNode;
            result.add(startNode);
        }

        LinkedHashSet<Integer> set = new LinkedHashSet<>(); // 노드 방문 순서 유지하는 HashSet
        for (int i = 0; i < result.size(); i++)
            set.add(result.get(i));
        set.add(0); // 중복 노드를 제거하면서 시작 노드가 제거되었으므로, 다시 추가 한다.
        List<Integer> resultList = set.stream().collect(Collectors.toList());
        resultList.add(0);

        StringBuffer sb = new StringBuffer();
        int prev = 0;
        double distance = 0d; // 총 이동 거리
        for (int i = 0; i < resultList.size(); i++) {
            System.out.println("현재 노드 : " + resultList.get(i));
            distance += getDistance(pairs.get(prev), pairs.get(resultList.get(i)));
            sb.append(prev + " -> " + resultList.get(i) + " 이동 거리 : " + getDistance(pairs.get(prev), pairs.get(resultList.get(i))) + "\n");
            prev = resultList.get(i);
        }
        System.out.println();
        System.out.print(sb.toString());
        System.out.println("총 이동 거리 : " + distance);
    }

    /**
     * 노드 위치 정보 초기화
     */
    static void initNodeInfo() {
        pairs.add(new Pair(0, 3));
        pairs.add(new Pair(7, 5));
        pairs.add(new Pair(6, 0));
        pairs.add(new Pair(4, 3));
        pairs.add(new Pair(1, 0));
        pairs.add(new Pair(5, 3));
        pairs.add(new Pair(2, 2));
        pairs.add(new Pair(4, 1));
    }

    /**
     * 노드 간의 거리 정보 초기화
     * A : 0
     * B : 1
     * C : 2
     * D : 3
     * E : 4
     * F : 5
     * G : 6
     * H : 7
     */
    static void initNodeDistance() {
        for (int i = 0; i < NUM; i++)
            Arrays.fill(graph[i], -1);
        graph[0][6] = getDistance(pairs.get(0), pairs.get(6));
        graph[6][0] = graph[0][6];

        graph[4][6] = getDistance(pairs.get(4), pairs.get(6));
        graph[6][4] = graph[4][6];

        graph[3][6] = getDistance(pairs.get(3), pairs.get(6));
        graph[6][3] = graph[3][6];

        graph[3][5] = getDistance(pairs.get(3), pairs.get(5));
        graph[5][3] = graph[3][5];

        graph[1][5] = getDistance(pairs.get(1), pairs.get(5));
        graph[5][1] = graph[1][5];

        graph[3][7] = getDistance(pairs.get(3), pairs.get(7));
        graph[7][3] = graph[3][7];

        graph[2][7] = getDistance(pairs.get(2), pairs.get(7));
        graph[7][2] = graph[2][7];
    }

    /**
     * 두 점 사이의 거리 반환
     */
    static double getDistance(Pair p1, Pair p2) {
        return Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
    }

    /**
     * 이동할 노드 반환
     *
     * @Param node : 현재 노드
     */
    static int getMoveNodeIndex(int node) {
        List<NodeInfo> list = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < NUM; i++) {
            double distance = graph[node][i]; // 현재 노드와의 거리
            if (distance != -1.0) { // 이동할 수 없는 노드
                if (minDistance > distance) // 현재 노드와의 거리가 더 짧을 경우
                    list.add(new NodeInfo(i, distance, visited[i]));
            }
        }
        /**
         * 정렬 우선순위
         * 1. 노드 방문 횟수 0번인 노드
         * 2. 노드 간의 거리가 짧은 노드
         */
        Collections.sort(list, (p1, p2) -> {
            if (p1.visitCount == p2.visitCount)
                return (int) (p1.distance - p2.distance);
            return p1.visitCount - p2.visitCount;
        });
        return list.get(0).nodeIndex; // 정렬 후, 이동할 노드 반환
    }
}

/**
 * (x, y) 좌표 쌍을 갖는 Pair 클래스
 */
class Pair {
    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * 노드 번호, 거리, 방문 회수 필드를 갖는 NodeInfo 클래스
 */
class NodeInfo {
    int nodeIndex;
    double distance;
    int visitCount;

    public NodeInfo(int nodeIndex, double distance, int visitCount) {
        this.nodeIndex = nodeIndex;
        this.distance = distance;
        this.visitCount = visitCount;
    }
}