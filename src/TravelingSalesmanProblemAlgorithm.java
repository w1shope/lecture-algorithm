//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class TravelingSalesmanProblemAlgorithm {
//    static final int NUM = 8; // 노드 개수
//    static int[] visited = new int[NUM]; // 노드 방문 횟수
//    static List<Pair> pairs = new ArrayList<>(); // 각 점의 (x,y) 좌표 저장
//    static double[][] graph = new double[NUM][NUM]; // 노드 간의 거리
//    static List<Pair> mst;
//
//    public static void main(String[] args) throws IOException {
//        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
//        mst = MST.getMST();
//
//        initNodeInfo(); // 노드가 위치한 정보 초기화
//        initNodeDistance(); // 노드 간의 거리 정보 초기화
//        List<Integer> result = new ArrayList<>();
//
//        int startNode = 0; // 시작 노드
//        visited[startNode] += 1; // 방문 회수 + 1
//        result.add(0);
//        while (visited[0] < 2) { // 출발 시에 시작점 방문 1번, 1번씩 방문 후 시작점 방문 1번 -> 2번 방문시 종료
//            int nextNode = getMoveNodeIndex(startNode); // 현재 노드에서 이동할 노드 반환
//            visited[nextNode] += 1; // 노드 이동 후 방문 횟수 1 증가
//            startNode = nextNode;
//            result.add(startNode);
//        }
//
//        LinkedHashSet<Integer> set = new LinkedHashSet<>(); // 노드 방문 순서 유지하는 HashSet
//        for (int i = 0; i < result.size(); i++)
//            set.add(result.get(i));
//        set.add(0); // 중복 노드를 제거하면서 시작 노드가 제거되었으므로, 다시 추가 한다.
//        List<Integer> resultList = set.stream().collect(Collectors.toList());
//        resultList.add(0);
//
//        int prev = 0;
//        double distance = 0d; // 총 이동 거리
//        for (int i = 0; i < resultList.size(); i++) {
//            System.out.println("현재 노드 : " + resultList.get(i));
//            bw.write("현재 노드 : " + resultList.get(i) + "\n");
//            distance += getDistance(pairs.get(prev), pairs.get(resultList.get(i)));
//            prev = resultList.get(i);
//        }
//        System.out.println();
//        bw.write("\n");
//        for (int i = 0; i < resultList.size(); i++) {
//            System.out.println(prev + " -> " + resultList.get(i) +
//                    " 이동 거리 : " + getDistance(pairs.get(prev), pairs.get(resultList.get(i))));
//            bw.write(prev + " -> " + resultList.get(i) +
//                    " 이동 거리 : " + getDistance(pairs.get(prev), pairs.get(resultList.get(i))) + "\n");
//        }
//        System.out.println("총 이동 거리 : " + distance);
//        bw.write("총 이동 거리 : " + distance);
//
//        bw.flush();
//        bw.close();
//    }
//
//    /**
//     * 노드 위치 정보 초기화
//     */
//    static void initNodeInfo() {
//        pairs.add(new Pair(0, 3));
//        pairs.add(new Pair(7, 5));
//        pairs.add(new Pair(6, 0));
//        pairs.add(new Pair(4, 3));
//        pairs.add(new Pair(1, 0));
//        pairs.add(new Pair(5, 3));
//        pairs.add(new Pair(2, 2));
//        pairs.add(new Pair(4, 1));
//    }
//
//    /**
//     * 노드 간의 거리 정보 초기화
//     * A : 0
//     * B : 1
//     * C : 2
//     * D : 3
//     * E : 4
//     * F : 5
//     * G : 6
//     * H : 7
//     */
//    static void initNodeDistance() {
//        for (int i = 0; i < NUM; i++)
//            Arrays.fill(graph[i], -1);
//        for (int i = 0; i < mst.size(); i++) {
//            Pair pair = mst.get(i);
//            int prev = pair.x;
//            int next = pair.y;
//            graph[prev][next] = getDistance(pairs.get(prev), pairs.get(next));
//            graph[next][prev] = graph[prev][next];
//        }
//    }
//
//    /**
//     * MST 생성
//     */
//
//    /**
//     * 두 점 사이의 거리 반환
//     */
//    static double getDistance(Pair p1, Pair p2) {
//        return Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
//    }
//
//    /**
//     * 이동할 노드 반환
//     *
//     * @Param node : 현재 노드
//     */
//    static int getMoveNodeIndex(int node) {
//        List<NodeInfo> list = new ArrayList<>();
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < NUM; i++) {
//            double distance = graph[node][i]; // 현재 노드와의 거리
//            if (distance != -1.0) { // 이동할 수 없는 노드
//                if (minDistance > distance) // 현재 노드와의 거리가 더 짧을 경우
//                    list.add(new NodeInfo(i, distance, visited[i]));
//            }
//        }
//        /**
//         * 정렬 우선순위
//         * 1. 노드 방문 횟수 0번인 노드
//         * 2. 노드 간의 거리가 짧은 노드
//         */
//        Collections.sort(list, (p1, p2) -> {
//            if (p1.visitCount == p2.visitCount)
//                return (int) (p1.distance - p2.distance);
//            return p1.visitCount - p2.visitCount;
//        });
//        return list.get(0).nodeIndex; // 정렬 후, 이동할 노드 반환
//    }
//}
//
///**
// * (x, y) 좌표 쌍을 갖는 Pair 클래스
// */
//class Pair {
//    int x;
//    int y;
//
//    public Pair(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//}
//
///**
// * 노드 번호, 거리, 방문 회수 필드를 갖는 NodeInfo 클래스
// */
//class NodeInfo {
//    int nodeIndex;
//    double distance;
//    int visitCount;
//
//    public NodeInfo(int nodeIndex, double distance, int visitCount) {
//        this.nodeIndex = nodeIndex;
//        this.distance = distance;
//        this.visitCount = visitCount;
//    }
//}
//
//class MST {
//    static final int NUM = 8; // 노드 개수
//    static double[][] graph = new double[NUM][NUM]; // 노드 간의 거리를 담고 있는 2차원 배열
//    static int[][] coordinates = { // 각 노드의 좌표
//            {0, 3},
//            {7, 5},
//            {6, 0},
//            {4, 3},
//            {1, 0},
//            {5, 3},
//            {2, 2},
//            {4, 1}
//    };
//    // T에 포함된 node
//    static List<Integer> tList = new ArrayList<>();
//    static List<Pair> mst = new ArrayList<>();
//    static double[] distance = new double[NUM];
//
//    public static void main(String[] args) {
//        init(); // 노드 간의 거리 설정
//
//        int startNode = 0; // A 노드부터 출발
//        distance[startNode] = 0d; // 시작점을 제외한 모든 노드의 distance는 무한대
//        tList.add(startNode); // 시작점을 tList에 포함
//        mst.add(new Pair(0, 0));
//
//        while (tList.size() < NUM) { // 모든 노드가 포함되면 종료
//            modifyDistance(startNode);
//            int smallestDistanceNode = findSmallestNode();
//            tList.add(smallestDistanceNode);
//            startNode = smallestDistanceNode;
//        }
//    }
//
//    /**
//     * tList에 노드 포함 -> 해당 노드와 연결된 간선의 길이 수정
//     */
//    static void modifyDistance(int v) {
//        for (int i = 0; i < NUM; i++) {
//            distance[i] = Math.min(distance[i], getDistance(v, i));
//        }
//    }
//
//    /**
//     * 노드 간의 distance 설정
//     */
//    static void init() {
//        for (int i = 0; i < NUM; i++) {
//            for (int j = 0; j < NUM; j++) {
//                graph[i][j] = getDistance(i, j);
//            }
//        }
//        Arrays.fill(distance, Double.MAX_VALUE);
//    }
//
//    /**
//     * 노드 간의 거리 반환
//     */
//    static double getDistance(int v1, int v2) {
//        return Math.sqrt(Math.pow(coordinates[v2][1] - coordinates[v1][1], 2) + Math.pow(coordinates[v2][0] - coordinates[v1][0], 2));
//    }
//
//    /**
//     * tList에 포함된 노드들과 연결된 노드 중에 가장 길이가 짧은 노드 반환
//     */
//    static int findSmallestNode() {
//        int minIndex = -1;
//        Pair pair = null;
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < tList.size(); i++) {
//            int v = tList.get(i);
//            for (int j = 0; j < NUM; j++) {
//                if (!tList.contains(j)) {
//                    if (minDistance > getDistance(v, j)) {
//                        minIndex = j;
//                        pair = new Pair(v, j);
//                        minDistance = getDistance(v, j);
//                    }
//                }
//            }
//        }
//        mst.add(pair);
//        return minIndex;
//    }
//
//    /**
//     * MST 반환
//     */
//    public static List<Pair> getMST() {
//        main(null);
//        return mst;
//    }
//}