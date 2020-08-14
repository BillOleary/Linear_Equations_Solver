package solver.EquationSolver.ComplexOrDoubleStrategy;

import solver.EquationSolver.ComplexValue.ComplexMaths;

import java.util.function.BiFunction;

public class ComplexMultiplier<ComplexNumber extends Number> implements Multiplier<ComplexNumber> {

    ComplexNumber multiplier;

    public ComplexMultiplier() {
        this.multiplier = (ComplexNumber) new solver.EquationSolver.ComplexValue.ComplexNumber(0, 0);
    }


    @Override
    public ComplexNumber multiplierValue(ComplexNumber[][] matrix, Integer[] rowCol, Enum<?> type) {
        int row = rowCol[0];        //The row value
        int col = rowCol[1];        //The column value
        multiplier = type.name().matches(String.valueOf(SELECT.DIVIDE)) ?
                ((ComplexNumber) ComplexMaths.divide(solver.EquationSolver.ComplexValue.ComplexNumber.complexNumberOne(),
                                                        (solver.EquationSolver.ComplexValue.ComplexNumber) matrix[row][row])):
                ((ComplexNumber) ComplexMaths.negate((solver.EquationSolver.ComplexValue.ComplexNumber) matrix[row][col]));
        return multiplier;
    }

    @Override
    public ComplexNumber[][] matrixMultiply(ComplexNumber[][] matrix, Integer[] rowColumn, BiFunction<ComplexNumber[][], Integer[], ComplexNumber[][]> multiplierFunction ) {

        return multiplierFunction.apply(matrix, rowColumn);
    }

    @Override
    public String toString() {
        return multiplier.toString();
    }
}
