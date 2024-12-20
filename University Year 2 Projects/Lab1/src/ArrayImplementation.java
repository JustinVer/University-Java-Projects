import java.util.*;

/**
 * This class uses an array approach to generate all permutations for a set of numbers.
 *
 * IDE used:IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-09-19
 */

public class ArrayImplementation {


    /**
     * This method generate all permutations of n numbers
     * @param k is the number of elements in the array
     * @param a is the array holding the values being permuted
     */
    public static void generateArrays(int k, int[] a){
        if (k == 1){
            System.out.println(Arrays.toString(a));
        } else {
            for (int i = 0; i<k;i++){
                generateArrays(k-1,a);
                if (k % 2 == 0){
                    //Swaps the two nodes
                    int temp = a[i];
                    a[i] = a[k-1];
                    a[k-1] = temp;
                } else {
                    //Swaps the two nodes
                    int temp = a[0];
                    a[0] = a[k-1];
                    a[k-1] = temp;
                }
            }
        }
    }// generateArrays
}// Main