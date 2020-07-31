package solver.EquationSolver;

import java.util.ArrayList;

/*
 * 06 July 2020
 * Provide a solution to a multi-variate linear equation.
 * Input to the class is a row/col array of numbers from which a solution is to be formed
 * The class implements a Solver Interface with the premise that a solver could be a
 * solution finder for different type of multivariate linear equations.
 */
public class MultiVariableSolver implements Solver {

    //Instance Variables to be updated from external source
    private final double[][] matrixValues;
    private final int numberOfVariables;
    private final int numberOfEquations;

    //Internal Use Instance Variables
    /**********FOR USE ON PROJECT UPGRADE***************
    private ArrayList<String> sequenceOfColumnSwaps = new ArrayList<>();
    private StringBuilder step = new StringBuilder();
    */
    private final ArrayList<Integer> numberOfSuperfluousEquations = new ArrayList<>();
    private static final String DIGIT_MATCH = "\\W?\\d+\\.0(\\d+)?";
    private int numberOfSignificantEquations;
    boolean hasALinearContradiction = false;


    //Constructor
    public MultiVariableSolver(double[][] matrixValues, int numberOfVariables, int numberOfEquations) {
        this.matrixValues = matrixValues;
        this.numberOfVariables = numberOfVariables;
        this.numberOfEquations = numberOfEquations;
        this.numberOfSignificantEquations = numberOfEquations;

    }

    /*
    * The method takes one parameter the matrix from which the solution to the problem may be obtained.
    * The algorithm works by taking the matrix and unfolding it to produce a matrix of length 2 x values[0].length - 1
    * The algo then steps through column by column and for each column it cycles through the corresponding rows.
    * On each pass it checks for the pivot point of 1 and if found within the matrix, swaps rows and if not found
    * using the row echelon rules to produce a unit pivot point.
    *
     */
    @Override
    public StringBuilder solution(double[][] values) {
        double[][] matrixValues;
        int row;
        int rowSize = values.length;
        int rowIndex;
        int columnSize = values[0].length;
        int columnIndex;
        StringBuilder solution = new StringBuilder();


        System.out.println("Start solving the equation.");
        //If any rows have a 1 in the first row first col promote that to row 1
        matrixValues = checkForUnity(values , 0);

        int calculationSpan = 2 * columnSize - 1;   //this is twice the length of the matrix
        for (int col = 0; col < calculationSpan; col++) {

            //Test if we have a contradiction i.e LHS != RHS (0 0 0 ... k) 0 != k
            if (rowOfZeros(matrixValues)) {
                if (hasALinearContradiction) {
                    System.out.println("No solutions");
                    return solution.append("No solutions");
                }
            }

            //ColumnIndex is used to fold the matrix over when the end of the column is reached
            columnIndex = col >= columnSize ? calculationSpan - col  : col;

            //Test if the current column exists for the current row
            //Try a row swap to see if we can get a form which is conducive to solving
            //the equation.
            if (columnIndex < rowSize && matrixValues[columnIndex][columnIndex] == 0) {
                rowSwap(values, columnIndex);
            }

            //pre-calculate the start and end points for the loop
            row = col >= rowSize ? 0 : columnIndex;

            for ( ; row < rowSize; row++) {
                rowIndex = col >= rowSize ? (rowSize - row - 1) % rowSize : row;
                //System.out.println("\t - Row Index Value " + rowIndex);
                if (rowIndex == columnIndex && matrixValues[rowIndex][rowIndex] != 0 && matrixValues[rowIndex][rowIndex] != 1) {
                    makeUnity(matrixValues, rowIndex);
                } else
                if (rowIndex != columnIndex && columnIndex < rowSize && matrixValues[rowIndex][columnIndex] != 0) {
                    rowReduce(matrixValues, rowIndex, columnIndex);
                }
            }
        }

        //System.out.println(step.toString());
        double[] results = Matrix.transpose(matrixValues)[columnSize - 1];
        if (numberOfSignificantEquations < numberOfVariables) {
            System.out.println("Infinitely many solutions");
            solution.append("Infinitely many solutions");
        } else {
            for (int index = 0; index < numberOfVariables; index++) {
                solution.append(results[index]);
                solution.append(" ");
            }
        }
        return solution;
    }

