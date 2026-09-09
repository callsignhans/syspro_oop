import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeapSortTest {

    @Test
    void heapSortCall1() {
        var result = HeapSort.heapSortCall(new Integer[] {5, 4, 3, 2, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }
}