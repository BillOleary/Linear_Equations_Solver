import java.util.Arrays;
import java.util.Scanner;

class Main {
    /*
    * Test for symmetry
     */
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        int matrixSize = readIn.nextInt();
        readIn.nextLine();
        int rowCount = 0;
        String[][] matrix = new String[matrixSize][matrixSize];

        while (readIn.hasNext()) {
            matrix[rowCount] = readIn.nextLine().split(" ");
            rowCount++;
        }

        String[][] transposedMatrix = transpose(matrix);
        rowCount = 0;
        boolean isEqual = true;
        while (rowCount != matrixSize) {
            isEqual = isEqual && Arrays.toString(transposedMatrix[rowCount]).equals(Arrays.toString(matrix[rowCount]));
            rowCount++;
        }
        System.out.println(isEqual ? "YES" : "NO");

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
}