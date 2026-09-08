import com.sun.jdi.Value;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Collections;


public class BinarySortHeap<T extends Comparable<? super T>> {

    private ArrayList<T> values;

    public BinarySortHeap() {
        values  = new ArrayList<>();
        values.add(null);
    }

    public BinarySortHeap(T[] notSortesArray) {
        values = new ArrayList<>();
        values.add(null);
        for (T item : notSortesArray) {
            AddItem(item);
        }
    }

    public int size() {
        return values.size() - 1;
    }

    public void AddItem(T item) {
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

    public T PopSmallestItem() {
        if (values.size() == 1) {
            return null;
        }
        T result = values.get(1);
        values.set(1, values.getLast());
        values.removeLast();
        ShiftDown(1);
        return result;
    }

    public ArrayList<T> GetResultArray() {
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
