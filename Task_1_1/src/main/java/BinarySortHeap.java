import java.util.ArrayList;
import java.util.Collections;


/**
 * Data struct implement API Binary Sort Heap for Heapsort.
 * @param <T> Data type of the input unsorted array
 */
public class BinarySortHeap<T extends Comparable<? super T>> {

    private ArrayList<T> values;

    /**
     * Default constructor for creating empty heap.
     */
    public BinarySortHeap() {
        values  = new ArrayList<>();
        values.add(null);
    }


    /**
     * Constructor for filling an unsorted array.
     * @param notSortesArray unsorted data
     */
    public BinarySortHeap(T[] notSortesArray) {
        values = new ArrayList<>();
        values.add(null);
        for (T item : notSortesArray) {
            addItem(item);
        }
    }

    /**
     * Returning count elements in heap.
     * @return count elements in heap
     */
    public int size() {
        return values.size() - 1;
    }

    /**
     * Add new element in heap.
     * @param item The element being added
     */
    public void addItem(T item) {
        values.add(item);
        for (int i = values.size() - 1; i/2 != 0; i /= 2) {
            if (values.get(i).compareTo(values.get(i/2)) < 0) {
                Collections.swap(values, i, i/2);
            }
            else {
                break;
            }
        }
    }

    /**
     * Returning minimal element and remove it from heap.
     * @return Minimal element in heap
     */
    public T popSmallestItem() {
        if (values.size() == 1) {
            return null;
        }
        T result = values.get(1);
        values.set(1, values.getLast());
        values.removeLast();
        ShiftDown(1);
        return result;
    }

    /**
     * Return clone private heap's array (method for test).
     * @return clone private heap's array
     */
    public ArrayList<T> getResultArray() {
        return new ArrayList<T>(values);
    }

    private void ShiftDown(int indexItem) {
        while (true) {
            if (indexItem * 2 + 1 < values.size() &&
                    (values.get(indexItem * 2).compareTo(values.get(indexItem * 2 + 1)) > 0) &&
                    (values.get(indexItem).compareTo(values.get(indexItem * 2 + 1)) > 0)) {
                Collections.swap(values, indexItem, indexItem * 2 + 1);
                indexItem = indexItem * 2 + 1;
            } else if (indexItem * 2 < values.size() &&
                    (values.get(indexItem).compareTo(values.get(indexItem * 2)) > 0)) {
                Collections.swap(values, indexItem, indexItem * 2);
                indexItem *= 2;
            }
             else {
                break;
            }
        }
    }




}
