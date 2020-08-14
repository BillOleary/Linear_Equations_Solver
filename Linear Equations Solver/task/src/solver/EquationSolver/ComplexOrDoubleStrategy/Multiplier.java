package solver.EquationSolver.ComplexOrDoubleStrategy;

import java.util.function.BiFunction;
/*
* The interface is used Polymorphically with the DoubleMultiplier or the ComplexMultiplier
 */
public interface Multiplier<T extends Number> {

    static enum SELECT {DIVIDE, MULTIPLY};
    T multiplierValue(T[][] matrix, Integer[] row, Enum<?> type);       //Get a multiplier Value for the row

    //Multiply the given Matrix using the multiply function.
    //The multiply function is user defined in the client as a Lambda
    /*
    *
    *   The function takes a matrix whose values are to be modified along with a Integer array.
    * This array has two elements row and column.
    * The last parameter is a Function Interface BiFunction with a generic I/P for the Matrix, the Integer array
    * and the output from the resulting function.
    * @param T[][] input matrix
    * @param Integer[] row/column values
    * @param BiFunction<T[][],I[],T[][]>
    *
     */
    T[][] matrixMultiply(T[][] matrix, Integer[] rowIndex, BiFunction<T[][], Integer[], T[][]> multiplyFunction);

    String toString();  //is toString()
}
