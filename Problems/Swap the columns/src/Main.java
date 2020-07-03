import java.util.Scanner;

/*
* This program works by first getting the transpose of the matrix.
* Once in that form the required rows are now swapped (row/col flipping)
* We the run a transpose back to convert it to the original matrix order.
*
* (A^T)^T = A
 */
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        int row = readIn.nextInt();
        int col = readIn.nextInt();
        readIn.nextLine();
        String[][] numbers = new String[row][col];

        for (int index = 0; index < row; index++) {
            numbers[index] = readIn.nextLine().split(" ");
        }

        int colSwapA = readIn.nextInt();
        int colSwapB = readIn.nextInt();

        String[][] transposedMatrix = transpose(numbers);

        //Swap the respective cols
        String[] temp = transposedMatrix[colSwapA];
        transposedMatrix[colSwapA] = transposedMatrix[colSwapB];
        transposedMatrix[colSwapB] = temp;

        transposedMatrix = transpose(transposedMatrix);

        printMatrix(transposedMatrix);
    }

    private static String[][] transpose(String[][] matrix) {

        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        String[][] transposedMatrix = new String[colLength][rowLength];

        for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
            for (int colIndex = 0; colIndex < colLength; colIndex++) {
                transposedMatrix[colIndex][rowIndex] = matrix[rowIndex][colIndex];
            }
        }
        return transposedMatrix;
    }

    public static void printMatrix(String[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

}