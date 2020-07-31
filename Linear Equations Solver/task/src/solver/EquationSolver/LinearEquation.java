package solver.EquationSolver;

/*
* A Class to pass the linear Equation to the solver.
 */
public class LinearEquation implements Matrix {

    Solver les;

    public LinearEquation(Solver les) {
        this.les = les;
    }

    @Override
    public StringBuilder readValues(double[][] values) {
        return les.solution(values);
    }
}
