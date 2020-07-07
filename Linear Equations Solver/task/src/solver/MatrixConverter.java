package solver;
/*
* 05 July 2020
* The aim of this FI is to be able to form a lambda expression to convert an array of type
* sting values to an array of doubles.
 */
@FunctionalInterface
public interface MatrixConverter {

    double[] convertStringArrayToInt(String[] s);
}
