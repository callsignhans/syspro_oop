import java.util.ArrayList;

/**
 * Class with static method heapsort.
 */
public class  HeapSort {

    /**
     * Sorts the provided array using heapsort
     * @param <T> Type elements in array
     * @param notSortArray Unsorted array
     * @return Sorted array
     */
    public static <T extends Comparable<? super T>> ArrayList<T> heapSortCall(T[] notSortArray) {
        var heap = new BinarySortHeap<>(notSortArray);
        var result = new ArrayList<T>();
        while (heap.size() != 0) {
            result.add(heap.popSmallestItem());
        }
        return result;
    }

}
