import java.util.PriorityQueue;

public class Dijkstra_Algorithm {
    static final int NUMBER = 10; // 자역 개수
    static final int MAX_NUM = Integer.MAX_VALUE; // 최대 값
    static final String[] locations = {"서울", "천안", "원주", "강릉", "논산", "대전", "대구", "포항", "광주", "부산"}; // 지역 정보
    // 양방향 간선 정보
    static int[][] local = {
            {0, 12, 15, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM},
            {12, 0, MAX_NUM, MAX_NUM, 4, 10, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM},
            {15, MAX_NUM, 0, 21, MAX_NUM, MAX_NUM, 7, MAX_NUM, MAX_NUM, MAX_NUM},
            {MAX_NUM, MAX_NUM, 21, 0, MAX_NUM, MAX_NUM, MAX_NUM, 25, MAX_NUM, MAX_NUM},
            {MAX_NUM, 4, MAX_NUM, MAX_NUM, 0, 3, MAX_NUM, MAX_NUM, 13, MAX_NUM},
            {MAX_NUM, 10, MAX_NUM, MAX_NUM, 3, 0, 10, MAX_NUM, MAX_NUM, MAX_NUM},
            {MAX_NUM, MAX_NUM, 7, MAX_NUM, MAX_NUM, 10, 0, 19, MAX_NUM, 9},
            {MAX_NUM, MAX_NUM, MAX_NUM, 25, MAX_NUM, MAX_NUM, 19, 0, MAX_NUM, 5},
            {MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, 13, MAX_NUM, MAX_NUM, MAX_NUM, 0, 15},
            {MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, MAX_NUM, 9, 5, 15, 0}
    };
    // 노드 번호와 distance를 갖는 Pair
    static Pair[] nodes = {
            new Pair(0, 0),
            new Pair(1, MAX_NUM),
            new Pair(2, MAX_NUM),
            new Pair(3, MAX_NUM),
            new Pair(4, MAX_NUM),
            new Pair(5, MAX_NUM),
            new Pair(6, MAX_NUM),
            new Pair(7, MAX_NUM),
            new Pair(8, MAX_NUM),
            new Pair(9, MAX_NUM),
    };

    // 확정된 노드 체크용
    static boolean[] visited = new boolean[NUMBER];
    // 우선순위 큐를 사용하여 distance가 작은 노드부터 처리
    static PriorityQueue<Pair> que = new PriorityQueue<>();

    public static void main(String[] args) {
        // 0번(서울)을 시작 노드로 설정
        que.offer(nodes[0]);
        while (!que.isEmpty()) {
            Pair node = que.poll();
            visited[node.nodeNumber] = true; // 노드 확정
            // 확정된 노드와 연결된 노드를 대상으로 수행
            for (int i = 0; i < NUMBER; i++) {
                // 확정되지 않은 노드에 대해서만 수행
                if (!visited[i]) {
                    // 확정된 노드로부터 현재 노드까지의 거리
                    int distance = node.distance + local[node.nodeNumber][i];
                    // 기존 distance와 현재 node로부터 distance까지의 distance를 비교 -> 더 짧은 distance 저장
                    if (distance >= 0 && nodes[i].distance > distance)
                        nodes[i].distance = distance;
                    que.offer(nodes[i]);
                }
            }
        }

        for (int i = 0; i < NUMBER; i++)
            System.out.println(locations[i] + ": " + nodes[i].distance);
    }
}

class Pair implements Comparable<Pair> {
    int nodeNumber;
    int distance;

    public Pair(int nodeNumber, int distance) {
        this.nodeNumber = nodeNumber;
        this.distance = distance;
    }

    @Override
    public int compareTo(Pair o) {
        return this.distance - o.distance;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "nodeNumber=" + nodeNumber +
                ", distance=" + distance +
                '}';
    }
}