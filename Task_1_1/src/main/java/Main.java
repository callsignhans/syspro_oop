import java.util.Arrays;

/**
 * Start point class.
 */
public class Main {

    /**
     * Start point method.
     * @param args argument command line
     */
    public static void main(String[] args) {
       var result = HeapSort.heapSortCall(new Integer[] {5, 4, 3, 2, 1});
       System.out.print(Arrays.deepToString(result.toArray()));
    }
}