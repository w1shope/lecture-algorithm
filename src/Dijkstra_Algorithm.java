import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra_Algorithm {
    static final int NUMBER = 10; // 자역 개수
    static final int MAX_NUM = 1000000; // 최대 값
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
    static Pair[] nodes;

    // 확정된 노드 체크용
    static boolean[] visited;
    // 우선순위 큐를 사용하여 distance가 작은 노드부터 처리
    static PriorityQueue<Pair> que = new PriorityQueue<>();

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        sb.append("\t\t");
        Arrays.stream(locations).forEach(name -> sb.append(name + "\t\t"));
        sb.append("\n");

        long startTime = System.currentTimeMillis(); // 시작 시간

        for (int i = 0; i < NUMBER; i++) {
            initNodes(i); // 노드 정보 초기화
            visited = new boolean[NUMBER]; // 확정 노드 초기화
            que.offer(nodes[i]); // i번 지역(노드)을 시작 노드로 설정
            while (!que.isEmpty()) {
                Pair node = que.poll();
                visited[node.nodeNumber] = true; // 노드 확정
                // 확정된 노드와 연결된 노드를 대상으로 수행
                for (int j = 0; j < NUMBER; j++) {
                    // 확정되지 않은 노드에 대해서만 수행
                    if (!visited[j]) {
                        int oldDistance = nodes[j].distance;
                        // 확정된 노드로부터 현재 노드까지의 거리
                        int newDistance = node.distance + local[node.nodeNumber][j];
                        // 기존 distance와 현재 node로부터 distance까지의 distance를 비교 -> 더 짧은 distance 저장
                        nodes[j].distance = Math.min(oldDistance, newDistance);
                        que.offer(nodes[j]);
                    }
                }
            }
            sb.append(locations[i] + "\t\t");
            for (int j = 0; j <= i; j++)
                sb.append("\t\t");
            for (int j = i + 1; j < NUMBER; j++)
                sb.append(nodes[j].distance + "\t\t");
            sb.append("\n");
        }

        long endTime = System.currentTimeMillis(); // 종료 시간
        long totalTime = (endTime - startTime) / 1000; // 수행 시간

        System.out.println(sb);
        System.out.println("소요시간 : " + totalTime);


    }

    /**
     * 노드 정보 초기화
     */
    static void initNodes(int nodeNumber) {
        nodes = new Pair[NUMBER];
        for (int i = 0; i < NUMBER; i++) {
            if (i == nodeNumber)
                nodes[i] = new Pair(i, 0);
            else
                nodes[i] = new Pair(i, MAX_NUM);
        }
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
}