    /*
     * Test if any row is all 0's.  This will let you know if we have an Linear System
     * with any solutions or not.
     * A Linear Contradiction is a case where the left hand side is all 0's and the RHS
     * is a none zero number.
     */
    private boolean rowOfZeros(double[][] values) {
        boolean rowIsAllZero = true;
        for (int rowCount = 0; rowCount < values.length; rowCount++) {
            rowIsAllZero = true;
            //if a row has already been tested and is in the List, do Not test it again
            if (numberOfSuperfluousEquations.contains(rowCount)) {
                continue;
            }
            for (int colCount = 0; colCount < values[0].length - 1;  colCount++) {
                rowIsAllZero =  rowIsAllZero &&
                        values[rowCount][colCount] == 0;
            }
            if (rowIsAllZero) {
                if (!numberOfSuperfluousEquations.contains(rowCount) &&
                        //test the last column is a 0 or not
                        matrixValues[rowCount][values[0].length - 1] == 0) {
                    numberOfSuperfluousEquations.add(rowCount);
                    numberOfSignificantEquations--;
                }
                else {
                    hasALinearContradiction = true;
                }
            }
        }
        return rowIsAllZero;
    }

    /*
     * The method is used to swap a row with the first available non zero row.
     */
    private boolean rowSwap(double[][] values, int currentRowToSwap) {
        boolean rowSwapSuccess = false;
        for (int index = currentRowToSwap + 1; index < values.length; index++) {
            if (values[index][currentRowToSwap] != 0) {
                rowSwap(values, currentRowToSwap, index);
                rowSwapSuccess = true;
                break;
            }
        }
        return rowSwapSuccess;
    }

    /*
     * Take the given matrix and get a unit value in the leading diagonal of the matrix.
     * Multiply the matrix with the value at index position marked by (k,k) where k is the
     * index position which lies on the leading diagonal plane.
     */
    private void makeUnity(double[][] matrixValues, int rowIndex) {
        double multiplier = 1 / matrixValues[rowIndex][rowIndex];
        //Match type
        // first char is a +/- -> \\W?
        // followed by a digit -> \\d+
        // followed by a period '.'
        //followed by a digit 0
        //the last match is for any 1 or more digits grouped.
        String printMultiplier =    Double.toString(multiplier).matches(DIGIT_MATCH) ?
                                    String.format("%d",(int) multiplier) :
                                    String.format("%.1f",multiplier);
        System.out.printf("%s * R%d -> R%d\n", printMultiplier, rowIndex + 1, rowIndex + 1);
        for (int column = 0; column < matrixValues[0].length; column++) {
            if (matrixValues[rowIndex][column] != 0) {
                matrixValues[rowIndex][column] *= multiplier;
            }
        }
    }

    /*
     * Checks if the matrix has a unit value in the index position.  If the unit
     */
    private double[][] checkForUnity(double[][] values, int row) {
        //If the current pivot row is not a 1 then look for row with a 1 in the pivot
        if (values[row][row] != 1) {
            for (int rowIndex = row + 1; rowIndex < values.length; rowIndex++) {
                if (values[rowIndex][0] == 1) {
                    rowSwap(values, 0, rowIndex);
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
        String printMultiplier =    Double.toString(multiplier).matches(DIGIT_MATCH) ?
                String.format("%d", (int) multiplier) :
                String.format("%.1f", multiplier);

        System.out.printf("%s * R%d + R%d -> R%d\n", printMultiplier, col + 1, row + 1, row + 1);
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
        System.out.println("R" + (rowA + 1) + " <-> " + "R" + (rowB + 1));
        return values;
    }

    /*
     * Swap the two given columns
     */

    @Deprecated
    public double[][] columnSwap(double[][] values, int columnA, int columnB) {
        Matrix.transpose(values);
        rowSwap(values, columnA, columnB);
        System.out.println("C" + (columnA + 1) + " <-> " + "C" + (columnB + 1));
        return Matrix.transpose(values);
    }


}
