package learn;

import java.util.Arrays;

/*
    * Dot Product Operation
    * - The Dot Product Operation in this instance multiplies each 
    *   value in each row of A with each value of each column in B. 
    *   This is then saved to a new 2d array. 
*/

public class dotProductEx {
    public static void main(String[] args) {
        int[][] a = {
                { 3, 2, 3 },
                { 1, 2, 3 },
                { 1, 2, 3 } };
        int[][] b = {
                { 2, 2, 3, 4 },
                { 1, 2, 3, 4 },
                { 1, 2, 3, 4 } };
        int[][] temp = new int[a.length][b[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < a[0].length; k++) {
                    System.out.println("doing " + a[i][k] + " times " + b[k][j]);
                    sum += a[i][k] * b[k][j];
                }
                temp[i][j] = sum;
                System.out.println(sum);
            }
        }
        System.out.println(Arrays.deepToString(temp));
    }

}
