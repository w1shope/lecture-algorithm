import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prim_Algorithm {
    private static List<List<Pair>> graph = new ArrayList<>();
    private static final int V = 6; // 노드 개수
    private static int[] distance = new int[V - 1]; // 거리
    private static int[] t = new int[V]; // T에 포함된 node
    public static void main(String[] args) throws IOException {

        init();
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