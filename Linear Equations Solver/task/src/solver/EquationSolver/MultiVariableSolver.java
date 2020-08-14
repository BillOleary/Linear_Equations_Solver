package solver.EquationSolver;

import solver.EquationSolver.ComplexOrDoubleStrategy.ComplexMultiplier;
import solver.EquationSolver.ComplexOrDoubleStrategy.DoubleMultiplier;
import solver.EquationSolver.ComplexOrDoubleStrategy.Multiplier;
import solver.EquationSolver.ComplexValue.ComplexMaths;
import solver.EquationSolver.ComplexValue.ComplexNumber;

import java.util.ArrayList;
import java.util.function.BiFunction;

/*
 * 06 July 2020
 * Provide a solution to a multi-variate linear equation.
 * Input to the class is a row/col array of numbers from which a solution is to be formed
 * The class implements a Solver Interface with the premise that a solver could be a
 * solution finder for different type of multivariate linear equations.
 * 14 Aug 2020
 * Code has been adapted to work with complex Numbers by using Generics.
 * Type "T extends Number" is designed to hold both Double or Complex Numbers
 * ComplexNumbers implement Numbers to help them join into the Numbers Family group.
 */
//public class MultiVariableSolver{
public class MultiVariableSolver<T extends Number> implements Solver {


    //Instance Variables to be updated from external source
    private T[][] matrixValues = null;
    private final int numberOfVariables;
    private final int numberOfEquations;
    boolean isComplexValue = false;

    //Internal Use Instance Variables
    /**********FOR USE ON PROJECT UPGRADE***************
    private ArrayList<String> sequenceOfColumnSwaps = new ArrayList<>();
    private StringBuilder step = new StringBuilder();
    */
    private final ArrayList<Integer> numberOfSuperfluousEquations = new ArrayList<>();
    public static final String DIGIT_MATCH = "\\W?[1-9]+\\.0(\\d+)?";   //Used by toString() method for check and print.
    private int numberOfSignificantEquations;
    boolean hasALinearContradiction = false;
    Multiplier createMultiplier;   //Aggregation inaction.  Multiplier can be a ComplexMultiplier or a DoubleMultiplier.

    //Lambda BiFunctions to present which path to select.
    //@param Double[][] -> I/P Matrix to the Lambda
    //@param Integer[] -> Array containing the Row and Column Information passed to the BiFunction
    //@param Double[][] -> Output resultant matrix after it has been updated by the function.
    BiFunction<Double[][], Integer[], Double[][]> makeUnityDoubleFunction;
    BiFunction<ComplexNumber[][], Integer[], ComplexNumber[][]> makeUnityComplexNumberFunction;
    BiFunction<ComplexNumber[][], Integer[], ComplexNumber[][]> complexNumberRowReduce;
    BiFunction<Double[][], Integer[], Double[][]> realNumberRowReduce;
    //
    T zero;     //Define a zero condition for a Double which is different from a 0 for a Complex Number
    T one;      //Same as above except for a unit value.


    //Constructor
    public MultiVariableSolver(T[][] matrixValues, int numberOfVariables, int numberOfEquations) {
        this.matrixValues = matrixValues;
        this.numberOfVariables = numberOfVariables;
        this.numberOfEquations = numberOfEquations;
        init();
    }

