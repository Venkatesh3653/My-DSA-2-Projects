import java.util.*;

public class TimsortDemo {

    static int detectRun(int[] arr, int lo, int hi) {

        if (lo + 1 >= hi)
            return hi;

        int run = lo + 1;

        if (arr[run] < arr[run - 1]) {

            while (run < hi &&
                    arr[run] < arr[run - 1]) {
                run++;
            }

            reverse(arr, lo, run - 1);

        } else {

            while (run < hi &&
                    arr[run] >= arr[run - 1]) {
                run++;
            }
        }

        return run;
    }

    static void reverse(int[] arr, int left, int right) {

        while (left < right) {

            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }

    static void insertionSort(int[] arr, int lo, int hi) {

        for (int i = lo + 1; i < hi; i++) {

            int key = arr[i];
            int j = i - 1;

            while (j >= lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    static int[] merge(int[] a, int[] b) {

        int[] result = new int[a.length + b.length];

        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {

            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length)
            result[k++] = a[i++];

        while (j < b.length)
            result[k++] = b[j++];

        return result;
    }

    static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {

        int[] arr = {
                3, 7, 11,
                5, 4,
                6, 9, 12,
                1, 2, 8, 10, 13, 14
        };

        int n = arr.length;

        List<int[]> runs = new ArrayList<>();

        int i = 0;

        while (i < n) {

            int end = detectRun(arr, i, n);

            runs.add(Arrays.copyOfRange(arr, i, end));

            i = end;
        }

        System.out.println("Runs After Detection:");

        for (int[] run : runs) {
            printArray(run);
        }

        int[] merge1 = merge(runs.get(0), runs.get(1));

        System.out.println("\nMerge 1:");
        printArray(merge1);

        int[] merge2 = merge(runs.get(2), runs.get(3));

        System.out.println("\nMerge 2:");
        printArray(merge2);

        int[] finalMerge = merge(merge1, merge2);

        System.out.println("\nFinal Merge:");
        printArray(finalMerge);

        System.out.println("\nFinal Sorted Array:");
        printArray(finalMerge);

        System.out.println("\nComplexities:");
        System.out.println("Run Detection = O(k)");
        System.out.println("Insertion Sort = O(k^2)");
        System.out.println("Timsort Worst Case = O(n log n)");
        System.out.println("Timsort Best Case = O(n)");
    }
}