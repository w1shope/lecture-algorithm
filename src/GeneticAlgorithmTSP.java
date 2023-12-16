import java.util.*;

public class GeneticAlgorithmTSP {
    static final int NODE_NUM = 8; // 점 개수
    static double[][] distance = new double[NODE_NUM][NODE_NUM]; // 두 점간의 거리
    static List<Pair> nodePair = new ArrayList<>(); // (x,y) 좌표 쌍
    static List<Candidate> fitnessList = new ArrayList<>();

    public static void main(String[] args) {
        initPair();
        setInitDistance();

        List<List<Pair>> createCandidates = createCandidatesWithDifferentDistances(); // 후보해 8개 생성, 중복 x
        List<Candidate> candidates = new ArrayList<>(); // createCandidates를 사용하여 면적을 포함하는 후보해 8개 저장

        for (int i = 0; i < createCandidates.size(); i++) {
            List<Pair> candidate = createCandidates.get(i); // TSP 이동 경로
            Candidate c = new Candidate(i, getMoveDistance(candidate), candidate); // (후보해 순서, 후보해의 총 이동거리, 후보해의 TSP)
            candidates.add(c);
        }
        setCandidateArea(candidates); // 각 후보해의 면적 설정

        for (int repeat = 0; repeat < 10000; repeat++) {
            double totalArea = getCandidateArea(candidates);// 모든 후보해의 면적을 더한 값 (평균 면적)
            List<Candidate> selectCandidates = getSelectCandidates(candidates, totalArea); // 룰렛 휠을 사용하여 면적에 대한 확률에 따라 후보해 8개 선정
            intersectionOperation(selectCandidates); // 사이클 교차 연산 수행
            // 돌연변이 연산
            for (int i = 0; i < NODE_NUM; i++)
                mutationOperation(selectCandidates.get(i));
            resetDistanceAndArea(candidates);
            fitnessList.add(getSmallestTSP(candidates));
        }
        Collections.sort(fitnessList, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                return (int) o1.distance - (int) o2.distance;
            }
        });

        Candidate smallestMST = fitnessList.get(0);
        System.out.println("[Genetic Algorithm 결과]");
        System.out.print("이동 순서 : ");
        for (int i = 0; i < smallestMST.moveOrder.size(); i++) {
            System.out.print((char) (65 + smallestMST.moveOrder.get(i).node) + " -> ");
        }
        System.out.println((char) (65 + smallestMST.moveOrder.get(0).node));
        System.out.println("총 이동거리 : " + smallestMST.distance);
    }

    /**
     * 모든 점(A,B,C,D,E,F,G,H)의 (x,y) 쌍 설정
     */
    static void initPair() {
        nodePair.add(new Pair(0, 0, 3));
        nodePair.add(new Pair(1, 7, 5));
        nodePair.add(new Pair(2, 6, 0));
        nodePair.add(new Pair(3, 4, 3));
        nodePair.add(new Pair(4, 1, 0));
        nodePair.add(new Pair(5, 5, 3));
        nodePair.add(new Pair(6, 2, 2));
        nodePair.add(new Pair(7, 4, 1));
    }

    /**
     * 모든 점(A,B,C,D,E,F,G,H) 간의 거리 설정
     */
    static void setInitDistance() {
        for (int i = 0; i < NODE_NUM; i++) {
            for (int j = 0; j < NODE_NUM; j++) {
                if (i != j)
                    distance[i][j] = getDistance(nodePair.get(i), nodePair.get(j));
            }
        }
    }

    /**
     * 두 점 사이의 거리 반환
     */
    static double getDistance(Pair p1, Pair p2) {
        return Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
    }

    /**
     * 후보해 생성
     * 8개의 후보해 생성
     */
    static List<List<Pair>> createCandidatesWithDifferentDistances() {
        List<List<Pair>> candidateList = new ArrayList<>();
        while (candidateList.size() < NODE_NUM) {
            List<Pair> candidate = generateCandidateWithDifferentDistances(); // 후보해 생성
            if (!isDuplicate(candidateList, candidate)) {
                candidateList.add(candidate);
            }
        }
        return candidateList;
    }

    static List<Pair> generateCandidateWithDifferentDistances() {
        List<Pair> candidate = new ArrayList<>();
        List<Integer> cityIndexes = new ArrayList<>(); // 노드 인덱스 저장
        for (int i = 0; i < NODE_NUM; i++) {
            cityIndexes.add(i);
        }
        Collections.shuffle(cityIndexes); // 도시 인덱스를 섞음

        for (int i = 0; i < NODE_NUM; i++) {
            candidate.add(nodePair.get(cityIndexes.get(i))); // 순서를 섞어 후보해에 추가
        }

        return candidate;
    }

    static boolean isDuplicate(List<List<Pair>> candidateList, List<Pair> candidate) {
        for (List<Pair> existingCandidate : candidateList) {
            if (isSameOrder(existingCandidate, candidate)) {
                return true; // 후보해 순서가 중복됨
            }
        }
        return false; // 중복되는 것 없음
    }

    static boolean isSameOrder(List<Pair> candidate1, List<Pair> candidate2) {
        for (int i = 0; i < NODE_NUM; i++) {
            if (!candidate1.get(i).equals(candidate2.get(i))) {
                return false; // 순서가 다름
            }
        }
        return true; // 순서가 같음
    }

    /**
     * TSP 이동 거리 반환
     */
    static double getMoveDistance(List<Pair> candidate) {
        double d = 0;
        for (int i = 0; i < candidate.size() - 1; i++)
            d += distance[candidate.get(i).node][candidate.get(i + 1).node];
        return d;
    }

    /**
     * 각 후보해의 면적 설정
     * 총 적합도 반환
     */
    static void setCandidateArea(List<Candidate> candidates) {
        double areaSum = getCandidateArea(candidates);
        // 각 후보해의 면적을 저장한다
        for (int i = 0; i < candidates.size(); i++)
            candidates.get(i).fitness = 100 - (candidates.get(i).distance / areaSum) * 100;
    }

    /**
     * 후보해 8개 총 면적
     */
    static double getCandidateArea(List<Candidate> candidates) {
        double areaSum = 0d;// 총 적합도
        // 8개 후보해의 총 거리를 구한다
        for (int i = 0; i < candidates.size(); i++) {
            areaSum += candidates.get(i).distance;
        }
        return areaSum;
    }

    /**
     * 선택 연산 : 룰렛 휠
     * 8개의 후보해 선택, 중복 OK
     */
    static List<Candidate> getSelectCandidates(List<Candidate> candidates, double totalArea) {
        List<Candidate> selectedCandidates = new ArrayList<>();
        double[] rouletteWheel = new double[candidates.size()];

        // 각 후보해의 선택 확률 계산
        for (int i = 0; i < candidates.size(); i++) {
            double areaRatio = candidates.get(i).fitness / totalArea;
            rouletteWheel[i] = areaRatio;
        }

        // 룰렛 휠을 이용하여 후보해 선택
        Random random = new Random();
        while (selectedCandidates.size() < NODE_NUM) {
            double randomValue = random.nextDouble() * 100;
            double cumulativeProbability = 0;

            for (int i = 0; i < candidates.size(); i++) {
                cumulativeProbability += rouletteWheel[i];
                if (randomValue <= cumulativeProbability) {
                    selectedCandidates.add(candidates.get(i));
                    break;
                }
            }
        }
        return selectedCandidates;
    }

    /**
     * 사이클 교차 연산
     */
    static void intersectionOperation(List<Candidate> selectCandidates) {
        for (int i = 0; i < NODE_NUM; i += 2) {
            Candidate c1 = selectCandidates.get(i);
            Candidate c2 = selectCandidates.get(i + 1);
            if (c1.moveOrder.equals(c2.moveOrder)) // 두 후보해가 동일할 경우 비교x
                continue;
            int randomIdx = (int) (Math.random() * NODE_NUM); // 임의의 점 선택
            swap(c1, c2, randomIdx);
            // 도시 변경 후 중복될 경우
            while (true) {
                int isDuplicate = isDuplicationNode(c1, randomIdx);
                if (isDuplicate != -1) {
                    swap(c1, c2, isDuplicate);
                    randomIdx = isDuplicate;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 사이클 교차 연산 후 후보해에 중복된 점이 있는지 확인
     */
    static int isDuplicationNode(Candidate candidate, int randomIdx) {
        for (int i = 0; i < NODE_NUM; i++) {
            if (i == randomIdx)
                continue;
            if (candidate.moveOrder.get(randomIdx).equals(candidate.moveOrder.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 사이클 교차 연산
     */
    static void swap(Candidate c1, Candidate c2, int index) {
        Pair p1 = c1.moveOrder.get(index);
        Pair p2 = c2.moveOrder.get(index);
        c1.moveOrder.remove(index);
        c1.moveOrder.add(index, p2);
        c2.moveOrder.remove(index);
        c2.moveOrder.add(index, p1);
    }

    /**
     * 돌연변이 연산
     */
    static void mutationOperation(Candidate candidate) {
        Random random = new Random();
        int percentage = random.nextInt(100); // 0 ~ 99 반환, 0 -> 1퍼센트
        if (percentage == 0) {
            int randomIdx1 = 0;
            int randomIdx2 = 0;
            if (randomIdx1 != randomIdx2)
                while (true) {
                    randomIdx1 = (int) Math.random() * NODE_NUM;
                    randomIdx2 = (int) Math.random() * NODE_NUM;
                    if (randomIdx1 != randomIdx2)
                        break;
                }
            swap(randomIdx1, randomIdx2, candidate);
        }
    }

    /**
     * 1퍼센트 확률로 돌연변이 연산 수행
     */
    static void swap(int randomIdx1, int randomIdx2, Candidate candidate) {
        Pair p1 = candidate.moveOrder.get(randomIdx1);
        Pair p2 = candidate.moveOrder.get(randomIdx2);
        candidate.moveOrder.remove(randomIdx1);
        candidate.moveOrder.add(randomIdx1, p2);
        candidate.moveOrder.remove(randomIdx2);
        candidate.moveOrder.add(randomIdx2, p1);
    }

    /**
     * 교차연산, 돌연변이 연산 후 거리 재설정
     */
    static void resetDistanceAndArea(List<Candidate> candidates) {
        for (int i = 0; i < candidates.size(); i++) {
            List<Pair> moveOrder = candidates.get(i).moveOrder;
            double distance = 0d;
            for (int j = 0; j < moveOrder.size() - 1; j++)
                distance += getDistance(moveOrder.get(j), moveOrder.get(j + 1));
            candidates.get(i).distance = distance;
        }
        setCandidateArea(candidates);
    }

    static Candidate getSmallestTSP(List<Candidate> candidates) {
        Candidate candidate = candidates.get(0);
        for (int i = 1; i < candidates.size(); i++) {
            if (candidate.distance < candidates.get(i).distance)
                candidate = candidates.get(i);
        }
        return candidate;
    }
}

class Pair {
    int node; // 노드 번호
    int x; // x 좌표
    int y; // y 좌표

    public Pair(int node, int x, int y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "node=" + node +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

class Candidate {
    double order; // 후보해 순서
    double distance; // 거리
    double fitness; // 적합도
    List<Pair> moveOrder; // 이동 순서

    public Candidate(double order, double distance, List<Pair> moveOrder) {
        this.order = order;
        this.distance = distance;
        this.moveOrder = moveOrder;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "order=" + order +
                ", distance=" + distance +
                ", fitness=" + fitness +
                ", moveOrder=" + moveOrder +
                '}';
    }
}