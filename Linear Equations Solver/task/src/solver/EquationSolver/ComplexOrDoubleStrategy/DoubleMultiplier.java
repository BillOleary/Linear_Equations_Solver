package solver.EquationSolver.ComplexOrDoubleStrategy;

import solver.EquationSolver.MultiVariableSolver;

import java.util.function.BiFunction;

public class DoubleMultiplier implements Multiplier<Double>{

    Double multiplier;

    public DoubleMultiplier() {
        this.multiplier = 1D;
    }

    @Override
    public Double multiplierValue(Double[][] matrix, Integer[] rowCol, Enum<?> type) {
        int row = rowCol[0];
        int col = rowCol[1];
        multiplier = type.name().matches(String.valueOf(SELECT.DIVIDE)) ?
                1d / matrix[row][row] :
                -1d * matrix[row][col];

        return multiplier;
    }

    @Override
    public Double[][] matrixMultiply(Double[][] matrix,
                                     Integer[] rowColumn,
                                     BiFunction<Double[][], Integer[], Double[][]> multiplyFunction) {
        return multiplyFunction.apply(matrix, rowColumn);
    }

    @Override
    public String toString() {
        //Match type
        // first char is a +/- -> \\W?
        // followed by a digit -> \\d+
        // followed by a period '.'
        //followed by a digit 0
        //the last match is for any 1 or more digits grouped.
        return this.multiplier.toString().matches(MultiVariableSolver.DIGIT_MATCH) ?
                String.format("%d", multiplier.intValue()) :
                String.format("%.2f",multiplier);
    }
}
