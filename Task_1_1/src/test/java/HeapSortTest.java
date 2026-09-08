import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void heapSortCall1() {
        var result = HeapSort.HeapSortCall(new Integer[] {5, 4, 3, 2, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }
}