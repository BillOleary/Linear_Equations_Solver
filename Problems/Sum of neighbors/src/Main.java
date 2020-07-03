import java.util.ArrayList;
import java.util.Scanner;
/*
1 July 2020
* Use an Array List to read the data and convert it to int values after entire data set is read.
* Take the data and then calculate the sum based on the required task sheet.
* Use of circular buffers to work out which elements are to be added and in which order.
*
 */
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        ArrayList<String> stringMatrix = new ArrayList<>();

        while (readIn.hasNext()) {
            String line = readIn.nextLine();
            if (line.trim().matches("end")) {
                break;
            }
            stringMatrix.add(line);
        }

        int row = stringMatrix.size();
        int col = stringMatrix.get(0).split(" ").length;

        int[][] intMatrix = convertToMatrixArray(stringMatrix, row, col);
        int[][] sumMatrix = new int[row][col];

        int previousRowPointer = 0;
        int nextRowPointer = 0;
        int nextColumnPointer = 0;
        int previousColumnPointer = 0;
        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            //Point to the next element in the matrix
            nextRowPointer = (rowIndex + 1) % row;
            for (int colIndex = 0; colIndex < col; colIndex++) {
                nextColumnPointer = (colIndex + 1) % col;
                if (colIndex == 0) {
                    previousRowPointer = (rowIndex + row - 1) % row;
                    previousColumnPointer = (colIndex + col - 1) % col;
                }
                int rowSum = intMatrix[rowIndex][previousColumnPointer] + intMatrix[rowIndex][nextColumnPointer];
                int colSum = intMatrix[previousRowPointer][colIndex] + intMatrix[nextRowPointer][colIndex];
                sumMatrix[rowIndex][colIndex] = rowSum + colSum;
                previousColumnPointer = colIndex;
            }

        }
        printMatrix(sumMatrix);
    }

    private static int[][] convertToMatrixArray(ArrayList<String> matrix, int row, int col) {
        int[][] matrixOfIntegers = new int[row][col];
        int newRow = 0;
        int newCol = 0;

        for (String element : matrix) {
            String[] rowValues = element.split(" ");
            for (String values : rowValues) {
                matrixOfIntegers[newRow][newCol++] = Integer.parseInt(values);
            }
            newRow++;
            newCol = 0;
        }
        return matrixOfIntegers;
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