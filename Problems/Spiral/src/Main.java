import java.util.Scanner;
/*
  * Build the matrix row by row starting with row 1.
  * Rotate the array clockwise to build the new row 1.
  * Do this 4 times to complete the outer edge.
  * Move down 1 row and continue the build process as above slowly working in-words.
 */
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);

        int matrixSize = readIn.nextInt();

        int[][] matrix = new int[matrixSize][matrixSize];
        //Fill the matrix with 0's
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                matrix[row][col] = row == 0 && col == 0 ? 1 : 0;
            }
        }

        int rowCount = 0;       //keep a count of which row you are building
        //if matrix row/col is even then go round 2 * n  times else 2 * n + 2 times
        int stop = matrixSize % 2 == 0 ? matrixSize * 2 : matrixSize * 2 + 2;
        for (int count = 1; count <= stop; count++) {
            matrix = fillRow(matrix, rowCount);
            matrix = rotateClockwise(matrix);
            //once for each side of the square
            if (count % 4 == 0) {
                rowCount++;
            }
        }
        printMatrix(matrix);
    }

    private static int[][] fillRow(int[][] matrix, int row) {
        for (int col = row; col < matrix.length - row; col++) {
            if (matrix[row][col] == 0) {
                matrix[row][col] = matrix[row][col - 1] + 1;
            }
        }
        return matrix;
    }


    /*
     *   Rotate a matrix clockwise on each call.
     */
    private static int[][] rotateClockwise(int[][] matrix) {
        int rowSize = matrix.length;
        int colSize = matrix[0].length;

        int[][] rotatedMatrix = new int[rowSize][colSize];
        for (int col = 0; col < matrix.length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                rotatedMatrix[rowSize - row - 1][col] = matrix[col][row];
            }
        }
        return rotatedMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}