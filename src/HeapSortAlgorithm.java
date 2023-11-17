import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeapSortAlgorithm {
    static int[] nums;
    static int heapSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/hope/Desktop/IdeaProjects/Algorithm/src/input.txt"));
        String str;
        List<Integer> tmp = new ArrayList<>();
        while ((str = br.readLine()) != null)
            tmp.add(Integer.parseInt(str));
        nums = tmp.stream().mapToInt(Integer::valueOf).toArray();
        heapSize = nums.length;

        /**
         * 정렬이 되지 않은 배열을 내림차순으로 먼저 정렬을 수행한다.
         다음에 있을 오름차순 정렬을 수행할 때, root에 반드시 최대값이 위치하여야 하고, 이를 배열의 가장 마지막 요소로 저장할 것이기 때문이다.
         */
        heapify(nums.length - 1);

        for (int i = 0; i < nums.length; i++) {
            swap(heapSize - 1);
            heapSize -= 1;
            heapify(heapSize);
            downHeap(i);
        }

        for (int i = 0; i < nums.length; i++)
            System.out.println(nums[i]);
    }

    /**
     * 처음에 의문이있던 것이, ppt에는 root에 항상 최대값이 있기 때문에 배열의 마지막 요소로 보낼 수 있었는데, 과제 input.txt의 root에는 최대값 보장x
     * 따라서 downHeap 먼저 사용하여, 내림차순으로 배열을 정렬해주어야함
     */
    static void downHeap(int index) {
        int leftChildIdx = 2 * index + 1;
        int rightChildIdx = 2 * index + 2;
        int bigger = index;

        // 왼쪽 자식 비교
        if (leftChildIdx < heapSize && nums[leftChildIdx] > nums[index])
            bigger = leftChildIdx;
        // 오른쪽 자식 비교
        if (rightChildIdx < heapSize && nums[rightChildIdx] > nums[bigger])
            bigger = rightChildIdx;

        if (bigger != index) {
            downHeapSwap(index, bigger);
            /**
             * 반드시 downHeap 재귀호출 필요
             * [3, 7, 8, 4, 2, 5]에서 index가 0일때 downHeap을 먼저 수행하면 -> [8, 7, 3, 4, 2, 5]가 된다 ==> bigger = 2
             이때, 3과 3의 자식인 5가 최대힙 성립하지가 않는다. -> 최대힙으로 만들기 위하여 재귀호출을 수행한다. ==> downHeap(2)
             */
            downHeap(bigger);
        }
    }

    static void downHeapSwap(int index, int bigger) {
        int tmp = nums[index];
        nums[index] = nums[bigger];
        nums[bigger] = tmp;
    }

    static void swap(int lastIndex) {
        int tmp = nums[0];
        nums[0] = nums[lastIndex];
        nums[lastIndex] = tmp;
    }

    static void heapify(int index) {
        for (int i = index; i >= 0; i--)
            downHeap(i);
    }
}
