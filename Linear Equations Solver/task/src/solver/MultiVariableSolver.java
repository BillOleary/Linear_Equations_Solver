package solver;

import java.util.ArrayList;

/*
* Provide a solution to a multi-variate linear equation.
* Input to the class is a row/col array of numbers from which a solution is to be formed
* The class implements a Solver Interface with the premise that a solver could be a
* solution finder for different type of multivariate linear equations.
 */
public class MultiVariableSolver implements Solver{

    private ArrayList<String> sequenceOfSteps = new ArrayList<>();
    private StringBuilder step = new StringBuilder();

    @Override
    public double[] solution(double[][] values) {
        double[][] matrixValues;
        int row;
        int rowIndex;
        int rowEndPoint;
        int columnIndex;
        int columnSize = values[0].length - 1;

        System.out.println("Start solving the equation.");
        //If any rows have a 1 in the first row first col promote that to row 1
        matrixValues = checkForUnity(values);
        int calculationSpan = 2 * columnSize - 1;   //this is twice the length of the matrix
        for (int col = 0; col < calculationSpan; col++) {
            columnIndex = col >= columnSize ? calculationSpan - col  : col;

            //pre-calculate the start and end points for the loop
            row = col >= columnSize ? 0 : columnIndex;
            rowEndPoint = col >= columnSize ? columnIndex : matrixValues.length;
            for ( ; row < rowEndPoint; row++) {
                rowIndex = row >= matrixValues.length || col >= matrixValues.length ? rowEndPoint - row - 1 : row;
                //System.out.println("\t - Row Index Value " + rowIndex);
                if (rowIndex == columnIndex && matrixValues[rowIndex][rowIndex] != 1) {
                    makeUnity(matrixValues, rowIndex);
                } else
                if (rowIndex != columnIndex) {
                    rowReduce(matrixValues, rowIndex, columnIndex);
                }
            }
        }
        //System.out.println(step.toString());
        return Matrix.transpose(matrixValues)[matrixValues.length];
    }

    /*
    * Take the given matrix and get a unit value in the leading diagonal of the matrix.
    * Multiply the matrix with the value at index position marked by (k,k) where k is the
    * index position which lies on the leading diagonal plane.
     */
    private void makeUnity(double[][] matrixValues, int rowIndex) {
        double multiplier = 1 / matrixValues[rowIndex][rowIndex];
        String printMultiplier = Double.toString(multiplier).matches("\\W?\\d+\\.0(\\d+)?") ? String.format("%d",(int) multiplier) : String.format("%.1f",multiplier);
        System.out.printf("%s * R%d -> R%d\n", printMultiplier, rowIndex + 1, rowIndex + 1);
        //step.append(divisor + " * R" + (rowIndex + 1) + " -> R" + (rowIndex + 1) +"\n");
        for (int column = 0; column < matrixValues[0].length; column++) {
            if (matrixValues[rowIndex][column] != 0) {
                matrixValues[rowIndex][column] *= multiplier;
            }
        }
    }

    /*
    * Checks if the matrix has a unit value in the index position.  If the unit
     */
    private double[][] checkForUnity(double[][] values) {
        for (int row = 0; row < values.length; row++) {
            if (values[row][row] != 1) {
                continue;
            }
            else {
                if (values[row][0] == 1) {
                    rowSwap(values, 0, row);
                    break;
                }
            }
        }
        return values;
    }

    /*
    * Given a matrix with row and col reduce the given row
     */
    private double[][] rowReduce(double[][] matrix, int row, int col) {

        double[] rowN = new double[matrix[0].length];
        double multiplier = -1 * matrix[row][col];
        for (int colIndex = 0; colIndex < matrix[0].length; colIndex++) {
            if (matrix[row][colIndex] == 0) {
                rowN[colIndex] = multiplier * matrix[col][colIndex];
            } else {
                rowN[colIndex] += multiplier * matrix[col][colIndex] + matrix[row][colIndex];
            }
        }
        matrix[row] = rowN;
        String printMultiplier = Double.toString(multiplier).matches("\\W?\\d+\\.0(\\d+)?") ? String.format("%d", (int) multiplier) : String.format("%.1f", multiplier);

        System.out.printf("%s * R%d + R%d -> R%d\n", printMultiplier, col + 1, row + 1, row + 1);
        //System.out.println(multiplier + " * R" + (col + 1) + " + R" + (row + 1) + " -> " + "R" + (row + 1) + "\n");
        //step.append(multiplier + " * R" + (col + 1) + " + R" + (row + 1) + " -> " + "R" + (row + 1) + "\n");
        return matrix;
    }

    /*
    * Swap two row in the matrix
    * @param values is the matrix whose row are to be swapped
    * @param rowA and rowB are the rows to be swapped around in the matrix
     */
    public double[][] rowSwap(double[][] values , int rowA, int rowB) {

        double[] temp = values[rowA];
        values[rowA] = values[rowB];
        values[rowB] = temp;
        return values;
    }


}
