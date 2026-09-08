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
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void addItem1() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.AddItem(1);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void addItem2() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.AddItem(1);
        test.AddItem(2);
        test.AddItem(4);
        test.AddItem(7);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void addItem3() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.AddItem(45);
        test.AddItem(12);
        test.AddItem(3);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void addItem4() {
        BinarySortHeap<Integer> test = new BinarySortHeap<>();
        test.AddItem(12);
        test.AddItem(43);
        test.AddItem(6);
        test.AddItem(120);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void ConstructorArrayDecreasingSequence() {
        Integer[] inputArray = new Integer[]{6,5,4,3,2,1};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));

    }

    @Test
    void ConstructorArrayAscendingSequence() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));

    }

    @Test
    void removeItem1() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        Integer ans = test.PopSmallestItem();
        Assertions.assertEquals(1, ans);
        Assertions.assertEquals(6, test.GetResultArray().size());
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));

    }

    @Test
    void removeItem2() {
        Integer[] inputArray = new Integer[]{1,2,3,4,5,6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        for (int i = 1; i < 7; i++) {
            Integer ans = test.PopSmallestItem();
            Assertions.assertEquals(i, ans);
            Assertions.assertEquals(7 - i, test.GetResultArray().size());
            Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
        }


    }
    @Test
    void removeItem3() {
        Integer[] inputArray = new Integer[]{};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        assertNull(test.PopSmallestItem());
        Assertions.assertEquals(1, test.GetResultArray().size());
        Assertions.assertTrue(checkCorrectHeap(test.GetResultArray()));
    }

    @Test
    void size() {
        Integer[] inputArray = new Integer[]{1, 2, 3, 4, 5, 6};
        BinarySortHeap<Integer> test = new BinarySortHeap<>(inputArray);
        for (int i = 1; i < 7; i++) {
            Integer ans = test.PopSmallestItem();
            Assertions.assertEquals(6 - i, test.size());
        }
    }
}