import java.util.Scanner;

/*
* The Program is a fail fast checker for the pattern required
 */
public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner readIn = new Scanner(System.in);
        String[] colorMatrix = new String[4];
        int rowIndex = 0;
        int previousIndex = 0;
        boolean testFail = false;

        do {
            colorMatrix[rowIndex] = readIn.nextLine();
            if (rowIndex > 0) {
                for (int colIndex = 0; colIndex < colorMatrix.length - 1; colIndex++) {
//                    int topRowCharSum = colorMatrix[rowIndex].toLowerCase().charAt(colIndex) +
//                                          colorMatrix[rowIndex].toLowerCase().charAt(colIndex + 1);
//                    int bottomRowSum = colorMatrix[previousIndex].toLowerCase().charAt(colIndex) +
//                                          colorMatrix[previousIndex].toLowerCase().charAt(colIndex + 1);
                    String topRowSequence = colorMatrix[rowIndex].
                                            substring(colIndex, colIndex + 2).toLowerCase();
                    String bottomRowSequence = colorMatrix[previousIndex].
                                                substring(colIndex, colIndex + 2).toLowerCase();
                    if (topRowSequence.matches(bottomRowSequence)) {
                        testFail = true;
                        break;
                    }
                }
            }
            if (testFail) {
                break;
            }
            previousIndex = rowIndex++;
        } while (readIn.hasNext());
        System.out.println(testFail ? "NO" : "YES");
    }
}