import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BinarySortHeapTest {

    private static <T extends Comparable<? super T>> boolean checkCorrectHeap(ArrayList<T> arrayList){
        if (arrayList.isEmpty() && (arrayList.getFirst() != null)) {
            return false;
        }
        for (int i = 1; i < arrayList.size(); i++) {
            if (i * 2 < arrayList.size() && arrayList.get(i).compareTo(arrayList.get(i * 2)) > 0) {
                return false;
            }
            if (i * 2 + 1 < arrayList.size() && arrayList.get(i).compareTo(arrayList.get(i * 2 + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    void ConstructorDefault() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void addItem1() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.addItem(1);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void addItem2() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.addItem(1);
        test.addItem(2);
        test.addItem(4);
        test.addItem(7);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void addItem3() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.addItem(45);
        test.addItem(12);
        test.addItem(3);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void addItem4() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.addItem(12);
        test.addItem(43);
        test.addItem(6);
        test.addItem(120);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void ConstructorArrayDecreasingSequence() {
        Integer[] inputArray = new Integer[]{6,5,4,3,2,1};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));

    }

    @Test
    void ConstructorArrayAscendingSequence() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));

    }

    @Test
    void removeItem1() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Integer ans = test.popSmallestItem();
        Assertions.assertEquals(1, ans);
        Assertions.assertEquals(6, test.getResultArray().size());
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));

    }

    @Test
    void removeItem2() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        for (int i = 1; i < 7; i++) {
            Integer ans = test.popSmallestItem();
            Assertions.assertEquals(i, ans);
            Assertions.assertEquals(7 - i, test.getResultArray().size());
            Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
        }


    }
    @Test
    void removeItem3() {
        Integer[] inputArray = new Integer[]{};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        assertNull(test.popSmallestItem());
        Assertions.assertEquals(1, test.getResultArray().size());
        Assertions.assertTrue(checkCorrectHeap(test.getResultArray()));
    }

    @Test
    void size() {
        Integer[] inputArray = new Integer[]{1, 2, 3, 4, 5, 6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        for (int i = 1; i < 7; i++) {
            Integer ans = test.popSmallestItem();
            Assertions.assertEquals(6 - i, test.size());
        }
    }
}