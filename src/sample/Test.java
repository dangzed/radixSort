package sample;

import java.util.Arrays;

// link radix sort: https://www.youtube.com/watch?v=nu4gDuFabIM
// link counting sort: https://www.youtube.com/watch?v=OKd534EWcdk
public class Test {
    public static int findMaxRadix(int[] array) {
        int max = array[0], count = 0;
        for (int value : array) if (max < value) max = value;
        while (max != 0) {
            max /= 10;
            count++;
        }
        return count - 1;
    }// find the greatest radix of an array, for example radix of 802 is 2, 72 is 1, so findMaxRadix(802,72) return 2

    public static int[] countingSortWithRadix(int[] initialArray, int radix) {
        int[] indexArray = new int[10];
        int[] sortedArray = new int[initialArray.length];

        for (int value : initialArray) {
            int radixOfValue = getNthDigit(value, radix);
            indexArray[radixOfValue]++;
        }

        for (int i = 1; i < indexArray.length; i++) indexArray[i] += indexArray[i - 1];
        for (int i = indexArray.length - 1; i >= 1; i--) indexArray[i] = indexArray[i - 1];//shift right 1 element
        indexArray[0] = 0;

        for (int value : initialArray) {
            sortedArray[indexArray[getNthDigit(value, radix)]] = value;
            indexArray[getNthDigit(value, radix)]++;
        }

        return sortedArray;
    } // counting sort algorithm with specific radix

    public static int getNthDigit(int element, int radix) {
        for (int i = 0; i < radix; i++) element /= 10;
        return element % 10;
    } // return digit at specific radix, for example getNthDigit(802,2) return 8

    public static void main(String[] args) {
        int[] initialArray = {170, 45, 75, 90, 802, 24, 2, 66};

        System.out.println("Array before sorted: ");
        System.out.println(Arrays.toString(initialArray));

        for (int i = 0; i <= findMaxRadix(initialArray); i++) {
            initialArray = countingSortWithRadix(initialArray, i);
        }

        System.out.println("Array after sorted: ");
        System.out.println(Arrays.toString(initialArray));
    }
}
