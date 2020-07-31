package solver.EquationSolver;
/*
* The Interface solver acts as a place holder for any implementation used to solve a Linear
* Equation.  It allows for a user to write his/her take on solving a Linear Equation
* and being able to place a call to the required API pass the Solver Object to the LinearEquation class
* to find its solution.
 */
public interface Solver {
    StringBuilder solution(double[][] values);
}
