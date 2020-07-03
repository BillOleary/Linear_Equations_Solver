
import java.util.Scanner;
/*
* Working method is as follows
* - Create an array of int of length 2 * n
* - Fill this array with value from [n, n-1 , n-2 .....0, 1, 2, 3, ...., n-1]
* - Now print out the last n elements of the array on the first pass
*   - second pass we go down one
* - keep printing until you reach array index 0.
*
 */
class Main {
    public static void main(String[] args) {
        // put your code here

        Scanner readIn = new Scanner(System.in);
        int matrixSize = readIn.nextInt();
        int[] numbers = new int[2 * matrixSize];

        //Create an array of length 2 * n to store values.
        //We will pick out elements from this array at index 'a' to index 'b' where a - b = length n (input element)
        for (int index = 0; index < 2 * matrixSize; index++) {
            numbers[index] = index < matrixSize ? matrixSize - index : index - matrixSize;
        }

        int count = 0;
        while (count < matrixSize) {
            int index = matrixSize - count;     //start of the array index to print out.
            int stop = index + matrixSize;      //end of the array index to print out.
            for (; index < stop; index++) {
                System.out.print(numbers[index] + " ");
            }
            count++;
            System.out.println();
        }
    }
}