    /*
    * Used to initialise the the object to a valid state
     */
    private void init() {
        ComplexMaths.COMPLEX_CONSTANT z = ComplexMaths.COMPLEX_CONSTANT.ZERO;
        ComplexMaths.COMPLEX_CONSTANT o = ComplexMaths.COMPLEX_CONSTANT.ONE;
        this.numberOfSignificantEquations = numberOfEquations;
        isComplexValue = matrixValues[0][0] instanceof ComplexNumber;
        createMultiplier = isComplexValue ? new ComplexMultiplier() : new DoubleMultiplier();
        this.zero = isComplexValue ? (T) z.zero() : (T) Double.valueOf(0);
        this.one = isComplexValue ? (T) o.one() : (T) Double.valueOf(1);

        //Strategy Pattern Var
        /*
         * Lambda for making a Unity Value for Real Number and Complex Number Values
         */
        makeUnityDoubleFunction = (e1,rowCol) -> {
            Integer rowIndex = rowCol[0];
            Double multiplier = (Double) createMultiplier.multiplierValue(e1, rowCol, Multiplier.SELECT.DIVIDE);
            for (int column = 0; column < e1[0].length; column++) {
                if (rowIndex == column) {
                    e1[rowIndex][column] = 1d;
                } else {
                    if (!e1[rowIndex][column].equals(this.zero)) {
                        e1[rowIndex][column] *= multiplier;
                    }
                }
            }
            return e1;
        };

        makeUnityComplexNumberFunction = (e1,rowCol) -> {
            Integer rowIndex = rowCol[0];
            ComplexNumber multiplier = (ComplexNumber) createMultiplier.multiplierValue(e1, rowCol,  Multiplier.SELECT.DIVIDE);
            for (int column = 0; column < e1[0].length; column++) {
                if (rowIndex == column) {
                    e1[rowIndex][column] = ComplexNumber.complexNumberOne();
                } else {
                    if (!e1[rowIndex][column].equals(this.zero)) {
                        e1[rowIndex][column] = ComplexMaths.multiply(multiplier, (ComplexNumber) matrixValues[rowIndex][column]);
                    }
                }
            }
            return e1;
        };

        complexNumberRowReduce = (e1, rowCol) -> {
            Integer row = rowCol[0];
            Integer col = rowCol[1];
            ComplexNumber multiplier = (ComplexNumber) createMultiplier.multiplierValue(e1, rowCol, Multiplier.SELECT.MULTIPLY);
            for (int colIndex = 0; colIndex < e1[0].length; colIndex++) {
                ComplexNumber multiply = ComplexMaths.multiply(multiplier, e1[col][colIndex]);
                e1[row][colIndex] = ComplexMaths.add(multiply, e1[row][colIndex]);
            }
            return e1;
        };

        realNumberRowReduce = (e1, rowCol) -> {
            Integer row = rowCol[0];
            Integer col = rowCol[1];

            Double multiplier = (Double) createMultiplier.multiplierValue(e1, rowCol, Multiplier.SELECT.MULTIPLY);

            for (int colIndex = 0; colIndex < e1[0].length; colIndex++) {
                e1[row][colIndex] = multiplier * e1[col][colIndex] + e1[row][colIndex];
            }
            return e1;
        };


    }

    /*
    * The method takes one parameter the matrix from which the solution to the problem may be obtained.
    * The algorithm works by taking the matrix and unfolding it to produce a matrix of length 2 x values[0].length - 1
    * The algo then steps through column by column and for each column it cycles through the corresponding rows.
    * On each pass it checks for the pivot point of 1 and if found within the matrix, swaps rows and if not found
    * using the row echelon rules to produce a unit pivot point.
    *
    * 1 Aug 2020
    * This method has been adapted to function for both Double and Complex Values.
    * Use of Generics and Strategy Pattern used.
    *
     */
    @Override
    public StringBuilder solution() {
        int row;
        int rowSize = numberOfEquations;
        int rowIndex;
        int columnSize = numberOfVariables + 1;
        int columnIndex;
        StringBuilder solution = new StringBuilder();



        System.out.println("Start solving the equation.");
        //If any rows have a 1 in the first row first col promote that to row 1
        printMatrix();
        matrixValues = checkForUnity(matrixValues, 0);

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
            if (columnIndex < rowSize && matrixValues[columnIndex][columnIndex].equals(zero)) {
                rowSwap(matrixValues, columnIndex);
            }

            //pre-calculate the start and end points for the loop
            row = col >= rowSize ? 0 : columnIndex;

            for ( ; row < rowSize; row++) {
                rowIndex = col >= rowSize ? (rowSize - row - 1) % rowSize : row;
                //System.out.println("\t - Row Index Value " + rowIndex);
                if (rowIndex == columnIndex &&
                        !matrixValues[rowIndex][rowIndex].equals(zero) &&
                        !matrixValues[rowIndex][rowIndex].equals(one)) {
                    makeUnity(matrixValues, rowIndex);
                } else
                if (rowIndex != columnIndex &&
                        columnIndex < rowSize &&
                        !matrixValues[rowIndex][columnIndex].equals(zero)) {
                    rowReduce(matrixValues, rowIndex, columnIndex);
                }
            }
            printMatrix();
            System.out.println();
        }

