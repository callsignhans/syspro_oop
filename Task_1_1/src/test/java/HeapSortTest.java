import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeapSortTest {

    @Test
    void heapSortCall1_EmptyArray() {
        var result = HeapSort.heapSortCall(new Integer[] {});
        Assertions.assertArrayEquals(new Integer[] {}, result.toArray());
    }

    @Test
    void heapSortCall2_SingleElement() {
        var result = HeapSort.heapSortCall(new Integer[] {42});
        Assertions.assertArrayEquals(new Integer[] {42}, result.toArray());
    }

    @Test
    void heapSortCall3_TwoElementsSorted() {
        var result = HeapSort.heapSortCall(new Integer[] {1, 2});
        Assertions.assertArrayEquals(new Integer[] {1, 2}, result.toArray());
    }

    @Test
    void heapSortCall4_TwoElementsInverted() {
        var result = HeapSort.heapSortCall(new Integer[] {2, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 2}, result.toArray());
    }

    @Test
    void heapSortCall5_AlreadySorted() {
        var result = HeapSort.heapSortCall(new Integer[] {1, 2, 3, 4, 5});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void heapSortCall6_ReverseSorted() {
        var result = HeapSort.heapSortCall(new Integer[] {5, 4, 3, 2, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void heapSortCall7_AllElementsEqual() {
        var result = HeapSort.heapSortCall(new Integer[] {7, 7, 7, 7, 7});
        Assertions.assertArrayEquals(new Integer[] {7, 7, 7, 7, 7}, result.toArray());
    }

    @Test
    void heapSortCall8_AlternatingElements() {
        var result = HeapSort.heapSortCall(new Integer[] {1, 2, 1, 2, 1, 2});
        Assertions.assertArrayEquals(new Integer[] {1, 1, 1, 2, 2, 2}, result.toArray());
    }

    @Test
    void heapSortCall9_NegativeNumbers() {
        var result = HeapSort.heapSortCall(new Integer[] {-5, -1, -3, -2, -4});
        Assertions.assertArrayEquals(new Integer[] {-5, -4, -3, -2, -1}, result.toArray());
    }

    @Test
    void heapSortCall10_MixedNumbers() {
        var result = HeapSort.heapSortCall(new Integer[] {-3, 0, 5, -1, 2});
        Assertions.assertArrayEquals(new Integer[] {-3, -1, 0, 2, 5}, result.toArray());
    }

    @Test
    void heapSortCall11_WithDuplicates() {
        var result = HeapSort.heapSortCall(new Integer[] {4, 2, 4, 1, 2, 3});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 2, 3, 4, 4}, result.toArray());
    }

    @Test
    void heapSortCall12_MaxMinIntValues() {
        var result = HeapSort.heapSortCall(new Integer[] {2147483647, 0, -2147483648, -1, 1});
        Assertions.assertArrayEquals(new Integer[] {-2147483648, -1, 0, 1, 2147483647}, result.toArray());
    }

    @Test
    void heapSortCall13_AlmostSorted() {
        var result = HeapSort.heapSortCall(new Integer[] {1, 2, 4, 3, 5});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void heapSortCall14_PeakShape() {
        var result = HeapSort.heapSortCall(new Integer[] {1, 3, 5, 4, 2});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void heapSortCall15_ValleyShape() {
        var result = HeapSort.heapSortCall(new Integer[] {5, 3, 1, 2, 4});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void heapSortCall16_ExtremesAtEdges() {
        var result = HeapSort.heapSortCall(new Integer[] {10, 5, 3, 7, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 3, 5, 7, 10}, result.toArray());
    }

    @Test
    void heapSortCall17_SortedWithZeros() {
        var result = HeapSort.heapSortCall(new Integer[] {0, 0, -5, 10, 0});
        Assertions.assertArrayEquals(new Integer[] {-5, 0, 0, 0, 10}, result.toArray());
    }

    @Test
    void heapSortCall18_ArithmeticProgressionInverted() {
        var result = HeapSort.heapSortCall(new Integer[] {10, 8, 6, 4, 2});
        Assertions.assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, result.toArray());
    }

    @Test
    void heapSortCall19_GeometricProgressionRandom() {
        var result = HeapSort.heapSortCall(new Integer[] {16, 2, 8, 4, 1});
        Assertions.assertArrayEquals(new Integer[] {1, 2, 4, 8, 16}, result.toArray());
    }

    @Test
    void heapSortCall20_LargeScaleSimulation() {
        var result = HeapSort.heapSortCall(new Integer[] {99, -15, 82, 34, 0, -55, 101, 23, 82, 7});
        Assertions.assertArrayEquals(new Integer[] {-55, -15, 0, 7, 23, 34, 82, 82, 99, 101}, result.toArray());
    }
}