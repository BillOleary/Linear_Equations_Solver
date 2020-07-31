package solver;

import solver.EquationSolver.LinearEquation;
import solver.EquationSolver.MultiVariableSolver;
import solver.IO.DataInputReader;
import solver.IO.DataOutputWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        Scanner readIn = new Scanner(System.in);
//        String[] readLine = readIn.nextLine().split(" ");
//
//        //singleVarLE(readLine);      //Single variable Linear Equation
//
//        String[] readLine2 = readIn.nextLine().split(" ");
//
//        doubleVarLE(readLine, readLine2);
        String local = "D:\\My Stuff\\Software Projects\\JetBrains-HyperSkill\\Projects\\Linear Equations Solver\\Linear Equations Solver\\task\\src\\solver\\";
        //String local = "";
        String line = "";
        StringBuilder result = new StringBuilder();
        try(DataInputReader dir = new DataInputReader(local + args[1])) {
            String[] readFirstLine = dir.readLine().split(" ");
            int numberOfVariables = Integer.parseInt(readFirstLine[0]);
            int numberOfEquations = Integer.parseInt(readFirstLine[1]);

            int rows = numberOfEquations;
            int columns = numberOfVariables + 1;

            double[][] matrix = new double[rows][columns];
            int row = 0;

            //Lambda expression for converting string to double matrix
            MatrixConverter mf = element -> {
                int count = 0;
                double[] values = new double[element.length];
                for (String s : element) {
                    values[count++] = Double.parseDouble(s);
                }
                return values;
            };

            while ((line = dir.readLine()) != null) {
                String[] s = line.split(" ");
                matrix[row++] = mf.convertStringArrayToInt(s);
            }

            //create the solver object
            LinearEquation les = new LinearEquation(new MultiVariableSolver(matrix, numberOfVariables, numberOfEquations));
            result = les.readValues(matrix);

            //Write data to file
            try(final DataOutputWriter dataOutputWriter = new DataOutputWriter(new FileWriter(new File(local + args[3])))) {
                boolean writeSuccessful = dataOutputWriter.dataWrite(result.toString());
                System.out.println(writeSuccessful ? "Saved to file out.txt" : "Write to File Not Successful");

            }

        }
        catch (FileNotFoundException fnfXception) {
            System.out.println("File name/location is incorrect.");
        }
        catch (IOException ioXception) {
            System.out.println("File I/O Error!");
        }
        
    }


    /*
     * OLD STUFF FROM THE PREVIOUS SOLUTION TO A 2 ARGUMENT LINEAR MATRIX
     */

//    private static void singleVarLE(String[] readLine) {
//        System.out.println(Double.parseDouble(readLine[1]) / Double.parseDouble(readLine[0]));
//    }
//
//
//    private static void doubleVarLE(String[] readLine, String[] readLine2) {
//        double a = Double.parseDouble(readLine[0]);
//        double b = Double.parseDouble(readLine[1]);
//        double c = Double.parseDouble(readLine[2]);
//
//        double d = Double.parseDouble(readLine2[0]);
//        double e = Double.parseDouble(readLine2[1]);
//        double f = Double.parseDouble(readLine2[2]);
//
//        double y = (f - c * d / a) / (e - b * d / a);
//        double x = (c - b * y) / a;
//
//        System.out.println(x + " " + y);
//    }
}
