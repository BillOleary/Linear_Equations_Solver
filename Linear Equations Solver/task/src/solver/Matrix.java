package solver;

public interface Matrix {

    double[] readValues(double[][] values);

    static double[][] transpose(double[][] matrix) {

        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        double[][] transposedMatrix = new double[colLength][rowLength];

        for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
            for (int colIndex = 0; colIndex < colLength; colIndex++) {
                transposedMatrix[colIndex][rowIndex] = matrix[rowIndex][colIndex];
            }
        }
        return transposedMatrix;
    }
}
