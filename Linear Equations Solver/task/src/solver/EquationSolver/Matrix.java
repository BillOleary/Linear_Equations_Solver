package solver.EquationSolver;

public interface Matrix<T extends Number> {

    //Implement the following method on implementation on the Matrix Interface
    //The readValues method must read a 2 X 2 matrix and output solution to the
    //2 X 2 Matrix.  This Interface is used in conjunction with a solver object
    //to pass the solution back to the calling object.
    StringBuilder readValues();
}
