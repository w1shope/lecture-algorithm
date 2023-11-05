import java.util.Arrays;

public class FloydWarshall_Algorithm {
    static final int NUMBER = 10; // 자역 개수
    static final int MAX_NUM = 100000; // 최대 값
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

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        // node : 거쳐 갈 노드
        for (int node = 0; node < NUMBER; node++) {
            // i : 시작 노드
            for (int i = 0; i < NUMBER; i++) {
                // j : 도착 노드
                for (int j = 0; j < NUMBER; j++) {
                    int oldDistance = local[i][j]; // 기존 거리
                    int newDistance = local[i][node] + local[node][j]; // 거쳐 가는 거리
                    // i->j 거리와 i -> node -> j 거리를 비교해서 더 짧은 거리를 저장
                    local[i][j] = Math.min(oldDistance, newDistance);
                }
            }
        }

//        for (int i = 0; i < NUMBER; i++)
//            sb.append(locations[i] + ": " + local[i][0] + "\n");
//        System.out.println(sb);

        sb.append("\t\t");
        Arrays.stream(locations).forEach(name -> sb.append(name + "\t\t"));
        sb.append("\n");
        for (int i = 0; i < NUMBER; i++) {
            sb.append(locations[i] + "\t\t");
            for(int j = 0; j <= i; j++)
                sb.append("\t\t");
            for (int j = i + 1; j < NUMBER; j++) {
                sb.append(local[i][j] + "\t\t");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}