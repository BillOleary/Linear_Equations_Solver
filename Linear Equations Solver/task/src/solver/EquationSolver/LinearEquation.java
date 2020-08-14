package solver.EquationSolver;

/*
* A Class to pass the linear Equation to the solver.
 */
public class LinearEquation<T extends Number> implements Matrix {

    Solver les;

    public LinearEquation(Solver les) {
        this.les = les;
    }

    @Override
    public StringBuilder readValues() {
        return les.solution();
    }
}
