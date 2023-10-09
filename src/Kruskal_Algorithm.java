import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kruskal_Algorithm {
    // 노트(v1, v2), 가중치 저장
    private static Pair[] graph;
    // 입력 횟수 저장
    private static List<String> inputs = new ArrayList<>();

    private static int n;
    // Cycle 여부 판단 -> Union Find
    private static int[] parent;
    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 콘솔창 입력
        while (true) {
            String input = br.readLine().replaceAll("\\D+", "");
            if (input.isEmpty())
                break;
            inputs.add(input);
        }
        n = inputs.size();
        graph = new Pair[n];
        parent = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;

        putEdge();

        // 가중치 오름차순 정렬
        Arrays.sort(graph, (p1, p2) -> p1.getW() - p2.getW());

        // 모든 노트가 선택되면 종료
        int count = 0;
        for (int i = 0; i < n; i++) {
            Pair pair = graph[i];
            int v1 = pair.getV1();
            int v2 = pair.getV2();
            int w = pair.getW();
            /**
             * Cycle 여부 판단 -> Union Find
             * union() -> true: : cycle X
             * union() -> false : cycle O
             */
            if (union(v1, v2)) {
                count++;
                if (count == alphabet.length)
                    break;
                sb.append(String.format("(%d, %d, %d)", v1, v2, w) + "\n");
            }
        }

        System.out.println(sb);
    }

    /**
     * 간선 저장
     */
    private static void putEdge() {
        for (int i = 0; i < n; i++) {
            int v1 = Character.getNumericValue(inputs.get(i).charAt(0));
            int v2 = Character.getNumericValue(inputs.get(i).charAt(1));
            int w = Character.getNumericValue(inputs.get(i).charAt(2));
            graph[i] = new Pair(v1, v2, w);
        }
    }

    /**
     * 부모 노드 반환
     */
    private static int find(int v) {
        if (parent[v] == v)
            return v;
        return find(parent[v]);
    }

    /**
     * 부모 노드가 동일한지 여부에 따라 true/false 반환
     * 부모 노드 동일 -> cycle이 생기므로 return false
     * 부모 노드 다름 -> cycle이 생기지 않으므로 return true
     */
    private static boolean union(int v1, int v2) {
        v1 = find(v1);
        v2 = find(v2);
        if (v1 == v2)
            return false;
        if (v1 < v2)
            parent[v2] = v1;
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
}