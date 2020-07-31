import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        int row = readIn.nextInt();
        int col = readIn.nextInt();
        int[] matrix = new int[row * col];

        for (int index = 0; index < row * col; index++) {
            if (index % col == 0) {
                readIn.nextLine();
            }
            matrix[index] = readIn.nextInt();
        }

        int[] sortedMatrix = matrix.clone();
        Arrays.sort(sortedMatrix);

        int maxElement = sortedMatrix[sortedMatrix.length - 1];

        for (int index = 0; index < row * col; index++) {
            if (matrix[index] == maxElement) {
                row = index > col ? index / col : 0;
                col = index % col;
                break;
            }
        }

        System.out.println(row + " " + col);
    }
}