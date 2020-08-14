package solver;
/*
* 05 July 2020
* The aim of this FI is to be able to form a lambda expression to convert an array of type
* string values to an array of doubles.
 */
@FunctionalInterface
public interface MatrixConverter<T extends Number> {

    /*
    * This method signature is used to convert an array of string to and array of doubles.
    * The implementing code is provided by the user implementing this Functional Interface.
    * Use of Lambdas is encouraged.
     */
    T[] convertStringArrayToInt(String[] s);
}
