package solver;

import solver.EquationSolver.ComplexValue.ComplexNumber;
import solver.EquationSolver.LinearEquation;
import solver.EquationSolver.MultiVariableSolver;
import solver.IO.DataInputReader;
import solver.IO.DataOutputWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
* The code is used to work with both Real and Complex Values.
* The code uses Generic types to work with both real and complex values.  The main code has not changed
* from the purely Real Number Linear Equation cruncher.
* To adapt it to work with Complex Values, classes have type parameter <T extends Number> added to them.
* Complex Number class implements the Number class and as Double also implements the Number class we have
* a common upper bound on the type.
* The Strategy pattern has been used to fold the if else selection for Real or Complex paths as a way to
* choose which solution to take.
*
 */
public class Main {

    MatrixConverter mf, cnConverter;


    public static void main(String[] args) {
//        Scanner readIn = new Scanner(System.in);
//        String[] readLine = readIn.nextLine().split(" ");
//
//        //singleVarLE(readLine);      //Single variable Linear Equation
//
//        String[] readLine2 = readIn.nextLine().split(" ");
//
//        doubleVarLE(readLine, readLine2);
        Main solution = new Main();
        solution.init();
        solution.startSolving(args);
    }

    private void init() {
        //Lambda expression for converting string to double Or Complex Number matrix

        mf = element -> {
            int count = 0;
            Double[] values = new Double[element.length];
            for (String s : element) {
                values[count++] = Double.parseDouble(s);
            }
            return values;
        };

        //Convert String to a Complex Number Object
        cnConverter = element -> {
            int count = 0;
            String[] value = new String[] {"0","0"};
            ComplexNumber[] complexValues = new ComplexNumber[element.length];
            Pattern pattern = Pattern.compile("(\\S?\\d+\\.\\d+)|(\\S?\\d+)");
            for(String s : element) {
                //Is the complex number (a +/- b*i) -> real and complex part or  (+/- i) plain complex part only.
                s = s.matches("(\\S?\\d+(\\.\\d+)?[+-]i)|([-]?i)") ?
                        s.replace("i", "1i") :
                        s;
                Matcher matcher = pattern.matcher(s);
                //System.out.println("Group Count \u2192 " + matcher.groupCount());
                boolean isOnlyImaginary = s.matches("\\S?\\d+(\\.\\d+)?i"); //Does the string contain only +/-"i" value
                int index = 0;
                while (matcher.find()) {
                    if (isOnlyImaginary) {
                        value[++index] = matcher.group();
                        break;
                    }
                    value[index++] = matcher.group();
                }
                complexValues[count++] = new ComplexNumber(Double.parseDouble(value[0]), Double.parseDouble(value[1]));
                //Reset the Array
                value[0] = value[1] = "0";
            }
            return complexValues;
        };

    }
    /*
    * Starts the solution finding process.
    *
     */
    public void startSolving(String... files) {
        String local = "D:\\My Stuff\\Software Projects\\JetBrains-HyperSkill\\Projects\\Linear Equations Solver\\Linear Equations Solver\\task\\src\\solver\\";
        //String local = "";
        String line = "";
        StringBuilder result = new StringBuilder();

        try(DataInputReader dir = new DataInputReader(local + files[1])) {
            String[] readFirstLine = dir.readLine().split(" ");
            int numberOfVariables = Integer.parseInt(readFirstLine[0]);
            int numberOfEquations = Integer.parseInt(readFirstLine[1]);

            int rows = numberOfEquations;
            int columns = numberOfVariables + 1;

            String[][] readValuesFromFile = new String[rows][columns];
            Double[][] matrix = new Double[rows][columns];
            ComplexNumber[][] cnMatrix = new ComplexNumber[rows][columns];
            int row = 0;


            //Read the file and based on the contents make it either a Real Number Matrix or a Complex Number Matrix
            //This first read is done to see if we need a complex Number Matrix or a Real Number Matrix
            //If we have a matrix of all real values and the last value is a complex value, then we have a
            //complex number matrix.
            //The boolean value "isImaginaryValue" captures this and is used in the conversion process loop below.

            boolean isImaginaryValues = isImaginaryValues(dir, readValuesFromFile);

            //Convert all the string values to the proper types
            for (int count = 0; count < readValuesFromFile.length; count++ ) {
                String[] linesRead = readValuesFromFile[count];
                if (!isImaginaryValues) {
                    matrix[row] = (Double[]) mf.convertStringArrayToInt(linesRead);
                } else {
                    cnMatrix[row] = (ComplexNumber[]) cnConverter.convertStringArrayToInt(linesRead);
                }
                row++;
            }

            //Insert the Values into the
            MultiVariableSolver solver;
            if (isImaginaryValues) {
                solver = new MultiVariableSolver(cnMatrix, numberOfVariables, numberOfEquations);
            } else {
                solver = new MultiVariableSolver(matrix, numberOfVariables, numberOfEquations);
            }

            //create the solver object
            //LinearEquation les = new LinearEquation(new MultiVariableSolver(cnMatrix, numberOfVariables, numberOfEquations));
            LinearEquation les = new LinearEquation(solver);
            result = les.readValues();

            //Write data to file
            try(final DataOutputWriter dataOutputWriter = new DataOutputWriter(new FileWriter(new File(local + files[3])))) {
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
    * Tests if the Data read from file in.txt are Real Numbers or Complex Numbers.  The test is performed on the
    * entire data block just in case we have a data block with all real numbers and the last data element is a complex
    * type.  This would make the entire data structure complex.
    * @param dir - read the i/p data directory
    * @param readValuesFromFile -> the data structure containing the values to be tested.
    * @param -> isImaginaryValues -> Output from the method to indicate a Real Number or Complex Number Linear Equation.
     */
    private boolean isImaginaryValues(DataInputReader dir, String[][] readValuesFromFile) throws IOException {
        String line;
        int index = 0;
        boolean isImaginaryValues = false;
        while ((line = dir.readLine()) != null) {
            String[] s = line.split(" ");
            readValuesFromFile[index++] = s;
            if (Arrays.stream(s).anyMatch(imaginary -> imaginary.contains("i"))) {
                isImaginaryValues = true;
            }
        }
        return isImaginaryValues;
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
