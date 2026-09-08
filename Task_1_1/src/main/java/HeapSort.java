import java.lang.reflect.Array;
import java.util.ArrayList;

public class  HeapSort {
    public static <T extends Comparable<? super T>> ArrayList<T> HeapSortCall(T[] notSortArray) {
        var heap = new BinarySortHeap<>(notSortArray);
        var result = new ArrayList<T>();
        while (heap.size() != 0) {
            result.add(heap.PopSmallestItem());
        }
        return result;
    }

}