        //System.out.println(step.toString());
        T[] results = transpose(matrixValues)[columnSize - 1];
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
    private boolean rowOfZeros(T[][] values) {
        boolean rowIsAllZero = true;
        for (int rowCount = 0; rowCount < values.length; rowCount++) {
            rowIsAllZero = true;
            //if a row has already been tested and is in the List, do Not test it again
            if (numberOfSuperfluousEquations.contains(rowCount)) {
                continue;
            }
            for (int colCount = 0; colCount < values[0].length - 1;  colCount++) {
                rowIsAllZero =  rowIsAllZero &&
                        values[rowCount][colCount].equals(zero);
            }
            if (rowIsAllZero) {
                if (!numberOfSuperfluousEquations.contains(rowCount) &&
                        //test the last column is a 0 or not
                        matrixValues[rowCount][values[0].length - 1].equals(zero)) {
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
    private boolean rowSwap(T[][] values, int currentRowToSwap) {
        boolean rowSwapSuccess = false;
        for (int index = currentRowToSwap + 1; index < values.length; index++) {
            if (!values[index][currentRowToSwap].equals(zero)) {
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
    private T[][] makeUnity(T[][] matrixValues, int rowIndex) {
        Integer[] rowColumn = {rowIndex, 0};
        String printMultiplier;
        if (isComplexValue) {
            matrixValues = (T[][]) createMultiplier.matrixMultiply(matrixValues, rowColumn, makeUnityComplexNumberFunction);
        } else {
            matrixValues = (T[][]) createMultiplier.matrixMultiply(matrixValues, rowColumn, makeUnityDoubleFunction);
        }

        printMultiplier = createMultiplier.toString();
        System.out.printf("%s * R%d -> R%d\n", printMultiplier, rowIndex + 1, rowIndex + 1);
        return matrixValues;
    }

    /*
     * Checks if the matrix has a unit value in the index position.
     * If the pivot value has a 1 in the matrix position.  If we do not have a unit pivot value,
     * search for any row with a unit value in the specific column.
     * If none is found, we do not alter the matrix.
     */
    private T[][] checkForUnity(T[][] values, int row) {
        //If the current pivot row is not a 1 then look for row with a 1 in the pivot in other rows
        if (!values[row][row].equals(1d)) {
            for (int rowIndex = row + 1; rowIndex < values.length; rowIndex++) {
                if (values[rowIndex][0].equals(one)) {
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
    private T[][] rowReduce(T[][] matrix, int row, int col) {
        Integer[] rowColumn = {row, col};
        if (isComplexValue) {
            matrixValues = (T[][]) createMultiplier.matrixMultiply(matrix, rowColumn, complexNumberRowReduce);
        } else {
            matrixValues = (T[][]) createMultiplier.matrixMultiply(matrix, rowColumn, realNumberRowReduce);
        }

        String printMultiplier =    createMultiplier.toString();
        System.out.printf("%s * R%d + R%d -> R%d\n", printMultiplier, col + 1, row + 1, row + 1);
        return matrix;
    }

    /*
     * Swap two row in the matrix
     * @param values is the matrix whose row are to be swapped
     * @param rowA and rowB are the rows to be swapped around in the matrix
     */
    public T[][] rowSwap(T[][] values , int rowA, int rowB) {

        T[] temp = values[rowA];
        values[rowA] = values[rowB];
        values[rowB] = temp;
        System.out.println("R" + (rowA + 1) + " <-> " + "R" + (rowB + 1));
        return values;
    }

    /*
     * Swap the two given columns
     */

    @Deprecated
    public T[][] columnSwap(T[][] values, int columnA, int columnB) {
        transpose(values);
        rowSwap(values, columnA, columnB);
        System.out.println("C" + (columnA + 1) + " <-> " + "C" + (columnB + 1));
        return transpose(values);
    }

    /*
     * Take a n x m matrix and transpose it.
     * @param - a 2 x 2 Matrix with the values to be transposed.
     * @param - output a 2 X 2 matrix of the transposed Matrix.
     */
    public T[][] transpose(T[][] matrix) {

        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        T[][] transposedMatrix = isComplexValue ?
                (T[][]) new ComplexNumber[colLength][rowLength] :
                (T[][]) new Double[colLength][rowLength];

        for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
            for (int colIndex = 0; colIndex < colLength; colIndex++) {
                transposedMatrix[colIndex][rowIndex] = matrix[rowIndex][colIndex];
            }
        }
        return transposedMatrix;
    }

    /*
    * A method to print out the Matrix - purely for testing and code checking purposes.
     */
    private void printMatrix() {

        for (int row = 0; row < matrixValues.length; row++) {
            for (int col = 0; col < matrixValues[0].length; col++) {
                System.out.printf("%20s",matrixValues[row][col]);
            }
            System.out.println();
        }
    }

}
