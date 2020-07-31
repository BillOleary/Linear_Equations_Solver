package solver.EquationSolver;

public interface Matrix {

    //Implement the following method on implementation on the Matrix Interface
    //The readValues method must read a 2 X 2 matrix and output solution to the
    //2 X 2 Matrix.  This Interface is used in conjunction with a solver object
    //to pass the solution back to the calling object.
    StringBuilder readValues(double[][] values);

    /*
    * Take a n x m matrix and transpose it.
    * @param - a 2 x 2 Matrix with the values to be transposed.
    * @param - output a 2 X 2 matrix of the transposed Matrix.
     */
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
